package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ComponentLibraryData
import com.example.data.SampleData
import com.example.ui.components.SafetyWarningCard
import com.example.ui.theme.*
import com.example.viewmodel.ZukiViewModel
import java.util.Locale

@Composable
fun ToolboxScreen(viewModel: ZukiViewModel) {
  val activeTab by viewModel.activeToolboxTab.collectAsState()

  val tabTitles = listOf(
    "ওহমের সূত্র",
    "পাওয়ার হিসাব",
    "কালার কোড",
    "মাল্টিমিটার",
    "ইউনিট কনভার্টার"
  )

  Column(modifier = Modifier.fillMaxSize()) {
    // Scrollable Tab Row
    ScrollableTabRow(
      selectedTabIndex = activeTab,
      containerColor = Navy800,
      contentColor = Color.White,
      edgePadding = 12.dp
    ) {
      tabTitles.forEachIndexed { index, title ->
        Tab(
          selected = activeTab == index,
          onClick = { viewModel.setToolboxTab(index) },
          text = {
            Text(
              text = title,
              fontWeight = if (activeTab == index) FontWeight.Bold else FontWeight.Normal,
              fontSize = 13.sp
            )
          },
          selectedContentColor = TurquoiseBright,
          unselectedContentColor = Slate300,
          modifier = Modifier.testTag("toolbox_tab_$index")
        )
      }
    }

    Box(modifier = Modifier.weight(1f)) {
      when (activeTab) {
        0 -> OhmsLawCalculatorView(viewModel)
        1 -> PowerCalculatorView(viewModel)
        2 -> ResistorColorCodeView(viewModel)
        3 -> MultimeterSimulatorView(viewModel)
        4 -> UnitConverterView(viewModel)
      }
    }
  }
}

