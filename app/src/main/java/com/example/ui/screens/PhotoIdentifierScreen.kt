package com.example.ui.screens

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ScreenSection
import com.example.ui.components.AdBannerSlot
import com.example.ui.components.SafetyWarningCard
import com.example.ui.theme.*
import com.example.viewmodel.ZukiViewModel

@Composable
fun PhotoIdentifierScreen(
  viewModel: ZukiViewModel,
  onNavigateToSection: (ScreenSection) -> Unit
) {
  val context = LocalContext.current
  val selectedBitmap by viewModel.selectedPhotoBitmap.collectAsState()
  val consent by viewModel.hasUserConsent.collectAsState()
  val result by viewModel.photoScanResult.collectAsState()
  val isScanning by viewModel.isPhotoScanning.collectAsState()
  val errorMsg by viewModel.photoScanErrorMessage.collectAsState()
  val entitlement by viewModel.userEntitlement.collectAsState()
  val isOffline by viewModel.isSimulatedOffline.collectAsState()

  var showFeedbackDialog by remember { mutableStateOf(false) }

  // Photo Pickers
  val galleryLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri ->
    if (uri != null) {
      try {
        context.contentResolver.openInputStream(uri)?.use { stream ->
          val bitmap = BitmapFactory.decodeStream(stream)
          viewModel.setSelectedPhotoBitmap(bitmap)
        }
      } catch (_: Exception) {}
    }
  }

  val cameraLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.TakePicturePreview()
  ) { bitmap ->
    if (bitmap != null) {
      viewModel.setSelectedPhotoBitmap(bitmap)
    }
  }

  if (showFeedbackDialog) {
    AlertDialog(
      onDismissRequest = { showFeedbackDialog = false },
      title = { Text(text = "ফলাফল সংশোধন ফিডব্যাক", fontWeight = FontWeight.Bold) },
      text = { Text("আপনার মূল্যবান ফিডব্যাকের জন্য ধন্যবাদ। পরবর্তী সংস্করণে AI মডেল এই কম্পোনেন্টটি আরও নিখুঁতভাবে শনাক্ত করতে শিখবে।") },
      confirmButton = {
        Button(
          onClick = {
            viewModel.provideFeedbackOnPhotoScan(false)
            showFeedbackDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = TurquoisePrimary, contentColor = Navy900)
        ) {
          Text("ঠিক আছে")
        }
      }
    )
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(vertical = 16.dp)
  ) {
    // 1. Header Card
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
                text = "ছবি দিয়ে কম্পোনেন্ট শনাক্তকরণ",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
              )
              Text(
                text = "ভিজ্যুয়াল AI চিপসেট ও পার্টস আইডেন্টিফায়ার",
                fontSize = 12.sp,
                color = TurquoiseBright
              )
            }

            Surface(
              color = if (entitlement.isPremiumActive) EmeraldSuccess else TurquoiseContainer,
              shape = RoundedCornerShape(8.dp)
            ) {
              Text(
                text = if (entitlement.isPremiumActive) "আনলিমিটেড প্রো" else "আজকে: ${entitlement.photoScansToday}/${entitlement.maxFreePhotoScansPerDay} ফ্রি",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Navy900,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }

          Text(
            text = "ক্যামেরা দিয়ে মাদারবোর্ডের কোনো IC, ক্যাপাসিটর, রেজিস্টর বা কয়েলের ছবি তুলুন। AI এর দৃশ্যমান রূপ ও টেস্টিং নির্দেশিকা জানিয়ে দেবে।",
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = Slate300
          )
        }
      }
    }

    // 2. Offline Notice
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
            Text(
              text = "অফলাইন অবস্থায় লাইভ ছবি বিশ্লেষণ অনুপলব্ধ। অনুগ্রহ করে অফলাইন কম্পোনেন্ট লাইব্রেরি দেখুন।",
              fontSize = 12.sp,
              modifier = Modifier.weight(1f)
            )
            TextButton(onClick = { onNavigateToSection(ScreenSection.COMPONENTS) }) {
              Text("লাইব্রেরি", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 3. Photo Capture & Preview Area
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
          // Action Buttons: Camera & Gallery
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = { cameraLauncher.launch(null) },
              modifier = Modifier.weight(1f).testTag("photo_take_camera_btn"),
              colors = ButtonDefaults.buttonColors(containerColor = Navy800, contentColor = Color.White),
              shape = RoundedCornerShape(12.dp)
            ) {
              Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("ক্যামেরা দিয়ে তুলুন", fontSize = 12.sp)
            }

            OutlinedButton(
              onClick = {
                galleryLauncher.launch(
                  androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
              },
              modifier = Modifier.weight(1f).testTag("photo_pick_gallery_btn"),
              shape = RoundedCornerShape(12.dp)
            ) {
              Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("গ্যালারি থেকে নিন", fontSize = 12.sp)
            }
          }

          // Image Preview Container
          if (selectedBitmap != null) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(2.dp, TurquoisePrimary, RoundedCornerShape(12.dp))
            ) {
              Image(
                bitmap = selectedBitmap!!.asImageBitmap(),
                contentDescription = "নির্বাচিত কম্পোনেন্টের ছবি",
                modifier = Modifier.fillMaxSize()
              )
              IconButton(
                onClick = { viewModel.setSelectedPhotoBitmap(null) },
                modifier = Modifier
                  .align(Alignment.TopEnd)
                  .padding(8.dp)
                  .background(Navy900.copy(alpha = 0.7f), CircleShape)
              ) {
                Icon(Icons.Default.Close, contentDescription = "ছবি বাতিল করুন", tint = Color.White)
              }
            }
          } else {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
              contentAlignment = Alignment.Center
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.DocumentScanner, contentDescription = null, tint = Slate600, modifier = Modifier.size(40.dp))
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = "স্পষ্ট ও আলোকিত ক্লোজ-আপ ছবি তুলুন",
                  fontSize = 12.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }

          // Privacy Consent Checkbox
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Checkbox(
              checked = consent,
              onCheckedChange = { viewModel.setHasUserConsent(it) }
            )
            Text(
              text = "আমি সম্মতি দিচ্ছি যে এই ছবিটি কম্পোনেন্ট শনাক্তকরণের জন্য প্রক্রিয়াজাত করা হবে। কোনো ব্যক্তিগত তথ্য সংরক্ষিত হবে না।",
              fontSize = 11.sp,
              lineHeight = 15.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          // Analyze Button
          Button(
            onClick = { viewModel.executeComponentPhotoScan() },
            enabled = !isScanning && selectedBitmap != null && consent,
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("photo_analyze_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = TurquoisePrimary, contentColor = Navy900),
            shape = RoundedCornerShape(12.dp)
          ) {
            if (isScanning) {
              CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Navy900,
                strokeWidth = 2.dp
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text("AI ছবি বিশ্লেষণ করছে...", fontWeight = FontWeight.Bold)
            } else {
              Icon(Icons.Default.AutoFixHigh, contentDescription = null)
              Spacer(modifier = Modifier.width(8.dp))
              Text("ছবি বিশ্লেষণ করে কম্পোনেন্ট শনাক্ত করুন", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
          }

          if (errorMsg != null) {
            Text(text = errorMsg!!, color = CrimsonDanger, fontSize = 12.sp)
          }
        }
      }
    }

    // 4. Identification Result Card
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = res.componentName,
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = "আত্মবিশ্বাস স্কোর: ${res.confidenceScore}%",
                  fontSize = 12.sp,
                  color = TurquoisePrimary,
                  fontWeight = FontWeight.SemiBold
                )
              }

              Surface(
                color = Color(res.confidenceLevel.colorHex).copy(alpha = 0.2f),
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(
                  text = res.confidenceLevel.labelBengali,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(res.confidenceLevel.colorHex),
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
              }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

            // Function
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
              Text(text = "১. বাংলায় কাজ ও ভূমিকা:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TurquoisePrimary)
              Text(text = res.bengaliFunction, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
            }

            // Visual Evidence
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
              Text(text = "২. শনাক্তকরণের দৃশ্যমান ভিত্তি:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TurquoisePrimary)
              Text(text = res.visualEvidence, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
            }

            // Alternative Candidates
            if (res.alternativeCandidates.isNotEmpty()) {
              Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(text = "৩. সম্ভাব্য বিকল্প কম্পোনেন্ট:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TurquoisePrimary)
                Text(text = res.alternativeCandidates.joinToString(", "), fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
              }
            }

            // Multimeter Guide
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(TurquoiseContainer)
                .padding(10.dp)
            ) {
              Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(text = "৪. মাল্টিমিটার টেস্টিং পদ্ধতি:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Navy900)
                Text(text = res.multimeterTestingGuide, fontSize = 12.sp, lineHeight = 17.sp, color = Navy800)
              }
            }

            // Safety
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(AmberAlert.copy(alpha = 0.12f))
                .padding(10.dp)
            ) {
              Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(text = "৫. নিরাপত্তা সতর্কতা:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = AmberAlert)
                Text(text = res.safetyCaution, fontSize = 12.sp, lineHeight = 17.sp, color = MaterialTheme.colorScheme.onSurface)
              }
            }

            // User Correction Feedback
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = if (res.userFeedbackGiven) "ফিডব্যাক গ্রহণ করা হয়েছে ✓" else "ফলাফল কি সঠিক?",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Slate600
              )

              if (!res.userFeedbackGiven) {
                TextButton(onClick = { showFeedbackDialog = true }) {
                  Text("এটি সঠিক নয় (সংশোধন করুন)", color = CrimsonDanger, fontSize = 12.sp)
                }
              }
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

    // 6. Safety Warning
    item {
      SafetyWarningCard(
        title = "দৃষ্টিগত শনাক্তকরণ সীমাবদ্ধতা",
        warningText = "ফটোগ্রাফিক শনাক্তকরণ একটি রেফারেন্স গাইড। মাল্টিমিটারে বৈদ্যুতিক পরীক্ষা না করে কখনোই বোর্ডে কোনো কম্পোনেন্টকে নিশ্চিত নষ্ট ঘোষণা করবেন না।"
      )
    }
  }
}
