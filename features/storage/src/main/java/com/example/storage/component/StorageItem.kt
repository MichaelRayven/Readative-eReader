package com.example.storage.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.dto.*

@Composable
fun StorageItemCard(
    file: StorageFileDto,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector =
                when(file) {
                    is ArchiveDto -> {
                        Icons.Filled.Archive
                    }
                    is FolderDto -> {
                        Icons.Filled.Folder
                    }
                    is StorageDestinationDto -> {
                        Icons.Filled.SdCard
                    }
                    else -> {
                        Icons.Filled.Folder
                    }
                },
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = file.name,
                maxLines = 1,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = if(file.bookCount != 0) file.bookCount.toString() else "")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.NavigateNext,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}