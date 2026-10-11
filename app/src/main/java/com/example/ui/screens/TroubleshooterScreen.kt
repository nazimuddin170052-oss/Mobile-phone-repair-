package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.data.TroubleshootingData
import com.example.model.DiagnosticStep
import com.example.model.TroubleIssue
import com.example.ui.components.SafetyWarningCard
import com.example.ui.theme.*
import com.example.viewmodel.ZukiViewModel

@Composable
fun TroubleshooterScreen(viewModel: ZukiViewModel) {
  val activeIssue by viewModel.activeTroubleIssue.collectAsState()
  val currentStep by viewModel.currentDiagnosticStep.collectAsState()
  val bookmarkedIds by viewModel.bookmarkedIds.collectAsState()
  val completedIds by viewModel.completedIds.collectAsState()

  if (activeIssue == null) {
    // 1. Issue Selection List
    TroubleIssueSelectionView(
      onSelectIssue = { issue -> viewModel.selectTroubleIssue(issue) }
    )
  } else {
    // 2. Interactive Decision Tree Step View
    DiagnosticStepInteractiveView(
      issue = activeIssue!!,
      step = currentStep,
      isBookmarked = bookmarkedIds.contains(activeIssue!!.id),
      isCompleted = completedIds.contains(activeIssue!!.id),
      onChooseOption = { isYes -> viewModel.chooseDiagnosticOption(isYes) },
      onRestart = { viewModel.restartDiagnostic() },
      onExit = { viewModel.exitDiagnostic() },
      onToggleBookmark = { viewModel.toggleBookmark(activeIssue!!.id) },
      onToggleComplete = { viewModel.toggleCompleted(activeIssue!!.id) }
    )
  }
}

@Composable
fun TroubleIssueSelectionView(
  onSelectIssue: (TroubleIssue) -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }
  val filteredIssues = remember(searchQuery) {
    if (searchQuery.isBlank()) TroubleshootingData.issuesList
    else TroubleshootingData.issuesList.filter {
      it.titleBengali.contains(searchQuery, ignoreCase = true) ||
          it.titleEnglish.contains(searchQuery, ignoreCase = true) ||
          it.category.contains(searchQuery, ignoreCase = true)
    }
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    contentPadding = PaddingValues(vertical = 16.dp)
  ) {
    item {
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
          text = "ইন্টারঅ্যাক্টিভ সমস্যা শনাক্তকরণ",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onBackground
        )
        Text(
          text = "আপনার মোবাইলের সমস্যাটি বেছে নিন। ধাপে ধাপে সিদ্ধান্তমূলক গাইড আপনাকে সঠিক সমাধানে পৌঁছাতে সাহায্য করবে।",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("সমস্যা লিখে খুঁজুন (যেমন: চার্জ, অন হয় না)...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TurquoisePrimary) },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("diagnostic_search_input"),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
      )
    }

    items(filteredIssues) { issue ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onSelectIssue(issue) }
          .testTag("issue_item_${issue.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          Box(
            modifier = Modifier
              .size(46.dp)
              .clip(CircleShape)
              .background(TurquoisePrimary.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.BuildCircle,
              contentDescription = null,
              tint = TurquoisePrimary,
              modifier = Modifier.size(28.dp)
            )
          }

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = issue.titleBengali,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = issue.titleEnglish,
              fontSize = 12.sp,
              color = Slate600
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
              color = Navy700.copy(alpha = 0.1f),
              shape = RoundedCornerShape(4.dp)
            ) {
              Text(
                text = issue.category,
                fontSize = 10.sp,
                color = TurquoisePrimary,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "শুরু করুন",
            tint = TurquoisePrimary
          )
        }
      }
    }
  }
}

