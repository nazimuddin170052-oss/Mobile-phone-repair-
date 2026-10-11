package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.data.QuizData
import com.example.ui.theme.*
import com.example.viewmodel.ZukiViewModel

@Composable
fun QuizScreen(viewModel: ZukiViewModel) {
  val selectedAnswers by viewModel.quizSelectedAnswers.collectAsState()
  val isSubmitted by viewModel.isQuizSubmitted.collectAsState()

  val questions = QuizData.quizQuestions
  val totalQuestions = questions.size

  // Calculate score
  val correctCount = questions.count { q ->
    selectedAnswers[q.id] == q.correctOptionIndex
  }
  val percentage = if (totalQuestions > 0) (correctCount * 100) / totalQuestions else 0

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(vertical = 16.dp)
  ) {
    // Header & Summary
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
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "বাংলা সার্ভিসিং মূল্যায়ন কুইজ",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
              )
              Text(
                text = "মোট প্রশ্ন: ২০টি | বিষয়ভিত্তিক মূল্যায়ন",
                fontSize = 12.sp,
                color = TurquoiseBright
              )
            }

            if (isSubmitted) {
              Surface(
                color = if (percentage >= 60) EmeraldSuccess else AmberAlert,
                shape = RoundedCornerShape(12.dp)
              ) {
                Text(
                  text = "$percentage%",
                  fontWeight = FontWeight.Black,
                  fontSize = 16.sp,
                  color = Navy900,
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
              }
            }
          }

          if (isSubmitted) {
            Text(
              text = "আপনার স্কোর: $correctCount / $totalQuestions টি সঠিক হয়েছে",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = Color.White
            )
            Text(
              text = if (percentage >= 80) "অসাধারণ! আপনি মোবাইল সার্ভিসিংয়ে অত্যন্ত দক্ষ।"
              else if (percentage >= 60) "ভালো হয়েছে! পাঠগুলো পুনরায় পর্যালোচনা করুন।"
              else "আরও অনুশীলনের প্রয়োজন। হার্ডওয়্যার ও মাল্টিমিটার অধ্যায় পড়ুন।",
              fontSize = 12.sp,
              color = Slate300
            )
          } else {
            Text(
              text = "উত্তর দেওয়া হয়েছে: ${selectedAnswers.size} / $totalQuestions টি",
              fontSize = 13.sp,
              color = Slate300
            )
          }
        }
      }
    }

    // Questions List
    itemsIndexed(questions) { index, q ->
      val userSelectedOption = selectedAnswers[q.id]

      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("quiz_question_card_${q.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          // Question Header
          Row(
            verticalAlignment = Alignment.Top,
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
                text = "${index + 1}",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = Navy900
              )
            }

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = q.questionBengali,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = q.category,
                fontSize = 11.sp,
                color = TurquoisePrimary
              )
            }
          }

          // 4 Options
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            q.options.forEachIndexed { optIndex, optText ->
              val isThisOptionSelected = userSelectedOption == optIndex
              val isCorrectOption = q.correctOptionIndex == optIndex

              val optionBackground = when {
                !isSubmitted && isThisOptionSelected -> TurquoiseContainer
                isSubmitted && isCorrectOption -> EmeraldSuccess.copy(alpha = 0.2f)
                isSubmitted && isThisOptionSelected && !isCorrectOption -> CrimsonDanger.copy(alpha = 0.2f)
                else -> MaterialTheme.colorScheme.surfaceVariant
              }

              val optionBorderColor = when {
                !isSubmitted && isThisOptionSelected -> TurquoisePrimary
                isSubmitted && isCorrectOption -> EmeraldSuccess
                isSubmitted && isThisOptionSelected && !isCorrectOption -> CrimsonDanger
                else -> Color.Transparent
              }

              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(10.dp))
                  .background(optionBackground)
                  .border(1.dp, optionBorderColor, RoundedCornerShape(10.dp))
                  .clickable(enabled = !isSubmitted) {
                    viewModel.selectQuizAnswer(q.id, optIndex)
                  }
                  .padding(12.dp)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                  Text(
                    text = "${('ক' + optIndex)})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                  Text(
                    text = optText,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                  )

                  if (isSubmitted) {
                    if (isCorrectOption) {
                      Icon(Icons.Default.CheckCircle, contentDescription = "সঠিক", tint = EmeraldSuccess, modifier = Modifier.size(18.dp))
                    } else if (isThisOptionSelected) {
                      Icon(Icons.Default.Cancel, contentDescription = "ভুল", tint = CrimsonDanger, modifier = Modifier.size(18.dp))
                    }
                  }
                }
              }
            }
          }

          // Detailed Explanation (shown after submission)
          if (isSubmitted) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(10.dp)
            ) {
              Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                  text = "ব্যাখ্যা ও বিশ্লেষণ:",
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = TurquoisePrimary
                )
                Text(
                  text = q.explanationBengali,
                  fontSize = 12.sp,
                  lineHeight = 17.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      }
    }

    // Submit / Reset Button
    item {
      if (!isSubmitted) {
        Button(
          onClick = { viewModel.submitQuiz() },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("submit_quiz_button"),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = TurquoisePrimary,
            contentColor = Navy900
          )
        ) {
          Text(text = "পরীক্ষা জমা দিন (Submit Exam)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
      } else {
        Button(
          onClick = { viewModel.resetQuiz() },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("reset_quiz_button"),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = TurquoisePrimary,
            contentColor = Navy900
          )
        ) {
          Icon(Icons.Default.Refresh, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "পুনরায় পরীক্ষা দিন", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
      }
    }
  }
}
