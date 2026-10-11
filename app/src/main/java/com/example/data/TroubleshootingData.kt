package com.example.data

import com.example.model.DiagnosticStep
import com.example.model.TroubleIssue

object TroubleshootingData {

  val issuesList = listOf(
    TroubleIssue(
      id = "issue_dead",
      titleBengali = "ফোন চালু হচ্ছে না (ডেড ফোন)",
      titleEnglish = "Phone Not Turning On / Dead",
      category = "পাওয়ার ও মাদারবোর্ড",
      initialStepId = "dead_step_1",
      iconName = "power_off",
      rootCauses = listOf(
        "ব্যাটারি সম্পূর্ণ শূন্য (০V) বা ডিপ-ডিসচার্জড",
        "পাওয়ার বাটনের মেকানিক্যাল বা ফ্লেক্স কেবল ফল্ট",
        "প্রাইমারি পাওয়ার লাইন (VBAT / VPH_PWR) শর্ট সার্কিট",
        "পাওয়ার আইসি (PMIC) অথবা সিপিইউ ফল্ট"
      ),
      requiredTools = listOf("ডিজিটাল মাল্টিমিটার", "ডিসি পাওয়ার সাপ্লাই (4V, 2A-5A)", "চার্জার ও ইউএসবি টেস্টার মিটার"),
      safetyWarning = "মাদারবোর্ডে শর্ট কিলার বা সরাসরি অতিরিক্ত ভোল্টেজ দেওয়ার চেষ্টা করবেন না। লিথিয়াম ব্যাটারি ক্ষতিগ্রস্ত হলে বিস্ফোরিত হতে পারে।"
    ),
    TroubleIssue(
      id = "issue_no_charge",
      titleBengali = "ফোন চার্জ হচ্ছে না",
      titleEnglish = "Phone Not Charging At All",
      category = "চার্জিং সার্কিট",
      initialStepId = "charge_step_1",
      iconName = "battery_alert",
      rootCauses = listOf(
        "চার্জার এডাপ্টার বা ইউএসবি কেবল ত্রুটিযুক্ত",
        "টাইপ-সি বা মাইক্রো ইউএসবি পোর্টে ময়লা বা জং ধরা",
        "সাব-বোর্ড (CC Board) বা মেইন ফ্লেক্স কেবল কাটা",
        "ওভিপি (OVP) প্রটেকশন আইসি পুড়ে যাওয়া"
      ),
      requiredTools = listOf("ইউএসবি টেস্টার মিটার", "মাল্টিমিটার (DC 20V মোড)", "আইসোপ্রোপাইল অ্যালকোহল ও ব্রাশ"),
      safetyWarning = "চার্জিং পোর্টে ধাতব পিন দিয়ে জোরে খোঁচা দেবেন না, অভ্যন্তরীণ পিন শর্ট হয়ে স্পার্ক হতে পারে।"
    ),
    TroubleIssue(
      id = "issue_slow_charge",
      titleBengali = "চার্জ ধীরে হচ্ছে (স্লো চার্জিং)",
      titleEnglish = "Slow Charging",
      category = "চার্জিং সার্কিট",
      initialStepId = "slow_step_1",
      iconName = "hourglass_bottom",
      rootCauses = listOf(
        "ইউএসবি কেবলের হাই ইন্টারনাল রেজিস্ট্যান্স",
        "ডেটা পিন (D+ / D-) লাইন ওপেন বা আলগা সোল্ডার",
        "ব্যাটারি স্বাস্থ্য অত্যন্ত দুর্বল হওয়া",
        "চার্জিং সকেটের পিনে কার্বন জমা"
      ),
      requiredTools = listOf("ইউএসবি অ্যাম্পিয়ার ডক্টর মিটার", "মাল্টিমিটার ডায়োড মোড"),
      safetyWarning = "অননুমোদিত হাই-ভোল্টেজ ফাস্ট চার্জার দিয়ে জোরপূর্বক চার্জের চেষ্টা করবেন না।"
    ),
    TroubleIssue(
      id = "issue_fake_charge",
      titleBengali = "চার্জিং চিহ্ন আসে, কিন্তু ব্যাটারি বাড়ে না",
      titleEnglish = "Fake Charging (Percentage Stuck)",
      category = "চার্জিং সার্কিট",
      initialStepId = "fake_step_1",
      iconName = "battery_unknown",
      rootCauses = listOf(
        "কারেন্ট সেন্স রেজিস্টর (R010/R020) মান বৃদ্ধি",
        "ব্যাটারি থার্মাল বা বিএসআই (BSI) লাইন ফল্ট",
        "চার্জিং আইসি কারেন্ট ডেলিভারি করতে ব্যর্থ",
        "সাব-বোর্ডের গ্রাউন্ড লুজ কানেকশন"
      ),
      requiredTools = listOf("ইউএসবি কারেন্ট টেস্টার", "মাল্টিমিটার ওহম ও ডায়োড মোড"),
      safetyWarning = "ব্যাটারি কানেক্টর খুলে পুনরায় লাগানোর সময় প্লাস-মাইনাস উল্টো শর্ট করবেন না।"
    ),
    TroubleIssue(
      id = "issue_battery_drain",
      titleBengali = "ব্যাটারি দ্রুত শেষ হয় (ব্যাটারি ড্রেনিং)",
      titleEnglish = "Rapid Battery Draining",
      category = "ব্যাটারি ও পাওয়ার",
      initialStepId = "drain_step_1",
      iconName = "battery_charging_20",
      rootCauses = listOf(
        "ব্যাকগ্রাউন্ড অ্যাপস বা ওএস ম্যালওয়্যার ড্রেন",
        "মাদারবোর্ডে সেকেন্ডারি লাইনে হাফ-শর্ট সার্কিট (Leakage Current)",
        "ব্যাটারির রাসায়নিক ক্ষয় ও ধারণক্ষমতা হ্রাস",
        "নেটওয়ার্ক পিএ (PA) আইসি অনবরত হাই কারেন্ট ড্র করা"
      ),
      requiredTools = listOf("ডিসি পাওয়ার সাপ্লাই (অ্যামিটার পর্যবেক্ষণ)", "মাল্টিমিটার"),
      safetyWarning = "ফোলা ব্যাটারি কখনোই পুনরায় ব্যবহারের চেষ্টা করবেন না, দ্রুত নিরাপদ রিসাইকেলে দিন।"
    ),
    TroubleIssue(
      id = "issue_display_black",
      titleBengali = "ডিসপ্লে কালো (আলো বা ছবি নেই)",
      titleEnglish = "Black Display / No Display",
      category = "ডিসপ্লে ও লাইট",
      initialStepId = "black_disp_step_1",
      iconName = "mobile_off",
      rootCauses = listOf(
        "ডিসপ্লে কেবল লুজ বা কানেক্টর ডিসকানেক্ট",
        "অভ্যন্তরীণ ডিসপ্লে প্যানেল বা ওএলইডি ক্র্যাক",
        "ব্যাকলাইট কয়েল বা বুস্ট ডায়োড পুড়ে যাওয়া",
        "ডিসপ্লে বায়াস আইসি (+5V / -5V) অনুপস্থিত"
      ),
      requiredTools = listOf("টর্চ লাইট", "মাল্টিমিটার (DC 200V / Diode)", "হট এয়ার গান"),
      safetyWarning = "ডিসপ্লে অন অবস্থায় কানেক্টরে টুইজার স্পর্শ করবেন না; ৩০-৪০V বুস্ট ভোল্টেজে শর্ট হতে পারে।"
    ),
    TroubleIssue(
      id = "issue_touch_not_working",
      titleBengali = "ছবি আছে, কিন্তু টাচ কাজ করে না",
      titleEnglish = "Display OK, Touch Screen Not Working",
      category = "ডিসপ্লে ও টাচ",
      initialStepId = "touch_step_1",
      iconName = "touch_app",
      rootCauses = listOf(
        "টাচ ডিজিটাইজার কাঁচ বা ফ্লেক্স ফাটা",
        "টাচ আইসির ১.৮V বা ৩.৩V ভি-ডিডি সাপ্লাই অনুপস্থিত",
        "I2C ডেটা/ক্লক (SDA/SCL) পুল-আপ রেজিস্টর ওপেন",
        "সফটওয়্যার ফ্রিজিং বা ক্র্যাশ"
      ),
      requiredTools = listOf("ওটিজি মাউস (OTG Mouse)", "মাল্টিমিটার ভোল্টেজ মোড"),
      safetyWarning = "স্ক্রিনের ফ্লেক্স কেবলে ভাঁজ বা অতিরিক্ত টান দেবেন না।"
    ),
    TroubleIssue(
      id = "issue_no_sound",
      titleBengali = "স্পিকার থেকে শব্দ আসে না",
      titleEnglish = "No Speaker / Loudspeaker Sound",
      category = "অডিও সিস্টেম",
      initialStepId = "sound_step_1",
      iconName = "volume_off",
      rootCauses = listOf(
        "হেডফোন মোড আটকে থাকা (Headphone Jack Stuck)",
        "স্পিকার কয়েল ছিঁড়ে যাওয়া (Open Coil)",
        "অডিও অ্যামপ্লিফায়ার আইসি (Audio PA) নষ্ট",
        "স্পিকার সংযোগ স্প্রিং পিন মিসিং বা বাঁকা"
      ),
      requiredTools = listOf("মাল্টিমিটার বাজার/ওহম মোড", "টুইজার"),
      safetyWarning = "অডিও লাইনে সরাসরি পাওয়ার ভোল্টেজ লাগাবেন না।"
    ),
    TroubleIssue(
      id = "issue_mic_not_working",
      titleBengali = "মাইক্রোফোন কাজ করে না (কথা শোনা যায় না)",
      titleEnglish = "Microphone Not Working",
      category = "অডিও সিস্টেম",
      initialStepId = "mic_step_1",
      iconName = "mic_off",
      rootCauses = listOf(
        "মাইকের বাইরের রাবার গ্রোমেট বা ছিদ্র ধুলাবালিতে বন্ধ",
        "ডিজিটাল এমইএমএস (MEMS) মাইক ক্ষতিগ্রস্ত",
        "মাইক বায়াস (MIC_BIAS 1.8V-2.8V) ভোল্টেজ অনুপস্থিত",
        "সাব-বোর্ড ফ্লেক্স কানেকশন আলগা"
      ),
      requiredTools = listOf("ভয়েস রেকর্ডার অ্যাপ", "চিকন সুই/পিন", "মাল্টিমিটার"),
      safetyWarning = "মাইকের ছিদ্রে সরাসরি সুই দিয়ে বেশি ভেতরে খোঁচা দেবেন না, ডায়াফ্রাম ফেটে যাবে।"
    ),
    TroubleIssue(
      id = "issue_no_network",
      titleBengali = "নেটওয়ার্ক পাওয়া যায় না (নো সার্ভিস)",
      titleEnglish = "No Service / Searching for Network",
      category = "আরএফ নেটওয়ার্ক",
      initialStepId = "net_step_1",
      iconName = "signal_cellular_connected_no_internet_0_bar",
      rootCauses = listOf(
        "সিম ট্রে পিন ক্ষতিগ্রস্ত বা সিম কার্ড নিষ্ক্রিয়",
        "বেসব্যান্ড ও আইএমইআই করাপ্ট হওয়া (*#06# ডায়ালে ফাঁকা)",
        "আরএফ কেবল ডিসকানেক্ট বা অ্যান্টেনা সুইচ ওপেন",
        "WTR / SDR ট্রান্সিভার বা পিএ (PA) আইসি ফল্ট"
      ),
      requiredTools = listOf("ডায়ালার কোড (*#06# / *#*#4636#*#*)", "মাল্টিমিটার", "মাইক্রোস্কোপ"),
      safetyWarning = "আরএফ অংশে অতিরিক্ত তাপে পুরো নেটওয়ার্ক আইসি নষ্ট হয়ে সেট ডেড হতে পারে।"
    ),
    TroubleIssue(
      id = "issue_overheating",
      titleBengali = "ফোন অতিরিক্ত গরম হয়",
      titleEnglish = "Phone Overheating Issue",
      category = "পাওয়ার ও থার্মাল",
      initialStepId = "heat_step_1",
      iconName = "thermostat",
      rootCauses = listOf(
        "সিপিইউ ১০০% ইউটিলাইজেশনে ব্যাকগ্রাউন্ড লুপে আটকে থাকা",
        "চার্জিং আইসি বা ভি-পিএইচ লাইনে হাফ শর্ট",
        "থার্মাল পেস্ট শুকিয়ে যাওয়া বা গ্রাফাইট প্যাড নষ্ট",
        "ব্যাটারির ইন্টারনাল রেজিস্ট্যান্স অতিরিক্ত বেড়ে যাওয়া"
      ),
      requiredTools = listOf("ডিসি পাওয়ার সাপ্লাই", "ইনফ্রারেড থার্মাল ক্যামেরা বা রোসিন ধোঁয়া"),
      safetyWarning = "গরম অবস্থায় ব্যাটারি চেপে দেখবেন না, তাপ বৃদ্ধির সাথে সাথে ব্যাটারি ফুলে যেতে পারে।"
    ),
    TroubleIssue(
      id = "issue_water_damaged",
      titleBengali = "পানিতে ভিজে গেছে (ওয়াটার ড্যামেজ)",
      titleEnglish = "Water Damaged Mobile",
      category = "জরুরি হার্ডওয়্যার",
      initialStepId = "water_step_1",
      iconName = "water_drop",
      rootCauses = listOf(
        "পানির ক্ষতিকর খনিজ দ্বারা শর্ট সার্কিট",
        "আইসি বল ও ট্র্যাকে অক্সিডাইজেশন বা জং ধরা",
        "ব্যাটারি সংযুক্ত থাকা অবস্থায় ইলেকট্রেলাইটিক ক্ষয়"
      ),
      requiredTools = listOf("আইসোপ্রোপাইল অ্যালকোহল (IPA 99%)", "আল্ট্রাসনিক ক্লিনার", "নরম ব্রাশ", "ব্লোয়ার"),
      safetyWarning = "পানিতে ভেজা ফোনে কখনোই তাৎক্ষণিক চার্জার লাগাবেন না বা অন করার চেষ্টা করবেন না! এতে আইসি স্থায়ীভাবে পুড়ে যাবে।"
    ),
    TroubleIssue(
      id = "issue_restart_loop",
      titleBengali = "ফোন বারবার রিস্টার্ট হয় (বুটলুপ)",
      titleEnglish = "Bootloop & Automatic Restart",
      category = "সফটওয়্যার ও বুট",
      initialStepId = "restart_step_1",
      iconName = "restart_alt",
      rootCauses = listOf(
        "পাওয়ার বাটন সুইচ অভ্যন্তরীণভাবে আটকে থাকা",
        "অ্যান্ড্রয়েড সিস্টেম ক্র্যাশ বা করাপ্ট ওটিএ আপডেট",
        "ব্যাটারি ফিউজ রেজিস্টর (Q-Switch) ড্রপ করা",
        "ইএমএমসি/ইউএফএস মেমোরির হেলথ শেষ হয়ে যাওয়া"
      ),
      requiredTools = listOf("মাল্টিমিটার বাজার মোড", "কম্পিউটার ও ফ্ল্যাশ টুল"),
      safetyWarning = "বুটলুপ চলাকালীন ব্যাটারি খুলে জোর করে বন্ধ না করে পাওয়ার ও ভলিউম চেপে রিকভারিতে যান।"
    ),
    TroubleIssue(
      id = "issue_no_vibration",
      titleBengali = "কম্পন কাজ করে না (ভাইব্রেটর ফল্ট)",
      titleEnglish = "Vibration Motor Not Working",
      category = "মেকানিক্যাল ও ড্রাইভ",
      initialStepId = "vib_step_1",
      iconName = "vibration",
      rootCauses = listOf(
        "ভাইব্রেশন মোটর কয়েন জাম হয়ে যাওয়া",
        "মোটরের কানেকশন প্যাড আলগা বা ক্ষয়প্রাপ্ত",
        "মোটর ড্রাইভার ট্রানজিস্টর বা পিএমআইসি লাইন ফল্ট"
      ),
      requiredTools = listOf("মাল্টিমিটার ওহম মোড", "১.৫V ব্যাটারি সেল টেস্ট"),
      safetyWarning = "মোটরে অতিরিক্ত ৩ ভোল্টের বেশি ডিসি দেবেন না।"
    ),
    TroubleIssue(
      id = "issue_camera_fail",
      titleBengali = "ক্যামেরা কাজ করে না (ক্যানট কানেক্ট)",
      titleEnglish = "Camera App Crashing / Cannot Connect",
      category = "ক্যামেরা সেন্সর",
      initialStepId = "cam_step_1",
      iconName = "camera_alt",
      rootCauses = listOf(
        "ক্যামেরা মডিউল সকেট থেকে খুলে যাওয়া বা পিন আলগা",
        "ক্যামেরা এলডিও (LDO 2.8V, 1.2V, 1.8V) ভোল্টেজ অনুপস্থিত",
        "ক্যামেরা গ্লাসে পানি বা ক্যামেরা ড্রপ ড্যামেজ",
        "অন্য ব্যাক ক্যামেরার ত্রুটির কারণে ফ্রন্ট ক্যামেরাও ব্লক"
      ),
      requiredTools = listOf("মাল্টিমিটার ভোল্টেজ মোড", "বিকল্প ভালো ক্যামেরা মডিউল"),
      safetyWarning = "ক্যামেরা সেন্সরের চোখে সরাসরি আইপিএ বা কোনো তরল স্প্রে করবেন না।"
    )
  )

