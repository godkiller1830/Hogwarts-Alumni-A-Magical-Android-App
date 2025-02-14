package com.kenstarry.harrypotter.feature_home.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.kenstarry.harrypotter.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onSearch: () -> Unit,
    onMore: () -> Unit
) {

    MediumTopAppBar(
        title = {
            Text(
                text = "Hogwarts",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.ExtraBold
            )
        },

        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.secondary
        ),

        actions = {
            IconButton(onClick = onSearch) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search icon"
                )
            }
            IconButton(onClick = onMore) {
                Icon(
                    painter = painterResource(id = R.drawable.ai_icon),
                    contentDescription = "More icon",
                    tint = androidx.compose.ui.graphics.Color.Unspecified
                )
            }
        }
    )
}