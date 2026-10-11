package com.example.data

import com.example.model.ComponentDetail
import com.example.model.ResistorBandColor

object ComponentLibraryData {

  val componentsList = listOf(
    ComponentDetail(
      id = "comp_resistor",
      bengaliName = "রেজিস্টর (রোধক)",
      englishName = "Resistor",
      symbol = "R (—/\\/\\/\\—)",
      shortFunction = "বিদ্যুৎ প্রবাহ হ্রাস করে নির্দিষ্ট ভোল্টেজ বা সংকেত তৈরি করা এবং কারেন্ট পরিমাপ করা।",
      howToIdentify = "মাদারবোর্ডে ছোট কালো রঙের আয়তাকার চ্যাপ্টা টুকরা। দুই মাথায় রুপালি সংযোগ প্যাড থাকে। মাঝে মাঝে মান লেখা থাকে (যেমন 0, 100, R010)।",
      multimeterTestingRule = "মাল্টিমিটার ওহম (Ω) রেঞ্জে রেখে দুই মাথায় ধরুন। রেজিস্টরের গায়ের মানের কাছাকাছি রিডিং দেখাবে।",
      commonFaults = "মান বেড়ে যাওয়া (Value Shifted) বা অভ্যন্তরীণভাবে ওপেন হয়ে অসীম (OL) দেখানো।",
      safetyNotes = "বোর্ডে পাওয়ার থাকা অবস্থায় রেজিস্ট্যান্স মাপবেন না, ভুল রিডিং আসবে বা মিটার নষ্ট হতে পারে।",
      visualIconType = "resistor"
    ),
    ComponentDetail(
      id = "comp_capacitor",
      bengaliName = "ক্যাপাসিটর (ধারক)",
      englishName = "Capacitor (MLCC)",
      symbol = "C (—||— / —|(—)",
      shortFunction = "বিদ্যুৎ সঞ্চয় করা, ভোল্টেজ ফিল্টারিং ও মসৃণ রাখা, এবং এসি সংকেত পাস করে ডিসি ব্লক করা।",
      howToIdentify = "হলুদাভ বাদামি, ধূসর বা হালকা কমলা রঙের কিউব আকৃতি। কোনো গায়ে মান লেখা থাকে না। দুই মাথায় রুপালি প্যাড থাকে।",
      multimeterTestingRule = "বাজার/ডায়োড মোডে লাল প্রোব গ্রাউন্ডে এবং কালো প্রোব ক্যাপাসিটরের দুই মাথায় ধরুন। এক মাথায় ডায়োড মান (৩৫০-৭০০) আসবে, অন্য মাথায় গ্রাউন্ড ০.০০০ আসবে।",
      commonFaults = "অভ্যন্তরীণভাবে শর্ট হয়ে যাওয়া। ক্যাপাসিটর শর্ট হলে পুরো মাদারবোর্ডের পাওয়ার লাইন গ্রাউন্ডের সাথে শর্ট হয়ে ফোন ডেড হয়ে যায়।",
      safetyNotes = "উচ্চ ক্ষমতার ক্যাপাসিটরে চার্জ জমা থাকতে পারে, হাত দেওয়ার আগে ডিসচার্জ করে নিন।",
      visualIconType = "capacitor"
    ),
    ComponentDetail(
      id = "comp_diode",
      bengaliName = "ডায়োড (একমুখী ভালভ)",
      englishName = "Diode",
      symbol = "D (—|>|—)",
      shortFunction = "বিদ্যুৎ প্রবাহকে কেবলমাত্র একমুখী (অ্যানোড থেকে ক্যাথোড) পরিচালনা করা এবং বিপরীত প্রবাহ ঠেকানো।",
      howToIdentify = "কালো রঙের আয়তাকার প্যাকেজ। এক মাথায় স্পষ্ট সাদা বা ধূসর দাগ থাকে যা ক্যাথোড (মাইনাস বা ঋণাত্মক প্রান্ত) নির্দেশ করে।",
      multimeterTestingRule = "ডায়োড মোডে লাল প্রোব অ্যানোডে এবং কালো ক্যাথোডে রাখলে ২০০-৬০০mV ডায়োড ভ্যালু দেখাবে। উল্টো ধরলে ১ বা OL দেখাবে।",
      commonFaults = "শর্ট হয়ে দুই দিকেই বিপ সাউন্ড দেওয়া অথবা ওপেন হয়ে কোনো দিকেই রিডিং না দেওয়া।",
      safetyNotes = "সঠিক পোলারিটি দেখে বসাতে হবে, উল্টো বসালে শর্ট সার্কিট ঘটবে।",
      visualIconType = "diode"
    ),
    ComponentDetail(
      id = "comp_zener_diode",
      bengaliName = "জেনার ডায়োড (ভোল্টেজ নিয়ন্ত্রক)",
      englishName = "Zener Diode",
      symbol = "ZD (—|>/|—)",
      shortFunction = "সার্কিটে নির্দিষ্ট ভোল্টেজ স্থিতিশীল রাখা এবং অতিরিক্ত ভোল্টেজ গ্রাউন্ডে বাইপাস করে আইসি রক্ষা করা (Over Voltage Clamping)।",
      howToIdentify = "সাধারণ ডায়োডের মতোই কালো বা লালচে কাঁচের দেহ, ক্যাথোডের মাথায় দাগযুক্ত। প্রায়শই চার্জিং ও ডেটা লাইনে থাকে।",
      multimeterTestingRule = "ডায়োড মোডে সাধারণ ডায়োডের মতো ফরওয়ার্ডে ভ্যালু ও রিভার্সে ওপেন দেখাবে। সার্কিটে নির্দিষ্ট ব্রেকডাউন ভোল্টেজ ধরে রাখে।",
      commonFaults = "হাই ভোল্টেজ সার্জে স্থায়ীভাবে শর্ট হয়ে লাইন জিরো ভোল্ট করে ফেলা।",
      safetyNotes = "লাইন শর্ট থাকলে জেনার ডায়োড খুলে ডায়োড ভ্যালু পুনরায় চেক করুন।",
      visualIconType = "zener"
    ),
    ComponentDetail(
      id = "comp_transistor",
      bengaliName = "ট্রানজিস্টর (ইলেকট্রনিক সুইচ)",
      englishName = "Bipolar Junction Transistor (BJT)",
      symbol = "Q / T",
      shortFunction = "দুর্বল সিগন্যালকে শক্তিশালী (Amplification) করা অথবা ইলেকট্রনিক দ্রুতগতির সুইচ হিসেবে কাজ করা।",
      howToIdentify = "সাধারণত ৩টি পিনযুক্ত ছোট কালো কম্পোনেন্ট (SOT-23 প্যাকেজ)। বেইস (Base), কালেক্টর (Collector) এবং ইমিটার (Emitter)।",
      multimeterTestingRule = "ডায়োড মোডে বেইস পিনের সাথে কালেক্টর ও ইমিটারের ডায়োড ড্রপ পরীক্ষা করুন (সাধারণত ৬০০-৭৫০mV)।",
      commonFaults = "কালেক্টর টু ইমিটার শর্ট বা বেইস ওপেন হয়ে সুইচিং বন্ধ হওয়া।",
      safetyNotes = "পিনআউট (NPN নাকি PNP) নিশ্চিত না হয়ে প্রতিস্থাপন করবেন না।",
      visualIconType = "transistor"
    ),
    ComponentDetail(
      id = "comp_mosfet",
      bengaliName = "মসফেট (ফিল্ড ইফেক্ট ট্রানজিস্টর)",
      englishName = "MOSFET (Metal Oxide Semiconductor FET)",
      symbol = "Q / MOSFET",
      shortFunction = "পাওয়ার ও চার্জিং লাইনে অত্যন্ত কম অপচয় সহ উচ্চ কারেন্ট অন/অফ সুইচিং ও ব্যাটারি রিভার্স প্রোটেকশন।",
      howToIdentify = "৩ থেকে ৮ পিন বিশিষ্ট চ্যাপ্টা আইসি বা ট্রানজিস্টর সদৃশ উপাদান। গেট (Gate), ড্রেন (Drain) এবং সোর্স (Source) পিন থাকে।",
      multimeterTestingRule = "ডায়োড মোডে ড্রেন ও সোর্সের মধ্যে বডি ডায়োড রিডিং (৪০০-৬০০mV) দেখাবে। গেটে হালকা স্পর্শ করলে ড্রেন-সোর্স কন্ডাক্ট করে।",
      commonFaults = "ড্রেন ও সোর্স অভ্যন্তরীণভাবে শর্ট হয়ে যাওয়া বা গেট পাংচার হয়ে সবসময় অন থাকা।",
      safetyNotes = "স্থির বিদ্যুৎ (ESD) সংবেদনশীল, কাজ করার সময় গ্রাউন্ড স্ট্র্যাপ ব্যবহার করুন।",
      visualIconType = "mosfet"
    ),
    ComponentDetail(
      id = "comp_inductor",
      bengaliName = "ইন্ডাক্টর / কয়েল",
      englishName = "Inductor / Coil",
      symbol = "L (—UUUU—)",
      shortFunction = "চৌম্বক ক্ষেত্রে শক্তি সঞ্চয় করে ভোল্টেজ বুস্ট (বৃদ্ধি) বা বাক (হ্রাস) করা এবং ডিসি কারেন্ট নির্বিঘ্নে পাস করা।",
      howToIdentify = "মোটা গাঢ় ধূসর বা কালো চারকোনা প্যাকেজ। মাঝে মাঝে তামার তার পেঁচানো দেখা যায়। বোর্ডের পাওয়ার ও লাইট আইসির পাশে বড় কয়েল থাকে।",
      multimeterTestingRule = "বাজার মোডে দুই প্রান্তে ধরলে বিপ সাউন্ড দেবে এবং ০-৩ ওহম রিডিং আসবে (কারণ এটি অবিচ্ছিন্ন তামার তার)।",
      commonFaults = "ভেতরের তার পুড়ে ওপেন হওয়া (কোনো বিপ সাউন্ড না আসা) অথবা কোর ক্ষতিগ্রস্ত হয়ে ইন্ডাকট্যান্স কমে যাওয়া।",
      safetyNotes = "বাক ও বুস্ট কয়েলের হেনরি/ইউএইচ মান মিলিয়ে একই মানের কয়েল লাগাতে হয়।",
      visualIconType = "inductor"
    ),
    ComponentDetail(
      id = "comp_ic",
      bengaliName = "আইসি (ইন্টিগ্রেটেড সার্কিট)",
      englishName = "Integrated Circuit (IC)",
      symbol = "U / IC",
      shortFunction = "হাজার হাজার ক্ষুদ্র ট্রানজিস্টর, ডায়োড ও ক্যাপাসিটর একত্রিত করে তৈরি জটিল মাইক্রোচিপ (যেমন সিপিইউ, পিএমআইসি, অডিও আইসি)।",
      howToIdentify = "কালো চারকোনা বা আয়তাকার সিলিকন চিপ। নিচে শত শত সূক্ষ্ম সোল্ডার বল (BGA) থাকে। গায়ে পার্ট নম্বর খোদাই করা থাকে।",
      multimeterTestingRule = "আইসির চারপাশে থাকা বাইপাস ক্যাপাসিটর ও কয়েলে ভোল্টেজ ও ডায়োড ভ্যালু মেপে আইসি সচল কি না নিশ্চিত করা হয়।",
      commonFaults = "শর্ট সার্কিটে অতিরিক্ত গরম হওয়া, অভ্যন্তরীণ গেট পুড়ে যাওয়া বা ড্রপ ড্যামেজে বল ক্র্যাক হওয়া।",
      safetyNotes = "আইসি তোলার সময় তাপমাত্রা ৩৪০-৩৬০°C এর বেশি রাখবেন না এবং আইসির ডট (পিন ১ মার্ক) মনে রাখবেন।",
      visualIconType = "ic"
    ),
    ComponentDetail(
      id = "comp_fuse",
      bengaliName = "ফিউজ / জিরো ওহম রেজিস্টর",
      englishName = "SMD Fuse / 0Ω Jumper",
      symbol = "F (—[S]—) / 0",
      shortFunction = "সার্কিটে অতিরিক্ত কারেন্ট প্রবাহিত হলে নিজেকে পুড়িয়ে অন্যান্য সংবেদনশীল আইসি ও সার্কিট রক্ষা করা।",
      howToIdentify = "ছোট সাদা, সবুজ বা কালো আয়তাকার পার্ট। গায়ে ০ বা F বা কোনো লেটার মার্কিং থাকে। ব্যাটারি ও চার্জিং লাইনে বেশি দেখা যায়।",
      multimeterTestingRule = "বাজার মোডে ধরলে ০ ওহম এবং জোরালো বিপ সাউন্ড দেবে।",
      commonFaults = "পুড়ে ওপেন (OL) হয়ে যাওয়া—এতে সার্কিটে পুরোপুরি বিদ্যুৎ যাওয়া বন্ধ হয়ে যায়।",
      safetyNotes = "ফিউজ পুড়ে গেলে সরাসরি তার দিয়ে জোড়া দেওয়ার আগে লাইনে কোনো শর্ট আছে কি না অবশ্যই পরীক্ষা করুন।",
      visualIconType = "fuse"
    ),
    ComponentDetail(
      id = "comp_connector",
      bengaliName = "কানেক্টর (এফপিসি ও বিটিবি)",
      englishName = "FPC / BTB Connector",
      symbol = "J / CN",
      shortFunction = "মাদারবোর্ডের সাথে ডিসপ্লে, ক্যামেরা, সাব-বোর্ড ও ব্যাটারির ফ্লেক্স কেবল নির্ভরযোগ্যভাবে সংযুক্ত করা।",
      howToIdentify = "ক্ষুদ্র প্লাস্টিক সকেট যার ভেতরে শত শত অতি সূক্ষ্ম গোল্ডেন মেটাল পিন সারিবদ্ধভাবে সাজানো থাকে।",
      multimeterTestingRule = "প্রতিটি পিনের ডায়োড মান মাদারবোর্ড গ্রাউন্ডের সাপেক্ষে পরীক্ষা করে কোনো পিন ওপেন বা কাটা কি না নিশ্চিত করা যায়।",
      commonFaults = "পিন বাঁকা হওয়া, ময়লা জমে কার্বন তৈরি বা বেশি চাপে প্লাস্টিক ভেঙে পিন ছুটে যাওয়া।",
      safetyNotes = "কানেক্টর লাগানোর সময় কখনোই অতিরিক্ত জোর করবেন না, আঙুলের আলতো চাপে 'ক্লিক' শব্দে বসবে।",
      visualIconType = "connector"
    ),
    ComponentDetail(
      id = "comp_battery",
      bengaliName = "লিথিয়াম ব্যাটারি (Li-ion / Li-Po)",
      englishName = "Lithium-Ion / Polymer Battery",
      symbol = "BATT (—|+ -|—)",
      shortFunction = "রাসায়নিক শক্তিকে বৈদ্যুতিক শক্তিতে রূপান্তর করে মোবাইলকে নিরবচ্ছিন্ন বিদ্যুৎ সরবরাহ করা।",
      howToIdentify = "ফোন বডির সবচেয়ে বড় চ্যাপ্টা উপাদান। গায়ে ক্ষমতা (যেমন 5000 mAh), ভোল্টেজ (3.85V / 4.4V Max) লেখা থাকে।",
      multimeterTestingRule = "ডিসি ২০V মোডে পজিটিভ ও নেগেটিভে ৩.৭V থেকে ৪.২V ভোল্টেজ প্রদর্শন করবে।",
      commonFaults = "ব্যাটারি ফুলে যাওয়া (Swelling), অভ্যন্তরীণ ব্যাকড্রপ রেজিস্ট্যান্স বেড়ে ফোন অন হতেই বন্ধ হওয়া, ০ ভোল্ট হয়ে যাওয়া।",
      safetyNotes = "কখনোই ফুটো করবেন না, বাঁকা করবেন না বা আগুন/উচ্চ তাপে রাখবেন না—তাত্ক্ষণিক বিস্ফোরণ ও বিষাক্ত ধোঁয়া ছড়াতে পারে!",
      visualIconType = "battery"
    )
  )

