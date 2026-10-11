package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.model.ScreenSection
import com.example.ui.theme.AmberAlert
import com.example.ui.theme.Slate600
import com.example.ui.theme.TurquoisePrimary
import com.example.ui.theme.TurquoiseTeal
import com.example.viewmodel.ZukiViewModel

@Composable
fun SoftwareRepairScreen(
  viewModel: ZukiViewModel,
  onOpenLesson: (String) -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }
  val bookmarkedIds by viewModel.bookmarkedIds.collectAsState()

  val swLessons = remember(searchQuery) {
    val list = SampleData.lessons.filter { it.section == ScreenSection.SOFTWARE }
    if (searchQuery.isBlank()) list
    else list.filter {
      it.title.contains(searchQuery, ignoreCase = true) ||
          it.englishTitle.contains(searchQuery, ignoreCase = true)
    }
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
    contentPadding = PaddingValues(vertical = 16.dp)
  ) {
    item {
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
          text = "মোবাইল সফটওয়্যার ও ফার্মওয়্যার প্রশিক্ষণ",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onBackground
        )
        Text(
          text = "বুটলুপ, স্টক রম ফ্ল্যাশিং, ইডিএল মোড ৯০০৮ এবং সিস্টেম পুনরুদ্ধার।",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("সফটওয়্যার পাঠ খুঁজুন (ফ্ল্যাশিং, বুটলুপ, ওডিন)...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TurquoisePrimary) },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("sw_search_input"),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
      )
    }

    items(swLessons) { lesson ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onOpenLesson(lesson.id) }
          .testTag("sw_lesson_item_${lesson.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = lesson.title,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = lesson.englishTitle,
              fontSize = 12.sp,
              color = Slate600
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Surface(
                color = TurquoiseTeal.copy(alpha = 0.15f),
                shape = RoundedCornerShape(4.dp)
              ) {
                Text(
                  text = lesson.difficulty.labelBengali,
                  fontSize = 10.sp,
                  color = TurquoiseTeal,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
              Surface(
                color = TurquoisePrimary.copy(alpha = 0.15f),
                shape = RoundedCornerShape(4.dp)
              ) {
                Text(
                  text = "${lesson.estimatedMinutes} মিনিট",
                  fontSize = 10.sp,
                  color = TurquoisePrimary,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
          }

          IconButton(onClick = { viewModel.toggleBookmark(lesson.id) }) {
            Icon(
              imageVector = if (bookmarkedIds.contains(lesson.id)) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = "বুকমার্ক",
              tint = if (bookmarkedIds.contains(lesson.id)) AmberAlert else Slate600
            )
          }
        }
      }
    }
  }
}
