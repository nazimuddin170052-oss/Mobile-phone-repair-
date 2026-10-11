package com.example.data

import com.example.model.*

object SampleData {

  val lessons = listOf(
    Lesson(
      id = "hw_dead_phone",
      title = "ডেড মোবাইল নির্ণয় ও রিকভারি পদ্ধতি",
      englishTitle = "Dead Mobile Diagnosis & Step-by-Step Recovery",
      section = ScreenSection.HARDWARE,
      shortDescription = "সম্পূর্ণ বন্ধ বা চালু না হওয়া স্মার্টফোনের প্রাথমিক ও সেকেন্ডারি লাইন পরীক্ষা এবং সমাধানের নিয়ম।",
      symptoms = listOf(
        "পাওয়ার বাটন চাপলেও কোনো ডিসপ্লে বা ভাইব্রেশন নেই।",
        "চার্জারে লাগালে কোনো কারেন্ট গ্রহণ করে না (০.০০ অ্যাম্পিয়ার)।",
        "ডিসি পাওয়ার সাপ্লাইয়ে সংযোগ দিলে সরাসরি রিডিং দেখায় বা কোনো রিডিং ওঠে না।"
      ),
      causes = listOf(
        "ব্যাটারি সম্পূর্ণ ডিসচার্জ বা ০ ভোল্ট হয়ে যাওয়া।",
        "ভি-ব্যাট (VBAT) অথবা ভি-পিএইচ (VPH_PWR) লাইনে ফুল শর্ট বা হাফ শর্ট।",
        "পাওয়ার আইসি (PMIC) বা ক্রিস্টাল অসিলেটর (26MHz Crystal) নষ্ট হওয়া।",
        "সিপিইউ (CPU) বা ইউএফএস/ইএমএমসি (EMMC/UFS) ড্রাই সোল্ডার বা ড্যামেজ।"
      ),
      toolsNeeded = listOf(
        "ডিজিটাল মাল্টিমিটার (Digital Multimeter)",
        "ডিসি রেগুলেটেড পাওয়ার সাপ্লাই (0-30V / 5A DC Power Supply)",
        "হট এয়ার গান (SMD Rework Station)",
        "রোসিন রেজিন স্মোক ডিসপেনসার (Rosin Smoke Atomizer)",
        "সোল্ডারিং আয়রন ও পেস্ট"
      ),
      steps = listOf(
        RepairStep(
          stepNumber = 1,
          stepTitle = "ব্যাটারি ভোল্টেজ পরীক্ষা",
          instruction = "মাল্টিমিটার ২০V ডিসি রেঞ্জে রেখে ব্যাটারির পজিটিভ ও নেগেটিভ পরীক্ষা করুন। ভোল্টেজ ৩.৭ ভোল্টের নিচে থাকলে ডিসি বুস্টার দিয়ে বুস্ট করুন।",
          importantWarning = "৩.০ ভোল্টের নিচে নামলে আধুনিক ফোন নিজে নিজে চার্জ শুরু করতে পারে না।"
        ),
        RepairStep(
          stepNumber = 2,
          stepTitle = "ব্যাটারি কানেক্টরে কোল্ড টেস্টিং (ডায়োড মোড)",
          instruction = "লাল প্রোব গ্রাউন্ডে এবং কালো প্রোব ভি-ব্যাট পিনে রাখুন। সাধারণ রিডিং ৩৫০-৫০০ আসা উচিত। রিভার্স বায়াসে (কালো গ্রাউন্ডে, লাল ভি-ব্যাটে) রিডিং ১ বা অসীম (OL) হওয়া উচিত। দুই পাশেই ০ বা বিপ সাউন্ড দিলে বুঝতে হবে লাইন ফুল শর্ট।",
          importantWarning = "বিপ সাউন্ড পাওয়া গেলে কখনোই সরাসরি ব্যাটারি লাগিয়ে অন করার চেষ্টা করবেন না।"
        ),
        RepairStep(
          stepNumber = 3,
          stepTitle = "ডিসি পাওয়ার সাপ্লাইয়ে কারেন্ট ড্রপ পর্যবেক্ষণ",
          instruction = "৪.০ ভোল্ট সেট করে ফোন সংযুক্ত করুন। পাওয়ার কি না চেপে যদি স্বয়ংক্রিয়ভাবে ০.৫A-২A কারেন্ট নেয়, তবে ভি-ব্যাট বা ভি-পিএইচ লাইনে শর্ট। আর পাওয়ার কি চাপলে ০.০২A উঠে ছেড়ে দিলে সিপিইউ বুট আটকে গেছে (বুট সিকোয়েন্স চেক করুন)।",
          importantWarning = "ডিসি সাপ্লাইয়ে অ্যামিটার রিডিং দেখে সমস্যা ৫০% শনাক্ত করা যায়।"
        ),
        RepairStep(
          stepNumber = 4,
          stepTitle = "রোসিন স্মোক টেস্ট দিয়ে শর্ট ক্যাপাসিটর চিহ্নিতকরণ",
          instruction = "মাদারবোর্ডে রোসিন ধোঁয়া লাগিয়ে সাদা আস্তরণ তৈরি করুন। এরপর শর্ট লাইনে ডিসি সাপ্লাই থেকে ৩.৮V ও ২A ইনজেক্ট করুন। যে ক্যাপাসিটর বা আইসিটি গরম হয়ে রোসিন গলিয়ে দেবে, সেটিই মূল কালপ্রিট।",
          importantWarning = "ভোল্টেজ যেন কখনোই মূল লাইনের রেটিং অতিক্রম না করে।"
        ),
        RepairStep(
          stepNumber = 5,
          stepTitle = "কম্পোনেন্ট পরিবর্তন ও ফাইনাল টেস্ট",
          instruction = "ত্রুটিযুক্ত ক্যাপাসিটর বা আইসিটি সতর্কতার সাথে টুইজার ও হট এয়ার গান (৩৫০°C) দিয়ে উঠিয়ে নতুন পার্ট বসান এবং পুনরায় ডায়োড ভ্যালু চেক করুন।"
        )
      ),
      safetyTips = listOf(
        "সিপিইউ ও মেমোরি আইসির ওপর অতিরিক্ত হিট দেবেন না, শিল্ডিংয়ের নিচে হিট ডিফ্লেক্টর টেপ ব্যবহার করুন।",
        "লিথিয়াম ব্যাটারিতে সরাসরি শর্ট কিলার প্রয়োগ করবেন না, বিস্ফোরণের ঝুঁকি থাকে।"
      ),
      testingMethod = "ডিসি পাওয়ার সাপ্লাইয়ে পাওয়ার কি চাপলে ০.১৫A থেকে ক্রমান্বয়ে ০.৮A-১.২A পর্যন্ত লাফিয়ে ফোন চালু হবে।",
      difficulty = DifficultyLevel.INTERMEDIATE,
      estimatedMinutes = 25
    ),

    Lesson(
      id = "hw_water_damage",
      title = "পানিতে পড়া ফোন ওয়াটার ড্যামেজ ক্লিন ও ড্রাই",
      englishTitle = "Water Damage Cleaning & Ultrasonic PCB Treatment",
      section = ScreenSection.HARDWARE,
      shortDescription = "পানিতে পড়া বা ভিজে যাওয়া মোবাইলের ক্ষয় (Corrosion) দূরীকরণ ও ড্রাই সোল্ডার সারানোর সঠিক নিয়ম।",
      symptoms = listOf(
        "পানিতে পড়ার পর অন হচ্ছে না বা অন হয়ে রিস্টার্ট নিচ্ছে।",
        "ব্যাটারি দ্রুত ড্রেন হচ্ছে ও ফোন অস্বাভাবিক গরম হচ্ছে।",
        "ক্যামেরা বা ডিসপ্লেতে কুয়াশাচ্ছন্ন দাগ বা লাইটিং ফ্লিকারিং।"
      ),
      causes = listOf(
        "মিনারেল ওয়াটারের কারণে মাদারবোর্ডে অক্সিডাইজেশন বা ফাঙ্গাস সৃষ্টি।",
        "আইসির বলের নিচে পানি ঢুকে পিন টু পিন শর্ট সার্কিট সৃষ্টি।"
      ),
      toolsNeeded = listOf(
        "আল্ট্রাসনিক ক্লিনার (Ultrasonic Cleaner Tank)",
        "আইসোপ্রোপাইল অ্যালকোহল (IPA 99.9% / PCB Cleaner)",
        "নরম অ্যান্ট্রিস্ট্যাটিক ব্রাশ",
        "হট এয়ার গান বা প্রিহিটার (১০০°C)"
      ),
      steps = listOf(
        RepairStep(
          stepNumber = 1,
          stepTitle = "তাত্ক্ষণিক ব্যাটারি সংযোগ বিচ্ছিন্ন",
          instruction = "ফোন খোলার সাথে সাথেই প্রথম কাজ হলো ব্যাটারি স্ট্রিপ খুলে ফেলা যাতে ইলেকট্রেলাইটিক ক্ষয় বন্ধ হয়।"
        ),
        RepairStep(
          stepNumber = 2,
          stepTitle = "মাদারবোর্ড শিল্ড রিমুভ",
          instruction = "মেটাল ক্যান বা শিল্ডিংগুলো কাটার বা সতর্কতার সাথে হিট দিয়ে খুলে ফেলুন।"
        ),
        RepairStep(
          stepNumber = 3,
          stepTitle = "আইপিএ ক্লিনিং ও আল্ট্রাসনিক ওয়াশ",
          instruction = "মাদারবোর্ডটি ৯৯% খাঁটি আইপিএ দ্রবণে আল্ট্রাসনিক ক্লিনারে ৫-১০ মিনিট রাখুন। এটি আইসির সূক্ষ্ম বলের নিচ থেকে ময়লা বের করে দেবে।"
        ),
        RepairStep(
          stepNumber = 4,
          stepTitle = "পূর্ণাঙ্গ ড্রাই ও বেকিং",
          instruction = "বোর্ডটি ৯০-১০০ ডিগ্রি সেলসিয়াস তাপমাত্রায় ১৫-২০ মিনিট শুকিয়ে নিন যাতে কোনো আর্দ্রতা অবশিষ্ট না থাকে।"
        )
      ),
      safetyTips = listOf(
        "ক্যামেরা মডিউল ও মাইক কখনোই আইপিএ দ্রবণে ডোবাবেন না, লেন্স ও ডায়াফ্রাম নষ্ট হয়ে যাবে।"
      ),
      testingMethod = "ক্লিন করার পর মাইক্রোস্কোপে প্রতিটি কানেক্টর ও আইসির চারপাশ চকচকে দেখাচ্ছে কিনা নিশ্চিত করুন।",
      difficulty = DifficultyLevel.BEGINNER,
      estimatedMinutes = 20
    ),

    Lesson(
      id = "hw_micro_jumper",
      title = "মাদারবোর্ডে মাইক্রো জাম্পারিং কৌশল",
      englishTitle = "PCB Micro Jumpering & UV Masking Technique",
      section = ScreenSection.HARDWARE,
      shortDescription = "বোর্ডের প্রিন্ট বা লাইন ছিঁড়ে গেলে অত্যন্ত সূক্ষ্ম ০.০২ মিমি এনামেল তার দিয়ে নতুন লাইন তৈরির নিয়ম।",
      symptoms = listOf(
        "ডিসপ্লে কানেক্টরের কোনো পিন ছিঁড়ে গেছে।",
        "আইসি তোলার পর বোর্ডের প্যাড/ট্র্যাক উঠে গেছে।",
        "কী-বোর্ডের ভলিউম বা পাওয়ার লাইনের ট্র্যাক কাটা।"
      ),
      causes = listOf(
        "স্ক্রু বেশি টাইট দেওয়া বা ড্রপ ড্যামেজে প্যাড ছিঁড়ে যাওয়া।",
        "অনভিজ্ঞ টেকনিশিয়ান দ্বারা অতিরিক্ত টানে আইসি তোলা।"
      ),
      toolsNeeded = listOf(
        "০.০১ বা ০.০২ মিমি ইনসুলেটেড জাম্পার ওয়্যার",
        "ইউভি সোল্ডার মাস্ক গ্লু (UV Green Oil)",
        "ইউভি কিউরিং লাইট (UV Curing Lamp)",
        "স্টেরিও মাইক্রোস্কোপ ও কার্ভড টুইজার"
      ),
      steps = listOf(
        RepairStep(
          stepNumber = 1,
          stepTitle = "কাটা ট্র্যাকের কপার অংশ বের করা",
          instruction = "সার্জিক্যাল ব্লেড দিয়ে সাবধানে কাটা লাইনের ওপরের গ্রিন কোটিং ঘষে চকচকে তামার অংশ বের করুন।"
        ),
        RepairStep(
          stepNumber = 2,
          stepTitle = "ফ্লাক্স ও রাং দিয়ে টিনিং",
          instruction = "অল্প রিবলিং পেস্ট বা রাং লাগিয়ে চিকন বিটের আয়রন দিয়ে ট্র্যাকে ছোট বল তৈরি করুন।"
        ),
        RepairStep(
          stepNumber = 3,
          stepTitle = "জাম্পার তার যুক্তকরণ",
          instruction = "তারটির মাথা হালকা পুড়িয়ে ইনসুলেশন মুক্ত করে প্যাডে নিখুঁতভাবে সোল্ডার করুন।"
        ),
        RepairStep(
          stepNumber = 4,
          stepTitle = "ইউভি গ্লু কোটিং ও কিউরিং",
          instruction = "জাম্পার তারের ওপর ড্রপ ইউভি মাস্ক দিয়ে ৩-৫ সেকেন্ড ইউভি লাইট ধরুন। তারটি পাথরের মতো শক্ত হয়ে সুরক্ষিত থাকবে।"
        )
      ),
      safetyTips = listOf(
        "বেশি চাপ দিয়ে স্ক্র্যাচ করবেন না, নিচে দ্বিতীয় লেয়ারের গ্রাউন্ড বা অন্য লাইনের সাথে শর্ট হয়ে যেতে পারে।"
      ),
      testingMethod = "মাল্টিমিটার বাজার মোডে রেখে জাম্পারের অপর প্রান্ত থেকে মূল ডেস্টিনেশনে বিপ সাউন্ড চেক করুন।",
      difficulty = DifficultyLevel.ADVANCED,
      estimatedMinutes = 30
    ),

    Lesson(
      id = "sw_bootloop",
      title = "বুটলুপ ও হ্যাং অন লোগো সমাধান",
      englishTitle = "Bootloop & Hang on Logo Complete Fix",
      section = ScreenSection.SOFTWARE,
      shortDescription = "ফোন অন হয়ে ব্র্যান্ড লোগোতে আটকে থাকা বা বারবার রিস্টার্ট নেওয়ার সফটওয়্যার ও ইএমএমসি সমাধান।",
      symptoms = listOf(
        "অন হওয়ার পর স্যামসাং, শাওমি বা রিয়েলমি লোগোতে আটকে থাকা।",
        "লোগো দেখিয়ে কয়েক সেকেন্ড পর পর স্বয়ংক্রিয়ভাবে রিস্টার্ট নেওয়া।",
        "রিকভারি মোডে প্রবেশ করতে ব্যর্থ হওয়া।"
      ),
      causes = listOf(
        "অ্যান্ড্রয়েড সিস্টেম ক্র্যাশ বা করাপ্ট ওটিএ (OTA) আপডেট।",
        "অভ্যন্তরীণ স্টোরেজ ১০০% ফুল হয়ে সিস্টেম ফাইল ব্লক।",
        "ইএমএমসি/ইউএফএস চিপের হেলথ ৯০% কনজিউমড হওয়া (Bad Sectors)।",
        "পাওয়ার বাটনের ভেতরের সুইচ আটকে থাকা (Hardware Bootloop)।"
      ),
      toolsNeeded = listOf(
        "পিসি/ল্যাপটপ ও হাই-স্পিড ইউএসবি ডেটা কেবল",
        "অ্যান্ড্রয়েড এসডিকে প্লাটফর্ম টুলস (Fastboot / ADB)",
        "অফিসিয়াল ফ্ল্যাশ টুল (SP Flash Tool / Odin / Mi Flash)"
      ),
      steps = listOf(
        RepairStep(
          stepNumber = 1,
          stepTitle = "পাওয়ার কী ফিজিক্যাল চেক",
          instruction = "অনেক সময় পাওয়ার বাটনের ভেতরের মেটাল পাত জমে শর্ট থাকে। মাল্টিমিটারে চেক করুন পাওয়ার কি অনবরত প্রেস অবস্থায় আছে কি না।"
        ),
        RepairStep(
          stepNumber = 2,
          stepTitle = "রিকভারি মোডে ওয়াইপ ক্যাশ ও ডেটা",
          instruction = "ভলিউম আপ + পাওয়ার চেপে রিকভারিতে যান। 'Wipe Cache Partition' এবং প্রয়োজনে 'Factory Reset' করুন।"
        ),
        RepairStep(
          stepNumber = 3,
          stepTitle = "স্টক রম ফ্ল্যাশিং (Stock Firmware)",
          instruction = "ডিভাইসের সঠিক মডেল ও রিজিওন কোড মিলিয়ে অফিশিয়াল ফার্মওয়্যার ডাউনলোড করে ফ্ল্যাশ করুন।"
        )
      ),
      safetyTips = listOf(
        "ভুল ফার্মওয়্যার বা ভুল বাইনারি (U1/U2/U3) ফ্ল্যাশ করলে ফোন হার্ড ব্রিক হতে পারে।"
      ),
      testingMethod = "ফ্ল্যাশ শেষে প্রথম বুট হতে ৫-১০ মিনিট লাগতে পারে, অপেক্ষা করুন এবং সেটআপ উইজার্ড আসা যাচাই করুন।",
      difficulty = DifficultyLevel.INTERMEDIATE,
      estimatedMinutes = 30
    ),

    Lesson(
      id = "sw_flashing",
      title = "অ্যান্ড্রয়েড স্টক রম ফ্ল্যাশিং গাইড",
      englishTitle = "Stock ROM Flashing (Odin, SP Flash, EDL 9008)",
      section = ScreenSection.SOFTWARE,
      shortDescription = "স্যামসাং, মিডিয়াটেক ও কোয়ালকম সিপিইউ ফোনের সম্পূর্ণ ওএস নতুন করে ইন্সটল করার প্রক্রিয়া।",
      symptoms = listOf(
        "সিস্টেম ফাইল ধ্বংস হওয়া বা ইনফেক্টেড ভাইরাস আক্রমণ।",
        "ডিভাইস পুরোপুরি ডেড বা অনলি পোর্ট মোড (Qualcomm HS-USB QDLoader 9008)।"
      ),
      causes = listOf(
        "অসমাপ্ত ফার্মওয়্যার আপডেট বা ভুল রুট প্রচেষ্টা।"
      ),
      toolsNeeded = listOf(
        "সঠিক ইউএসবি ড্রাইভার (Qualcomm / MTK / Samsung USB Driver)",
        "সিপিইউ ভিত্তিক ফ্ল্যাশার টুলস",
        "ইডিএল কেবল বা টেস্ট পয়েন্ট টুইজার"
      ),
      steps = listOf(
        RepairStep(
          stepNumber = 1,
          stepTitle = "সিপিইউ প্ল্যাটফর্ম চিহ্নিতকরণ",
          instruction = "ফোনটি কি কোয়ালকম (Qualcomm), মিডিয়াটেক (MediaTek/Dimensity), স্প্রেডট্রাম নাকি এক্সিনোস তা নিশ্চিত হোন।"
        ),
        RepairStep(
          stepNumber = 2,
          stepTitle = "ড্রাইভার ইন্সটলেশন ও পোর্ট ভেরিফিকেশন",
          instruction = "কম্পিউটারের ডিভাইস ম্যানেজারে ফোনটি প্লাগ করে সঠিক কম পোর্ট (COM Port) পেয়েছে কি না দেখুন।"
        ),
        RepairStep(
          stepNumber = 3,
          stepTitle = "ফাইল লোড ও ফ্ল্যাশ এক্সিকিউশন",
          instruction = "Scatter ফাইল বা RawProgram/Patch xml ফাইল সিলেক্ট করে Download বাটনে ক্লিক করুন।"
        )
      ),
      safetyTips = listOf(
        "ফ্ল্যাশিং চলাকালীন কখনোই ইউএসবি কেবল নাড়াচাড়া করবেন না।"
      ),
      testingMethod = "টুলে ১০০% সবুজ PASS বার্তার পর ফোন নিজে নিজে রিস্টার্ট নেবে।",
      difficulty = DifficultyLevel.INTERMEDIATE,
      estimatedMinutes = 35
    ),

    Lesson(
      id = "ch_fake_charging",
      title = "ফেক চার্জিং ও চার্জ না বাড়ার সমাধান",
      englishTitle = "Fake Charging & Battery Percentage Stuck Fix",
      section = ScreenSection.HARDWARE,
      shortDescription = "চার্জিং অ্যানিমেশন দেখালেও ব্যাটারির চার্জ বৃদ্ধি না পাওয়া বা চার্জ কমে যাওয়ার ত্রুটি নির্ণয়।",
      symptoms = listOf(
        "চার্জার লাগালে স্ক্রিনে চার্জিং লোগো দেখায় কিন্তু ঘণ্টার পর ঘণ্টা একই শতাংশ থাকে।",
        "চার্জার লাগানো অবস্থায় মোবাইল ব্যবহার করলে চার্জ আরও কমে যায়।",
        "ইউএসবি টেস্টার মিটারে কারেন্ট মাত্র ০.১০A-০.২০A গ্রহণ করে।"
      ),
      causes = listOf(
        "চার্জিং সকেটের ডেটা পিন (D+ / D-) লাইন ওপেন বা ডিসকানেক্ট।",
        "ব্যাটারি আইডি (ID) বা থার্মাল (BATT_TEMP / NTC) লাইন ক্ষতিগ্রস্ত।",
        "চার্জিং আইসি (PMI / PMIC) অভ্যন্তরীণভাবে কারেন্ট পাস করতে পারছে না।",
        "কারেন্ট সেন্স রেজিস্টর (Current Sense Resistor - R010/R020) মান পরিবর্তিত হওয়া।"
      ),
      toolsNeeded = listOf(
        "ইউএসবি পাওয়ার টেস্টার মিটার (USB Doctor / Ammeter)",
        "মাল্টিমিটার ডায়োড ও ডিসি মোড",
        "চার্জিং সাব-বোর্ড বা সকেট রিপ্লেসমেন্ট"
      ),
      steps = listOf(
        RepairStep(
          stepNumber = 1,
          stepTitle = "ইউএসবি টেস্টার দিয়ে অ্যাম্পিয়ার পর্যবেক্ষণ",
          instruction = "সাধারণ ফাস্ট চার্জিংয়ে ১.২A-২.০A কারেন্ট নেয়। যদি ০.২০A-এর কম থাকে তবে এটি নিশ্চিত ফেক চার্জিং।"
        ),
        RepairStep(
          stepNumber = 2,
          stepTitle = "ডেটা লাইন D+ ও D- ডায়োড মান পরীক্ষা",
          instruction = "চার্জিং পিনের D+ ও D- পিনে ডায়োড মোডে রিডিং ৫০০-৭৫০ ও প্রায় সমান হওয়া উচিত। কোনো একটি ওপেন (OL) হলে ফাস্ট চার্জিং কাজ করবে না।"
        ),
        RepairStep(
          stepNumber = 3,
          stepTitle = "সেন্স রেজিস্টর ও চার্জিং কয়েল চেক",
          instruction = "চার্জিং আইসির পাশে থাকা কালো বুস্ট কয়েল ও R010 রেজিস্টরটি চেক করুন। রেজিস্টর মান ০.০১ ওহম হওয়া উচিত।"
        ),
        RepairStep(
          stepNumber = 4,
          stepTitle = "চার্জিং আইসি পরিবর্তন",
          instruction = "কয়েল ও লাইন ঠিক থাকলে চার্জিং আইসিটি পরিবর্তন করুন।"
        )
      ),
      safetyTips = listOf(
        "সস্তা বা ভেজাল ক্যাবল ব্যবহার করে পরীক্ষা করবেন না, সবসময় মানসম্মত টেস্ট কেবল ব্যবহার করুন।"
      ),
      testingMethod = "টেস্টার মিটারে কারেন্ট ১.৫ অ্যাম্পিয়ার বা তার বেশি টানা এবং ব্যাটারি পার্সেন্টেজ বাড়তে থাকা।",
      difficulty = DifficultyLevel.INTERMEDIATE,
      estimatedMinutes = 20
    ),

    Lesson(
      id = "ch_thermal_error",
      title = "ব্যাটারি টেম্পারেচার হাই/লো এরর সমাধান",
      englishTitle = "Thermal Error (Battery Temperature High/Low Fix)",
      section = ScreenSection.HARDWARE,
      shortDescription = "চার্জিংয়ে লাগালে 'Charging Paused: Battery temperature too high/low' মেসেজ আসার কারণ ও থার্মিস্টর মেরামত।",
      symptoms = listOf(
        "চার্জার লাগালে সতর্কবার্তা ও বিপ সাউন্ড হয়ে চার্জিং বন্ধ হয়ে যায়।",
        "স্বাভাবিক রুম টেম্পারেচারেও ফোন গরম দেখাচ্ছে।"
      ),
      causes = listOf(
        "এনটিসি (NTC) থার্মাল রেজিস্টর (১০k, ৪৭k বা ১০০k) ড্যামেজ বা মিসিং।",
        "সাব-বোর্ড থেকে মেইন বোর্ডের থার্মাল লাইনের ফ্লেক্স কেবল ছিঁড়ে যাওয়া।"
      ),
      toolsNeeded = listOf(
        "মাল্টিমিটার ওহম মোড (200kΩ)",
        "সার্কিট ডায়াগ্রাম বা বোর্নিও স্কিম্যাটিক্স (Schematics)",
        "প্রতিস্থাপনযোগ্য ৪৭k/১০০k রেজিস্টর"
      ),
      steps = listOf(
        RepairStep(
          stepNumber = 1,
          stepTitle = "ব্যাটারি থার্মাল টার্মিনাল চেক",
          instruction = "প্রথমে অন্য একটি ভালো ব্যাটারি লাগিয়ে দেখুন ব্যাটারির ভেতরের থার্মিস্টর নষ্ট কি না।"
        ),
        RepairStep(
          stepNumber = 2,
          stepTitle = "সাব-বোর্ডের NTC রেজিস্টর চিহ্নিতকরণ",
          instruction = "চার্জিং সাব-বোর্ডে থাকা ৪৭k ওহম থার্মিস্টরটি ওহম মোডে মেপে দেখুন ৪৭ কিলোওহম রিডিং দেয় কি না। ০ বা অসীম দেখাবে না।"
        ),
        RepairStep(
          stepNumber = 3,
          stepTitle = "রেজিস্টর পরিবর্তন বা জাম্পার",
          instruction = "নষ্ট থার্মিস্টরটি ফেলে একই মানের আরেকটি বোর্ড থেকে রেজিস্টর তুলে লাগান।"
        )
      ),
      safetyTips = listOf(
        "থার্মাল লাইন সরাসরি গ্রাউন্ডে জাম্পার করবেন না, এতে প্রটেকশন পুরোপুরি বন্ধ হয়ে যাবে।"
      ),
      testingMethod = "চার্জার লাগালে কোনো ওয়ার্নিং পপ-আপ ছাড়া নির্বিঘ্নে চার্জ শুরু হবে।",
      difficulty = DifficultyLevel.BEGINNER,
      estimatedMinutes = 15
    ),

    Lesson(
      id = "bd_display_replacement",
      title = "ডিসপ্লে ও টাচ কম্বো ফোল্ডার পরিবর্তনের এসওপি",
      englishTitle = "Display & Touch Screen Folder Replacement SOP",
      section = ScreenSection.HARDWARE,
      shortDescription = "ভাঙা স্ক্রিন সাবধানে খোলা, ফ্রেম পরিষ্কার এবং নতুন অরিজিনাল/ওএলইডি কম্বো ফিটিংয়ের পেশাদার নিয়ম।",
      symptoms = listOf(
        "ডিসপ্লে গ্লাস ভাঙা, টাচ কাজ করে না বা স্ক্রিনে রঙিন দাগ/কালি ছড়িয়ে যাওয়া।"
      ),
      causes = listOf(
        "হাত থেকে পড়ে মেকানিক্যাল ক্ষতি।"
      ),
      toolsNeeded = listOf(
        "ডিসপ্লে সেপারেটর মেশিন (৮০°-৯০°C)",
        "টি-৭০০০ / বি-৭০০০ ডিসপ্লে ব্ল্যাক গ্লু",
        "সাকশন কাপ ও প্লাস্টিক ওপেনিং প্রিক",
        "রাবার ব্যান্ড ক্ল্যাম্প"
      ),
      steps = listOf(
        RepairStep(
          stepNumber = 1,
          stepTitle = "৮০ ডিগ্রি তাপে প্রি-হিটিং",
          instruction = "সেপারেটর মেশিনে ৩ মিনিট রেখে আঠা নরম করুন।"
        ),
        RepairStep(
          stepNumber = 2,
          stepTitle = "পুরাতন ফ্রেম পরিষ্কারকরণ",
          instruction = "ফ্রেমের খাঁজে থাকা পুরোনো আঠা ও কাঁচের গুঁড়ো সম্পূর্ণরূপে পরিষ্কার করুন।"
        ),
        RepairStep(
          stepNumber = 3,
          stepTitle = "ড্রাই টেস্ট (আঠা লাগানোর পূর্বে)",
          instruction = "আঠা না লাগিয়েই নতুন ডিসপ্লের কেবল লাগিয়ে ডিসপ্লে লাইট, কালার ও কী-বোর্ড টাচ পুরোপুরি টেস্ট করুন।"
        ),
        RepairStep(
          stepNumber = 4,
          stepTitle = "গ্লু প্রয়োগ ও রাবার ব্যান্ডিং",
          instruction = "ফ্রেমের বর্ডারে সুষমভাবে গ্লু লাগান এবং রাবার ব্যান্ড দিয়ে হালকা চাপে ১ ঘণ্টা বেঁধে রাখুন।"
        )
      ),
      safetyTips = listOf(
        "স্ক্রিনের কোণে অতিরিক্ত চাপ দেবেন না, ওএলইডি প্যানেল ফেটে যেতে পারে।"
      ),
      testingMethod = "সকল বর্ণ ও টাচ এরিয়া সমান স্পর্শে কাজ করছে কি না ডায়ালার কোড (*#0*#) দিয়ে চেক করুন।",
      difficulty = DifficultyLevel.BEGINNER,
      estimatedMinutes = 30
    ),

    Lesson(
      id = "bd_backlight_repair",
      title = "ডিসপ্লে লাইট ও ব্যাকলাইট সার্কিট মেরামত",
      englishTitle = "Backlight Boost Circuit (Coil & Diode 20V-40V) Repair",
      section = ScreenSection.HARDWARE,
      shortDescription = "স্ক্রিনে আবছা ছবি দেখা যায় কিন্তু কোনো আলো নেই—ব্যাকলাইট কয়েল ও বুস্ট ডায়োড মেরামত।",
      symptoms = listOf(
        "টর্চ মারলে স্ক্রিনে ছবি বা লেখা দেখা যায় কিন্তু কোনো ডিসপ্লে লাইট জ্বলে না।"
      ),
      causes = listOf(
        "বুস্ট কয়েল (১০uH বা ২২uH) পুড়ে যাওয়া।",
        "শটকি ডায়োড (Schottky Diode) শর্ট বা ওপেন হওয়া।",
        "ডিসপ্লে লাইট অ্যানোড (LED_A) লাইনের ফিল্টার রেজিস্টর ওপেন।"
      ),
      toolsNeeded = listOf(
        "ডিজিটাল মাল্টিমিটার",
        "হট এয়ার গান",
        "১০uH বুস্ট কয়েল ও শটকি ডায়োড"
      ),
      steps = listOf(
        RepairStep(
          stepNumber = 1,
          stepTitle = "ডিসপ্লে অ্যানোড পিনের কোল্ড টেস্টিং",
          instruction = "এফপিসি কানেক্টরের LED_A পিনে ডায়োড রিডিং ৪০০-৬০০ ওহম থাকা উচিত। OL পেলে লাইনের কয়েল কাটা।"
        ),
        RepairStep(
          stepNumber = 2,
          stepTitle = "হট টেস্টিং (ডিসপ্লে অন অবস্থায়)",
          instruction = "ডিসপ্লে চালু থাকা অবস্থায় অ্যানোড লাইনে ২০ ভোল্ট থেকে ৪০ ভোল্ট বুস্টিং ভোল্টেজ থাকতে হবে।"
        ),
        RepairStep(
          stepNumber = 3,
          stepTitle = "বুস্ট কয়েল ও ডায়োড পরিবর্তন",
          instruction = "যদি ৩.৭ ভোল্ট থাকে কিন্তু ২০ ভোল্টে বুস্ট না হয়, তবে বুস্ট কয়েল ও ডায়োড একসঙ্গে পরিবর্তন করুন।"
        )
      ),
      safetyTips = listOf(
        "ডিসপ্লে কানেক্টরে ব্যাটারি লাগানো অবস্থায় হাত বা টুইজার লাগাবেন না, হাই ভোল্টেজে বোর্ড জ্বলে যেতে পারে।"
      ),
      testingMethod = "স্ক্রিনের ব্যাকলাইট পূর্ণ ব্রাইটনেসে আলো দেবে।",
      difficulty = DifficultyLevel.INTERMEDIATE,
      estimatedMinutes = 25
    ),

    Lesson(
      id = "ns_no_service",
      title = "নো সার্ভিস ও ইমার্জেন্সি কল অনলি সমস্যার সমাধান",
      englishTitle = "No Service & Emergency Calls Only Troubleshooting",
      section = ScreenSection.HARDWARE,
      shortDescription = "সিম কার্ড পেলেও নেটওয়ার্ক সিগন্যাল বার না আসার কারণ, WTR আইসি ও অ্যান্টেনা সুইচ মেরামত।",
      symptoms = listOf(
        "সিম কার্ড শো করে কিন্তু 'No Service' বা 'Emergency calls only' লেখা থাকে।",
        "ম্যানুয়াল নেটওয়ার্ক সার্চ দিলে কোনো অপারেটর লিস্ট আসে না।"
      ),
      causes = listOf(
        "আইএমইআই বা বেসব্যান্ড ভার্সন আননোন (Unknown Baseband)।",
        "ডব্লিউটিআর / এসডিআর আরএফ ট্রান্সসিভার (WTR/SDR IC) নষ্ট।",
        "অ্যান্টেনা কেবল বা সুইচ খোলা।"
      ),
      toolsNeeded = listOf(
        "মাল্টিমিটার",
        "ডিসি পাওয়ার সাপ্লাই",
        "WTR2965 / SDR660 বা সমমানের আইসি"
      ),
      steps = listOf(
        RepairStep(
          stepNumber = 1,
          stepTitle = "সফটওয়্যার ও আইএমইআই যাচাই",
          instruction = "ডায়াল করুন *#06#, আইএমইআই নম্বর সঠিকভাবে না আসলে এটি সফটওয়্যার বা সিপিইউ সমস্যা।"
        ),
        RepairStep(
          stepNumber = 2,
          stepTitle = "অ্যান্টেনা কেবল ও গোল্ডেন টিপ চেক",
          instruction = "কো-অ্যাক্সিয়াল ক্যাবলটির কন্টিনিউটি বাজার মোডে চেক করুন।"
        ),
        RepairStep(
          stepNumber = 3,
          stepTitle = "WTR আইসির ভোল্টেজ লাইন চেক (VREG_L5 1.0V & 1.8V)",
          instruction = "সিম লাগিয়ে সার্চ করার সময় WTR আইসির ইনপুট লাইনে ১.০ ভোল্ট ও ১.৮ ভোল্ট এলডিও আসছে কি না মাপুন।"
        ),
        RepairStep(
          stepNumber = 4,
          stepTitle = "WTR আইসি হিট বা পরিবর্তন",
          instruction = "ভোল্টেজ ঠিক থাকলে ৩৫০°C তাপে WTR আইসি পরিবর্তন করুন।"
        )
      ),
      safetyTips = listOf(
        "আরএফ সেকশন খুব সেনসিটিভ, অতিরিক্ত তাপে নেটওয়ার্ক পুরোপুরি নষ্ট হতে পারে।"
      ),
      testingMethod = "সিম ঢোকানোর কয়েক সেকেন্ডে ফুল টাওয়ার ৪জি/৫জি সিগন্যাল শো করবে।",
      difficulty = DifficultyLevel.ADVANCED,
      estimatedMinutes = 30
    ),

    Lesson(
      id = "ns_speaker_mic",
      title = "রিংগার, ইয়ারপিস ও ডিজিটাল মাইক মেরামত",
      englishTitle = "Ringer, Earpiece & Digital MEMS Mic Replacement",
      section = ScreenSection.HARDWARE,
      shortDescription = "কল চলাকালীন কথা না শোনা বা ওপাশে কথা না যাওয়ার হার্ডওয়্যার সমাধান।",
      symptoms = listOf(
        "লাউডস্পিকারে গান বাজে না কিন্তু হেডফোনে সাউন্ড আছে।",
        "রেকর্ডারে কথা রেকর্ড হয় না (মাইক কাজ করছে না)।"
      ),
      causes = listOf(
        "স্পিকার কয়েল কেটে যাওয়া বা ধূলিকণা জমে আটকে যাওয়া।",
        "ডিজিটাল ৪-পিন মেমস মাইকের ওপর অতিরিক্ত তাপ লেগে ড্যামেজ।"
      ),
      toolsNeeded = listOf(
        "মাল্টিমিটার বাজার মোড",
        "৪-পিন গোল্ডেন ডিজিটাল মাইক",
        "সোল্ডারিং আয়রন"
      ),
      steps = listOf(
        RepairStep(
          stepNumber = 1,
          stepTitle = "স্পিকার রেজিস্ট্যান্স পরিমাপ",
          instruction = "স্পিকারের দুই প্রান্তে মাল্টিমিটার ধরলে ৮ থেকে ৩২ ওহম রিডিং ও হালকা খড়খড় শব্দ হবে।"
        ),
        RepairStep(
          stepNumber = 2,
          stepTitle = "ডিজিটাল মাইক নিরাপদ পরিবর্তন",
          instruction = "ডিজিটাল মাইকের ছিদ্রের ওপর কখনো সরাসরি হট এয়ার মারবেন না। বোর্ডের নিচ থেকে হিট দিয়ে অথবা সোল্ডারিং আয়রনের ওপর সাব-বোর্ড রেখে আলতো করে মাইক তুলুন ও বসান।"
        )
      ),
      safetyTips = listOf(
        "মাইকের মাইক্রোফোনে কোনো পেস্ট বা আইপিএ তরল ঢুকতে দেবেন না।"
      ),
      testingMethod = "ভয়েস রেকর্ডার অ্যাপে কথা বলে স্পষ্ট প্লেব্যাক শোনা।",
      difficulty = DifficultyLevel.BEGINNER,
      estimatedMinutes = 15
    )
  )

