package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.model.BottomNavTab
import com.example.model.ScreenSection
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.TurquoiseBright
import com.example.ui.theme.TurquoiseContainer

@Composable
fun ZukiBottomNav(
  currentSection: ScreenSection,
  onTabSelected: (ScreenSection) -> Unit
) {
  val tabs = listOf(
    Triple(BottomNavTab.HOME, "হোম", Icons.Default.Home),
    Triple(BottomNavTab.DIAGNOSIS, "সমস্যা নির্ণয়", Icons.Default.Build),
    Triple(BottomNavTab.TOOLBOX, "টুলবক্স", Icons.Default.Handyman),
    Triple(BottomNavTab.COMPONENTS, "কম্পোনেন্ট", Icons.Default.Memory),
    Triple(BottomNavTab.SETTINGS, "প্রোফাইল", Icons.Default.Person)
  )

  NavigationBar(
    containerColor = Navy900,
    tonalElevation = 8.dp
  ) {
    tabs.forEach { (tab, labelBengali, icon) ->
      val isSelected = currentSection == tab.section

      NavigationBarItem(
        icon = {
          Icon(
            imageVector = icon,
            contentDescription = labelBengali
          )
        },
        label = {
          Text(
            text = labelBengali,
            style = MaterialTheme.typography.labelSmall
          )
        },
        selected = isSelected,
        onClick = { onTabSelected(tab.section) },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = Navy900,
          selectedTextColor = TurquoiseBright,
          indicatorColor = TurquoiseBright,
          unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
          unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier.testTag("bottom_nav_${tab.section.id}")
      )
    }
  }
}
