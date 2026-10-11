package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Navy800
import com.example.ui.theme.Slate600
import com.example.ui.theme.TurquoiseBright
import com.example.ui.theme.TurquoisePrimary

@Composable
fun AdBannerSlot(
  adsEnabled: Boolean,
  onGoPremiumClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  if (!adsEnabled) return

  Card(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp)),
    colors = CardDefaults.cardColors(containerColor = Navy800.copy(alpha = 0.95f)),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(TurquoisePrimary.copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "বিজ্ঞাপন",
            tint = TurquoiseBright,
            modifier = Modifier.size(20.dp)
          )
        }

        Column {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Surface(
              color = Slate600.copy(alpha = 0.3f),
              shape = RoundedCornerShape(3.dp)
            ) {
              Text(
                text = "বিজ্ঞাপন / Ad",
                fontSize = 9.sp,
                color = TurquoiseBright,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
              )
            }
            Text(
              text = "বিশ্বস্ত মোবাইল টুলস পার্টনার",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }
          Text(
            text = "অরিজিনাল হট এয়ার গান ও মাল্টিমিটার সরঞ্জাম",
            fontSize = 11.sp,
            color = Color.LightGray
          )
        }
      }

      TextButton(
        onClick = onGoPremiumClick,
        colors = ButtonDefaults.textButtonColors(contentColor = TurquoiseBright)
      ) {
        Text(text = "বিজ্ঞাপন সরান", fontSize = 11.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}
