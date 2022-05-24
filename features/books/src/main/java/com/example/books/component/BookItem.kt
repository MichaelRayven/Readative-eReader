package com.example.books.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.component.Action
import com.example.component.OverflowMenu
import com.example.component.ShowAsAction
import com.example.model.dto.*
import com.example.model.local.util.BookFormat
import com.example.model.local.util.FileSize
import com.example.theme.R

@Composable
fun BookItemCard(
    book: BasicBookDto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { onClick() },
        elevation = 8.dp
    ) {
        Row(modifier = Modifier.height(dimensionResource(R.dimen.cover_height))) {
            BookCover(
                model = book.bookCover,
                modifier = Modifier.padding(8.dp, 8.dp, 12.dp, 8.dp)
            )
            Column(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                BookTitle(text = book.title)

                BookAuthorAndSeries(book.authors, book.series)
                BookFileMetadata(book.file)
                
                Spacer(modifier = Modifier.weight(1f))
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    progress = book.readingProgress
                )
//                Spacer(modifier = Modifier.height(8.dp))
                Spacer(modifier = Modifier.weight(1f))
                BookCardMenu(modifier = Modifier.align(Alignment.End))
            }
        }
    }
}

@Composable
fun BookCardMenu(
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        OverflowMenu(listOf(
            Action(
                "Mark as finished",
                Icons.Rounded.DoneAll,
                ShowAsAction.ALWAYS
            ) {/*TODO*/ },
            Action(
                "Mark as favourite",
                Icons.Rounded.Star,
                ShowAsAction.ALWAYS
            ) {/*TODO*/ },
            Action(
                "Add to a collection",
                Icons.Rounded.PlaylistAdd,
                ShowAsAction.ALWAYS
            ) {/*TODO*/ },
            Action(
                "Edit",
                Icons.Rounded.Edit,
                ShowAsAction.IF_ROOM
            ) {/*TODO*/ },
            Action(
                "Delete",
                Icons.Rounded.Delete,
                ShowAsAction.IF_ROOM
            ) {/*TODO*/ },
            Action(
                "Share",
                Icons.Rounded.Share,
                ShowAsAction.IF_ROOM
            ) {/*TODO*/ }
        ),
            modifier,
            Arrangement.spacedBy(16.dp)
        )
    }
}

@Composable
fun BookTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.subtitle1
    )
}

@Composable
fun BookAuthorAndSeries(
    authors: List<BasicAuthorDto>,
    series: List<SeriesDto>,
    modifier: Modifier = Modifier
) {
    val authorAndSeries = StringBuilder()

    authors.forEach { item ->
        authorAndSeries.append(item.shortenedFullName + ", ")
    }

    series.forEach { item ->
        authorAndSeries.append((
                item.name + if (item.part != null) " ${item.part}" else ""
                ).trimEnd() + ", ")
    }

    BookMetadata(
        text = authorAndSeries.substring(
            0,
            (authorAndSeries.length - 2)
            .coerceAtLeast(0)
        ).toString(),
        modifier = modifier
    )
}

@Composable
fun BookFileMetadata(
    file: BasicBookFileDto?,
    modifier: Modifier = Modifier
) {
    if (file != null) {
        BookMetadata(text = "${file.bookFormat.bookFormat}, ${file.fileSize}", modifier = modifier)
    }
}

@Composable
fun BookMetadata(
    text: String,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            modifier = modifier,
            text = text,
            maxLines = 1,
            style = MaterialTheme.typography.body2.copy(
                fontSize = 12.sp
            )
        )
    }
}

@Composable
fun BookCover(
    model: Any?,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp
    ) {
        AsyncImage(
            modifier = Modifier
                .size(
                    width = dimensionResource(R.dimen.cover_width),
                    height = dimensionResource(R.dimen.cover_height)
                ),
            model = model,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            contentDescription = stringResource(R.string.cover_description),
            placeholder = painterResource(R.drawable.placeholder)
        )
    }
}