// 1. Ohm's Law Calculator
@Composable
fun OhmsLawCalculatorView(viewModel: ZukiViewModel) {
  val mode by viewModel.ohmMode.collectAsState()
  val input1 by viewModel.ohmInput1.collectAsState()
  val input2 by viewModel.ohmInput2.collectAsState()

  // Calculate results based on mode
  val num1 = input1.toDoubleOrNull()
  val num2 = input2.toDoubleOrNull()

  var resultString = ""
  var formulaText = ""
  var calculationSteps = ""
  var isError = false

  when (mode) {
    0 -> {
      // Find Voltage V = I * R (num1 = Current in A, num2 = Resistance in Ω)
      formulaText = "সূত্র: V = I × R"
      if (num1 != null && num2 != null) {
        val v = num1 * num2
        resultString = String.format(Locale.US, "%.3f V (ভোল্ট)", v)
        calculationSteps = "$num1 A × $num2 Ω = ${String.format(Locale.US, "%.3f", v)} V"
      } else {
        isError = true
      }
    }
    1 -> {
      // Find Current I = V / R (num1 = Voltage in V, num2 = Resistance in Ω)
      formulaText = "সূত্র: I = V ÷ R"
      if (num1 != null && num2 != null) {
        if (num2 == 0.0) {
          resultString = "ত্রুটি: শূন্য (০) দিয়ে ভাগ করা সম্ভব নয়!"
          isError = true
        } else {
          val i = num1 / num2
          val i_mA = i * 1000.0
          resultString = String.format(Locale.US, "%.4f A (%.1f mA)", i, i_mA)
          calculationSteps = "$num1 V ÷ $num2 Ω = ${String.format(Locale.US, "%.4f", i)} A"
        }
      } else {
        isError = true
      }
    }
    2 -> {
      // Find Resistance R = V / I (num1 = Voltage in V, num2 = Current in A)
      formulaText = "সূত্র: R = V ÷ I"
      if (num1 != null && num2 != null) {
        if (num2 == 0.0) {
          resultString = "ত্রুটি: শূন্য (০) কারেন্ট হলে অসীম (OL) রোধ হয়!"
          isError = true
        } else {
          val r = num1 / num2
          resultString = if (r >= 1000) {
            String.format(Locale.US, "%.2f kΩ (%.1f Ω)", r / 1000.0, r)
          } else {
            String.format(Locale.US, "%.2f Ω (ওহম)", r)
          }
          calculationSteps = "$num1 V ÷ $num2 A = ${String.format(Locale.US, "%.2f", r)} Ω"
        }
      } else {
        isError = true
      }
    }
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Text(
        text = "ওহমের সূত্রের ক্যালকুলেটর (V = I × R)",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onBackground
      )
      Text(
        text = "যেকোনো দুটি মান ইনপুট দিলে অপরটির সুনির্দিষ্ট গণনা ও ধাপে ধাপে সমাধান দেখাবে।",
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    // Target Selection Tabs
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        listOf("ভোল্টেজ (V)", "কারেন্ট (I)", "রেজিস্ট্যান্স (R)").forEachIndexed { index, title ->
          FilterChip(
            selected = mode == index,
            onClick = { viewModel.setOhmMode(index) },
            label = { Text(title, fontSize = 12.sp) },
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // Inputs
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          val label1 = when (mode) {
            0 -> "কারেন্ট I (অ্যাম্পিয়ার / A)"
            1 -> "ভোল্টেজ V (ভোল্ট / V)"
            else -> "ভোল্টেজ V (ভোল্ট / V)"
          }
          val label2 = when (mode) {
            0 -> "রেজিস্ট্যান্স R (ওহম / Ω)"
            1 -> "রেজিস্ট্যান্স R (ওহম / Ω)"
            else -> "কারেন্ট I (অ্যাম্পিয়ার / A)"
          }

          OutlinedTextField(
            value = input1,
            onValueChange = { viewModel.setOhmInput1(it) },
            label = { Text(label1) },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("ohm_input_1"),
            singleLine = true
          )

          OutlinedTextField(
            value = input2,
            onValueChange = { viewModel.setOhmInput2(it) },
            label = { Text(label2) },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("ohm_input_2"),
            singleLine = true
          )
        }
      }
    }

    // Result Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isError) CrimsonDanger.copy(alpha = 0.1f) else TurquoiseContainer
        )
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(
            text = formulaText,
            fontWeight = FontWeight.Bold,
            color = Navy900,
            fontSize = 14.sp
          )
          Text(
            text = "ফলাফল: $resultString",
            fontWeight = FontWeight.Black,
            fontSize = 18.sp,
            color = if (isError) CrimsonDanger else Navy900
          )
          if (calculationSteps.isNotEmpty()) {
            Text(
              text = "গণনার ধাপ: $calculationSteps",
              fontSize = 13.sp,
              color = Navy800
            )
          }
        }
      }
    }

    item {
      SafetyWarningCard(
        title = "ওহমের সূত্রের ব্যবহারিক প্রয়োগ",
        warningText = "মোবাইলের বিভিন্ন সেন্স রেজিস্টরে (যেমন R010 = ০.০১ ওহম) কারেন্ট পরিমাপে ওহমের সূত্র ব্যবহৃত হয়। ভুল রেজিস্ট্যান্স ব্যবহার করলে আইসি পুড়ে যেতে পারে।"
      )
    }
  }
}

