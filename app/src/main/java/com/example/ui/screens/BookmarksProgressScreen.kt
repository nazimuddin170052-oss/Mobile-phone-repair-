package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.data.TroubleshootingData
import com.example.ui.theme.*
import com.example.viewmodel.ZukiViewModel

@Composable
fun BookmarksProgressScreen(
  viewModel: ZukiViewModel,
  onOpenLesson: (String) -> Unit
) {
  val bookmarkedIds by viewModel.bookmarkedIds.collectAsState()
  val completedIds by viewModel.completedIds.collectAsState()

  val totalLessons = SampleData.lessons.size
  val completedLessonsCount = completedIds.size
  val progressPercent = if (totalLessons > 0) (completedLessonsCount * 100) / totalLessons else 0

  val bookmarkedLessons = SampleData.lessons.filter { bookmarkedIds.contains(it.id) }
  val bookmarkedIssues = TroubleshootingData.issuesList.filter { bookmarkedIds.contains(it.id) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(vertical = 16.dp)
  ) {
    // 1. Progress Header Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Navy800)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "আমার শেখার অগ্রগতি",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
              )
              Text(
                text = "ZUKI টেকনিশিয়ান প্রোফাইল",
                fontSize = 12.sp,
                color = TurquoiseBright
              )
            }

            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(TurquoisePrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.School, contentDescription = null, tint = Navy900)
            }
          }

          // Progress Bar
          LinearProgressIndicator(
            progress = { progressPercent / 100f },
            modifier = Modifier
              .fillMaxWidth()
              .height(10.dp)
              .clip(RoundedCornerShape(5.dp)),
            color = TurquoiseBright,
            trackColor = Navy600
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "$completedLessonsCount / $totalLessons টি পাঠ সম্পন্ন",
              fontSize = 13.sp,
              color = Color.White
            )
            Text(
              text = "$progressPercent% পূর্ণ",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = TurquoiseBright
            )
          }

          // Current Rank Badge
          Surface(
            color = TurquoiseContainer,
            shape = RoundedCornerShape(8.dp)
          ) {
            val rankTitle = when {
              progressPercent >= 80 -> "লেভেল: মাস্টার টেকনিশিয়ান"
              progressPercent >= 40 -> "লেভেল: সিনিয়র টেকনিশিয়ান"
              progressPercent >= 10 -> "লেভেল: জুনিয়র সার্ভিসিং ইঞ্জিনিয়ার"
              else -> "লেভেল: শিক্ষানবিস টেকনিশিয়ান"
            }
            Text(
              text = rankTitle,
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              color = Navy900,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
          }
        }
      }
    }

    // 2. Bookmarked Lessons Header
    item {
      Text(
        text = "বুকমার্ক করা পাঠসমূহ (${bookmarkedLessons.size} টি)",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
      )
    }

    if (bookmarkedLessons.isEmpty() && bookmarkedIssues.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(Icons.Default.Bookmark, contentDescription = null, tint = Slate600, modifier = Modifier.size(36.dp))
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = "কোনো বুকমার্ক সংরক্ষিত নেই",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "যেকোনো পাঠ বা সমস্যার বুকমার্ক আইকনে ক্লিক করে এখানে যুক্ত করুন।",
                fontSize = 12.sp,
                color = Slate600
              )
            }
          }
        }
      }
    }

    items(bookmarkedLessons) { lesson ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onOpenLesson(lesson.id) }
          .testTag("bookmarked_lesson_${lesson.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
              fontSize = 14.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(text = lesson.englishTitle, fontSize = 11.sp, color = Slate600)
          }

          IconButton(onClick = { viewModel.toggleBookmark(lesson.id) }) {
            Icon(
              imageVector = Icons.Default.BookmarkRemove,
              contentDescription = "বুকমার্ক মুছুন",
              tint = AmberAlert
            )
          }
        }
      }
    }
  }
}