  val components = listOf(
    ComponentItem(
      id = "comp_resistor",
      bengaliName = "এসএমডি রেজিস্টর (রোধক)",
      englishName = "SMD Resistor",
      symbol = "R",
      function = "বিদ্যুৎ প্রবাহকে বাধা দিয়ে ভোল্টেজ নিয়ন্ত্রণ ও কারেন্ট সেন্সিং করা।",
      appearance = "সাধারণত কালো রঙের চ্যাপ্টা আয়তাকার টুকরা, যার দুই মাথায় চকচকে রুপালি সোল্ডার প্যাড থাকে। কখনো কখনো গায়ে মান লেখা থাকে (যেমন 0, 100, R010)।",
      damageSigns = "পুড়ে কালো হয়ে ফোস্কা পড়া, ফেটে যাওয়া বা মান অসীম (OL) হয়ে যাওয়া।",
      howToTest = "মাল্টিমিটার ওহম (Ω) রেঞ্জে রেখে দুই মাথায় প্রোব ধরুন।",
      goodReading = "রেজিস্টরের গায়ের মানের কাছাকাছি রিডিং দেখাবে (সহনশীলতা ±৫%)।",
      badReading = "অসীম (OL) বা অত্যন্ত বেশি ভ্রান্ত মান।"
    ),
    ComponentItem(
      id = "comp_capacitor",
      bengaliName = "সিরামিক ক্যাপাসিটর (ধারক)",
      englishName = "SMD Ceramic Capacitor (MLCC)",
      symbol = "C",
      function = "ডিসি ভোল্টেজ ফিল্টারিং করা, নয়েজ দূর করা এবং তাৎক্ষণিক চার্জ সঞ্চয় করে ভোল্টেজ স্থিতিশীল রাখা।",
      appearance = "হলুদাভ বাদামী বা ধূসর রঙের কিউব আকৃতি। কোনো পোলারিটি নেই, দুই মাথা চকচকে রুপালি।",
      damageSigns = "অভ্যন্তরীণভাবে শর্ট হয়ে যাওয়া—এতে পুরো পাওয়ার লাইন গ্রাউন্ডের সাথে শর্ট হয়ে যায়।",
      howToTest = "মাল্টিমিটার বাজার/ডায়োড মোডে রেখে দুই মাথায় প্রোব স্পর্শ করুন।",
      goodReading = "এক প্রান্তে কোনো বিপ সাউন্ড হবে না, ডায়োড ভ্যালু দেখাবে বা ১/অসীম থাকবে।",
      badReading = "উভয় প্রান্তে ০.০০১ রিডিং সহ একটানা বিপ (Beep) শব্দ বাজবে (ফুল শর্ট)।"
    ),
    ComponentItem(
      id = "comp_inductor",
      bengaliName = "ইনডাক্টর / কয়েল",
      englishName = "SMD Inductor / Boost & Buck Coil",
      symbol = "L",
      function = "ভোল্টেজ বুস্ট (বাড়ানো) বা বাক (কমানো) করা এবং হাই-ফ্রিকোয়েন্সি কারেন্ট ফিল্টারিং।",
      appearance = "গাঢ় ধূসর বা কালো রঙের মোটা চারকোনা কম্পোনেন্ট, কখনো তামার তারের কয়েল দেখা যায়।",
      damageSigns = "ভেতরের তার পুড়ে ওপেন বা সংযোগ বিচ্ছিন্ন হওয়া।",
      howToTest = "মাল্টিমিটার কন্টিনিউটি / বাজার মোডে দুই মাথায় ধরুন।",
      goodReading = "কয়েল ঠিক থাকলে কন্টিনিউটি বিপ সাউন্ড দেবে এবং ০ থেকে ২ ওহম রিডিং দেখাবে।",
      badReading = "কোনো সাউন্ড নেই ও মাল্টিমিটারে ১ বা OL দেখায় (লাইন ওপেন)।"
    ),
    ComponentItem(
      id = "comp_diode",
      bengaliName = "ডায়োড ও শটকি ডায়োড",
      englishName = "Diode & Schottky Diode",
      symbol = "D",
      function = "বিদ্যুৎ প্রবাহকে কেবল একমুখী করা এবং ব্যাকলাইট সার্কিটে ভোল্টেজ রেকটিফিকেশন।",
      appearance = "কালো আয়তাকার দেহ, এক মাথায় সাদা বা ধূসর দাগ থাকে যা ক্যাথোড (মাইনাস) নির্দেশ করে।",
      damageSigns = "উভয় দিকে শর্ট হয়ে যাওয়া বা সম্পূর্ণ ওপেন হওয়া।",
      howToTest = "মাল্টিমিটার ডায়োড মোডে রেখে ফরোয়ার্ড ও রিভার্স বায়াস টেস্ট করুন।",
      goodReading = "ফরোয়ার্ডে (লাল অ্যানোড, কালো ক্যাথোড) ২০০-৬০০ ভ্যালু; রিভার্সে ১ (OL)।",
      badReading = "উভয় দিকে বিপ সাউন্ড (শর্ট) বা কোনো দিকেই মান না দেখানো (ওপেন)।"
    ),
    ComponentItem(
      id = "comp_pmic",
      bengaliName = "পাওয়ার ম্যানেজমেন্ট আইসি (PMIC)",
      englishName = "Power Management IC",
      symbol = "U / PMIC",
      function = "ব্যাটারি থেকে বিদ্যুৎ নিয়ে প্রসেসর, মেমোরি, ক্যামেরা ও ডিসপ্লের জন্য বিভিন্ন বাক ও এলডিও ভোল্টেজ তৈরি ও বিতরণ।",
      appearance = "চারকোনা বিজিএ (BGA) চিপ, যার নিচে শত শত সূক্ষ্ম সোল্ডার বল থাকে। চারপাশে অনেকগুলো কয়েল ও ক্যাপাসিটর থাকে।",
      damageSigns = "ফোন অতিরিক্ত গরম হওয়া, চার্জ না নেওয়া বা কোনো সেকেন্ডারি আউটপুট ভোল্টেজ না বের হওয়া।",
      howToTest = "আইসির চারপাশের বাক কয়েলগুলোতে হট টেস্টিংয়ে ০.৮V, ১.১V, ১.৮V আউটপুট চেক করুন।",
      goodReading = "সকল বাক ও এলডিও ভোল্টেজ স্থিতিশীল থাকে।",
      badReading = "কোনো ভোল্টেজ নেই বা কোনো কয়েলের লাইন গ্রাউন্ডের সাথে শর্ট।"
    ),
    ComponentItem(
      id = "comp_cpu",
      bengaliName = "সেন্ট্রাল প্রসেসিং ইউনিট (CPU / SoC)",
      englishName = "Central Processing Unit (CPU / SoC)",
      symbol = "U / CPU",
      function = "স্মার্টফোনের মূল মস্তিষ্ক—অপারেটিং সিস্টেম রান করা ও সকল ক্রিয়াকলাপ সমন্বয় করা।",
      appearance = "মাদারবোর্ডের সবচেয়ে বড় বিজিএ চিপ, যার ওপরে কোয়ালকম স্ন্যাপড্রাগন, মিডিয়াটেক হেলিও বা এক্সিনোস নাম খোদাই করা থাকে।",
      damageSigns = "ফোন অন না হওয়া, কম্পিউটার পোর্টে পোর্ট ধরে ছেড়ে দেওয়া বা ড্রপ ড্যামেজে প্যাড ড্রাই হওয়া।",
      howToTest = "ইউএসবি দিয়ে পিসিতে লাগালে ৯০০৮ বা এমটিকে পোর্ট সংযোগ এবং বুট কারেন্ট পর্যবেক্ষণ।",
      goodReading = "পাওয়ার বাটন চাপলে সঠিক বুট সিকোয়েন্সে কারেন্ট ০.১A থেকে ১A পর্যন্ত মসৃণভাবে ওঠা।",
      badReading = "কারেন্ট ০.০২A-তে স্থির দাঁড়িয়ে থাকা বা কোনো প্রতিক্রিয়া না থাকা।"
    )
  )