// 2. Power Calculator
@Composable
fun PowerCalculatorView(viewModel: ZukiViewModel) {
  val vInput by viewModel.powerVoltageInput.collectAsState()
  val iInput by viewModel.powerCurrentInput.collectAsState()

  val v = vInput.toDoubleOrNull()
  val i = iInput.toDoubleOrNull()

  var resultWatts = ""
  var resultMilliWatts = ""
  var isInvalid = false

  if (v != null && i != null) {
    val p = v * i
    resultWatts = String.format(Locale.US, "%.3f W (ওয়াট)", p)
    resultMilliWatts = String.format(Locale.US, "%.1f mW (মিলিওয়াট)", p * 1000.0)
  } else {
    isInvalid = true
    resultWatts = "সঠিক সংখ্যা ইনপুট দিন"
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Text(
        text = "পাওয়ার ক্যালকুলেটর (P = V × I)",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onBackground
      )
      Text(
        text = "মোবাইল চার্জিং এবং ব্যাটারি বিদ্যুৎ খরচের ওয়াট হিসাব করুন।",
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          OutlinedTextField(
            value = vInput,
            onValueChange = { viewModel.setPowerVoltageInput(it) },
            label = { Text("ভোল্টেজ V (যেমন: 5.0, 9.0, 12.0 V)") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("power_v_input"),
            singleLine = true
          )

          OutlinedTextField(
            value = iInput,
            onValueChange = { viewModel.setPowerCurrentInput(it) },
            label = { Text("কারেন্ট I (অ্যাম্পিয়ার / A, যেমন: 2.0, 3.0)") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("power_i_input"),
            singleLine = true
          )
        }
      }
    }

    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TurquoiseContainer)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(text = "সূত্র: P = V × I", fontWeight = FontWeight.Bold, color = Navy900)
          Text(
            text = "পাওয়ার: $resultWatts",
            fontWeight = FontWeight.Black,
            fontSize = 20.sp,
            color = if (isInvalid) CrimsonDanger else Navy900
          )
          if (!isInvalid) {
            Text(text = "মিলিওয়াট একক: $resultMilliWatts", fontSize = 14.sp, color = Navy800)
            if (v != null && i != null) {
              Text(
                text = "গণনা: $v V × $i A = ${String.format(Locale.US, "%.3f", v * i)} Watt",
                fontSize = 13.sp,
                color = Navy800
              )
            }
          }
        }
      }
    }

    item {
      SafetyWarningCard(
        title = "চার্জার ওয়াটেজ গাইড",
        warningText = "আধুনিক ফাস্ট চার্জার ৫V ২A = ১০W, ৯V ২A = ১৮W, এবং ১১V ৬A = ৬৬W পর্যন্ত পাওয়ার সরবরাহ করে।"
      )
    }
  }
}

