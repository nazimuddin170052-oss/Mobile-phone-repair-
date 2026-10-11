package com.example.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.ZukiViewModel

@Composable
fun SettingsScreen(viewModel: ZukiViewModel) {
  val isDark by viewModel.isDarkMode.collectAsState()
  val fontScale by viewModel.fontSizeScale.collectAsState()
  val entitlement by viewModel.userEntitlement.collectAsState()
  var showResetDialog by remember { mutableStateOf(false) }

  if (showResetDialog) {
    AlertDialog(
      onDismissRequest = { showResetDialog = false },
      title = { Text(text = "অগ্রগতি ও বুকমার্ক রিসেট", fontWeight = FontWeight.Bold) },
      text = { Text(text = "আপনি কি নিশ্চিত যে আপনার সমস্ত শেখার অগ্রগতি, সম্পন্ন পাঠ এবং বুকমার্ক মুছে ফেলতে চান?") },
      confirmButton = {
        Button(
          onClick = {
            viewModel.resetAllProgress()
            showResetDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = CrimsonDanger)
        ) {
          Text("হ্যাঁ, মুছে ফেলুন")
        }
      },
      dismissButton = {
        TextButton(onClick = { showResetDialog = false }) {
          Text("বাতিল করুন")
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
    // 1. Profile Header
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Navy800)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          androidx.compose.foundation.Image(
            painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.ic_zuki_app_icon),
            contentDescription = "ZUKI একাডেমি লোগো",
            modifier = Modifier
              .size(54.dp)
              .clip(RoundedCornerShape(12.dp))
              .border(1.5.dp, TurquoisePrimary, RoundedCornerShape(12.dp))
          )

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "জুকি টেকনিশিয়ান",
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp,
              color = Color.White
            )
            Text(
              text = if (entitlement.tier == com.example.model.SubscriptionTier.FREE) "ফ্রি শিক্ষার্থী অ্যাকাউন্ট" else "প্রিমিয়াম ভিআইপি টেকনিশিয়ান",
              fontSize = 12.sp,
              color = TurquoiseBright
            )
            Surface(
              color = Navy700,
              shape = RoundedCornerShape(4.dp),
              modifier = Modifier.padding(top = 4.dp)
            ) {
              Text(
                text = if (entitlement.tier == com.example.model.SubscriptionTier.FREE) "বিজ্ঞাপন সক্রিয়" else "বিজ্ঞাপনমুক্ত ও আনলিমিটেড AI",
                fontSize = 10.sp,
                color = if (entitlement.tier == com.example.model.SubscriptionTier.FREE) AmberAlert else EmeraldSuccess,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = { viewModel.navigateTo(com.example.model.ScreenSection.PREMIUM) },
            colors = ButtonDefaults.buttonColors(containerColor = TurquoisePrimary, contentColor = Navy900),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(1f).testTag("settings_premium_btn")
          ) {
            Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("প্রিমিয়াম প্ল্যান", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }

          OutlinedButton(
            onClick = { viewModel.navigateTo(com.example.model.ScreenSection.OFFLINE_MANAGER) },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TurquoiseBright),
            border = androidx.compose.foundation.BorderStroke(1.dp, TurquoiseBright),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(1f).testTag("settings_offline_btn")
          ) {
            Icon(Icons.Default.CloudDownload, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("অফলাইন প্যাক", fontSize = 12.sp)
          }
        }
      }
    }

    // 2. Preferences
    item {
      Text(
        text = "অ্যাপ্লিকেশন সেটিংস",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
      )
    }

    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          // Dark Mode Toggle
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(text = "ডার্ক মোড (Dark Theme)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
              Text(text = "চোখের সুরক্ষার জন্য নেভি ডার্ক মোড", fontSize = 12.sp, color = Slate600)
            }
            Switch(
              checked = isDark,
              onCheckedChange = { viewModel.toggleDarkMode() },
              modifier = Modifier.testTag("dark_mode_switch")
            )
          }

          HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.surfaceVariant)

          // Font Scale
          Text(text = "বাংলা ফন্ট সাইজ (Font Scaling)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
          Text(text = "লেখার আকার পছন্দ অনুযায়ী বড় বা ছোট করুন", fontSize = 12.sp, color = Slate600)

          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            FilterChip(
              selected = fontScale == 0.9f,
              onClick = { viewModel.setFontSizeScale(0.9f) },
              label = { Text("ছোট (Small)") },
              modifier = Modifier.weight(1f)
            )
            FilterChip(
              selected = fontScale == 1.0f,
              onClick = { viewModel.setFontSizeScale(1.0f) },
              label = { Text("স্বাভাবিক (Normal)") },
              modifier = Modifier.weight(1f)
            )
            FilterChip(
              selected = fontScale == 1.15f,
              onClick = { viewModel.setFontSizeScale(1.15f) },
              label = { Text("বড় (Large)") },
              modifier = Modifier.weight(1f)
            )
          }
        }
      }
    }

    // 3. Data Storage & Reset
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Text(
            text = "ডেটা ও অফলাইন সংরক্ষণ",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
          )
          Text(
            text = "আপনার সমস্ত বুকমার্ক, প্রগ্রেস এবং কুইজ রেকর্ড ফোনে নিরাপদে সংরক্ষিত থাকে। কোনো ইন্টারনেটের প্রয়োজন নেই।",
            fontSize = 12.sp,
            color = Slate600
          )

          OutlinedButton(
            onClick = { showResetDialog = true },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("reset_data_btn"),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = CrimsonDanger)
          ) {
            Icon(Icons.Default.DeleteForever, contentDescription = null, tint = CrimsonDanger)
            Spacer(modifier = Modifier.width(6.dp))
            Text("সমস্ত অগ্রগতি ও ডেটা রিসেট করুন")
          }
        }
      }
    }

    // 4. About ZUKI Mobile Servicing
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
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            androidx.compose.foundation.Image(
              painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.ic_zuki_app_icon),
              contentDescription = "ZUKI অফিসিয়াল লোগো",
              modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.2.dp, TurquoisePrimary, RoundedCornerShape(10.dp))
            )
            Column {
              Text(
                text = "ZUKI মোবাইল সার্ভিসিং একাডেমি",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = TurquoisePrimary
              )
              Text(
                text = "অফিসিয়াল ভেরিফাইড ইনস্টিটিউট",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = EmeraldSuccess
              )
            }
          }
          Text(
            text = "ZUKI হলো বাংলাদেশের সবচেয়ে আধুনিক, নির্ভরযোগ্য এবং সম্পূর্ণ বাংলা ভিত্তিক মোবাইল হার্ডওয়্যার ও সফটওয়্যার মেরামত লার্নিং প্ল্যাটফর্ম।",
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(4.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "সংস্করণ: ২.৪.০ (অফলাইন প্রিমিয়াম)", fontSize = 11.sp, color = Slate600)
            Text(text = "ZUKI Tech Lab", fontSize = 11.sp, color = TurquoisePrimary)
          }
        }
      }
    }
  }
}