  val diagnosticStepsMap: Map<String, DiagnosticStep> = mapOf(
    // Phone Not Turning On
    "dead_step_1" to DiagnosticStep(
      id = "dead_step_1",
      stepNumber = 1,
      titleBengali = "চার্জার ও ইউএসবি টেস্টার টেস্ট",
      instructionBengali = "একটি পরীক্ষিত ভালো চার্জার ও কেবল ফোনে সংযুক্ত করুন এবং ইউএসবি টেস্টার মিটারের কারেন্ট (A) লক্ষ্য করুন।",
      testingMethod = "ইউএসবি ডক্টরে ভোল্টেজ ৫V আছে কি না এবং অ্যাম্পিয়ার ০.০০A নাকি ০.৬০A-২.০A নিচ্ছে তা দেখুন।",
      toolsNeeded = listOf("ইউএসবি টেস্টার মিটার", "মূল চার্জার ও কেবল"),
      observationQuestion = "ইউএসবি মিটারে কি ০.৫ অ্যাম্পিয়ারের বেশি কারেন্ট নিচ্ছে?",
      yesOptionLabel = "হ্যাঁ, ০.৫A-১.৫A কারেন্ট নিচ্ছে",
      yesNextStepId = "dead_step_screen_check",
      yesOutcomeDescription = "ফোন চার্জ নিচ্ছে, যার মানে ব্যাটারি বা মাদারবোর্ডের প্রাইমারি লাইন চালু আছে। সমস্যাটি ডিসপ্লে বা লাইটের হতে পারে।",
      noOptionLabel = "না, ০.০০A বা ০.০২A-তে আটকে আছে",
      noNextStepId = "dead_step_2",
      noOutcomeDescription = "ফোন চার্জার থেকে বিদ্যুৎ গ্রহণ করছে না। সরাসরি ব্যাটারি বা সংযোগ পরীক্ষা প্রয়োজন।"
    ),
    "dead_step_screen_check" to DiagnosticStep(
      id = "dead_step_screen_check",
      stepNumber = 2,
      titleBengali = "ভাইব্রেশন ও নোটিফিকেশন সাউন্ড পর্যবেক্ষণ",
      instructionBengali = "পাওয়ার বাটন ১০-১৫ সেকেন্ড চেপে ধরে রাখুন। কোনো ভাইব্রেশন বা বুট সাউন্ড কি অনুভব করা যায়?",
      testingMethod = "হালকা কম্পন বা চার্জার লাগানোর বীপ সাউন্ড লক্ষ্য করুন।",
      toolsNeeded = listOf("নিজস্ব অনুভূতি ও কান"),
      observationQuestion = "ফোন কি ভাইব্রেট করে বা সাউন্ড শোনা যায়?",
      yesOptionLabel = "হ্যাঁ, ভাইব্রেশন বা সাউন্ড আছে কিন্তু ডিসপ্লে অন্ধকার",
      yesNextStepId = null,
      yesOutcomeDescription = "অভিনন্দন! ফোনটির মাদারবোর্ড সচল রয়েছে। সমস্যাটি ডিসপ্লে প্যানেল, ব্যাকলাইট সার্কিট বা ডিসপ্লে কেবলের। ডিসপ্লে সেকশন চেক করুন।",
      noOptionLabel = "না, কোনো ভাইব্রেশন বা সাউন্ড নেই",
      noNextStepId = "dead_step_2",
      noOutcomeDescription = "ফোন চালু হতে পারছে না। পাওয়ার লাইনের পরীক্ষা শুরু করুন।"
    ),
    "dead_step_2" to DiagnosticStep(
      id = "dead_step_2",
      stepNumber = 3,
      titleBengali = "ব্যাটারি ভোল্টেজ পরীক্ষা",
      instructionBengali = "সাবধানে ব্যাকশেল খুলে ব্যাটারি কানেক্টরে মাল্টিমিটার ২০V ডিসি রেঞ্জে রেখে ভোল্টেজ পরীক্ষা করুন।",
      testingMethod = "লাল প্রোব (+) এবং কালো প্রোব (-) টার্মিনালে স্পর্শ করুন।",
      toolsNeeded = listOf("ডিজিটাল মাল্টিমিটার", "স্ক্রু-ড্রাইভার সেট"),
      observationQuestion = "ব্যাটারি ভোল্টেজ কি ৩.৭ ভোল্ট বা তার বেশি দেখাচ্ছে?",
      yesOptionLabel = "হ্যাঁ, ৩.৭V থেকে ৪.২V আছে",
      yesNextStepId = "dead_step_3",
      yesOutcomeDescription = "ব্যাটারিতে পর্যাপ্ত চার্জ রয়েছে। সমস্যা পাওয়ার কি, বুট লাইন বা শর্ট সার্কিটের।",
      noOptionLabel = "না, ৩.০ ভোল্টের নিচে (বা ০V)",
      noNextStepId = "dead_step_boost",
      noOutcomeDescription = "ব্যাটারি ডিপ-ডিসচার্জড হয়ে গেছে। ফোন নিজে নিজে চার্জ শুরু করতে অক্ষম।"
    ),
    "dead_step_boost" to DiagnosticStep(
      id = "dead_step_boost",
      stepNumber = 4,
      titleBengali = "ব্যাটারি বুস্টিং ও সমাধান",
      instructionBengali = "ডিসি পাওয়ার সাপ্লাইয়ে ৪.২ ভোল্ট ও ১ অ্যাম্পিয়ার সেট করে ব্যাটারিকে সরাসরি বাহ্যিকভাবে ৫-১০ মিনিট চার্জ দিয়ে ৩.৮ ভোল্টে তুলুন।",
      testingMethod = "বুস্ট করার পর পুনরায় ভোল্টেজ মাপুন এবং ফোনে সংযুক্ত করে অন করার চেষ্টা করুন।",
      toolsNeeded = listOf("ডিসি পাওয়ার সাপ্লাই / ব্যাটারি বুস্টার বোর্ড"),
      observationQuestion = "ব্যাটারি বুস্ট করার পর ফোন কি অন হচ্ছে?",
      yesOptionLabel = "হ্যাঁ, ফোন অন হয়েছে ও চার্জ নিচ্ছে!",
      yesNextStepId = null,
      yesOutcomeDescription = "সমস্যার সফল সমাধান হয়েছে! ব্যাটারি ডিপ ডিসচার্জে আটকে ছিল। ব্যবহারকারীকে নিয়মিত চার্জ করার পরামর্শ দিন।",
      noOptionLabel = "না, এখনও কোনো সাড়া নেই",
      noNextStepId = "dead_step_3",
      noOutcomeDescription = "পরবর্তী ধাপে মাদারবোর্ডের কোল্ড টেস্টিং পরীক্ষা করা প্রয়োজন।"
    ),
    "dead_step_3" to DiagnosticStep(
      id = "dead_step_3",
      stepNumber = 5,
      titleBengali = "ব্যাটারি কানেক্টরে কোল্ড টেস্টিং (ডায়োড মোড)",
      instructionBengali = "মাল্টিমিটার ডায়োড মোডে নিন। লাল প্রোব মাদারবোর্ডের গ্রাউন্ডে (টিনের শিল্ডে) রাখুন এবং কালো প্রোব ব্যাটারি কানেক্টরের পজিটিভ পিনে রাখুন।",
      testingMethod = "ফরওয়ার্ড বায়াসে ৩৫০-৬০০ রিডিং আসবে। রিভার্স বায়াসে (কালো গ্রাউন্ডে, লাল পজিটিভে) অসীম (OL বা ১) হওয়া স্বাভাবিক।",
      toolsNeeded = listOf("মাল্টিমিটার ডায়োড মোড"),
      observationQuestion = "উভয় পাশেই কি ০.০০১ রিডিং সহ একটানা বিপ (Beep) শব্দ হচ্ছে?",
      yesOptionLabel = "হ্যাঁ, বিপ সাউন্ড বাজছে (০.০০১ রিডিং)",
      yesNextStepId = "dead_step_short_rosin",
      yesOutcomeDescription = "প্রাইমারি লাইন (VBAT বা VPH_PWR) ফুল শর্ট হয়ে গেছে। কোনো ফিল্টার ক্যাপাসিটর বা আইসি পুড়ে গেছে।",
      noOptionLabel = "না, স্বাভাবিক রিডিং (৩৫০-৫০০) দেখাচ্ছে",
      noNextStepId = "dead_step_dc_reading",
      noOutcomeDescription = "প্রাইমারি লাইনে শর্ট নেই। ডিসি সাপ্লাই বুট রিডিং পর্যবেক্ষণ করতে হবে।"
    ),
    "dead_step_short_rosin" to DiagnosticStep(
      id = "dead_step_short_rosin",
      stepNumber = 6,
      titleBengali = "রোসিন রেজিন ধোঁয়া ও শর্ট ক্যাপাসিটর অপসারণ",
      instructionBengali = "মাদারবোর্ডে রোসিন ধোঁয়া লাগিয়ে সাদা প্রলেপ দিন। শর্ট লাইনে ডিসি সাপ্লাই থেকে ৩.৭V ও ২A ইনজেক্ট করুন। যে পার্টটি গরম হয়ে সাদা ধোঁয়া গলিয়ে দেবে সেটি চিমটা দিয়ে তুলে ফেলুন।",
      testingMethod = "শর্ট কম্পোনেন্ট সরানোর পর পুনরায় ডায়োড ভ্যালু চেক করুন।",
      toolsNeeded = listOf("রোসিন রেজিন স্মোক ডিসপেনসার", "ডিসি পাওয়ার সাপ্লাই", "টুইজার"),
      observationQuestion = "শর্ট দূর হয়েছে এবং রিডিং কি স্বাভাবিকে ফিরেছে?",
      yesOptionLabel = "হ্যাঁ, বিপ বন্ধ হয়েছে এবং স্বাভাবিক রিডিং এসেছে",
      yesNextStepId = null,
      yesOutcomeDescription = "দারুণ সাফল্য! শর্ট ক্যাপাসিটর অপসারিত হয়েছে। নতুন পার্ট লাগিয়ে ফোন অন করুন।",
      noOptionLabel = "না, কোনো পার্ট গরম হচ্ছে না বা আইসি গরম হচ্ছে",
      noOutcomeDescription = "প্রধান পাওয়ার আইসি (PMIC) বা সিপিইউ ড্যামেজ হওয়ার সম্ভাবনা রয়েছে। এটি অ্যাডভান্সড চিপ-লেভেল মাইক্রোস্কোপিক কাজ।",
      requiresProfessionalService = true,
      isTerminalResolution = true,
      finalResolution = "দক্ষ চিপ-লেভেল টেকনিশিয়ানের মাধ্যমে পিএমআইসি রিবলিং বা পরিবর্তন করান।"
    ),
    "dead_step_dc_reading" to DiagnosticStep(
      id = "dead_step_dc_reading",
      stepNumber = 7,
      titleBengali = "ডিসি পাওয়ার সাপ্লাইয়ে বুট সিকোয়েন্স কারেন্ট পরীক্ষা",
      instructionBengali = "ফোনটি ডিসি সাপ্লাইয়ে (৪.০V) লাগান এবং পাওয়ার বাটন চাপুন। কারেন্ট মিটার রিডিং কীভাবে ওঠানামা করে লক্ষ্য করুন।",
      testingMethod = "পাওয়ার বাটন চাপার পর কারেন্ট ০.০২A উঠে আটকে যায়, নাকি ০.১৫A থেকে ক্রমান্বয়ে বেড়ে লাফিয়ে চলে দেখুন।",
      toolsNeeded = listOf("ডিসি পাওয়ার সাপ্লাই (0-30V 5A)"),
      observationQuestion = "পাওয়ার কী চাপলে ডিসি রিডিং কী দেখাচ্ছে?",
      yesOptionLabel = "০.০২A-০.০৫A উঠে দাঁড়িয়ে থাকে (Stuck)",
      yesNextStepId = null,
      yesOutcomeDescription = "সিপিইউ প্রাথমিক বুট করতে ব্যর্থ হচ্ছে। ইএমএমসি/ইউএফএস ক্লক বা পাওয়ার আইসির সেকেন্ডারি বাক কয়েল (Buck Coils 0.8V, 1.1V) ড্রপ হয়েছে।",
      noOptionLabel = "কোনো কারেন্ট ওঠে না (০.০০A)",
      noNextStepId = null,
      noOutcomeDescription = "পাওয়ার বাটনের লাইন কাটা, কিংবা পাওয়ার আইসি পুরোপুরি ডেড। পাওয়ার কি ট্র্যাক জাম্পার বা পিএমআইসি চেক প্রয়োজন।",
      requiresProfessionalService = true,
      isTerminalResolution = true,
      finalResolution = "পাওয়ার কি-র ট্র্যাক এবং সেকেন্ডারি বাক ও এলডিও ভোল্টেজ পরীক্ষা করে পিএমআইসি রিপ্লেসমেন্ট প্রয়োজন।"
    ),

    // Charging Not Working Flow
    "charge_step_1" to DiagnosticStep(
      id = "charge_step_1",
      stepNumber = 1,
      titleBengali = "চার্জার, ক্যাবল ও চার্জিং পোর্ট পরিদর্শন",
      instructionBengali = "অন্য একটি নিশ্চিত ভালো অরিজিনাল চার্জার ও কেবল ব্যবহার করুন। একটি টর্চ লাইট দিয়ে ফোনের চার্জিং পোর্টের ভেতরে তাকান।",
      testingMethod = "পোর্টের ভেতরে পিন বাঁকা আছে কি না বা পকেটের ময়লা-তুলা জমে পিন ঢোকা আটকে দিচ্ছে কি না দেখুন।",
      toolsNeeded = listOf("টর্চ লাইট", "চিকন পরিষ্কার কাঠি/ব্রাশ", "পরীক্ষিত চার্জার"),
      observationQuestion = "পোর্টের ভেতরে কি দৃশ্যমান ময়লা রয়েছে বা কেবলটি আলগা হয়ে নড়বড়ে লাগছে?",
      yesOptionLabel = "হ্যাঁ, পিন আলগা বা ভেতরে ময়লা জমে আছে",
      yesNextStepId = "charge_step_clean",
      yesOutcomeDescription = "পোর্টের ভেতরের ময়লার কারণে চার্জিং প্লাগ ঠিকমতো স্পর্শ করতে পারছে না।",
      noOptionLabel = "না, পোর্ট পরিষ্কার ও টাইটভাবে বসেছে",
      noNextStepId = "charge_step_vbus",
      noOutcomeDescription = "হার্ডওয়্যার লাইনে বিদ্যুৎ প্রবাহের পরীক্ষা করতে হবে।"
    ),
    "charge_step_clean" to DiagnosticStep(
      id = "charge_step_clean",
      stepNumber = 2,
      titleBengali = "পোর্ট পরিষ্কার ও পুনরায় টেস্ট",
      instructionBengali = "একটি ব্রাশে সামান্য আইপিএ (IPA 99%) লাগিয়ে আলতো করে পোর্ট পরিষ্কার করুন। ময়লা বের করে বাতাসে শুকিয়ে নিন।",
      testingMethod = "কেবল লাগিয়ে চার্জিং অ্যানিমেশন আসে কি না দেখুন।",
      toolsNeeded = listOf("আইসোপ্রোপাইল অ্যালকোহল", "নরম ব্রাশ"),
      observationQuestion = "পরিষ্কার করার পর কি চার্জিং শুরু হয়েছে?",
      yesOptionLabel = "হ্যাঁ, চার্জ সুন্দরভাবে হচ্ছে!",
      yesNextStepId = null,
      yesOutcomeDescription = "সফল সমাধান! চার্জিং পোর্টে ময়লা জমার কারণে কন্টাক্ট বিচ্ছিন্ন ছিল। পোর্ট পরিষ্কার করায় ঠিক হয়ে গেছে।",
      noOptionLabel = "না, এখনও কোনো চার্জিং রেসপন্স নেই",
      noNextStepId = "charge_step_vbus",
      noOutcomeDescription = "ভোল্টেজ ড্রপ টেস্ট প্রয়োজন।"
    ),
    "charge_step_vbus" to DiagnosticStep(
      id = "charge_step_vbus",
      stepNumber = 3,
      titleBengali = "সাব-বোর্ডে VBUS ৫.০V পরীক্ষা",
      instructionBengali = "ফোনটি খুলে চার্জার প্লাগ করুন। মাল্টিমিটার ডিসি ২০V রেঞ্জে রেখে চার্জিং সাব-বোর্ডের VBUS টেস্ট পয়েন্ট ও গ্রাউন্ডে ভোল্টেজ মাপুন।",
      testingMethod = "চার্জিং পিনের আউটপুটে ৫.০V - ৫.২V ডিসি রিডিং আসা বাধ্যতামূলক।",
      toolsNeeded = listOf("মাল্টিমিটার ২০V ডিসি", "চার্জার ক্যাবল"),
      observationQuestion = "সাব-বোর্ডে কি ৫.০ ভোল্ট পাওয়া যাচ্ছে?",
      yesOptionLabel = "হ্যাঁ, ৫.০ ভোল্ট উপস্থিত আছে",
      yesNextStepId = "charge_step_flex",
      yesOutcomeDescription = "চার্জিং পোর্ট পুরোপুরি ভালো আছে। সমস্যা মেইন কানেক্টিং ফ্লেক্স কেবল বা মাদারবোর্ডে।",
      noOptionLabel = "না, ০.০০ ভোল্ট বা ওঠানামা করছে",
      noNextStepId = null,
      noOutcomeDescription = "চার্জিং পোর্ট বা সাব-বোর্ড (CC Board) নষ্ট। নতুন চার্জিং পোর্ট সোল্ডার করুন বা সাব-বোর্ড পরিবর্তন করুন।",
      isTerminalResolution = true,
      finalResolution = "চার্জিং পোর্ট অথবা পুরো চার্জিং সাব-বোর্ড (Sub Board) পরিবর্তন করলেই সমাধান হবে।"
    ),
    "charge_step_flex" to DiagnosticStep(
      id = "charge_step_flex",
      stepNumber = 4,
      titleBengali = "মেইন ফ্লেক্স কেবল ও ওভিপি (OVP) পরীক্ষা",
      instructionBengali = "মাদারবোর্ডের চার্জিং কানেক্টরে ৫V পৌঁছেছে কি না দেখুন। এরপর ওভিপি আইসির ইনপুট ও আউটপুটে ৫V পরিমাপ করুন।",
      testingMethod = "ওভিপি আইসির ইনপুটে ৫V আছে কিন্তু আউটপুটে নেই—মানে ওভিপি আইসি নষ্ট।",
      toolsNeeded = listOf("মাল্টিমিটার", "সার্কিট স্কিম্যাটিক"),
      observationQuestion = "ওভিপি আইসির আউটপুটে কি ৫ ভোল্ট বের হচ্ছে?",
      yesOptionLabel = "হ্যাঁ, ৫V বের হয়ে চার্জিং আইসিতে ঢুকছে",
      yesNextStepId = null,
      yesOutcomeDescription = "চার্জিং আইসি (PMI IC) অথবা ব্যাটারি চার্জিং কন্ট্রোলার নষ্ট। আইসি পরিবর্তন করতে হবে।",
      noOptionLabel = "না, ওভিপি আউটপুটে ভোল্টেজ নেই",
      noNextStepId = null,
      noOutcomeDescription = "ওভিপি আইসিটি ওভারভোল্টেজ প্রটেকশনে পুড়ে গেছে। ওভিপি আইসি পরিবর্তন বা জাম্পার বাইপাস করুন।",
      isTerminalResolution = true,
      finalResolution = "ওভিপি আইসি প্রতিস্থাপন অথবা প্রধান চার্জিং আইসি (PMI/BQ series) পরিবর্তন প্রয়োজন।"
    ),

    // Water damaged quick steps
    "water_step_1" to DiagnosticStep(
      id = "water_step_1",
      stepNumber = 1,
      titleBengali = "জরুরি প্রাথমিক পদক্ষেপ (পাওয়ার অফ ও ব্যাটারি খোলা)",
      instructionBengali = "সবচেয়ে জরুরি: ফোনটি সাথে সাথে বন্ধ করুন এবং কোনো অবস্থাতেই চার্জারে লাগাবেন না! সম্ভব হলে দ্রুত ব্যাটারি কানেক্টর খুলে বিদ্যুৎ সরবরাহ বন্ধ করুন।",
      testingMethod = "ব্যাটারি খুলে রাখা নিশ্চিত করুন।",
      toolsNeeded = listOf("ওপেনার টুল"),
      observationQuestion = "ব্যাটারি সংযোগ কি বিচ্ছিন্ন করা হয়েছে?",
      yesOptionLabel = "হ্যাঁ, ব্যাটারি সংযোগ বিচ্ছিন্ন করা হয়েছে",
      yesNextStepId = "water_step_2",
      yesOutcomeDescription = "চমৎকার! বিদ্যুৎ বিচ্ছিন্ন থাকায় শর্ট সার্কিট এবং ক্ষয় থেকে মাদারবোর্ড রক্ষা পাবে।",
      noOptionLabel = "না, এখনও ব্যাটারি লাগানো অবস্থায় আছে",
      noNextStepId = "water_step_1",
      noOutcomeDescription = "বিলম্ব না করে অবিলম্বে ব্যাটারি রিমুভ করুন! দেরি করলে আইসির নিচের বল গলে মাদারবোর্ড চিরতরে ডেড হতে পারে।"
    ),
    "water_step_2" to DiagnosticStep(
      id = "water_step_2",
      stepNumber = 2,
      titleBengali = "আইপিএ দ্রবণ ও আল্ট্রাসনিক ওয়াশ",
      instructionBengali = "মাদারবোর্ডটি খুলে ক্যামেরা ও মাইক আলাদা করুন। এরপর ৯৯% খাঁটি আইসোপ্রোপাইল অ্যালকোহল (IPA) দিয়ে ব্রাশ করে বা আল্ট্রাসনিক ক্লিনারে ৫ মিনিট ওয়াশ করুন।",
      testingMethod = "বোর্ডের সর্বত্র ফাঙ্গাস ও ক্ষয় মুক্ত হয়েছে কি না মাইক্রোস্কোপে দেখুন।",
      toolsNeeded = listOf("আইপিএ ৯৯%", "অ্যান্ট্রিস্ট্যাটিক ব্রাশ", "ব্লোয়ার"),
      observationQuestion = "বোর্ড পুরোপুরি পরিষ্কার এবং ৯০°C তাপে শুকানো হয়েছে কি?",
      yesOptionLabel = "হ্যাঁ, শুকানো ও কোল্ড টেস্ট সম্পন্ন হয়েছে",
      yesNextStepId = null,
      yesOutcomeDescription = "সফল পরিচ্ছন্নতা! এখন ডিসি পাওয়ার সাপ্লাইয়ে লাগিয়ে কারেন্ট ড্র পরীক্ষা করুন এবং অন করে ক্যামেরা/ডিসপ্লে চেক করুন।",
      noOptionLabel = "না, এখনও আর্দ্রতা রয়েছে",
      noNextStepId = "water_step_2",
      noOutcomeDescription = "কোনো আর্দ্রতা থাকা অবস্থায় কারেন্ট দেবেন না, সম্পূর্ণ শুকাতে সময় দিন।"
    )
  )