  val multimeterScenarios = listOf(
    MultimeterTestScenario(
      id = "test_cap_short",
      componentTitle = "ক্যাপাসিটর ফুল শর্ট টেস্ট",
      englishTitle = "Filter MLCC Capacitor Short Circuit Test",
      properMode = "Buzzer / Diode",
      probeInstructions = "লাল প্রোব মাদারবোর্ডের গ্রাউন্ডে (টিনের শিল্ডে) রাখুন এবং কালো প্রোব ক্যাপাসিটরের দুই মাথায় পর্যায়ক্রমে স্পর্শ করুন।",
      healthyReading = "এক মাথায় গ্রাউন্ড বিপ (০.০০১) দেবে, কিন্তু অপর মাথায় ৪০০-৬০০ ডায়োড ভ্যালু থাকবে (বিপ হবে না)।",
      faultyReading = "উভয় মাথায় একটানা বিপ সাউন্ড হবে এবং ০.০০১ রিডিং দেখাবে।",
      simulatedHealthyValue = 485.0,
      simulatedFaultyValue = 0.001,
      unitSymbol = "mV (Diode)",
      makesBeepSound = true,
      explanation = "ক্যাপাসিটর দুই পাশেই বিপ দিলে বুঝতে হবে ক্যাপাসিটরটি পুড়ে শর্ট অথবা পুরো লাইনটি শর্ট হয়ে গেছে।"
    ),
    MultimeterTestScenario(
      id = "test_battery_voltage",
      componentTitle = "ব্যাটারি ভোল্টেজ পরীক্ষা",
      englishTitle = "Battery Terminal DC Voltage Measurement",
      properMode = "DC Voltage (20V)",
      probeInstructions = "লাল প্রোব ব্যাটারির (+) পিনে এবং কালো প্রোব (-) পিনে স্পর্শ করুন।",
      healthyReading = "চার্জ থাকা ব্যাটারিতে ৩.৭V থেকে ৪.২V ডিসি ভোল্টেজ দেখাবে।",
      faultyReading = "০.০০V থেকে ২.৮V (ডিপ ডিসচার্জড বা প্রোটেকশন লক)।",
      simulatedHealthyValue = 3.85,
      simulatedFaultyValue = 1.20,
      unitSymbol = "V DC",
      makesBeepSound = false,
      explanation = "ভোল্টেজ ৩.০ ভোল্টের নিচে নামলে ব্যাটারি বুস্টার দিয়ে চার্জ দিয়ে ৩.৭ ভোল্টে না তোলা পর্যন্ত ফোন অন হবে না।"
    ),
    MultimeterTestScenario(
      id = "test_coil_continuity",
      componentTitle = "বাক/বুস্ট কয়েল কন্টিনিউটি টেস্ট",
      englishTitle = "Inductor Continuity & Integrity Test",
      properMode = "Buzzer / Diode",
      probeInstructions = "কয়েলের দুই প্রান্তে দুইটি প্রোব স্পর্শ করুন।",
      healthyReading = "কয়েল ভালো থাকলে কন্টিনিউটি বিপ সাউন্ড বাজবে এবং ০.০০০ থেকে ০.০০৩ ওহম দেখাবে।",
      faultyReading = "কোনো বিপ সাউন্ড হবে না, ডিসপ্লেতে ১ বা OL (Open Loop) দেখাবে।",
      simulatedHealthyValue = 0.001,
      simulatedFaultyValue = 999.0,
      unitSymbol = "Ω",
      makesBeepSound = true,
      explanation = "কয়েল তারের পেঁচানো কুন্ডলী হওয়ায় সুস্থ কয়েলে অবিচ্ছিন্ন সংযোগ বা বিপ সাউন্ড আসা স্বাভাবিক।"
    ),
    MultimeterTestScenario(
      id = "test_usb_vbus",
      componentTitle = "ইউএসবি VBUS ৫ ভোল্ট ইনপুট টেস্ট",
      englishTitle = "USB VBUS 5V Charger Input Hot Test",
      properMode = "DC Voltage (20V)",
      probeInstructions = "চার্জার লাগিয়ে কালো প্রোব গ্রাউন্ডে এবং লাল প্রোব সাব-বোর্ডের VBUS টেস্ট পয়েন্টে রাখুন।",
      healthyReading = "৫.০ ভোল্ট থেকে ৫.২ ভোল্ট স্থিতিশীল ডিসি রিডিং আসবে।",
      faultyReading = "০.০০ ভোল্ট বা ওঠানামা করা ফ্লিকারিং ভোল্টেজ।",
      simulatedHealthyValue = 5.12,
      simulatedFaultyValue = 0.45,
      unitSymbol = "V DC",
      makesBeepSound = false,
      explanation = "৫ ভোল্ট না আসলে বুঝতে হবে চার্জিং পোর্ট বা চার্জার ক্যাবল নষ্ট।"
    )
  )

