package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ComponentLibraryData
import com.example.model.ComponentDetail
import com.example.ui.theme.*

@Composable
fun ComponentLibraryScreen() {
  var searchQuery by remember { mutableStateOf("") }
  var expandedId by remember { mutableStateOf<String?>(null) }

  val filteredComponents = remember(searchQuery) {
    if (searchQuery.isBlank()) ComponentLibraryData.componentsList
    else ComponentLibraryData.componentsList.filter {
      it.bengaliName.contains(searchQuery, ignoreCase = true) ||
          it.englishName.contains(searchQuery, ignoreCase = true) ||
          it.symbol.contains(searchQuery, ignoreCase = true)
    }
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
    contentPadding = PaddingValues(vertical = 16.dp)
  ) {
    item {
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
          text = "মোবাইল কম্পোনেন্ট লাইব্রেরি",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onBackground
        )
        Text(
          text = "মাদারবোর্ডের ১২টি প্রধান যন্ত্রাংশ, সার্কিট প্রতীক, টেস্টিং পদ্ধতি ও ত্রুটি বিশ্লেষণ।",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("যন্ত্রাংশ খুঁজুন (রেজিস্টর, ক্যাপাসিটর, IC, ডায়োড)...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TurquoisePrimary) },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("component_search_input"),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
      )
    }

    items(filteredComponents) { comp ->
      val isExpanded = expandedId == comp.id

      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { expandedId = if (isExpanded) null else comp.id }
          .testTag("component_card_${comp.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isExpanded) TurquoiseContainer else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Box(
              modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(if (isExpanded) TurquoisePrimary else Navy700),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = comp.symbol.take(2).trim(),
                fontWeight = FontWeight.Black,
                fontSize = 16.sp,
                color = if (isExpanded) Navy900 else Color.White
              )
            }

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = comp.bengaliName,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = if (isExpanded) Navy900 else MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = comp.englishName,
                fontSize = 12.sp,
                color = if (isExpanded) Navy800 else Slate600
              )
            }

            Icon(
              imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
              contentDescription = null,
              tint = if (isExpanded) Navy900 else TurquoisePrimary
            )
          }

          // Short summary preview
          Text(
            text = comp.shortFunction,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = if (isExpanded) Navy800 else MaterialTheme.colorScheme.onSurfaceVariant
          )

          // Expanded Details
          if (isExpanded) {
            HorizontalDivider(color = Navy800.copy(alpha = 0.2f))

            ComponentDetailField(
              title = "সার্কিট প্রতীক (Symbol):",
              content = comp.symbol
            )

            ComponentDetailField(
              title = "শনাক্ত করার উপায় (Identification):",
              content = comp.howToIdentify
            )

            ComponentDetailField(
              title = "মাল্টিমিটার টেস্টিং পদ্ধতি (Testing):",
              content = comp.multimeterTestingRule
            )

            ComponentDetailField(
              title = "সাধারণ ত্রুটি ও ফল্ট (Fault Symptoms):",
              content = comp.commonFaults
            )

            ComponentDetailField(
              title = "নিরাপত্তা সতর্কতা (Safety Rule):",
              content = comp.safetyNotes
            )
          }
        }
      }
    }
  }
}

@Composable
fun ComponentDetailField(title: String, content: String) {
  Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
    Text(
      text = title,
      fontWeight = FontWeight.Bold,
      fontSize = 12.sp,
      color = Navy900
    )
    Text(
      text = content,
      fontSize = 13.sp,
      lineHeight = 18.sp,
      color = Navy800
    )
  }
}