// 3. Resistor Color Code Calculator
@Composable
fun ResistorColorCodeView(viewModel: ZukiViewModel) {
  val is5Band by viewModel.is5BandResistor.collectAsState()
  val b1 by viewModel.band1.collectAsState()
  val b2 by viewModel.band2.collectAsState()
  val b3 by viewModel.band3.collectAsState()
  val bMult by viewModel.bandMultiplier.collectAsState()
  val bTol by viewModel.bandTolerance.collectAsState()

  val colors = ComponentLibraryData.resistorColors

  // Compute Resistance
  val d1 = colors[b1].digitValue ?: 0
  val d2 = colors[b2].digitValue ?: 0
  val d3 = colors[b3].digitValue ?: 0
  val mult = colors[bMult].multiplier
  val tol = colors[bTol].tolerancePercent ?: 5.0

  val rawResistance = if (is5Band) {
    (d1 * 100 + d2 * 10 + d3) * mult
  } else {
    (d1 * 10 + d2) * mult
  }

  val formattedResistance = if (rawResistance >= 1000000) {
    String.format(Locale.US, "%.2f MΩ (মেগাওহম)", rawResistance / 1000000.0)
  } else if (rawResistance >= 1000) {
    String.format(Locale.US, "%.2f kΩ (কিলোওহম)", rawResistance / 1000.0)
  } else {
    String.format(Locale.US, "%.2f Ω (ওহম)", rawResistance)
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Text(
        text = "রেজিস্টর কালার কোড ক্যালকুলেটর",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onBackground
      )
      Text(
        text = "৪-ব্যান্ড অথবা ৫-ব্যান্ড নির্বাচন করে রেজিস্ট্যান্স মান বের করুন।",
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    // 4-band vs 5-band toggle
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        FilterChip(
          selected = !is5Band,
          onClick = { viewModel.setIs5BandResistor(false) },
          label = { Text("৪ ব্যান্ড রেজিস্টর") },
          modifier = Modifier.weight(1f)
        )
        FilterChip(
          selected = is5Band,
          onClick = { viewModel.setIs5BandResistor(true) },
          label = { Text("৫ ব্যান্ড রেজিস্টর") },
          modifier = Modifier.weight(1f)
        )
      }
    }

    // Resistor Visual Preview
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Navy700)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Visual Resistor Body
          Box(
            modifier = Modifier
              .width(220.dp)
              .height(44.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(Color(0xFFE2C499)), // Resistor tan body
            contentAlignment = Alignment.Center
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceEvenly,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(modifier = Modifier.width(10.dp).fillMaxHeight().background(Color(colors[b1].hexColor)))
              Box(modifier = Modifier.width(10.dp).fillMaxHeight().background(Color(colors[b2].hexColor)))
              if (is5Band) {
                Box(modifier = Modifier.width(10.dp).fillMaxHeight().background(Color(colors[b3].hexColor)))
              }
              Box(modifier = Modifier.width(10.dp).fillMaxHeight().background(Color(colors[bMult].hexColor)))
              Spacer(modifier = Modifier.width(10.dp))
              Box(modifier = Modifier.width(10.dp).fillMaxHeight().background(Color(colors[bTol].hexColor)))
            }
          }

          Text(
            text = "মান: $formattedResistance",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TurquoiseBright
          )
          Text(
            text = "টলারেন্স: ±${tol}%",
            fontSize = 13.sp,
            color = Color.White
          )
        }
      }
    }

    // Color Pickers for Bands
    item {
      Text(
        text = "ব্যান্ডের রঙ পরিবর্তন করুন:",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
      )
    }

    item {
      BandColorSelectorRow(
        title = "ব্যান্ড ১ (প্রথম ডিজিট): ${colors[b1].nameBengali}",
        colors = colors.filter { it.digitValue != null },
        selectedIndex = b1,
        onColorSelected = { viewModel.setResistorBand(1, it) }
      )
    }

    item {
      BandColorSelectorRow(
        title = "ব্যান্ড ২ (দ্বিতীয় ডিজিট): ${colors[b2].nameBengali}",
        colors = colors.filter { it.digitValue != null },
        selectedIndex = b2,
        onColorSelected = { viewModel.setResistorBand(2, it) }
      )
    }

    if (is5Band) {
      item {
        BandColorSelectorRow(
          title = "ব্যান্ড ৩ (তৃতীয় ডিজিট): ${colors[b3].nameBengali}",
          colors = colors.filter { it.digitValue != null },
          selectedIndex = b3,
          onColorSelected = { viewModel.setResistorBand(3, it) }
        )
      }
    }

    item {
      BandColorSelectorRow(
        title = "মাল্টিপ্লায়ার ব্যান্ড (গুণক): ${colors[bMult].nameBengali}",
        colors = colors,
        selectedIndex = bMult,
        onColorSelected = { viewModel.setResistorBand(4, it) }
      )
    }

    item {
      BandColorSelectorRow(
        title = "টলারেন্স ব্যান্ড (সহনশীলতা): ${colors[bTol].nameBengali}",
        colors = colors.filter { it.tolerancePercent != null },
        selectedIndex = bTol,
        onColorSelected = { viewModel.setResistorBand(5, it) }
      )
    }
  }
}

@Composable
fun BandColorSelectorRow(
  title: String,
  colors: List<com.example.model.ResistorBandColor>,
  selectedIndex: Int,
  onColorSelected: (Int) -> Unit
) {
  Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
    Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
      itemsIndexed(ComponentLibraryData.resistorColors) { index, c ->
        val isSelected = selectedIndex == index
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Color(c.hexColor))
            .border(
              width = if (isSelected) 3.dp else 1.dp,
              color = if (isSelected) TurquoiseBright else Slate600,
              shape = CircleShape
            )
            .clickable { onColorSelected(index) }
        )
      }
    }
  }
}

