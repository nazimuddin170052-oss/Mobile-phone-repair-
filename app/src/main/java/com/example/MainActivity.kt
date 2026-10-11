package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.ScreenSection
import com.example.ui.components.ZukiBottomNav
import com.example.ui.components.ZukiTopAppBar
import com.example.ui.screens.*
import com.example.ui.theme.ZukiServicingTheme
import com.example.viewmodel.ZukiViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val viewModel: ZukiViewModel = viewModel()
      val isDark by viewModel.isDarkMode.collectAsState()

      ZukiServicingTheme(darkTheme = isDark) {
        ZukiAppRoot(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun ZukiAppRoot(viewModel: ZukiViewModel) {
  val currentSection by viewModel.currentSection.collectAsState()
  val selectedLessonId by viewModel.selectedLessonId.collectAsState()

  // Handle Android physical/gesture back button
  BackHandler(enabled = currentSection != ScreenSection.HOME) {
    if (currentSection == ScreenSection.LESSON_DETAIL) {
      viewModel.navigateTo(ScreenSection.HARDWARE)
    } else {
      viewModel.navigateTo(ScreenSection.HOME)
    }
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      ZukiTopAppBar(
        currentSection = currentSection,
        onBackClick = {
          if (currentSection == ScreenSection.LESSON_DETAIL) {
            viewModel.navigateTo(ScreenSection.HARDWARE)
          } else {
            viewModel.navigateTo(ScreenSection.HOME)
          }
        },
        onSearchClick = {
          viewModel.navigateTo(ScreenSection.HOME)
        },
        onBookmarkClick = {
          viewModel.navigateTo(ScreenSection.BOOKMARKS)
        }
      )
    },
    bottomBar = {
      ZukiBottomNav(
        currentSection = currentSection,
        onTabSelected = { section ->
          viewModel.navigateTo(section)
        }
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (currentSection) {
        ScreenSection.HOME -> {
          HomeScreen(
            viewModel = viewModel,
            onNavigateToSection = { section -> viewModel.navigateTo(section) },
            onOpenLesson = { lessonId -> viewModel.openLessonDetail(lessonId) }
          )
        }
        ScreenSection.DIAGNOSIS -> {
          TroubleshooterScreen(viewModel = viewModel)
        }
        ScreenSection.TOOLBOX -> {
          ToolboxScreen(viewModel = viewModel)
        }
        ScreenSection.COMPONENTS -> {
          ComponentLibraryScreen()
        }
        ScreenSection.CIRCUITS -> {
          CircuitDiagramScreen()
        }
        ScreenSection.HARDWARE -> {
          HardwareRepairScreen(
            viewModel = viewModel,
            onOpenLesson = { lessonId -> viewModel.openLessonDetail(lessonId) }
          )
        }
        ScreenSection.SOFTWARE -> {
          SoftwareRepairScreen(
            viewModel = viewModel,
            onOpenLesson = { lessonId -> viewModel.openLessonDetail(lessonId) }
          )
        }
        ScreenSection.QUIZ -> {
          QuizScreen(viewModel = viewModel)
        }
        ScreenSection.BOOKMARKS -> {
          BookmarksProgressScreen(
            viewModel = viewModel,
            onOpenLesson = { lessonId -> viewModel.openLessonDetail(lessonId) }
          )
        }
        ScreenSection.SETTINGS -> {
          SettingsScreen(viewModel = viewModel)
        }
        ScreenSection.AI_ASSISTANT -> {
          AIAssistantScreen(
            viewModel = viewModel,
            onNavigateToSection = { section -> viewModel.navigateTo(section) }
          )
        }
        ScreenSection.PHOTO_IDENTIFIER -> {
          PhotoIdentifierScreen(
            viewModel = viewModel,
            onNavigateToSection = { section -> viewModel.navigateTo(section) }
          )
        }
        ScreenSection.PREMIUM -> {
          PremiumSubscriptionScreen(viewModel = viewModel)
        }
        ScreenSection.OFFLINE_MANAGER -> {
          OfflineManagerScreen(
            viewModel = viewModel,
            onNavigateToSection = { section -> viewModel.navigateTo(section) }
          )
        }
        ScreenSection.LESSON_DETAIL -> {
          LessonDetailScreen(
            viewModel = viewModel,
            lessonId = selectedLessonId,
            onBack = { viewModel.navigateTo(ScreenSection.HOME) }
          )
        }
      }
    }
  }
}
