package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CircuitData
import com.example.model.CircuitDiagramItem
import com.example.ui.components.SafetyWarningCard
import com.example.ui.theme.*

@Composable
fun CircuitDiagramScreen() {
  var searchQuery by remember { mutableStateOf("") }
  var selectedCircuitId by remember { mutableStateOf<String?>(CircuitData.circuitDiagrams.first().id) }

  val diagrams = CircuitData.circuitDiagrams
  val filtered = remember(searchQuery) {
    if (searchQuery.isBlank()) diagrams
    else diagrams.filter {
      it.titleBengali.contains(searchQuery, ignoreCase = true) ||
          it.titleEnglish.contains(searchQuery, ignoreCase = true)
    }
  }

  val activeDiagram = diagrams.find { it.id == selectedCircuitId } ?: diagrams.first()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(vertical = 16.dp)
  ) {
    item {
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
          text = "সার্কিট ডায়াগ্রাম ও স্কিম্যাটিক বিভাগ",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onBackground
        )
        Text(
          text = "পাওয়ার, চার্জিং, ব্যাটারি ও ডিসপ্লে লাইনের কালার-কোডেড সার্কিট ও সিগন্যাল প্রবাহ।",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("সার্কিট বা লাইন খুঁজুন (VBUS, VBAT, LED_A)...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TurquoisePrimary) },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("circuit_search_input"),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
      )
    }

    // Circuit Category Selector Tabs
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        filtered.forEach { circ ->
          FilterChip(
            selected = circ.id == activeDiagram.id,
            onClick = { selectedCircuitId = circ.id },
            label = { Text(circ.titleBengali.take(12) + "...", fontSize = 11.sp) },
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // Schematic Interactive Canvas Graphic
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Navy900),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = activeDiagram.titleBengali,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 15.sp
              )
              Text(
                text = activeDiagram.voltageRange,
                color = TurquoiseBright,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
            Icon(
              imageVector = Icons.Default.ZoomIn,
              contentDescription = "জুম ভিউ",
              tint = TurquoiseBright
            )
          }

          // Custom Drawing Canvas for Circuit Tracks
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(140.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(Navy800)
              .border(1.dp, Navy600, RoundedCornerShape(8.dp))
          ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
              val w = size.width
              val h = size.height

              // Grid lines
              for (i in 1..8) {
                val x = (w / 9) * i
                drawLine(Color(0xFF1E3A5F), Offset(x, 0f), Offset(x, h), strokeWidth = 1f)
              }
              for (j in 1..4) {
                val y = (h / 5) * j
                drawLine(Color(0xFF1E3A5F), Offset(0f, y), Offset(w, y), strokeWidth = 1f)
              }

              // Bus Line
              drawLine(
                color = TurquoiseBright,
                start = Offset(20f, h * 0.35f),
                end = Offset(w - 20f, h * 0.35f),
                strokeWidth = 6f
              )

              // Branch to IC 1
              drawLine(
                color = AmberAlert,
                start = Offset(w * 0.3f, h * 0.35f),
                end = Offset(w * 0.3f, h * 0.75f),
                strokeWidth = 4f
              )

              // Branch to IC 2
              drawLine(
                color = EmeraldSuccess,
                start = Offset(w * 0.7f, h * 0.35f),
                end = Offset(w * 0.7f, h * 0.75f),
                strokeWidth = 4f
              )

              // Circles for Test Points (TP)
              drawCircle(Color.White, radius = 7f, center = Offset(w * 0.3f, h * 0.75f))
              drawCircle(Color.White, radius = 7f, center = Offset(w * 0.7f, h * 0.75f))
              drawCircle(Color.Red, radius = 7f, center = Offset(w * 0.5f, h * 0.35f))
            }

            Column(
              modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
            ) {
              Text(text = "● TP_MAIN (লাল)", fontSize = 10.sp, color = Color.White)
              Text(text = "● TP_LDO (সাদা)", fontSize = 10.sp, color = TurquoiseBright)
            }
          }

          Text(
            text = activeDiagram.description,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = Slate300
          )
        }
      }
    }

    // Safety Caution for this Circuit
    item {
      SafetyWarningCard(
        title = "সার্কিট নিরাপত্তা সতর্কতা",
        warningText = activeDiagram.safetyCaution
      )
    }

    // Key Components in this circuit
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
            text = "সার্কিটের প্রধান কম্পোনেন্টসমূহ:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
          activeDiagram.keyComponents.forEach { compName ->
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Text(text = "■", color = TurquoisePrimary, fontSize = 12.sp)
              Text(text = compName, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        }
      }
    }

    // Test Lines & Signal Explanation
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
            text = "প্রধান সিগন্যাল ও ভোল্টেজ লাইন বিশ্লেষণ:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
          activeDiagram.testLines.forEach { (lineName, purpose) ->
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
              Text(
                text = lineName,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = TurquoisePrimary
              )
              Text(
                text = purpose,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              HorizontalDivider(modifier = Modifier.padding(top = 4.dp), color = MaterialTheme.colorScheme.surfaceVariant)
            }
          }
        }
      }
    }
  }
}