  val resistorColors = listOf(
    ResistorBandColor("কালো (Black)", "Black", 0xFF000000, 0, 1.0, null),
    ResistorBandColor("বাদামি (Brown)", "Brown", 0xFF8B4513, 1, 10.0, 1.0),
    ResistorBandColor("লাল (Red)", "Red", 0xFFFF0000, 2, 100.0, 2.0),
    ResistorBandColor("কমলা (Orange)", "Orange", 0xFFFFA500, 3, 1000.0, null),
    ResistorBandColor("হলুদ (Yellow)", "Yellow", 0xFFFFD700, 4, 10000.0, null),
    ResistorBandColor("সবুজ (Green)", "Green", 0xFF008000, 5, 100000.0, 0.5),
    ResistorBandColor("নীল (Blue)", "Blue", 0xFF0000FF, 6, 1000000.0, 0.25),
    ResistorBandColor("বেগুনি (Violet)", "Violet", 0xFFEE82EE, 7, 10000000.0, 0.1),
    ResistorBandColor("ধূসর (Gray)", "Gray", 0xFF808080, 8, 100000000.0, 0.05),
    ResistorBandColor("সাদা (White)", "White", 0xFFFFFFFF, 9, 1000000000.0, null),
    ResistorBandColor("সোনালি (Gold)", "Gold", 0xFFFFD700, null, 0.1, 5.0),
    ResistorBandColor("রুপালি (Silver)", "Silver", 0xFFC0C0C0, null, 0.01, 10.0)
  )
}
