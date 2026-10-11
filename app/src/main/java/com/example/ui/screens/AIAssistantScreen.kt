package com.example.ui.screens

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.automirrored.filled.TrendingFlat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GeminiService
import com.example.model.ScreenSection
import com.example.ui.components.AdBannerSlot
import com.example.ui.components.SafetyWarningCard
import com.example.ui.theme.*
import com.example.viewmodel.ZukiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIAssistantScreen(
  viewModel: ZukiViewModel,
  onNavigateToSection: (ScreenSection) -> Unit
) {
  val context = LocalContext.current
  val query by viewModel.aiQuery.collectAsState()
  val result by viewModel.aiResult.collectAsState()
  val isLoading by viewModel.isAiLoading.collectAsState()
  val errorMessage by viewModel.aiErrorMessage.collectAsState()
  val isOffline by viewModel.isSimulatedOffline.collectAsState()
  val entitlement by viewModel.userEntitlement.collectAsState()

  var selectedImageBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
  var categoryMenuExpanded by remember { mutableStateOf(false) }

  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri ->
    if (uri != null) {
      try {
        context.contentResolver.openInputStream(uri)?.use { stream ->
          val bitmap = BitmapFactory.decodeStream(stream)
          selectedImageBitmap = bitmap
          if (bitmap != null) {
            val base64 = GeminiService.bitmapToBase64(bitmap)
            viewModel.updateAIQuery { it.copy(imageBase64 = base64) }
          }
        }
      } catch (_: Exception) {}
    }
  }

  val popularBrands = listOf("Samsung", "Xiaomi", "iPhone", "Vivo", "Oppo", "Realme", "Symphony", "Walton")

  val categories = listOf(
    "পাওয়ার ও চালু না হওয়া (Dead / No Power)",
    "চার্জিং সমস্যা ও ব্যাটারি (Charging / Battery)",
    "ডিসপ্লে, লাইট ও টাচ স্ক্রিন (Display / Touch)",
    "নেটওয়ার্ক ও সিম সিগন্যাল (Network / No Service)",
    "স্পিকার, মাইক ও অডিও (Sound / Mic / Audio)",
    "পানিতে ভেজা ও শর্ট সার্কিট (Water Damage / Short)",
    "বুটলুপ ও সফটওয়্যার সমস্যা (Bootloop / Hang On Logo)",
    "অন্যান্য হার্ডওয়্যার সমস্যা (General Hardware)"
  )

  val sampleSymptoms = listOf(
    "পাওয়ার বাটনে চাপলে কোনো রেসপন্স নেই",
    "চার্জার লাগালে লোগো এসে বন্ধ হয়",
    "পাওয়ার সাপ্লাইয়ে ০.০৫A কারেন্ট আটকে থাকে",
    "ডিসপ্লে কালো কিন্তু ভাইব্রেশন ও কল আসে",
    "চার্জ হয় খুব ধীরে (Fake Charging)"
  )

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(vertical = 16.dp)
  ) {
    // 1. Header Card with Navy & Turquoise Design
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Navy800),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
      ) {
        Column(
          modifier = Modifier.padding(18.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(44.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .background(TurquoiseBright.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Psychology,
                  contentDescription = null,
                  tint = TurquoiseBright,
                  modifier = Modifier.size(26.dp)
                )
              }
              Column {
                Text(
                  text = "AI মেরামত সহকারী",
                  fontWeight = FontWeight.Bold,
                  fontSize = 19.sp,
                  color = Color.White
                )
                Text(
                  text = "স্মার্ট হার্ডওয়্যার ও সফটওয়্যার সমাধান",
                  fontSize = 12.sp,
                  color = TurquoiseBright
                )
              }
            }

            Surface(
              color = if (entitlement.isPremiumActive) EmeraldSuccess else TurquoiseContainer,
              shape = RoundedCornerShape(8.dp)
            ) {
              Text(
                text = if (entitlement.isPremiumActive) "আনলিমিটেড প্রো" else "আজকে: ${entitlement.aiQueriesToday}/${entitlement.maxFreeAiQueriesPerDay} ফ্রি",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Navy900,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }

          Text(
            text = "ফোনের ব্র্যান্ড, মডেল, সমস্যার বিভাগ ও বিস্তারিত লক্ষণ দিন। জুকি AI বিশেষজ্ঞ আপনাকে নিরাপদ কোল্ড টেস্ট, সম্ভাব্য আইসি ত্রুটি ও সমাধান ধাপ জানিয়ে দেবে।",
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = Slate300
          )
        }
      }
    }

    // 2. Offline Mode Warning if offline
    if (isOffline) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = AmberAlert.copy(alpha = 0.15f))
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Icon(Icons.Default.CloudOff, contentDescription = null, tint = AmberAlert)
            Column(modifier = Modifier.weight(1f)) {
              Text(text = "অফলাইন মোড সক্রিয়", fontWeight = FontWeight.Bold, fontSize = 13.sp)
              Text(text = "ইন্টারনেট নেই। আপনি আগে সংরক্ষিত ডায়াগ্রাম ও সিদ্ধান্ত গাইড দেখতে পারেন।", fontSize = 12.sp)
            }
            TextButton(onClick = { onNavigateToSection(ScreenSection.DIAGNOSIS) }) {
              Text("গাইড দেখুন", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 3. Primary Input Form Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "ফোনের তথ্য ও সমস্যার বিবরণ",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = MaterialTheme.colorScheme.onSurface
            )

            // Preset autofill button for instant testing
            TextButton(
              onClick = {
                viewModel.updateAIQuery {
                  it.copy(
                    brand = "Samsung",
                    model = "Galaxy A12",
                    category = "পাওয়ার ও চালু না হওয়া (Dead / No Power)",
                    description = "ফোন সম্পূর্ণ ডেড। পাওয়ার কি চাপলে ০.০৫A কারেন্ট নেয় কিন্তু অন হয় না। কোনো ভাইব্রেশন বা ডিসপ্লে নেই।",
                    durationHistory = "রাতে চার্জে ছিল, সকালে আর চালু হয়নি",
                    priorTestsDone = "নতুন ব্যাটারি ও সাব-বোর্ড কেবল চেক করা হয়েছে",
                    multimeterReading = "V-BAT রিডিং ৩.৮V স্বাভাবিক, কোনো ফুল শর্ট নেই"
                  )
                }
              },
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = TurquoisePrimary, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("নমুনা ডেটা পূরণ", fontSize = 12.sp, color = TurquoisePrimary)
            }
          }

          // A. Phone Brand (ফোনের ব্র্যান্ড)
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
              text = "১. ফোনের ব্র্যান্ড (Brand):",
              fontWeight = FontWeight.SemiBold,
              fontSize = 13.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            OutlinedTextField(
              value = query.brand,
              onValueChange = { b -> viewModel.updateAIQuery { it.copy(brand = b) } },
              placeholder = { Text("যেমন: Samsung, Xiaomi, iPhone...") },
              leadingIcon = {
                Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = TurquoisePrimary)
              },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("ai_input_brand"),
              singleLine = true,
              shape = RoundedCornerShape(12.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TurquoisePrimary,
                unfocusedBorderColor = Slate300.copy(alpha = 0.6f)
              )
            )

            // Popular Brand Quick Pills
            LazyRow(
              horizontalArrangement = Arrangement.spacedBy(6.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              items(popularBrands) { brandName ->
                val isSelected = query.brand.equals(brandName, ignoreCase = true)
                Surface(
                  shape = RoundedCornerShape(16.dp),
                  color = if (isSelected) TurquoisePrimary else Navy700.copy(alpha = 0.12f),
                  modifier = Modifier.clickable {
                    viewModel.updateAIQuery { it.copy(brand = brandName) }
                  }
                ) {
                  Text(
                    text = brandName,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) Navy900 else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                  )
                }
              }
            }
          }

          // B. Phone Model (মডেল)
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
              text = "২. ফোনের মডেল (Model):",
              fontWeight = FontWeight.SemiBold,
              fontSize = 13.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            OutlinedTextField(
              value = query.model,
              onValueChange = { m -> viewModel.updateAIQuery { it.copy(model = m) } },
              placeholder = { Text("যেমন: Galaxy A12, Redmi Note 10, iPhone 11...") },
              leadingIcon = {
                Icon(Icons.Default.Devices, contentDescription = null, tint = TurquoisePrimary)
              },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("ai_input_model"),
              singleLine = true,
              shape = RoundedCornerShape(12.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TurquoisePrimary,
                unfocusedBorderColor = Slate300.copy(alpha = 0.6f)
              )
            )
          }

          // C. Problem Category Dropdown (সমস্যার বিভাগ)
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
              text = "৩. সমস্যার বিভাগ (Problem Category):",
              fontWeight = FontWeight.SemiBold,
              fontSize = 13.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            ExposedDropdownMenuBox(
              expanded = categoryMenuExpanded,
              onExpandedChange = { categoryMenuExpanded = !categoryMenuExpanded }
            ) {
              OutlinedTextField(
                value = query.category,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("সমস্যার বিভাগ নির্বাচন করুন") },
                leadingIcon = {
                  Icon(Icons.Default.Category, contentDescription = null, tint = TurquoisePrimary)
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryMenuExpanded) },
                modifier = Modifier
                  .fillMaxWidth()
                  .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = TurquoisePrimary,
                  unfocusedBorderColor = Slate300.copy(alpha = 0.6f)
                )
              )
              ExposedDropdownMenu(
                expanded = categoryMenuExpanded,
                onDismissRequest = { categoryMenuExpanded = false }
              ) {
                categories.forEach { cat ->
                  DropdownMenuItem(
                    text = { Text(cat, fontSize = 13.sp) },
                    onClick = {
                      viewModel.updateAIQuery { it.copy(category = cat) }
                      categoryMenuExpanded = false
                    }
                  )
                }
              }
            }
          }

          // D. Problem Details (সমস্যার বিস্তারিত বিবরণ)
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
              text = "৪. সমস্যার বিস্তারিত বর্ণনা (Problem Details):",
              fontWeight = FontWeight.SemiBold,
              fontSize = 13.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            OutlinedTextField(
              value = query.description,
              onValueChange = { d -> viewModel.updateAIQuery { it.copy(description = d) } },
              placeholder = { Text("যেমন: চার্জার লাগালে লোগো এসে রিস্টার্ট নেয়, ডিসপ্লে সম্পূর্ণ সাদা হয়ে যায়, অথবা ০.০৫A কারেন্ট ড্র করে...") },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("ai_input_description"),
              minLines = 3,
              maxLines = 6,
              shape = RoundedCornerShape(12.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TurquoisePrimary,
                unfocusedBorderColor = Slate300.copy(alpha = 0.6f)
              )
            )

            // Quick Symptom Chips
            Text(
              text = "দ্রুত লক্ষণ নির্বাচন (ট্যাপ করে যুক্ত করুন):",
              fontSize = 11.sp,
              color = Slate600
            )
            LazyRow(
              horizontalArrangement = Arrangement.spacedBy(6.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              items(sampleSymptoms) { symptom ->
                Surface(
                  shape = RoundedCornerShape(14.dp),
                  color = Navy800.copy(alpha = 0.08f),
                  border = androidx.compose.foundation.BorderStroke(1.dp, TurquoisePrimary.copy(alpha = 0.4f)),
                  modifier = Modifier.clickable {
                    val currentDesc = query.description.trim()
                    val newDesc = if (currentDesc.isBlank()) symptom else "$currentDesc | $symptom"
                    viewModel.updateAIQuery { it.copy(description = newDesc) }
                  }
                ) {
                  Text(
                    text = symptom,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                  )
                }
              }
            }
          }

          // E. Optional History & Tests
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            OutlinedTextField(
              value = query.durationHistory,
              onValueChange = { h -> viewModel.updateAIQuery { it.copy(durationHistory = h) } },
              label = { Text("আগের ইতিহাস (হাত থেকে পড়া/পানি)") },
              modifier = Modifier.weight(1f),
              singleLine = true,
              shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
              value = query.multimeterReading,
              onValueChange = { r -> viewModel.updateAIQuery { it.copy(multimeterReading = r) } },
              label = { Text("মাল্টিমিটার রিডিং") },
              placeholder = { Text("যেমন: ৩.৮V বা ডায়োড ০.৪৫০") },
              modifier = Modifier.weight(1f),
              singleLine = true,
              shape = RoundedCornerShape(12.dp)
            )
          }

          // F. Image Attachment (Optional)
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            OutlinedButton(
              onClick = {
                photoPickerLauncher.launch(
                  androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
              },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.outlinedButtonColors(contentColor = TurquoisePrimary),
              border = androidx.compose.foundation.BorderStroke(1.dp, TurquoisePrimary),
              modifier = Modifier.testTag("ai_attach_photo_btn")
            ) {
              Icon(Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(if (selectedImageBitmap != null) "ছবি পরিবর্তন করুন" else "মাদারবোর্ড বা পার্টসের ছবি দিন", fontSize = 12.sp)
            }

            if (selectedImageBitmap != null) {
              IconButton(onClick = {
                selectedImageBitmap = null
                viewModel.updateAIQuery { it.copy(imageBase64 = null) }
              }) {
                Icon(Icons.Default.Delete, contentDescription = "ছবি মুছুন", tint = CrimsonDanger)
              }
            }
          }

          if (selectedImageBitmap != null) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.5.dp, TurquoisePrimary, RoundedCornerShape(10.dp))
            ) {
              Image(
                bitmap = selectedImageBitmap!!.asImageBitmap(),
                contentDescription = "নির্বাচিত ছবি",
                modifier = Modifier.fillMaxSize()
              )
            }
          }

          // G. Submit Button
          Button(
            onClick = { viewModel.executeAIRepairDiagnosis() },
            enabled = !isLoading && query.description.isNotBlank(),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("ai_submit_button"),
            colors = ButtonDefaults.buttonColors(
              containerColor = TurquoisePrimary,
              contentColor = Navy900
            ),
            shape = RoundedCornerShape(12.dp)
          ) {
            if (isLoading) {
              CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                color = Navy900,
                strokeWidth = 2.5.dp
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text("AI বিশ্লেষণ করছে...", fontWeight = FontWeight.Bold)
            } else {
              Icon(Icons.Default.Psychology, contentDescription = null)
              Spacer(modifier = Modifier.width(8.dp))
              Text("AI মেরামত বিশ্লেষণ শুরু করুন", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
          }

          if (errorMessage != null) {
            Text(
              text = errorMessage!!,
              color = CrimsonDanger,
              fontSize = 12.sp,
              lineHeight = 16.sp
            )
          }
        }
      }
    }

    // 4. Structured AI Result Section (if available)
    if (result != null) {
      val res = result!!

      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            // Result Header & Tag
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TurquoisePrimary)
                Text(
                  text = "AI বিশেষজ্ঞ বিশ্লেষণ ফলাফল",
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
              }

              Surface(
                color = Color(res.confidenceLevel.colorHex).copy(alpha = 0.2f),
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(
                  text = res.confidenceLevel.labelBengali,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(res.confidenceLevel.colorHex),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

            // 1. Summary
            AIResponseSection(
              title = "১. সমস্যার সংক্ষিপ্ত বিবরণ:",
              content = res.summary,
              icon = Icons.Default.Summarize,
              color = TurquoisePrimary
            )

            // 2. Probable Root Causes
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text(
                text = "২. সম্ভাব্য মূল কারণসমূহ:",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = TurquoisePrimary
              )
              res.rootCauses.forEachIndexed { i, cause ->
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Text(text = "${i + 1}.", fontWeight = FontWeight.Bold, color = Slate600)
                  Text(text = cause, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                }
              }
            }

            // 3. First Safe Cold Test
            AIResponseSection(
              title = "৩. সবার আগে করণীয় নিরাপদ কোল্ড টেস্ট:",
              content = res.firstSafeTest,
              icon = Icons.Default.Security,
              color = EmeraldSuccess
            )

            // 4. Tools Needed
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text(
                text = "৪. প্রয়োজনীয় টুলস:",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = TurquoisePrimary
              )
              Text(
                text = res.toolsNeeded.joinToString(", "),
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
            }

            // 5. Step Instructions
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text(
                text = "৫. ধাপে ধাপে নির্দেশনা:",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = TurquoisePrimary
              )
              res.stepInstructions.forEach { step ->
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Text(text = "•", color = TurquoisePrimary, fontWeight = FontWeight.Bold)
                  Text(text = step, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                }
              }
            }

            // 6. Expected Outcomes
            AIResponseSection(
              title = "৬. প্রত্যাশিত ফলাফল ও রিডিং:",
              content = res.expectedOutcomes,
              icon = Icons.Default.Checklist,
              color = Color(0xFF38BDF8)
            )

            // 7. Next Steps
            AIResponseSection(
              title = "৭. ফলাফল অনুযায়ী পরবর্তী পদক্ষেপ:",
              content = res.nextSteps,
              icon = Icons.AutoMirrored.Filled.TrendingFlat,
              color = Color(0xFFA855F7)
            )

            // 8. Warning / When to consult pro
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(AmberAlert.copy(alpha = 0.12f))
                .padding(10.dp)
            ) {
              Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                  text = "৮. কখন প্রফেশনাল ল্যাবে যাবেন:",
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = AmberAlert
                )
                Text(
                  text = res.whenToConsultPro,
                  fontSize = 12.sp,
                  lineHeight = 17.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
              }
            }

            // Clear Button
            OutlinedButton(
              onClick = { viewModel.clearAIResult() },
              modifier = Modifier.fillMaxWidth()
            ) {
              Text("নতুন বিশ্লেষণ শুরু করুন")
            }
          }
        }
      }
    }

    // 5. Ad Slot for Free Users
    item {
      AdBannerSlot(
        adsEnabled = entitlement.adsEnabled,
        onGoPremiumClick = { onNavigateToSection(ScreenSection.PREMIUM) }
      )
    }

    // 6. Safety Card
    item {
      SafetyWarningCard(
        title = "AI নির্দেশিকা ব্যবহারের নিয়মাবলী",
        warningText = "AI সমাধান একটি বুদ্ধিমান সহায়তা। নিশ্চিত কোল্ড ও হট পরীক্ষার পূর্বে কোনো আইসি তুলবেন না। লিথিয়াম ব্যাটারি ছিদ্র করা বা অতিরিক্ত হিট দেওয়া সম্পূর্ণ নিষিদ্ধ।"
      )
    }
  }
}

@Composable
fun AIResponseSection(
  title: String,
  content: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  color: Color
) {
  Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
      Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        color = color
      )
    }
    Text(
      text = content,
      fontSize = 13.sp,
      lineHeight = 18.sp,
      color = MaterialTheme.colorScheme.onSurface
    )
  }
}