  val circuitSections = listOf(
    CircuitSection(
      id = "sec_power",
      titleBengali = "প্রাইমারি পাওয়ার ও পিএমআইসি লাইন",
      titleEnglish = "Primary Power & PMIC System (VBAT, VPH_PWR)",
      voltageRange = "৩.৭V - ৪.৪V",
      primaryICs = listOf("PMI8998 / SM5713", "PM845 / MT6357", "Main Power IC"),
      keyLines = listOf("VBAT", "VPH_PWR / VCC_MAIN", "VREG_S4 1.8V", "VDD_CORE 0.8V", "VREG_L12"),
      commonFaults = "ভি-পিএইচ লাইনে ফুল শর্ট হলে ফোন পুরোপুরি ডেড হয় এবং কোনো রিডিং ছাড়া গরম হয়।",
      colorHex = 0xFFFF5722,
      testPoints = listOf("TP_VBAT", "TP_VPH", "L_BUCK1", "L_BUCK2", "C_PMIC_IN")
    ),
    CircuitSection(
      id = "sec_charging",
      titleBengali = "চার্জিং ও ওভিপি সার্কিট",
      titleEnglish = "Charging System & OVP Protection (VBUS 5V)",
      voltageRange = "৫.০V - ৯.০V / ১২V (QC/PD)",
      primaryICs = listOf("OVP IC (Over Voltage Protection)", "BQ25601 / BQ24190", "Type-C Controller"),
      keyLines = listOf("VBUS_5V", "USB_DP (D+)", "USB_DM (D-)", "CC1 / CC2", "BATT_TEMP"),
      commonFaults = "ওভিপি আইসি পুড়ে গেলে ৫ ভোল্ট ইনপুট হলেও চার্জিং আইসিতে বিদ্যুৎ পৌঁছায় না।",
      colorHex = 0xFF00ADB5,
      testPoints = listOf("TP_VBUS", "TP_DP", "TP_DM", "TP_OVP_OUT", "TP_CC1")
    ),
    CircuitSection(
      id = "sec_display",
      titleBengali = "ডিসপ্লে ও ব্যাকলাইট বুস্ট সার্কিট",
      titleEnglish = "Display & Backlight Boost Circuit (20V - 40V)",
      voltageRange = "১.৮V, ৩.০V (Logic) / ২০V-৪০V (LED Anode)",
      primaryICs = listOf("Backlight Driver IC", "Display PMIC (TPS65132)", "AMOLED Bias IC"),
      keyLines = listOf("LED_A (Anode)", "LED_K1 (Cathode 1)", "LED_K2 (Cathode 2)", "VSP (+5V)", "VSN (-5V)"),
      commonFaults = "বুস্ট ডায়োড নষ্ট হলে বা ডিসপ্লে ফিল্টার পুড়লে আলো জ্বলে না।",
      colorHex = 0xFF9C27B0,
      testPoints = listOf("TP_LEDA", "TP_LEDK", "TP_VSP", "TP_VSN", "TP_PWM")
    ),
    CircuitSection(
      id = "sec_rf",
      titleBengali = "আরএফ নেটওয়ার্ক ও ট্রান্সসিভার",
      titleEnglish = "RF Cellular & Transceiver (2G/4G/5G)",
      voltageRange = "১.০V, ১.৮V, ৩.৮V (VPA)",
      primaryICs = listOf("WTR2965 / SDR660", "Skyworks 2G/3G/4G PA", "Antenna Switch"),
      keyLines = listOf("VPA_APT", "VREG_L5 1.0V", "VREG_L1 1.8V", "RF_CLK 19.2MHz"),
      commonFaults = "WTR আইসিতে ১.০V বা ১.৮V সাপ্লাই বন্ধ হলে নো সার্ভিস দেখায়।",
      colorHex = 0xFF2196F3,
      testPoints = listOf("TP_VPA", "TP_WTR_1V", "TP_WTR_1V8", "ANT_DET")
    )
  )

