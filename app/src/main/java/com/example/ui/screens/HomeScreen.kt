package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SampleData
import com.example.model.ScreenSection
import com.example.ui.components.SafetyWarningCard
import com.example.ui.theme.*
import com.example.viewmodel.ZukiViewModel

data class HomeCategoryCard(
  val section: ScreenSection,
  val titleBengali: String,
  val subtitleBengali: String,
  val icon: ImageVector,
  val accentColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  viewModel: ZukiViewModel,
  onNavigateToSection: (ScreenSection) -> Unit,
  onOpenLesson: (String) -> Unit
) {
  val searchQuery by viewModel.searchQuery.collectAsState()
  val bookmarkedIds by viewModel.bookmarkedIds.collectAsState()
  val completedIds by viewModel.completedIds.collectAsState()

  val categories = listOf(
    HomeCategoryCard(ScreenSection.DIAGNOSIS, "সমস্যা নির্ণয়", "১৬টি ইন্টারেক্টিভ সিদ্ধান্ত গাইড", Icons.Default.Build, TurquoisePrimary),
    HomeCategoryCard(ScreenSection.AI_ASSISTANT, "AI মেরামত সহকারী", "Gemini AI স্মার্ট সমাধান ও পরামর্শ", Icons.Default.Psychology, Color(0xFF6366F1)),
    HomeCategoryCard(ScreenSection.PHOTO_IDENTIFIER, "ছবি স্ক্যান ও পার্টস", "ছবি তুলে সার্কিট বা আইসি শনাক্ত", Icons.Default.DocumentScanner, Color(0xFF0EA5E9)),
    HomeCategoryCard(ScreenSection.TOOLBOX, "টেকনিশিয়ান টুলবক্স", "ওহমের সূত্র, পাওয়ার ও কালার কোড", Icons.Default.Handyman, Color(0xFF38BDF8)),
    HomeCategoryCard(ScreenSection.COMPONENTS, "কম্পোনেন্ট লাইব্রেরি", "১২টি প্রধান যন্ত্রাংশ ও টেস্টিং", Icons.Default.Memory, Color(0xFFF59E0B)),
    HomeCategoryCard(ScreenSection.CIRCUITS, "সার্কিট ডায়াগ্রাম", "পাওয়ার, চার্জিং ও ডিসপ্লে স্কিম্যাটিক", Icons.Default.Schema, Color(0xFFA855F7)),
    HomeCategoryCard(ScreenSection.HARDWARE, "হার্ডওয়্যার মেরামত", "মাদারবোর্ড, শর্ট ও রিবলিং টিউটোরিয়াল", Icons.Default.DeveloperBoard, Color(0xFF10B981)),
    HomeCategoryCard(ScreenSection.SOFTWARE, "সফটওয়্যার মেরামত", "ফ্ল্যাশিং, বুটলুপ ও আনলকিং এসওপি", Icons.Default.SystemUpdate, Color(0xFFEC4899)),
    HomeCategoryCard(ScreenSection.OFFLINE_MANAGER, "অফলাইন প্যাক", "নেট ছাড়া ব্যবহারের গাইড ও ডায়াগ্রাম", Icons.Default.DownloadForOffline, Color(0xFF14B8A6)),
    HomeCategoryCard(ScreenSection.PREMIUM, "প্রিমিয়াম ও সাবস্ক্রিপশন", "ভিআইপি সুবিধা ও বিজ্ঞাপন অপশন", Icons.Default.Verified, Color(0xFFEAB308)),
    HomeCategoryCard(ScreenSection.QUIZ, "বাংলা কুইজ ও পরীক্ষা", "২০টি পূর্ণাঙ্গ মূল্যায়ন প্রশ্ন", Icons.Default.Quiz, Color(0xFFEC4899)),
    HomeCategoryCard(ScreenSection.BOOKMARKS, "বুকমার্ক ও অগ্রগতি", "সংরক্ষিত পাঠ ও শেখার ধাপ", Icons.Default.Bookmark, Color(0xFF06B6D4))
  )

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(vertical = 16.dp)
  ) {
    // 1. Hero Banner with Real Illustrated Repair Bench Graphic
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Navy800),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
      ) {
        Column {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(150.dp)
          ) {
            Image(
              painter = painterResource(id = R.drawable.hero_banner_servicing),
              contentDescription = "সার্ভিসিং ল্যাব ব্যানার",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
            // Gradient Overlay
            Box(
              modifier = Modifier
                .fillMaxSize()
                .background(
                  Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Navy900.copy(alpha = 0.85f))
                  )
                )
            )
            // Title & Official Logo overlay
            Row(
              modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(14.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Image(
                painter = painterResource(id = R.drawable.ic_zuki_app_icon),
                contentDescription = "ZUKI অফিসিয়াল লোগো",
                modifier = Modifier
                  .size(44.dp)
                  .clip(RoundedCornerShape(10.dp))
                  .border(1.5.dp, TurquoiseBright.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
              )
              Column {
                Text(
                  text = "ZUKI মোবাইল সার্ভিসিং ল্যাব",
                  fontWeight = FontWeight.Bold,
                  fontSize = 18.sp,
                  color = Color.White
                )
                Text(
                  text = "শিখুন | সারান | এগিয়ে যান",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = TurquoiseBright
                )
              }
            }
          }

          // Welcome and Quick Stats
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "স্বাগতম, টেকনিশিয়ান!",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
              Text(
                text = "সহজ বাংলায় বাস্তব মোবাইল মেরামত শিখুন",
                style = MaterialTheme.typography.bodySmall,
                color = Slate300
              )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              OutlinedButton(
                onClick = { onNavigateToSection(ScreenSection.AI_ASSISTANT) },
                colors = ButtonDefaults.outlinedButtonColors(
                  contentColor = TurquoiseBright
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, TurquoiseBright),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier.testTag("hero_quick_ai_button")
              ) {
                Text(text = "AI সহকারী", fontWeight = FontWeight.Bold, fontSize = 12.sp)
              }
              Button(
                onClick = { onNavigateToSection(ScreenSection.DIAGNOSIS) },
                colors = ButtonDefaults.buttonColors(
                  containerColor = TurquoisePrimary,
                  contentColor = Navy900
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier.testTag("hero_quick_diagnose_button")
              ) {
                Text(text = "সমস্যা নির্ণয়", fontWeight = FontWeight.Bold, fontSize = 12.sp)
              }
            }
          }
        }
      }
    }

    // 2. Search Field
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { viewModel.setSearchQuery(it) },
        placeholder = { Text("খুঁজুন (যেমন: চার্জিং, ডিসপ্লে, ওহম, ক্যাপাসিটর)...") },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = "খুঁজুন", tint = TurquoisePrimary)
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { viewModel.setSearchQuery("") }) {
              Icon(Icons.Default.Clear, contentDescription = "মুছুন")
            }
          }
        },
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("home_search_input"),
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = TurquoisePrimary,
          unfocusedBorderColor = Slate300.copy(alpha = 0.5f),
          focusedContainerColor = MaterialTheme.colorScheme.surface,
          unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
      )
    }

    // 3. Learning Progress Summary Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Navy700)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceAround,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${completedIds.size} টি", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TurquoiseBright)
            Text(text = "সম্পন্ন পাঠ", fontSize = 12.sp, color = Slate300)
          }
          VerticalDivider(
            modifier = Modifier
              .height(30.dp)
              .width(1.dp),
            color = Slate600
          )
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${bookmarkedIds.size} টি", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = AmberAlert)
            Text(text = "বুকমার্ক", fontSize = 12.sp, color = Slate300)
          }
          VerticalDivider(
            modifier = Modifier
              .height(30.dp)
              .width(1.dp),
            color = Slate600
          )
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "১৬ টি", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF38BDF8))
            Text(text = "সমস্যা গাইড", fontSize = 12.sp, color = Slate300)
          }
        }
      }
    }

    // 4. Laboratory Safety Tip
    item {
      SafetyWarningCard(
        title = "পেশাদার ল্যাব নিরাপত্তা সতর্কতা",
        warningText = "সার্কিটে কাজ করার আগে অবশ্যই ফোনের ব্যাটারি কানেক্টর বিচ্ছিন্ন করুন। লাইভ বোর্ডে ওহম বা কন্টিনিউটি মোড চালালে মাল্টিমিটারের ইন্টারনাল ফিউজ পুড়ে যেতে পারে।"
      )
    }

    // 5. Main Categories Section Header
    item {
      Text(
        text = "প্রধান সার্ভিসিং বিভাগসমূহ",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
      )
    }

    // Category Grid (2 items per row)
    items(categories.chunked(2)) { rowItems ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        rowItems.forEach { cat ->
          Card(
            modifier = Modifier
              .weight(1f)
              .clickable { onNavigateToSection(cat.section) }
              .testTag("category_card_${cat.section.id}"),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
          ) {
            Column(
              modifier = Modifier.padding(14.dp),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(40.dp)
                  .clip(RoundedCornerShape(10.dp))
                  .background(cat.accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = cat.icon,
                  contentDescription = cat.titleBengali,
                  tint = cat.accentColor,
                  modifier = Modifier.size(24.dp)
                )
              }
              Text(
                text = cat.titleBengali,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = cat.subtitleBengali,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
        if (rowItems.size == 1) {
          Spacer(modifier = Modifier.weight(1f))
        }
      }
    }

    // 6. Featured Practical Lessons Section
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "জনপ্রিয় ব্যবহারিক পাঠসমূহ",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onBackground
        )
        TextButton(onClick = { onNavigateToSection(ScreenSection.HARDWARE) }) {
          Text(text = "সব দেখুন", color = TurquoisePrimary)
        }
      }
    }

    // Featured Lesson Cards
    items(SampleData.lessons.take(4)) { lesson ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onOpenLesson(lesson.id) }
          .testTag("lesson_card_${lesson.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(TurquoisePrimary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Handyman,
              contentDescription = null,
              tint = TurquoisePrimary
            )
          }

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = lesson.title,
              fontWeight = FontWeight.SemiBold,
              fontSize = 14.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = lesson.englishTitle,
              fontSize = 11.sp,
              color = Slate600
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Surface(
                color = Navy700.copy(alpha = 0.1f),
                shape = RoundedCornerShape(4.dp)
              ) {
                Text(
                  text = "${lesson.estimatedMinutes} মিনিট",
                  fontSize = 10.sp,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
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
            }
          }

          IconButton(
            onClick = { viewModel.toggleBookmark(lesson.id) },
            modifier = Modifier.testTag("bookmark_toggle_${lesson.id}")
          ) {
            Icon(
              imageVector = if (bookmarkedIds.contains(lesson.id)) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = "বুকমার্ক",
              tint = if (bookmarkedIds.contains(lesson.id)) AmberAlert else Slate600
            )
          }
        }
      }
    }

    // 7. Today's Pro Service Tip
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TurquoiseContainer)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(Icons.Default.Lightbulb, contentDescription = "টিপস", tint = Navy800)
            Text(
              text = "আজকের জরুরি সার্ভিসিং টিপস",
              fontWeight = FontWeight.Bold,
              color = Navy900,
              fontSize = 14.sp
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = SampleData.dailyServicingTips.first(),
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = Navy800
          )
        }
      }
    }
  }
}
