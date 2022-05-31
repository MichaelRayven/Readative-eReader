package com.example.reading.component

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.reader.ReadativeBookContent
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import java.io.IOException
import java.io.StringReader
import kotlin.math.roundToInt


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun EPUBView(
    modifier: Modifier = Modifier,
    source: ReadativeBookContent.EPUBContents,
    currentPage: MutableState<Int>,
    pageCount: MutableState<Int>,
    collectText: Boolean,
    textInput: MutableState<String>,
    horizontal: Boolean = false,
) {
    val state = rememberWebViewStateWithHTMLData(data = source.contents, baseUrl = source.baseUrl)
    var prevX by remember { mutableStateOf(0f) }
    var canCollectText by remember { mutableStateOf(true) }
    var webView: WebView? by remember { mutableStateOf(null) }

    if (collectText && canCollectText) {
        canCollectText = false
        getTextContents(webView, textInput)
    }

    WebView(
        modifier = modifier,
        state = state,
        onCreated = {
            webView = it

            it.settings.javaScriptEnabled = true
            it.settings.allowFileAccess = true
            it.isVerticalScrollBarEnabled = false
            it.isHorizontalScrollBarEnabled = false

            if (horizontal) {
                it.setOnTouchListener { view, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            view.performClick()
                            prevX = event.x
                        }
                        MotionEvent.ACTION_MOVE -> {
                            val deltaX = prevX - event.x
                            view.scrollX += deltaX.toInt()
                            prevX = event.x
                        }
                        MotionEvent.ACTION_UP -> {
                            val newPage = (view.scrollX.toFloat() / view.measuredWidth.toFloat())
                                .roundToInt()
                            if (newPage != currentPage.value) {
                                currentPage.value = newPage + 1
                            }
                            val newScrollX = (newPage) * view.measuredWidth
                            val anim = ObjectAnimator.ofInt(
                                view,
                                "scrollX",
                                view.scrollX,
                                newScrollX
                            )
                            anim.setDuration(100).start()
                        }
                    }
                    true
                }
            } else {
                it.setOnScrollChangeListener { view: View, _: Int, scrollY: Int, _: Int, _: Int ->
                    val newPage = (scrollY.toFloat() / view.measuredHeight.toFloat())
                        .roundToInt()
                    if (newPage != currentPage.value - 1) {
                        currentPage.value = newPage + 1
                    }
                }
            }
        },
        client = PagingWebViewClient(currentPage, pageCount, horizontal)
    )
}

private class PagingWebViewClient(
    val page: MutableState<Int>,
    val count: MutableState<Int>,
    val horizontal: Boolean
) : AccompanistWebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val prevUrl = view?.url
        val currentUrl = request?.url
        Log.d("Test", prevUrl.toString())
        Log.d("Test", currentUrl.toString())
        return true
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        view?.settings?.javaScriptEnabled
        val init = "var html = document.querySelector('html');" +
                "var height = window.innerHeight;" +
                "var width = window.innerWidth;" +
                "html.style.padding = '5px';" +
                "var pageCount = Math.floor(html.offsetHeight/height) + 1;"
        view?.evaluateJavascript(init, null)
        view?.evaluateJavascript("(function(){return pageCount})()") {
            val reader = JsonReader(StringReader(it))
            reader.isLenient = true
            try {
                if (reader.peek() != JsonToken.NULL) {
                    if (reader.peek() == JsonToken.NUMBER) {
                        val data = reader.nextInt()
                        count.value = data
                    }
                }
            } catch (e: IOException) {
                Log.e("WebView", "PagingWebViewClient: IOException ${e.stackTrace}")
            }
        }

        if (horizontal) {
            val addPaging = "html.style.webkitColumnCount = pageCount;" +
                    "html.style.webkitColumnGap = '0px';" +
                    "html.style.width = pageCount*width + 'px'"

            view?.evaluateJavascript(addPaging, null)
            view?.scrollX = view?.measuredWidth ?: 0 * (page.value - 1)
        } else {
            view?.scrollY = view?.measuredHeight ?: 0 * (page.value - 1)
        }

        super.onPageFinished(view, url)
    }
}

//fun WebView.getCurrentPage(
//    horizontal: Boolean
//): Int {
//    return if (horizontal) {
//        (this.scrollX / this.measuredWidth) + 1
//    } else {
//        (this.scrollY / this.measuredHeight) + 1
//    }
//}

fun getTextContents(
    view: WebView?,
    textInput: MutableState<String>
) {
        view?.evaluateJavascript(
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
            textInput.value = result.replace(Regex("\\\\n\\s*"), " ")
        }
}
