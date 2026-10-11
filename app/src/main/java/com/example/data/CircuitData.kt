package com.example.data

import com.example.model.CircuitDiagramItem

object CircuitData {

  val circuitDiagrams = listOf(
    CircuitDiagramItem(
      id = "circ_charging",
      titleBengali = "চার্জিং ও ওভিপি প্রোটেকশন সার্কিট",
      titleEnglish = "USB Type-C Charging & OVP Protection Circuit",
      voltageRange = "৫.০V - ১২.০V (QC/PD ফাস্ট চার্জিং)",
      description = "ইউএসবি পোর্ট থেকে বিদ্যুৎ গ্রহণ করে ওভিপি (Over Voltage Protection) আইসির মাধ্যমে ফিল্টার হয়ে প্রধান চার্জিং আইসি (PMI/BQ) ও ব্যাটারিতে পৌঁছানোর পূর্ণ প্রবাহ ডায়াগ্রাম।",
      keyComponents = listOf(
        "Type-C Connector (VBUS, GND, CC1, CC2, D+, D-)",
        "OVP IC (Over Voltage Clamping & MOSFET)",
        "Charging IC / Sub-PMIC (BQ25601 / PMI8998)",
        "Charging Boost/Buck Coil (1.0uH / 2.2uH)",
        "Current Sense Resistor (R010 / 0.01Ω)"
      ),
      testLines = listOf(
        "VBUS_IN (5V)" to "চার্জার প্লাগ ইনপুট ভোল্টেজ (চার্জার থেকে সরাসরি ৫V-৯V আসে)",
        "VBUS_OVP_OUT (5V)" to "ওভিপি আইসির সুরক্ষিত আউটপুট ভোল্টেজ (চার্জিং আইসির ইনপুটে যায়)",
        "USB_DP / USB_DM (D+/D-)" to "ফাস্ট চার্জিং নেগোসিয়েশন ও ইউএসবি ডেটা লাইন (মান ৫০০-৭৫০mV)",
        "CC1 / CC2 (Type-C)" to "কেবলের দিক ও চার্জিং ক্ষমতা ডিটেকশন লাইন (১.৭V পুল-আপ)",
        "VBAT_CHG (4.2V)" to "চার্জিং আইসি থেকে ব্যাটারিতে প্রবাহিত নিরাপদ চার্জিং ভোল্টেজ"
      ),
      safetyCaution = "ওভিপি আইসি শর্ট বাইপাস করার সময় চার্জার অরিজিনাল কি না নিশ্চিত হোন, নকল চার্জারের হাই ভোল্টেজ সরাসরি চার্জিং আইসি জ্বালিয়ে দেবে।"
    ),
    CircuitDiagramItem(
      id = "circ_power",
      titleBengali = "প্রাইমারি পাওয়ার ও পিএমআইসি বিতরণ সার্কিট",
      titleEnglish = "Primary Power System (VBAT, VPH_PWR & Buck/LDO)",
      voltageRange = "৩.৭V - ৪.৪V (VBAT/VPH) / ০.৮V - ২.৮V (Secondary)",
      description = "ব্যাটারি অথবা চার্জার থেকে মাদারবোর্ডের প্রধান পাওয়ার লাইন (VPH_PWR/VCC_MAIN) সৃষ্টি এবং প্রধান পাওয়ার আইসি (PMIC) কর্তৃক সিপিইউ, র্যাম ও ক্যামেরার জন্য বিভিন্ন বাক ও এলডিও ভোল্টেজ বিতরণ।",
      keyComponents = listOf(
        "Battery Connector & Fuel Gauge IC",
        "DC-DC Switching Inductors (Buck Coils L1, L2, L3)",
        "Main PMIC (Power Management IC)",
        "Filter Capacitors (High Capacitance MLCC)",
        "System Crystal Oscillator (19.2MHz / 26MHz)"
      ),
      testLines = listOf(
        "VBAT (3.8V)" to "সরাসরি ব্যাটারি থেকে আসা আনরেগুলেটেড ভোল্টেজ",
        "VPH_PWR (3.7V - 4.2V)" to "সিস্টেমের প্রধান জীবনরেখা; এই লাইনে শর্ট হলে পুরো ফোন ডেড হয়",
        "VDD_CORE (0.8V)" to "সিপিইউ কোর প্রসেসিং ভোল্টেজ (হাই কারেন্ট বাক লাইন)",
        "VREG_S4 (1.8V)" to "মেমোরি, সেন্সর ও আইও লজিক ইন্টারফেস লাইন",
        "VDD_LPDDR (1.1V)" to "র‌্যাম মেমোরির প্রধান অপারেটিং সাপ্লাই ভোল্টেজ"
      ),
      safetyCaution = "ভিডিডি কোরের (VDD_CORE) লাইনে কখনো ১.০ ভোল্টের বেশি ডিসি ইনজেক্ট করবেন না, সরাসরি সিপিইউ প্রসেসর নষ্ট হয়ে যাবে।"
    ),
    CircuitDiagramItem(
      id = "circ_backlight",
      titleBengali = "ডিসপ্লে ও ব্যাকলাইট বুস্ট সার্কিট",
      titleEnglish = "Display & Backlight Boost Circuit (20V - 40V)",
      voltageRange = "৩.৭V (ইনপুট) ➔ ২০V - ৪০V (বুস্টেড আউটপুট)",
      description = "এলসিডি ডিসপ্লের ভেতরের এলইডি ব্যাকলাইট জ্বালানোর জন্য ব্যাটারির ৩.৭ ভোল্টকে বুস্ট কয়েল ও শটকি ডায়োডের সাহায্যে উচ্চ ২০ থেকে ৪০ ভোল্টে রূপান্তরের সার্কিট।",
      keyComponents = listOf(
        "Backlight Driver IC (6-pin / 12-pin BGA)",
        "Boost Inductor (10uH / 22uH)",
        "Fast Recovery Schottky Diode",
        "High Voltage Filter Capacitor (50V rated)",
        "Display FPC Connector (LED_A & LED_K pins)"
      ),
      testLines = listOf(
        "VPH_PWR (3.7V)" to "ব্যাকলাইট কয়েল ও আইসির ইনপুট সাপ্লাই লাইন",
        "LED_A / VLED+ (20V - 40V)" to "ডিসপ্লে এলইডি অ্যানোড (পজিটিভ); ডিসপ্লে অন হলে বুস্ট হয়",
        "LED_K1 / LED_K2" to "ক্যাথোড (নেগেটিভ) ফিডব্যাক লাইন; ব্রাইটনেস নিয়ন্ত্রণের কাজ করে",
        "LCM_1.8V (VREG_LDO)" to "ডিসপ্লে লজিক অন করার সিগন্যাল",
        "PWM / EN (Enable)" to "সিপিইউ থেকে ব্যাকলাইট চালু করার কন্ট্রোল পালস"
      ),
      safetyCaution = "ডিসপ্লে কানেক্টরে ২০-৪০ ভোল্ট বুস্ট থাকা অবস্থায় কখনো টুইজার বা ধাতব বস্তু লাগাবেন না, সঙ্গে সঙ্গে স্পার্ক করে আইসি পুড়ে যাবে।"
    ),
    CircuitDiagramItem(
      id = "circ_battery",
      titleBengali = "ব্যাটারি সুরক্ষা ও তাপমাত্রা পরিমাপ সার্কিট",
      titleEnglish = "Battery Protection & Fuel Gauge NTC Circuit",
      voltageRange = "০V - ৪.৪V (BATT_V) / ১.৮V (NTC BIAS)",
      description = "লিথিয়াম ব্যাটারির চার্জের সঠিক শতাংশ গণনা (Fuel Gauge), ওভার-ডিসচার্জ ও ওভার-চার্জ সুরক্ষা (Protection Board) এবং এনটিসি থার্মিস্টর দ্বারা তাপমাত্রা পর্যবেক্ষণ সার্কিট।",
      keyComponents = listOf(
        "Battery PCM Board (Protection Circuit Module)",
        "Dual N-Channel Protection MOSFET",
        "NTC Thermistor (10kΩ / 47kΩ / 100kΩ)",
        "Battery Fuel Gauge IC",
        "Battery Connector (Pins: VBAT+, BATT_ID, BATT_TEMP/NTC, GND)"
      ),
      testLines = listOf(
        "VBAT+ (3.7V - 4.4V)" to "ব্যাটারির প্রধান পাওয়ার পজিটিভ টার্মিনাল",
        "BATT_ID (1.8V Pullup)" to "ব্যাটারির সত্যতা ও মডেল চিহ্নিতকারী ডিজিটাল লাইন",
        "BATT_TEMP / NTC (1.8V/0.9V)" to "তাপমাত্রা সেন্সিং ভোল্টেজ ডিভাইডার লাইন",
        "VBAT- / GND" to "সিস্টেম গ্রাউন্ড রেফারেন্স"
      ),
      safetyCaution = "ব্যাটারির পিচিএম (PCM) প্রোটেকশন বোর্ড কখনো সরাসরি বাইপাস করবেন না, এতে ব্যাটারি অতিরিক্ত চার্জ হয়ে ফেটে যাওয়ার তীব্র ঝুঁকি তৈরি হয়।"
    )
  )
}