  fun getStep(stepId: String): DiagnosticStep? {
    diagnosticStepsMap[stepId]?.let { return it }
    val issue = issuesList.find { it.initialStepId == stepId } ?: return null
    return DiagnosticStep(
      id = stepId,
      stepNumber = 1,
      titleBengali = "প্রাথমিক ডায়াগনসিস ও নিরাপত্তা পরীক্ষা",
      instructionBengali = "${issue.titleBengali}-এর ক্ষেত্রে প্রথমে কোনো দৃশ্যমান ক্ষতি, আর্দ্রতা বা আলগা কেবল কানেকশন আছে কি না পরীক্ষা করুন। প্রয়োজনীয় টুল: ${issue.requiredTools.joinToString(", ")}।",
      testingMethod = "মাল্টিমিটার ও পরীক্ষিত টেস্ট টুল দিয়ে ইনপুট ভোল্টেজ বা ডায়োড মান পরীক্ষা করুন।",
      toolsNeeded = issue.requiredTools,
      observationQuestion = "প্রাথমিক পরীক্ষায় কি কোনো অস্বাভাবিকতা বা ড্রপ ধরা পড়েছে?",
      yesOptionLabel = "হ্যাঁ, লাইনে ত্রুটি শনাক্ত হয়েছে",
      yesNextStepId = null,
      yesOutcomeDescription = "সম্ভাব্য মূল কারণ: ${issue.rootCauses.firstOrNull() ?: "হার্ডওয়্যার লাইন ফল্ট"}। সমাধান: সংশ্লিষ্ট আইসি বা কানেক্টর প্রতিস্থাপন প্রয়োজন।",
      noOptionLabel = "না, প্রাথমিক রিডিং স্বাভাবিক",
      noNextStepId = null,
      noOutcomeDescription = "বাহ্যিক সংযোগ ভালো আছে। মাদারবোর্ডের সেকেন্ডারি পাওয়ার লাইন এবং প্রসেসর কমিউনিকেশন চেক করুন।"
    )
  }
}
