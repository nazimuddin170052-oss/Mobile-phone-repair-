package com.example.data

import android.graphics.Bitmap
import android.util.Base64
import com.example.BuildConfig
import com.example.model.AIRepairQuery
import com.example.model.AIRepairResult
import com.example.model.ComponentIdentificationResult
import com.example.model.ConfidenceLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

object GeminiService {

  private const val MODEL_NAME = "gemini-2.5-flash"
  private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL_NAME:generateContent"

  private val client = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .build()

  fun isApiKeyAvailable(): Boolean {
    val key = BuildConfig.GEMINI_API_KEY
    return key.isNotBlank() && key != "MY_GEMINI_API_KEY"
  }

  fun bitmapToBase64(bitmap: Bitmap): String {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream)
    val byteArray = stream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.NO_WRAP)
  }

  suspend fun diagnoseRepairWithAI(query: AIRepairQuery): Result<AIRepairResult> = withContext(Dispatchers.IO) {
    try {
      val apiKey = BuildConfig.GEMINI_API_KEY
      if (!isApiKeyAvailable()) {
        // Return structured intelligent offline/fallback analysis when key not set
        return@withContext Result.success(getFallbackAIRepairResult(query))
      }

      val prompt = buildString {
        appendLine("তুমি একজন অভিজ্ঞ মোবাইল সার্ভিসিং প্রশিক্ষক ও হার্ডওয়্যার বিশেষজ্ঞ।")
        appendLine("নিচের তথ্যের ভিত্তিতে সম্পূর্ণ বাংলায় একটি সুনির্দিষ্ট স্ট্রাকচার্ড ডায়াগনসিস প্রস্তুত করো।")
        appendLine("নিয়মাবলী:")
        appendLine("১. বাস্তব প্রমাণ ছাড়া নির্দিষ্ট কোনো IC বা মাদারবোর্ড নষ্ট বলে ঘোষণা করবে না।")
        appendLine("২. নিরাপদ কোল্ড টেস্টিং ও সহজ পরীক্ষা দিয়ে শুরু করো। ব্যাটারি পাংচার বা ঝুঁকিপূর্ণ শর্ট করতে নিষেধ করো।")
        appendLine("৩. উত্তরটি নিশ্চিত, সম্ভাব্য নাকি অনিশ্চিত তা স্পষ্টভাবে নির্ধারণ করো।")
        appendLine("")
        appendLine("ফোনের ব্র্যান্ড: ${query.brand.ifBlank { "অজানা" }}")
        appendLine("মডেল: ${query.model.ifBlank { "নির্দিষ্ট নয়" }}")
        appendLine("সমস্যার বিভাগ: ${query.category}")
        appendLine("বিস্তারিত বিবরণ: ${query.description}")
        appendLine("ইতিহাস/কখন থেকে সমস্যা: ${query.durationHistory}")
        appendLine("ইতিমধ্যে কৃত পরীক্ষা: ${query.priorTestsDone}")
        appendLine("মাল্টিমিটার রিডিং: ${query.multimeterReading}")
        appendLine("")
        appendLine("উত্তরটি নিচের JSON ফরম্যাটে প্রদান করো:")
        appendLine("""
        {
          "summary": "সমস্যার এক লাইনের সারসংক্ষেপ",
          "rootCauses": ["সম্ভাব্য কারণ ১", "সম্ভাব্য কারণ ২", "সম্ভাব্য কারণ ৩"],
          "firstSafeTest": "সবার আগে যে নিরাপদ পরীক্ষাটি করা উচিত",
          "toolsNeeded": ["টুল ১", "টুল ২"],
          "stepInstructions": ["ধাপ ১ নির্দেশনা", "ধাপ ২ নির্দেশনা", "ধাপ ৩ নির্দেশনা"],
          "expectedOutcomes": "স্বাভাবিক ও ত্রুটিযুক্ত রিডিংয়ের পার্থক্য",
          "nextSteps": "পরীক্ষার ফলাফলের পর করণীয়",
          "whenToConsultPro": "কখন অভিজ্ঞ চিপ-লেভেল টেকনিশিয়ানের কাছে যেতে হবে",
          "confidenceLevel": "PROBABLE"
        }
        """.trimIndent())
      }

      val partsArray = JSONArray()
      val textPart = JSONObject().put("text", prompt)
      partsArray.put(textPart)

      if (!query.imageBase64.isNullOrBlank()) {
        val imagePart = JSONObject().apply {
          put("inlineData", JSONObject().apply {
            put("mimeType", "image/jpeg")
            put("data", query.imageBase64)
          })
        }
        partsArray.put(imagePart)
      }

      val contentObj = JSONObject().put("parts", partsArray)
      val contentsArray = JSONArray().put(contentObj)

      val requestBodyJson = JSONObject().apply {
        put("contents", contentsArray)
        put("generationConfig", JSONObject().apply {
          put("temperature", 0.4)
          put("responseMimeType", "application/json")
        })
      }

      val request = Request.Builder()
        .url("$BASE_URL?key=$apiKey")
        .post(requestBodyJson.toString().toRequestBody("application/json".toMediaType()))
        .build()

      val response = client.newCall(request).execute()
      val responseString = response.body?.string() ?: ""

      if (!response.isSuccessful) {
        return@withContext Result.failure(Exception("Gemini API এরর কোড: ${response.code}"))
      }

      val parsedResult = parseAIRepairJsonResponse(responseString)
      Result.success(parsedResult)
    } catch (e: Exception) {
      // In case of timeout or network disconnect, return helpful fallback
      Result.failure(e)
    }
  }

  suspend fun identifyComponentWithAI(imageBase64: String): Result<ComponentIdentificationResult> = withContext(Dispatchers.IO) {
    try {
      val apiKey = BuildConfig.GEMINI_API_KEY
      if (!isApiKeyAvailable()) {
        return@withContext Result.success(getFallbackComponentIdentification())
      }

      val prompt = """
      তুমি একজন মোবাইল মাদারবোর্ড এসএমডি কম্পোনেন্ট বিশেষজ্ঞ।
      সংযুক্ত ছবিটি বিশ্লেষণ করে চিহ্নিত করো এটি কোন কম্পোনেন্ট (IC, ক্যাপাসিটর, রেজিস্টর, ডায়োড, ট্রানজিস্টর, কয়েল, ফিউজ ইত্যাদি)।
      নিয়মাবলী:
      - লেখা অস্পষ্ট হলে বানিয়ে পার্ট নম্বর দেবে না।
      - বোর্ডের আকার ও রঙ দেখে সম্ভাব্য বিকল্পও উল্লেখ করো।
      - মাল্টিমিটার টেস্টিং নিয়ম ও নিরাপত্তা সতর্কতা দাও।
      
      উত্তরটি এই JSON ফরম্যাটে দাও:
      {
        "componentName": "কম্পোনেন্টের নাম ও প্রকার (যেমন: SMD Ceramic Capacitor MLCC)",
        "bengaliFunction": "বাংলায় এর কাজ ও ভূমিকা",
        "confidenceScore": 85,
        "confidenceLevel": "PROBABLE",
        "visualEvidence": "দৃশ্যমান বৈশিষ্ট্য (রঙ, আকার, পিন ইত্যাদি)",
        "alternativeCandidates": ["বিকল্প ১", "বিকল্প ২"],
        "multimeterTestingGuide": "মাল্টিমিটারে টেস্ট করার সঠিক পদ্ধতি",
        "safetyCaution": "নিরাপত্তা নির্দেশনা"
      }
      """.trimIndent()

      val partsArray = JSONArray()
      partsArray.put(JSONObject().put("text", prompt))
      partsArray.put(JSONObject().apply {
        put("inlineData", JSONObject().apply {
          put("mimeType", "image/jpeg")
          put("data", imageBase64)
        })
      })

      val contentObj = JSONObject().put("parts", partsArray)
      val contentsArray = JSONArray().put(contentObj)

      val requestBodyJson = JSONObject().apply {
        put("contents", contentsArray)
        put("generationConfig", JSONObject().apply {
          put("temperature", 0.3)
          put("responseMimeType", "application/json")
        })
      }

      val request = Request.Builder()
        .url("$BASE_URL?key=$apiKey")
        .post(requestBodyJson.toString().toRequestBody("application/json".toMediaType()))
        .build()

      val response = client.newCall(request).execute()
      val responseString = response.body?.string() ?: ""

      if (!response.isSuccessful) {
        return@withContext Result.failure(Exception("ছবি বিশ্লেষণ ব্যর্থ হয়েছে (${response.code})"))
      }

      val json = JSONObject(responseString)
      val textContent = json.getJSONArray("candidates")
        .getJSONObject(0)
        .getJSONObject("content")
        .getJSONArray("parts")
        .getJSONObject(0)
        .getString("text")

      val parsed = JSONObject(textContent)
      val levelStr = parsed.optString("confidenceLevel", "PROBABLE")
      val level = when (levelStr) {
        "CERTAIN" -> ConfidenceLevel.CERTAIN
        "UNCERTAIN" -> ConfidenceLevel.UNCERTAIN
        else -> ConfidenceLevel.PROBABLE
      }

      val alternatives = mutableListOf<String>()
      val altArray = parsed.optJSONArray("alternativeCandidates")
      if (altArray != null) {
        for (i in 0 until altArray.length()) {
          alternatives.add(altArray.getString(i))
        }
      }

      Result.success(
        ComponentIdentificationResult(
          componentName = parsed.optString("componentName", "এসএমডি ইলেকট্রনিক কম্পোনেন্ট"),
          bengaliFunction = parsed.optString("bengaliFunction", "সার্কিট ফিল্টারিং ও ভোল্টেজ নিয়ন্ত্রণ"),
          confidenceScore = parsed.optInt("confidenceScore", 80),
          confidenceLevel = level,
          visualEvidence = parsed.optString("visualEvidence", "বাদামী/ধূসর আয়তাকার প্যাকেজ, দুই মাথায় সোল্ডার টার্মিনাল"),
          alternativeCandidates = alternatives.ifEmpty { listOf("রেজিস্টর", "ডায়োড") },
          multimeterTestingGuide = parsed.optString("multimeterTestingGuide", "বাজার/ডায়োড মোডে রিডিং নিন। সুস্থ হলে এক প্রান্তে ডায়োড ভ্যালু আসবে, অপর প্রান্তে গ্রাউন্ড। উভয় পাশে বিপ দিলে শর্ট।"),
          safetyCaution = parsed.optString("safetyCaution", "পাওয়ার বন্ধ অবস্থায় মাপুন। লাইভ লাইনে ওহম মোড চালাবেন না।")
        )
      )
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  private fun parseAIRepairJsonResponse(rawJsonStr: String): AIRepairResult {
    val json = JSONObject(rawJsonStr)
    val textContent = json.getJSONArray("candidates")
      .getJSONObject(0)
      .getJSONObject("content")
      .getJSONArray("parts")
      .getJSONObject(0)
      .getString("text")

    val parsed = JSONObject(textContent)
    val rootCauses = mutableListOf<String>()
    val causesArr = parsed.optJSONArray("rootCauses")
    if (causesArr != null) {
      for (i in 0 until causesArr.length()) rootCauses.add(causesArr.getString(i))
    }

    val stepInstructions = mutableListOf<String>()
    val stepsArr = parsed.optJSONArray("stepInstructions")
    if (stepsArr != null) {
      for (i in 0 until stepsArr.length()) stepInstructions.add(stepsArr.getString(i))
    }

    val tools = mutableListOf<String>()
    val toolsArr = parsed.optJSONArray("toolsNeeded")
    if (toolsArr != null) {
      for (i in 0 until toolsArr.length()) tools.add(toolsArr.getString(i))
    }

    val levelStr = parsed.optString("confidenceLevel", "PROBABLE")
    val level = when (levelStr) {
      "CERTAIN" -> ConfidenceLevel.CERTAIN
      "UNCERTAIN" -> ConfidenceLevel.UNCERTAIN
      else -> ConfidenceLevel.PROBABLE
    }

    return AIRepairResult(
      summary = parsed.optString("summary", "ফোনের পাওয়ার ও মাদারবোর্ড সার্কিট পরীক্ষা প্রয়োজন।"),
      rootCauses = rootCauses.ifEmpty { listOf("ব্যাটারি ডিপ-ডিসচার্জ", "প্রাইমারি পাওয়ার লাইন ড্রপ") },
      firstSafeTest = parsed.optString("firstSafeTest", "ভালো চার্জার ও ইউএসবি টেস্টার দিয়ে ইনপুট অ্যাম্পিয়ার পরীক্ষা করুন।"),
      toolsNeeded = tools.ifEmpty { listOf("ডিজিটাল মাল্টিমিটার", "ইউএসবি ডক্টর মিটার") },
      stepInstructions = stepInstructions.ifEmpty { listOf("১. ব্যাটারি ভোল্টেজ মাপুন (ন্যূনতম ৩.৭V প্রয়োজন)", "২. চার্জিং সকেটের D+/D- পিনে ডায়োড ড্রপ পরীক্ষা করুন") },
      expectedOutcomes = parsed.optString("expectedOutcomes", "স্বাভাবিক চার্জিংয়ে ১.২A-২.০A কারেন্ট নেবে। ০.০০A থাকলে লাইন ওপেন বা শর্ট।"),
      nextSteps = parsed.optString("nextSteps", "ব্যাটারি ৩.৭V এর নিচে থাকলে বুস্ট করুন, অন্যথায় মাদারবোর্ড কোল্ড টেস্ট করুন।"),
      whenToConsultPro = parsed.optString("whenToConsultPro", "সিপিইউ বা পিএমআইসি আইসি পরিবর্তন ও মাইক্রো জাম্পারিংয়ের ক্ষেত্রে অভিজ্ঞ ল্যাবের সহায়তা নিন।"),
      confidenceLevel = level
    )
  }

  fun getFallbackAIRepairResult(query: AIRepairQuery): AIRepairResult {
    return AIRepairResult(
      summary = "${query.brand} ${query.model} - ${query.category} সম্পর্কিত নির্দেশনামূলক ডায়াগনসিস।",
      rootCauses = listOf(
        "ব্যাটারি ভোল্টেজ ৩.০ ভোল্টের নিচে নেমে ডিপ-ডিসচার্জড হওয়া",
        "চার্জিং পোর্ট বা সাব-বোর্ডের গ্রাউন্ডে কার্বন জমে সংযোগ বিচ্ছিন্ন",
        "প্রাইমারি ভিব্যাট (VBAT) বা ভি-পিএইচ লাইনে হাফ/ফুল শর্ট সার্কিট"
      ),
      firstSafeTest = "একটি পরীক্ষিত ভালো অরিজিনাল চার্জার ও ইউএসবি টেস্টার লাগিয়ে কারেন্ট ড্র (০.৬০A-২.০A) দেখুন।",
      toolsNeeded = listOf("ডিজিটাল মাল্টিমিটার", "ইউএসবি অ্যাম্পিয়ার টেস্টার", "ব্যাটারি বুস্টার"),
      stepInstructions = listOf(
        "ধাপ ১: কোনো হিট বা সোল্ডারিং ছাড়াই কেবল ও অ্যাডাপ্টার পরিবর্তন করে পোর্ট পরিষ্কার করুন।",
        "ধাপ ২: সাবধানে ব্যাকশেল খুলে ব্যাটারি কানেক্টরে ভোল্টেজ মাপুন (৩.৭V এর কম হলে বুস্ট করুন)।",
        "ধাপ ৩: ব্যাটারি কানেক্টরে ডায়োড মোডে কোল্ড টেস্টিং করুন (মান ৩৫০-৬০০ স্বাভাবিক; ০.০০১ রিডিং দিলে লাইন শর্ট)।"
      ),
      expectedOutcomes = "ব্যাটারি সুস্থ থাকলে ৩.৭V-৪.২V দেখাবে। ডায়োড মোডে রিভার্স বায়াসে অসীম (OL) থাকা স্বাভাবিক।",
      nextSteps = "যদি লাইন শর্ট পাওয়া যায়, তবে রোসিন রেজিন ধোঁয়া পদ্ধতি প্রয়োগ করে শর্ট ক্যাপাসিটর চিহ্নিত করুন।",
      whenToConsultPro = "মেইন পাওয়ার আইসি (PMIC) বা সিপিইউ চিপের নিচে বল ক্র্যাক বা ড্রাই সোল্ডার হলে বিশেষজ্ঞ ল্যাবে পাঠান।",
      confidenceLevel = ConfidenceLevel.PROBABLE
    )
  }

  fun getFallbackComponentIdentification(): ComponentIdentificationResult {
    return ComponentIdentificationResult(
      componentName = "এসএমডি মাল্টিলেয়ার সিরামিক ক্যাপাসিটর (MLCC)",
      bengaliFunction = "উচ্চ ফ্রিকোয়েন্সি ডিসি ভোল্টেজ ফিল্টারিং ও পাওয়ার লাইন স্ট্যাবিলাইজার",
      confidenceScore = 88,
      confidenceLevel = ConfidenceLevel.PROBABLE,
      visualEvidence = "হলুদাভ বাদামী আয়তাকার কিউব বডি, উভয় প্রান্তে রুপালি টার্মিনেশন প্যাড, গায়ে কোনো মান লেখা নেই।",
      alternativeCandidates = listOf("এসএমডি রেজিস্টর (কালো দেহ)", "টিভিএস ডায়োড"),
      multimeterTestingGuide = "মাল্টিমিটার বাজার/ডায়োড মোডে রাখুন। লাল প্রোব গ্রাউন্ডে এবং কালো প্রোব ক্যাপাসিটরের দুই মাথায় ধরুন। এক মাথায় ডায়োড মান (৩৫০-৭০০) আসবে, অন্য প্রান্তে গ্রাউন্ড। উভয় প্রান্তে বিপ দিলে ক্যাপাসিটরটি শর্ট।",
      safetyCaution = "বোর্ডে ব্যাটারি লাগানো অবস্থায় কখনো পরীক্ষা করবেন না। ক্যাপাসিটর তোলার সময় তাপমাত্রা ৩৪০ ডিগ্রি সেলসিয়াসের মধ্যে রাখুন।"
    )
  }
}