// 4. Multimeter Simulator
@Composable
fun MultimeterSimulatorView(viewModel: ZukiViewModel) {
  val scenarioIndex by viewModel.multimeterScenarioIndex.collectAsState()
  val dialMode by viewModel.multimeterDialMode.collectAsState()
  val isFaulty by viewModel.isComponentFaulty.collectAsState()
  val isProbesConnected by viewModel.areProbesConnected.collectAsState()

  val scenarios = SampleData.multimeterScenarios
  val activeScenario = scenarios[scenarioIndex]

  // Compute live simulated reading
  val lcdReading = remember(isProbesConnected, dialMode, isFaulty, activeScenario) {
    if (!isProbesConnected) {
      if (dialMode == "Buzzer / Diode") "1  .  " else "0.00"
    } else {
      if (isFaulty) {
        String.format(Locale.US, "%.3f", activeScenario.simulatedFaultyValue)
      } else {
        String.format(Locale.US, "%.3f", activeScenario.simulatedHealthyValue)
      }
    }
  }

  val isBeeping = isProbesConnected && (isFaulty && activeScenario.makesBeepSound)

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Text(
        text = "মাল্টিমিটার শেখা ও ভার্চুয়াল ল্যাব",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onBackground
      )
      Text(
        text = "প্র্যাকটিক্যালভাবে মাল্টিমিটার মোড সিলেক্ট করুন এবং কম্পোনেন্ট টেস্ট করে রিডিং দেখুন।",
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    // Virtual Multimeter Body
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Navy900),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          // Top Brand & Mode Label
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "ZUKI DMM-99", fontWeight = FontWeight.Bold, color = TurquoiseBright, fontSize = 14.sp)
            Text(text = dialMode, color = Slate300, fontSize = 12.sp)
          }

          // Backlit Digital LCD Screen
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(80.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(Color(0xFF8BA888)) // Realistic LCD Green
              .border(2.dp, Navy700, RoundedCornerShape(8.dp))
              .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = lcdReading,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                fontSize = 36.sp,
                color = Color(0xFF1B2A1E)
              )
              Column(horizontalAlignment = Alignment.End) {
                Text(text = activeScenario.unitSymbol, fontWeight = FontWeight.Bold, color = Color(0xFF1B2A1E), fontSize = 14.sp)
                if (isBeeping) {
                  Text(text = "BEEP ♫", fontWeight = FontWeight.Black, color = CrimsonDanger, fontSize = 12.sp)
                }
              }
            }
          }

          // Dial Buttons
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            listOf("DC Voltage (20V)", "Buzzer / Diode", "Resistance 200Ω").forEach { mode ->
              FilterChip(
                selected = dialMode == mode,
                onClick = { viewModel.setMultimeterDialMode(mode) },
                label = { Text(mode, fontSize = 10.sp) },
                modifier = Modifier.weight(1f)
              )
            }
          }

          // Interactive Probe Connection Button
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = { viewModel.toggleProbesConnected() },
              modifier = Modifier
                .weight(1f)
                .testTag("multimeter_probes_btn"),
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isProbesConnected) EmeraldSuccess else TurquoisePrimary,
                contentColor = Navy900
              ),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text(
                text = if (isProbesConnected) "প্রোব সংযোগ বিচ্ছিন্ন করুন" else "প্রোব স্পর্শ করুন (Touch Probes)",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              )
            }

            OutlinedButton(
              onClick = { viewModel.toggleComponentFaulty() },
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text(
                text = if (isFaulty) "অবস্থা: নষ্ট পার্ট" else "অবস্থা: সুস্থ পার্ট",
                fontSize = 12.sp,
                color = if (isFaulty) CrimsonDanger else TurquoiseBright
              )
            }
          }
        }
      }
    }

    // Scenario Picker
    item {
      Text(
        text = "অনুশীলন করার টেস্ট দৃশ্যকল্প নির্বাচন:",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
      )
    }

    itemsIndexed(scenarios) { index, s ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { viewModel.setMultimeterScenario(index) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (scenarioIndex == index) TurquoiseContainer else MaterialTheme.colorScheme.surface
        )
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = s.componentTitle,
            fontWeight = FontWeight.Bold,
            color = if (scenarioIndex == index) Navy900 else MaterialTheme.colorScheme.onSurface
          )
          Text(text = s.englishTitle, fontSize = 11.sp, color = Slate600)
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "প্রোব সংযোগ: ${s.probeInstructions}", fontSize = 12.sp, color = Navy800)
        }
      }
    }
  }
}

