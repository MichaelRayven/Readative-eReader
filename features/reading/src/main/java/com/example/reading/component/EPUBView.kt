package com.example.reading.component

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.speech.tts.TextToSpeech
import android.webkit.WebView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.example.reader.ReadativeBookContent
import com.example.reading.ReadingUiState
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import kotlinx.coroutines.launch
import kotlin.math.round
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun EPUBView(
    modifier: Modifier = Modifier,
    source: ReadativeBookContent.EPUBContents,
    uiState: ReadingUiState
) {
    val webViewState =
        rememberWebViewStateWithHTMLData(data = source.contents, baseUrl = source.baseUrl)
    var webView: WebView? by remember { mutableStateOf(null) }

    var offset by remember { mutableStateOf(0f) }
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(webViewState) {
        snapshotFlow { offset }.collect {
            if (uiState.horizontalView.value) {
                webView?.scrollX = it.roundToInt()
            } else {
                webView?.scrollY = it.roundToInt()
            }

            val page = webView?.currentPage(uiState.horizontalView.value) ?: return@collect
            if (page != uiState.readingProgress.value.currentPage - 1) {
                uiState.readingProgress.value =
                    uiState.readingProgress.value.copy(currentPage = page + 1)
            }
        }
    }

    LaunchedEffect(webViewState) {
        snapshotFlow { uiState.readingProgress.value.currentPage }.collect { newPage ->
            val page = webView?.currentPage(uiState.horizontalView.value) ?: return@collect
            if (page != newPage - 1) {
                webView?.scrollToPage(uiState.horizontalView.value, { offset = it }, newPage - 1)
            }
        }
    }

    LaunchedEffect(webViewState) {
        snapshotFlow { uiState.horizontalView.value }.collect { horizontal ->
            // Apply styles depending on rotation
            if (horizontal) {
                val addPaging = """
                        html.style.setProperty('padding', '0px', 'important');
                        html.style.setProperty('height', html.clientHeight + 'px', 'important'); 
                        html.style.setProperty('-webkit-column-gap', '0px', 'important');
                        html.style.setProperty('-webkit-column-count', pageCount, 'important');
                        html.style.setProperty('-webkit-column-width', ${webView?.measuredWidth} + 'px', 'important');
                        html.style.setProperty('word-break', 'break-all', 'important');
                        """

                webView?.evaluateJavascript(addPaging, null)
            } else {
                val removePaging = """
                        html.style.setProperty('padding', '5px', 'important');
                        html.style.setProperty('height', 'initial', 'important'); 
                        html.style.setProperty('-webkit-column-gap', '0px', 'important');
                        html.style.setProperty('-webkit-column-count', 0, 'important');
                        html.style.setProperty('-webkit-column-width', ${webView?.measuredWidth} + 'px', 'important');
                        """

                webView?.evaluateJavascript(removePaging, null)
            }
            webView?.scrollToPage(uiState.horizontalView.value, { offset = it }, uiState.readingProgress.value.currentPage - 1)
        }
    }

    LaunchedEffect(webViewState) {
        snapshotFlow { uiState.playTTS.value }.collect { playTTS ->
            if (playTTS) {
                if (uiState.job == null) {
                    webView?.let { getTextContents(it, uiState) }
                    uiState.startTTS()
                } else {
                    uiState.resumeTTS()
                }
            } else {
                uiState.pauseTTS()
            }
        }
    }

    WebView(
        modifier = modifier,
        state = webViewState,
        onCreated = {
            webView = it

            // Initial WebView settings
            it.settings.javaScriptEnabled = true
            it.settings.allowFileAccess = true
            it.isVerticalScrollBarEnabled = false
            it.isHorizontalScrollBarEnabled = false
        },
        client = PagingWebViewClient(
            uiState = uiState
        )
    )

    if (uiState.horizontalView.value) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    uiState.showUi.value = !uiState.showUi.value
                }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        if (webView?.canScrollHorizontally((-delta).toInt()) == true) {
                            offset -= delta
                        }
                    },
                    onDragStopped = {
                        val page = webView?.currentPage(true) ?: return@draggable
                        webView?.smoothScrollToPage(true, { offset = it }, page)
                    }
                )
        ) {}
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    webView?.performClick()
                    uiState.showUi.value = !uiState.showUi.value
                }
                .scrollable(
                    orientation = Orientation.Vertical,
                    state = rememberScrollableState { delta ->
                        if (webView?.canScrollVertically((-delta).toInt()) == true) {
                            offset -= delta
                        }

                        delta
                    }
                )
        ) {}
    }
}

private class PagingWebViewClient(
    val uiState: ReadingUiState
) : AccompanistWebViewClient() {
    override fun onPageFinished(view: WebView?, url: String?) {
        view?.settings?.javaScriptEnabled
        val init = """var html = document.querySelector('html');
                var pageCount = Math.floor(html.offsetHeight / window.innerHeight) + 1;"""
        view?.evaluateJavascript(init, null)
        view?.evaluateJavascript("(function(){return pageCount})()") {
            val pages = it.toInt()
            uiState.readingProgress.value =
                uiState.readingProgress.value.copy(pageCount = pages)
        }

        super.onPageFinished(view, url)
    }
}

fun getTextContents(
    webView: WebView,
    uiState: ReadingUiState
) {
    webView.evaluateJavascript(
        "(function() {\n" +
                "    let arr = []\n" +
                "    for(let child of Array.from(document.body.children)) {\n" +
                "        for(let innerChild of Array.from(child.children)) {\n" +
                "            arr.push(innerChild.textContent)\n" +
                "        }\n" +
                "    }\n" +
                "    return arr.join(\" \")\n" +
                "})()"
    ) { result ->
        uiState.job = uiState.coroutineScope.launch {
            val cleanText = result.replace(Regex("\\\\n\\s*"), " ")
            uiState.addToTTSQueue(cleanText)
        }
    }
}

fun WebView.currentPage(horizontal: Boolean): Int {
    return (if (horizontal) {
        this.scrollX / this.width.toFloat()
    } else {
        this.scrollY / this.height.toFloat()
    }).roundToInt()
}

fun WebView.scrollToPage(
    horizontal: Boolean,
    sync: (Float) -> Unit,
    pageInd: Int
) {
    if (horizontal) {
        this.scrollTo((this.width + 2) * pageInd, 0)
        sync(this.scrollX.toFloat())
    } else {
        this.scrollTo(0, this.height * pageInd)
        sync(this.scrollY.toFloat())
    }
}

fun WebView.smoothScrollToPage(
    horizontal: Boolean,
    sync: (Float) -> Unit,
    pageInd: Int
) {
    if (horizontal) {
        val newScrollX = ((this.width + 2) * pageInd)
        this.scrollY = 0
        val anim = ObjectAnimator.ofInt(
            this,
            "scrollX",
            this.scrollX,
            newScrollX
        )
        anim.setDuration(100).start()
        sync(newScrollX.toFloat())
    } else {
        val newScrollY = this.height * pageInd
        this.scrollX = 0
        val anim = ObjectAnimator.ofInt(
            this,
            "scrollY",
            this.scrollY,
            newScrollY
        )
        anim.setDuration(100).start()
        sync(newScrollY.toFloat())
    }
}
