package com.example.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.House
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theme.R

@Composable
fun OverflowMenu(
    items: List<Action>,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.End
) {
    // Sort items
    val actions = ArrayList<Action>()
    val overflow = ArrayList<Action>()

    for(item in items) {
        when(item.showAsAction) {
            ShowAsAction.ALWAYS -> {
                actions.add(item)
            }
            ShowAsAction.IF_ROOM -> {
                actions.add(item)
            }
            ShowAsAction.NEVER -> {
                overflow.add(item)
            }
            ShowAsAction.HIDE -> { /*IGNORE*/ }
        }
    }

    var needsOverflow = overflow.size > 0
    var isFirstOverflow = overflow.size == 0

    BoxWithConstraints(
        modifier = modifier
    ) {
        var width = actions.size * 48
        val rowMaxWidth = maxWidth.value

        if(!isFirstOverflow) width += 48

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = horizontalArrangement
        ) {
            actions.forEach { action ->
                if (width <= rowMaxWidth || action.showAsAction == ShowAsAction.ALWAYS) {
                    OverflowMenuAction(action = action)
                } else {
                    needsOverflow = true
                    overflow.add(action)

                    if (isFirstOverflow) {
                        isFirstOverflow = false
                    } else {
                        // Free up icon width of space
                        width -= 48
                    }
                }
            }

            if (needsOverflow) {
                OverflowMenuDropdown(items = overflow)
            }
        }
    }
}

@Composable
fun OverflowMenuDropdown(
    items: List<Action>
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.size(48.dp)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = if(expanded)
                    stringResource(R.string.hide_dropdown) else
                        stringResource(R.string.show_dropdown))
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { item ->
                DropdownMenuItem(onClick = item.onClick) {
                    Text(item.text)
                }
            }
        }
    }
}

@Composable
fun OverflowMenuAction(
    action: Action
) {
    IconButton(
        modifier = Modifier.size(48.dp),
        onClick = action.onClick
    ) {
        Icon(imageVector = action.icon, contentDescription = action.text)
    }
}


// Whether an item should be shown as an action or overflow into the dropdown menu
// Actions flagged with hide are not rendered
enum class ShowAsAction {
    ALWAYS, IF_ROOM, NEVER, HIDE
}

data class Action(
    val text: String,
    val icon: ImageVector,
    val showAsAction: ShowAsAction = ShowAsAction.IF_ROOM,
    val onClick: () -> Unit,
)