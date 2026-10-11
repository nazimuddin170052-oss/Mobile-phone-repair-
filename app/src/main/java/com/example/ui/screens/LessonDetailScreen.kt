package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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
import com.example.model.Lesson
import com.example.ui.components.SafetyWarningCard
import com.example.ui.theme.*
import com.example.viewmodel.ZukiViewModel

@Composable
fun LessonDetailScreen(
  viewModel: ZukiViewModel,
  lessonId: String?,
  onBack: () -> Unit
) {
  val lesson = SampleData.lessons.find { it.id == lessonId } ?: SampleData.lessons.first()
  val bookmarkedIds by viewModel.bookmarkedIds.collectAsState()
  val completedIds by viewModel.completedIds.collectAsState()

  val isBookmarked = bookmarkedIds.contains(lesson.id)
  val isCompleted = completedIds.contains(lesson.id)

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(vertical = 16.dp)
  ) {
    // Top Bar Actions
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(onClick = onBack, modifier = Modifier.testTag("lesson_back_button")) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ফিরে যান")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          IconButton(onClick = { viewModel.toggleBookmark(lesson.id) }) {
            Icon(
              imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = "বুকমার্ক",
              tint = if (isBookmarked) AmberAlert else MaterialTheme.colorScheme.onSurface
            )
          }
          IconButton(onClick = { viewModel.toggleCompleted(lesson.id) }) {
            Icon(
              imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.CheckCircleOutline,
              contentDescription = "সম্পন্ন",
              tint = if (isCompleted) EmeraldSuccess else MaterialTheme.colorScheme.onSurface
            )
          }
        }
      }
    }

    // Header Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Navy800)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(
            text = lesson.title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
          )
          Text(
            text = lesson.englishTitle,
            fontSize = 13.sp,
            color = TurquoiseBright
          )
          Spacer(modifier = Modifier.height(4.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Surface(
              color = TurquoiseContainer,
              shape = RoundedCornerShape(6.dp)
            ) {
              Text(
                text = "আনুমানিক সময়: ${lesson.estimatedMinutes} মিনিট",
                fontSize = 11.sp,
                color = Navy900,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
            Surface(
              color = TurquoiseTeal.copy(alpha = 0.2f),
              shape = RoundedCornerShape(6.dp)
            ) {
              Text(
                text = "স্তর: ${lesson.difficulty.labelBengali}",
                fontSize = 11.sp,
                color = TurquoiseTeal,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }
      }
    }

    // Short Summary
    item {
      Text(
        text = lesson.shortDescription,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = MaterialTheme.colorScheme.onSurface
      )
    }

    // Safety Tips First
    item {
      SafetyWarningCard(
        title = "গুরুত্বপূর্ণ নিরাপত্তা নির্দেশনা",
        warningText = lesson.safetyTips.joinToString(" ")
      )
    }

    // Symptoms Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(
            text = "সমস্যার প্রাথমিক লক্ষণসমূহ:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
          lesson.symptoms.forEach { symptom ->
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Text(text = "•", color = TurquoisePrimary, fontWeight = FontWeight.Bold)
              Text(text = symptom, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        }
      }
    }

    // Required Tools
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(
            text = "প্রয়োজনীয় যন্ত্রপাতি ও টুলস:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
          lesson.toolsNeeded.forEach { tool ->
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Icon(Icons.Default.Build, contentDescription = null, tint = TurquoisePrimary, modifier = Modifier.size(16.dp))
              Text(text = tool, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        }
      }
    }

    // Step by step repair flow
    item {
      Text(
        text = "ধাপে ধাপে মেরামত প্রক্রিয়া:",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onBackground
      )
    }

    items(lesson.steps) { step ->
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(
              modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(TurquoisePrimary),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "${step.stepNumber}",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = Navy900
              )
            }
            Text(
              text = step.stepTitle,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          }

          Text(
            text = step.instruction,
            fontSize = 13.sp,
            lineHeight = 19.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          if (step.importantWarning != null) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(AmberAlert.copy(alpha = 0.12f))
                .padding(8.dp)
            ) {
              Text(
                text = "সতর্কতা: ${step.importantWarning}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
              )
            }
          }
        }
      }
    }

    // Testing and Verification
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TurquoiseContainer)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Text(
            text = "চূড়ান্ত যাচাইকরণ ও টেস্টিং পদ্ধতি:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Navy900
          )
          Text(
            text = lesson.testingMethod,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = Navy800
          )
        }
      }
    }

    // Completion Button
    item {
      Button(
        onClick = { viewModel.toggleCompleted(lesson.id) },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("mark_lesson_complete_button"),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = if (isCompleted) Slate600 else TurquoisePrimary,
          contentColor = if (isCompleted) Color.White else Navy900
        )
      ) {
        Icon(
          imageVector = if (isCompleted) Icons.Default.Check else Icons.Default.DoneAll,
          contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = if (isCompleted) "পাঠটি সম্পন্ন হয়েছে (Completed)" else "পাঠ সম্পন্ন হিসেবে মার্ক করুন",
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}