// 5. Unit Converter
@Composable
fun UnitConverterView(viewModel: ZukiViewModel) {
  val catIndex by viewModel.converterCategory.collectAsState()
  val inputVal by viewModel.converterInput.collectAsState()

  val categories = listOf("ভোল্টেজ (V)", "কারেন্ট (A)", "রেজিস্ট্যান্স (Ω)", "পাওয়ার (W)", "ক্যাপাসিট্যান্স (F)")
  val num = inputVal.toDoubleOrNull() ?: 0.0

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Text(
        text = "ইলেকট্রনিক্স ইউনিট কনভার্টার",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onBackground
      )
      Text(
        text = "তাৎক্ষণিকভাবে এবং নির্ভুলভাবে টেকনিক্যাল একক রূপান্তর করুন।",
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    // Category Selector
    item {
      ScrollableTabRow(
        selectedTabIndex = catIndex,
        containerColor = MaterialTheme.colorScheme.surface,
        edgePadding = 0.dp
      ) {
        categories.forEachIndexed { i, title ->
          Tab(
            selected = catIndex == i,
            onClick = { viewModel.setConverterCategory(i) },
            text = { Text(title, fontSize = 12.sp) }
          )
        }
      }
    }

    // Input Field
    item {
      OutlinedTextField(
        value = inputVal,
        onValueChange = { viewModel.setConverterInput(it) },
        label = { Text("মূল্য লিখুন (Enter Value)") },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("converter_input"),
        singleLine = true
      )
    }

    // Conversion Table Card
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
          when (catIndex) {
            0 -> {
              // Voltage
              ConverterRow("ভোল্ট (V)", "${String.format(Locale.US, "%.4f", num)} V")
              ConverterRow("মিলিভোল্ট (mV)", "${String.format(Locale.US, "%.1f", num * 1000)} mV")
              ConverterRow("কিলোভোল্ট (kV)", "${String.format(Locale.US, "%.6f", num / 1000)} kV")
            }
            1 -> {
              // Current
              ConverterRow("অ্যাম্পিয়ার (A)", "${String.format(Locale.US, "%.4f", num)} A")
              ConverterRow("মিলিঅ্যাম্পিয়ার (mA)", "${String.format(Locale.US, "%.2f", num * 1000)} mA")
              ConverterRow("মাইক্রোঅ্যাম্পিয়ার (µA)", "${String.format(Locale.US, "%.1f", num * 1000000)} µA")
            }
            2 -> {
              // Resistance
              ConverterRow("ওহম (Ω)", "${String.format(Locale.US, "%.2f", num)} Ω")
              ConverterRow("কিলোওহম (kΩ)", "${String.format(Locale.US, "%.4f", num / 1000)} kΩ")
              ConverterRow("মেগাওহম (MΩ)", "${String.format(Locale.US, "%.6f", num / 1000000)} MΩ")
            }
            3 -> {
              // Power
              ConverterRow("ওয়াট (W)", "${String.format(Locale.US, "%.3f", num)} W")
              ConverterRow("মিলিওয়াট (mW)", "${String.format(Locale.US, "%.1f", num * 1000)} mW")
              ConverterRow("কিলোওয়াট (kW)", "${String.format(Locale.US, "%.6f", num / 1000)} kW")
            }
            4 -> {
              // Capacitance
              ConverterRow("মাইক্রোফ্যারাড (µF)", "${String.format(Locale.US, "%.3f", num)} µF")
              ConverterRow("ন্যানোফ্যারাড (nF)", "${String.format(Locale.US, "%.1f", num * 1000)} nF")
              ConverterRow("পিকোফ্যারাড (pF)", "${String.format(Locale.US, "%.1f", num * 1000000)} pF")
            }
          }
        }
      }
    }
  }
}

@Composable
fun ConverterRow(label: String, value: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = label, fontWeight = FontWeight.Medium, fontSize = 13.sp)
    Text(text = value, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TurquoisePrimary)
  }
}
