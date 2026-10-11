package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ScreenSection
import com.example.ui.components.SafetyWarningCard
import com.example.ui.theme.*
import com.example.viewmodel.ZukiViewModel
import java.util.Locale

@Composable
fun OfflineManagerScreen(
  viewModel: ZukiViewModel,
  onNavigateToSection: (ScreenSection) -> Unit
) {
  val isOfflineSimulated by viewModel.isSimulatedOffline.collectAsState()
  val packs by viewModel.offlinePacks.collectAsState()

  val totalDownloadedMb = packs.filter { it.isDownloaded }.sumOf { it.sizeMb }

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
                text = "অফলাইন কনটেন্ট ও স্টোরেজ ম্যানেজার",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
              )
              Text(
                text = "ইন্টারনেট ছাড়াই সম্পূর্ণ সার্ভিসিং একাডেমি",
                fontSize = 12.sp,
                color = TurquoiseBright
              )
            }

            Surface(
              color = if (isOfflineSimulated) AmberAlert else EmeraldSuccess,
              shape = RoundedCornerShape(8.dp)
            ) {
              Text(
                text = if (isOfflineSimulated) "অফলাইন মোড" else "অনলাইন মোড",
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = Navy900,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }

          Text(
            text = "বর্তমানে মোট ${String.format(Locale.US, "%.1f", totalDownloadedMb)} MB ডেটা স্থানীয়ভাবে সংরক্ষিত আছে। কোনো ইন্টারনেট ছাড়াই সমস্যা নির্ণয়, পাঠ, কুইজ এবং ক্যালকুলেটর চলবে।",
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = Slate300
          )
        }
      }
    }

    // 2. Offline Simulation Switch Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "অফলাইন মোড পরীক্ষা করুন (Simulate Offline)",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "ইন্টারনেট বিচ্ছিন্ন থাকা অবস্থায় অ্যাপ কীভাবে কাজ করে তা তাৎক্ষণিক যাচাই করতে এই সুইচটি চালু করুন।",
              fontSize = 12.sp,
              color = Slate600
            )
          }

          Switch(
            checked = isOfflineSimulated,
            onCheckedChange = { viewModel.toggleOfflineSimulation() },
            modifier = Modifier.testTag("offline_toggle_switch")
          )
        }
      }
    }

    // 3. Offline Package List
    item {
      Text(
        text = "ডাউনলোডযোগ্য পাঠ প্যাকেজসমূহ:",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
      )
    }

    items(packs) { pack ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("pack_item_${pack.packId}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (pack.isDownloaded) TurquoiseContainer else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Icon(
            imageVector = if (pack.isDownloaded) Icons.Default.CheckCircle else Icons.Default.DownloadForOffline,
            contentDescription = null,
            tint = if (pack.isDownloaded) EmeraldSuccess else TurquoisePrimary,
            modifier = Modifier.size(32.dp)
          )

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = pack.packName,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = if (pack.isDownloaded) Navy900 else MaterialTheme.colorScheme.onSurface
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Text(
                text = "${pack.sizeMb} MB",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (pack.isDownloaded) Navy800 else TurquoisePrimary
              )
              Text(
                text = "• ${pack.lessonCount} টি আইটেম",
                fontSize = 12.sp,
                color = if (pack.isDownloaded) Navy800 else Slate600
              )
            }
          }

          Button(
            onClick = { viewModel.toggleOfflinePack(pack.packId) },
            colors = ButtonDefaults.buttonColors(
              containerColor = if (pack.isDownloaded) CrimsonDanger else TurquoisePrimary,
              contentColor = if (pack.isDownloaded) Color.White else Navy900
            ),
            shape = RoundedCornerShape(8.dp)
          ) {
            Text(
              text = if (pack.isDownloaded) "মুছুন" else "ডাউনলোড",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }

    // 4. Offline Features Checklist Card
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
            text = "অফলাইনে যেসব ফিচার নির্বিঘ্নে চলবে:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
          listOf(
            "১৬টি সমস্যা শনাক্তকারী সিদ্ধান্তভিত্তিক গাইড (Decision Tree)",
            "ওহমের সূত্রের ক্যালকুলেটর ও পাওয়ার ক্যালকুলেটর",
            "রেজিস্টর কালার কোড ও ইউনিট কনভার্টার",
            "১২টি প্রধান ইলেকট্রনিক কম্পোনেন্ট লাইব্রেরি ও টেস্ট রুলস",
            "সকল সার্কিট ডায়াগ্রামের লেআউট ও লাইন ব্যাখ্যা",
            "২০টি বাংলা কুইজ এবং সমস্ত সংরক্ষিত বুকমার্ক"
          ).forEach { feat ->
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(16.dp))
              Text(text = feat, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        }
      }
    }

    // 5. Practical Disclaimer
    item {
      SafetyWarningCard(
        title = "অফলাইন ডেটা সংরক্ষণ নির্দেশিকা",
        warningText = "ক্যাশ করা সমস্ত পাঠ ফোনের অভ্যন্তরীণ মেমরিতে থাকে। অ্যাপের ডেটা ক্লিয়ার না করা পর্যন্ত এই অফলাইন ফাইলগুলো সবসময় সুরক্ষিত থাকে।"
      )
    }
  }
}
