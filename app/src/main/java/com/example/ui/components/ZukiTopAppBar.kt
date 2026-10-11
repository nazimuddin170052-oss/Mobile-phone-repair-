package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ScreenSection
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.TurquoiseBright
import com.example.ui.theme.TurquoisePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZukiTopAppBar(
  currentSection: ScreenSection,
  onBackClick: () -> Unit,
  onSearchClick: () -> Unit,
  onBookmarkClick: () -> Unit
) {
  val isRootScreen = currentSection == ScreenSection.HOME

  TopAppBar(
    title = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Image(
          painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.ic_zuki_app_icon),
          contentDescription = "ZUKI লোগো",
          modifier = Modifier
            .size(36.dp)
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
        )

        Column {
          Text(
            text = if (isRootScreen) "ZUKI মোবাইল সার্ভিসিং" else currentSection.titleBengali,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = if (isRootScreen) "আধুনিক সার্ভিসিং একাডেমি" else currentSection.titleEnglish,
            style = MaterialTheme.typography.labelSmall,
            color = TurquoiseBright
          )
        }
      }
    },
    navigationIcon = {
      if (!isRootScreen) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier.testTag("app_bar_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "ফিরে যান (Back)"
          )
        }
      }
    },
    actions = {
      IconButton(
        onClick = onSearchClick,
        modifier = Modifier.testTag("app_bar_search_button")
      ) {
        Icon(
          imageVector = Icons.Default.Search,
          contentDescription = "অনুসন্ধান (Search)"
        )
      }
      IconButton(
        onClick = onBookmarkClick,
        modifier = Modifier.testTag("app_bar_bookmarks_button")
      ) {
        Icon(
          imageVector = Icons.Default.Bookmark,
          contentDescription = "বুকমার্ক (Bookmarks)"
        )
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = Navy800,
      titleContentColor = Color.White,
      navigationIconContentColor = Color.White,
      actionIconContentColor = Color.White
    )
  )
}