  val quizQuestions = QuizData.quizQuestions

  val dailyServicingTips = listOf(
    "টিপস ১: যেকোনো মাদারবোর্ডে কাজ করার আগে অবশ্যই প্রথম পদক্ষেপে ব্যাটারি কানেক্টর খুলে নিন। ব্যাটারি সংযুক্ত অবস্থায় কাজ করলে আইসি জ্বলে যাওয়ার ঝুঁকি থাকে।",
    "টিপস ২: হট এয়ার গানে সিপিইউ বা ইউএফএস তোলার সময় তাপমাত্রা সর্বোচ্চ ৩৪০°-৩৬০°C এবং এয়ার ৩-৪ এ রাখুন, অতিরিক্ত হিটে বোর্ড ফুলে যায়।",
    "টিপস ৩: নতুন ডিসপ্লে লাগানোর আগে কখনোই আঠা লাগাবেন না। প্রথমে কেবল যুক্ত করে ডিসপ্লে ও টাচের প্রতিটি কী ভালোভাবে টেস্ট করে নিন।",
    "টিপস ৪: মাইক্রো জাম্পারিংয়ের পর সবসময় ইউভি গ্লু (UV Mask) লাগিয়ে ইউভি লাইট দিয়ে শুকিয়ে নিন, অন্যথায় বডি শর্ট হওয়ার সম্ভাবনা থাকে।",
    "টিপস ৫: শর্ট কিলার ব্যবহারের সময় মূল লাইনের ভোল্টেজ রেটিং (যেমন VDD_CORE-এ সর্বোচ্চ ১.০V) মাথায় রাখুন, কখনোই বেশি ভোল্টেজ দেবেন না।"
  )
}