@Composable
fun DiagnosticStepInteractiveView(
  issue: TroubleIssue,
  step: DiagnosticStep?,
  isBookmarked: Boolean,
  isCompleted: Boolean,
  onChooseOption: (Boolean) -> Unit,
  onRestart: () -> Unit,
  onExit: () -> Unit,
  onToggleBookmark: () -> Unit,
  onToggleComplete: () -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(vertical = 16.dp)
  ) {
    // Header Bar with Back and Actions
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onExit,
          modifier = Modifier.testTag("diagnostic_exit_button")
        ) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "সমস্যার তালিকায় ফিরুন")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          IconButton(onClick = onToggleBookmark) {
            Icon(
              imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = "বুকমার্ক",
              tint = if (isBookmarked) AmberAlert else MaterialTheme.colorScheme.onSurface
            )
          }
          IconButton(onClick = onRestart) {
            Icon(Icons.Default.Refresh, contentDescription = "প্রথম ধাপে ফিরুন")
          }
        }
      }
    }

    // Issue Title Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Navy800)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = issue.titleBengali,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
          Text(
            text = issue.titleEnglish,
            fontSize = 12.sp,
            color = TurquoiseBright
          )
        }
      }
    }

    // Safety Alert for this issue
    item {
      SafetyWarningCard(
        title = "বিশেষ নিরাপত্তা সতর্কতা",
        warningText = issue.safetyWarning
      )
    }

    // Active Diagnostic Step Card
    if (step != null) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            // Step Number Badge
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(28.dp)
                  .clip(CircleShape)
                  .background(TurquoisePrimary),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "${step.stepNumber}",
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp,
                  color = Navy900
                )
              }
              Text(
                text = step.titleBengali,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

            // Step Explanation
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text(
                text = "কীভাবে পরীক্ষা করবেন:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = TurquoisePrimary
              )
              Text(
                text = step.instructionBengali,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
            }

            // Detailed Method
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(10.dp)
            ) {
              Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                  text = "টেস্টিং মেথড:",
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                  text = step.testingMethod,
                  fontSize = 13.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
              }
            }

            // Tools Needed for this step
            if (step.toolsNeeded.isNotEmpty()) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Handyman,
                  contentDescription = null,
                  tint = AmberAlert,
                  modifier = Modifier.size(16.dp)
                )
                Text(
                  text = "প্রয়োজনীয় টুল: ${step.toolsNeeded.joinToString(", ")}",
                  fontSize = 12.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }

            // The Interactive Question
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(TurquoiseContainer)
                .padding(12.dp)
            ) {
              Text(
                text = step.observationQuestion,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Navy900
              )
            }

            // Interactive Decision Option Buttons
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
              Button(
                onClick = { onChooseOption(true) },
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("diagnostic_option_yes"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = EmeraldSuccess,
                  contentColor = Color.White
                )
              ) {
                Text(
                  text = step.yesOptionLabel,
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp
                )
              }

              OutlinedButton(
                onClick = { onChooseOption(false) },
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("diagnostic_option_no"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                  contentColor = MaterialTheme.colorScheme.onSurface
                )
              ) {
                Text(
                  text = step.noOptionLabel,
                  fontWeight = FontWeight.Medium,
                  fontSize = 14.sp
                )
              }
            }

            // If terminal resolution reached
            if (step.isTerminalResolution && step.finalResolution != null) {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(10.dp))
                  .background(if (step.requiresProfessionalService) CrimsonDanger.copy(alpha = 0.15f) else EmeraldSuccess.copy(alpha = 0.15f))
                  .padding(12.dp)
              ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                  Text(
                    text = if (step.requiresProfessionalService) "প্রফেশনাল ল্যাব রেফারেল সংকেত" else "চূড়ান্ত সমাধান সারসংক্ষেপ",
                    fontWeight = FontWeight.Bold,
                    color = if (step.requiresProfessionalService) CrimsonDanger else EmeraldSuccess
                  )
                  Text(
                    text = step.finalResolution,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                }
              }
            }
          }
        }
      }
    }

    // Root Causes Summary Card
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
            text = "এই সমস্যার সম্ভাব্য কারণসমূহ:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
          issue.rootCauses.forEachIndexed { index, cause ->
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Text(text = "•", color = TurquoisePrimary, fontWeight = FontWeight.Bold)
              Text(
                text = cause,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }
    }

    // Required Tools List Card
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
            text = "প্রয়োজনীয় টুলের তালিকা:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
          issue.requiredTools.forEach { tool ->
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TurquoiseTeal, modifier = Modifier.size(16.dp))
              Text(text = tool, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        }
      }
    }

    // Completion Button
    item {
      Button(
        onClick = onToggleComplete,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("diagnostic_complete_button"),
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
          text = if (isCompleted) "গাইডটি সম্পন্ন হয়েছে" else "গাইড সম্পন্ন হিসেবে মার্ক করুন",
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}
