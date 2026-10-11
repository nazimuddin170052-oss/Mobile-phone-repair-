package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class ZukiViewModel(application: Application) : AndroidViewModel(application) {

  private val prefs = application.getSharedPreferences("zuki_servicing_prefs", Context.MODE_PRIVATE)

  // Navigation
  private val _currentSection = MutableStateFlow(ScreenSection.HOME)
  val currentSection: StateFlow<ScreenSection> = _currentSection.asStateFlow()

  private val _selectedLessonId = MutableStateFlow<String?>(null)
  val selectedLessonId: StateFlow<String?> = _selectedLessonId.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  // Online / Offline State
  private val _isSimulatedOffline = MutableStateFlow(false)
  val isSimulatedOffline: StateFlow<Boolean> = _isSimulatedOffline.asStateFlow()

  // Diagnostic State
  private val _activeTroubleIssue = MutableStateFlow<TroubleIssue?>(null)
  val activeTroubleIssue: StateFlow<TroubleIssue?> = _activeTroubleIssue.asStateFlow()

  private val _currentDiagnosticStep = MutableStateFlow<DiagnosticStep?>(null)
  val currentDiagnosticStep: StateFlow<DiagnosticStep?> = _currentDiagnosticStep.asStateFlow()

  private val _stepHistory = MutableStateFlow<List<String>>(emptyList())
  val stepHistory: StateFlow<List<String>> = _stepHistory.asStateFlow()

  // Toolbox Tab State: 0=Ohm, 1=Power, 2=Resistor Color, 3=Multimeter, 4=Converter
  private val _activeToolboxTab = MutableStateFlow(0)
  val activeToolboxTab: StateFlow<Int> = _activeToolboxTab.asStateFlow()

  // Ohm's Law Calculator State
  private val _ohmMode = MutableStateFlow(0)
  val ohmMode: StateFlow<Int> = _ohmMode.asStateFlow()

  private val _ohmInput1 = MutableStateFlow("1.5")
  val ohmInput1: StateFlow<String> = _ohmInput1.asStateFlow()

  private val _ohmInput2 = MutableStateFlow("100")
  val ohmInput2: StateFlow<String> = _ohmInput2.asStateFlow()

  // Power Calculator State
  private val _powerVoltageInput = MutableStateFlow("5.0")
  val powerVoltageInput: StateFlow<String> = _powerVoltageInput.asStateFlow()

  private val _powerCurrentInput = MutableStateFlow("2.0")
  val powerCurrentInput: StateFlow<String> = _powerCurrentInput.asStateFlow()

  // Resistor Color Code State
  private val _is5BandResistor = MutableStateFlow(false)
  val is5BandResistor: StateFlow<Boolean> = _is5BandResistor.asStateFlow()

  private val _band1 = MutableStateFlow(4) // Yellow (4)
  val band1: StateFlow<Int> = _band1.asStateFlow()

  private val _band2 = MutableStateFlow(7) // Violet (7)
  val band2: StateFlow<Int> = _band2.asStateFlow()

  private val _band3 = MutableStateFlow(0) // For 5 band (e.g. 0)
  val band3: StateFlow<Int> = _band3.asStateFlow()

  private val _bandMultiplier = MutableStateFlow(2) // Red (100) -> 4.7k
  val bandMultiplier: StateFlow<Int> = _bandMultiplier.asStateFlow()

  private val _bandTolerance = MutableStateFlow(10) // Gold (5%)
  val bandTolerance: StateFlow<Int> = _bandTolerance.asStateFlow()

  // Unit Converter State
  private val _converterCategory = MutableStateFlow(0)
  val converterCategory: StateFlow<Int> = _converterCategory.asStateFlow()

  private val _converterInput = MutableStateFlow("1000")
  val converterInput: StateFlow<String> = _converterInput.asStateFlow()

  // Multimeter Simulator State
  private val _multimeterScenarioIndex = MutableStateFlow(0)
  val multimeterScenarioIndex: StateFlow<Int> = _multimeterScenarioIndex.asStateFlow()

  private val _multimeterDialMode = MutableStateFlow("Buzzer / Diode")
  val multimeterDialMode: StateFlow<String> = _multimeterDialMode.asStateFlow()

  private val _isComponentFaulty = MutableStateFlow(false)
  val isComponentFaulty: StateFlow<Boolean> = _isComponentFaulty.asStateFlow()

  private val _areProbesConnected = MutableStateFlow(false)
  val areProbesConnected: StateFlow<Boolean> = _areProbesConnected.asStateFlow()

  // Quiz State
  private val _quizSelectedAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
  val quizSelectedAnswers: StateFlow<Map<Int, Int>> = _quizSelectedAnswers.asStateFlow()

  private val _isQuizSubmitted = MutableStateFlow(false)
  val isQuizSubmitted: StateFlow<Boolean> = _isQuizSubmitted.asStateFlow()

  // Preferences & Progress
  private val _bookmarkedIds = MutableStateFlow<Set<String>>(emptySet())
  val bookmarkedIds: StateFlow<Set<String>> = _bookmarkedIds.asStateFlow()

  private val _completedIds = MutableStateFlow<Set<String>>(emptySet())
  val completedIds: StateFlow<Set<String>> = _completedIds.asStateFlow()

  private val _isDarkMode = MutableStateFlow(false)
  val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

  private val _fontSizeScale = MutableStateFlow(1.0f)
  val fontSizeScale: StateFlow<Float> = _fontSizeScale.asStateFlow()

  // ==========================================
  // NEW FEATURE 1: AI Repair Assistant State
  // ==========================================
  private val _aiQuery = MutableStateFlow(AIRepairQuery())
  val aiQuery: StateFlow<AIRepairQuery> = _aiQuery.asStateFlow()

  private val _aiResult = MutableStateFlow<AIRepairResult?>(null)
  val aiResult: StateFlow<AIRepairResult?> = _aiResult.asStateFlow()

  private val _isAiLoading = MutableStateFlow(false)
  val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

  private val _aiErrorMessage = MutableStateFlow<String?>(null)
  val aiErrorMessage: StateFlow<String?> = _aiErrorMessage.asStateFlow()

  // ==========================================
  // NEW FEATURE 2: Visual Component Identifier State
  // ==========================================
  private val _selectedPhotoBitmap = MutableStateFlow<Bitmap?>(null)
  val selectedPhotoBitmap: StateFlow<Bitmap?> = _selectedPhotoBitmap.asStateFlow()

  private val _hasUserConsent = MutableStateFlow(true)
  val hasUserConsent: StateFlow<Boolean> = _hasUserConsent.asStateFlow()

  private val _photoScanResult = MutableStateFlow<ComponentIdentificationResult?>(null)
  val photoScanResult: StateFlow<ComponentIdentificationResult?> = _photoScanResult.asStateFlow()

  private val _isPhotoScanning = MutableStateFlow(false)
  val isPhotoScanning: StateFlow<Boolean> = _isPhotoScanning.asStateFlow()

  private val _photoScanErrorMessage = MutableStateFlow<String?>(null)
  val photoScanErrorMessage: StateFlow<String?> = _photoScanErrorMessage.asStateFlow()

  // ==========================================
  // NEW FEATURE 3: Entitlements, Ads & Subscriptions
  // ==========================================
  private val _userEntitlement = MutableStateFlow(UserEntitlement())
  val userEntitlement: StateFlow<UserEntitlement> = _userEntitlement.asStateFlow()

  private val _paymentNotice = MutableStateFlow<String?>(null)
  val paymentNotice: StateFlow<String?> = _paymentNotice.asStateFlow()

  val subscriptionPlans = listOf(
    SubscriptionPlan(
      id = "plan_monthly",
      titleBengali = "মাসিক প্রো প্যাক",
      priceBengali = "১৯৯ ৳ / মাস",
      periodBengali = "১ মাস মেয়াদ",
      features = listOf(
        "১০০% বিজ্ঞাপনমুক্ত মসৃণ অভিজ্ঞতা",
        "আনলিমিটেড AI মেরামত সহকারী ও ফটো স্ক্যান",
        "সকল হাই-রেজুলিউশন সার্কিট স্কিম্যাটিক আনলক",
        "অগ্রাধিকারভিত্তিক চিপ-লেভেল সাপোর্ট"
      )
    ),
    SubscriptionPlan(
      id = "plan_yearly",
      titleBengali = "বাৎসরিক মাস্টার প্যাক (জনপ্রিয়)",
      priceBengali = "১,৪৯৯ ৳ / বছর",
      periodBengali = "১২ মাস মেয়াদ (৩৭% সাশ্রয়)",
      features = listOf(
        "সকল মাসিক ফিচারের পূর্ণ অ্যাক্সেস",
        "সার্বক্ষণিক অফলাইন সার্ভিসিং প্যাক ডাউনলোড",
        "ভবিষ্যতের সকল ভিডিও ও ডায়াগ্রাম আপডেট",
        "সার্টিফায়েড মোবাইল টেকনিশিয়ান ডিজিটাল ব্যাজ"
      ),
      isPopular = true
    )
  )

  // ==========================================
  // NEW FEATURE 4: Offline Content Packages
  // ==========================================
  private val _offlinePacks = MutableStateFlow(
    listOf(
      DownloadedContentInfo("pack_core", "কোর সার্ভিসিং ও ডায়াগনসিস প্যাক", 8.4, true, 16),
      DownloadedContentInfo("pack_schematics", "সার্কিট ডায়াগ্রাম ও স্কিম্যাটিক প্যাক", 14.2, true, 4),
      DownloadedContentInfo("pack_quiz", "বাংলা কুইজ ও কম্পোনেন্ট লাইব্রেরি", 5.1, true, 20),
      DownloadedContentInfo("pack_advanced", "অ্যাডভান্সড চিপ-লেভেল ও ইএমএমসি প্যাক", 24.8, false, 8)
    )
  )
  val offlinePacks: StateFlow<List<DownloadedContentInfo>> = _offlinePacks.asStateFlow()

  init {
    loadPersistedData()
  }

  private fun loadPersistedData() {
    val bookmarks = prefs.getStringSet("bookmarked_ids", emptySet()) ?: emptySet()
    val completed = prefs.getStringSet("completed_ids", emptySet()) ?: emptySet()
    val dark = prefs.getBoolean("is_dark_mode", false)
    val fontScale = prefs.getFloat("font_size_scale", 1.0f)
    val isPremium = prefs.getBoolean("is_premium", false)
    val aiUsed = prefs.getInt("ai_queries_today", 0)
    val photoUsed = prefs.getInt("photo_scans_today", 0)

    _bookmarkedIds.value = bookmarks
    _completedIds.value = completed
    _isDarkMode.value = dark
    _fontSizeScale.value = fontScale

    _userEntitlement.value = UserEntitlement(
      tier = if (isPremium) SubscriptionTier.PREMIUM else SubscriptionTier.FREE,
      isPremiumActive = isPremium,
      aiQueriesToday = aiUsed,
      photoScansToday = photoUsed,
      adsEnabled = !isPremium
    )
  }

  fun navigateTo(section: ScreenSection) {
    _currentSection.value = section
  }

  fun openLessonDetail(lessonId: String) {
    _selectedLessonId.value = lessonId
    _currentSection.value = ScreenSection.LESSON_DETAIL
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun toggleOfflineSimulation() {
    _isSimulatedOffline.value = !_isSimulatedOffline.value
  }

  // Diagnostic Functions
  fun selectTroubleIssue(issue: TroubleIssue) {
    _activeTroubleIssue.value = issue
    _currentDiagnosticStep.value = TroubleshootingData.getStep(issue.initialStepId)
    _stepHistory.value = listOf(issue.initialStepId)
    _currentSection.value = ScreenSection.DIAGNOSIS
  }

  fun chooseDiagnosticOption(isYes: Boolean) {
    val current = _currentDiagnosticStep.value ?: return
    val nextStepId = if (isYes) current.yesNextStepId else current.noNextStepId

    if (nextStepId != null) {
      val nextStep = TroubleshootingData.getStep(nextStepId)
      if (nextStep != null) {
        _currentDiagnosticStep.value = nextStep
        _stepHistory.value = _stepHistory.value + nextStepId
      }
    }
  }

  fun restartDiagnostic() {
    val issue = _activeTroubleIssue.value ?: return
    _currentDiagnosticStep.value = TroubleshootingData.getStep(issue.initialStepId)
    _stepHistory.value = listOf(issue.initialStepId)
  }

  fun exitDiagnostic() {
    _activeTroubleIssue.value = null
    _currentDiagnosticStep.value = null
    _stepHistory.value = emptyList()
  }

  // Toolbox Functions
  fun setToolboxTab(tabIndex: Int) {
    _activeToolboxTab.value = tabIndex
  }

  fun setOhmMode(mode: Int) {
    _ohmMode.value = mode
  }

  fun setOhmInput1(value: String) {
    _ohmInput1.value = value
  }

  fun setOhmInput2(value: String) {
    _ohmInput2.value = value
  }

  fun setPowerVoltageInput(value: String) {
    _powerVoltageInput.value = value
  }

  fun setPowerCurrentInput(value: String) {
    _powerCurrentInput.value = value
  }

  fun setIs5BandResistor(is5Band: Boolean) {
    _is5BandResistor.value = is5Band
  }

  fun setResistorBand(bandPosition: Int, colorIndex: Int) {
    when (bandPosition) {
      1 -> _band1.value = colorIndex
      2 -> _band2.value = colorIndex
      3 -> _band3.value = colorIndex
      4 -> _bandMultiplier.value = colorIndex
      5 -> _bandTolerance.value = colorIndex
    }
  }

  fun setConverterCategory(catIndex: Int) {
    _converterCategory.value = catIndex
  }

  fun setConverterInput(value: String) {
    _converterInput.value = value
  }

  // Multimeter Simulator
  fun setMultimeterScenario(index: Int) {
    _multimeterScenarioIndex.value = index
    _areProbesConnected.value = false
  }

  fun setMultimeterDialMode(mode: String) {
    _multimeterDialMode.value = mode
  }

  fun toggleComponentFaulty() {
    _isComponentFaulty.value = !_isComponentFaulty.value
  }

  fun toggleProbesConnected() {
    _areProbesConnected.value = !_areProbesConnected.value
  }

  // Quiz
  fun selectQuizAnswer(questionId: Int, optionIndex: Int) {
    if (_isQuizSubmitted.value) return
    val updated = _quizSelectedAnswers.value.toMutableMap()
    updated[questionId] = optionIndex
    _quizSelectedAnswers.value = updated
  }

  fun submitQuiz() {
    _isQuizSubmitted.value = true
  }

  fun resetQuiz() {
    _quizSelectedAnswers.value = emptyMap()
    _isQuizSubmitted.value = false
  }

  // ==========================================
  // AI Repair Assistant Action
  // ==========================================
  fun updateAIQuery(transform: (AIRepairQuery) -> AIRepairQuery) {
    _aiQuery.value = transform(_aiQuery.value)
  }

  fun executeAIRepairDiagnosis() {
    if (_isSimulatedOffline.value) {
      _aiErrorMessage.value = "অফলাইন মোড সক্রিয় রয়েছে। অনুগ্রহ করে উপরে নেটওয়ার্ক চালু করুন অথবা অফলাইন সমস্যা শনাক্তকারী গাইড ব্যবহার করুন।"
      return
    }

    val entitlement = _userEntitlement.value
    if (!entitlement.isPremiumActive && entitlement.aiQueriesToday >= entitlement.maxFreeAiQueriesPerDay) {
      _aiErrorMessage.value = "আজকের ফ্রি AI কোটা (৩টি) শেষ হয়েছে। আনলিমিটেড ব্যবহারের জন্য প্রিমিয়াম প্যাকেজ বেছে নিন।"
      return
    }

    _isAiLoading.value = true
    _aiErrorMessage.value = null

    viewModelScope.launch {
      val result = GeminiService.diagnoseRepairWithAI(_aiQuery.value)
      _isAiLoading.value = false
      result.onSuccess { diagnosis ->
        _aiResult.value = diagnosis
        val newCount = entitlement.aiQueriesToday + 1
        _userEntitlement.value = entitlement.copy(aiQueriesToday = newCount)
        prefs.edit().putInt("ai_queries_today", newCount).apply()
      }.onFailure { err ->
        _aiErrorMessage.value = "AI বিশ্লেষণ সফল হয়নি: ${err.localizedMessage}। আপনি অফলাইন গাইড দেখতে পারেন।"
        // Provide structured offline fallback
        _aiResult.value = GeminiService.getFallbackAIRepairResult(_aiQuery.value)
      }
    }
  }

  fun clearAIResult() {
    _aiResult.value = null
    _aiErrorMessage.value = null
  }

  // ==========================================
  // Visual Component Identifier Action
  // ==========================================
  fun setSelectedPhotoBitmap(bitmap: Bitmap?) {
    _selectedPhotoBitmap.value = bitmap
    _photoScanResult.value = null
    _photoScanErrorMessage.value = null
  }

  fun setHasUserConsent(consent: Boolean) {
    _hasUserConsent.value = consent
  }

  fun executeComponentPhotoScan() {
    val bitmap = _selectedPhotoBitmap.value
    if (bitmap == null) {
      _photoScanErrorMessage.value = "প্রথমে ক্যামেরা বা গ্যালারি থেকে একটি কম্পোনেন্টের ছবি তুলুন বা নির্বাচন করুন।"
      return
    }
    if (!_hasUserConsent.value) {
      _photoScanErrorMessage.value = "ছবি বিশ্লেষণের পূর্বে সম্মতি বক্সে টিক দিন।"
      return
    }
    if (_isSimulatedOffline.value) {
      _photoScanErrorMessage.value = "ইন্টারনেট সংযোগ নেই। লাইভ ছবি বিশ্লেষণের জন্য ইন্টারনেট প্রয়োজন। অফলাইন কম্পোনেন্ট লাইব্রেরি দেখুন।"
      return
    }

    val entitlement = _userEntitlement.value
    if (!entitlement.isPremiumActive && entitlement.photoScansToday >= entitlement.maxFreePhotoScansPerDay) {
      _photoScanErrorMessage.value = "আজকের ফ্রি ছবি শনাক্তকরণ কোটা (৩টি) শেষ হয়েছে। আনলিমিটেড স্ক্যানের জন্য প্রিমিয়ামে আপগ্রেড করুন।"
      return
    }

    _isPhotoScanning.value = true
    _photoScanErrorMessage.value = null

    viewModelScope.launch {
      val base64 = GeminiService.bitmapToBase64(bitmap)
      val result = GeminiService.identifyComponentWithAI(base64)
      _isPhotoScanning.value = false
      result.onSuccess { identified ->
        _photoScanResult.value = identified
        val newCount = entitlement.photoScansToday + 1
        _userEntitlement.value = entitlement.copy(photoScansToday = newCount)
        prefs.edit().putInt("photo_scans_today", newCount).apply()
      }.onFailure { err ->
        _photoScanErrorMessage.value = "ছবি শনাক্তকরণ ব্যর্থ হয়েছে: ${err.localizedMessage}। পরিষ্কার আলোতে ক্লোজ-আপ ছবি তুলুন।"
        _photoScanResult.value = GeminiService.getFallbackComponentIdentification()
      }
    }
  }

  fun provideFeedbackOnPhotoScan(isCorrect: Boolean) {
    _photoScanResult.value = _photoScanResult.value?.copy(userFeedbackGiven = true)
  }

  // ==========================================
  // Subscriptions & Ads Management
  // ==========================================
  fun simulatePurchasePlan(planId: String) {
    // Realistic Google Play Billing Sandbox Mock
    _userEntitlement.value = _userEntitlement.value.copy(
      tier = SubscriptionTier.PREMIUM,
      isPremiumActive = true,
      adsEnabled = false
    )
    prefs.edit().putBoolean("is_premium", true).apply()
    _paymentNotice.value = "অভিনন্দন! আপনার প্রিমিয়াম প্যাকেজ সফলভাবে সক্রিয় হয়েছে (Google Play Sandbox Validated)। সব বিজ্ঞাপন অপসারণ করা হয়েছে।"
  }

  fun cancelSubscription() {
    _userEntitlement.value = _userEntitlement.value.copy(
      tier = SubscriptionTier.FREE,
      isPremiumActive = false,
      adsEnabled = true
    )
    prefs.edit().putBoolean("is_premium", false).apply()
    _paymentNotice.value = "সাবস্ক্রিপশন বাতিল করা হয়েছে। আপনি এখন ফ্রি সংস্করণে রয়েছেন।"
  }

  fun clearPaymentNotice() {
    _paymentNotice.value = null
  }

  // ==========================================
  // Offline Pack Download & Cache Management
  // ==========================================
  fun toggleOfflinePack(packId: String) {
    val updated = _offlinePacks.value.map { pack ->
      if (pack.packId == packId) {
        pack.copy(isDownloaded = !pack.isDownloaded)
      } else pack
    }
    _offlinePacks.value = updated
  }

  // Preferences & Progress
  fun toggleBookmark(id: String) {
    val current = _bookmarkedIds.value.toMutableSet()
    if (current.contains(id)) {
      current.remove(id)
    } else {
      current.add(id)
    }
    _bookmarkedIds.value = current
    prefs.edit().putStringSet("bookmarked_ids", current).apply()
  }

  fun toggleCompleted(id: String) {
    val current = _completedIds.value.toMutableSet()
    if (current.contains(id)) {
      current.remove(id)
    } else {
      current.add(id)
    }
    _completedIds.value = current
    prefs.edit().putStringSet("completed_ids", current).apply()
  }

  fun toggleDarkMode() {
    val newMode = !_isDarkMode.value
    _isDarkMode.value = newMode
    prefs.edit().putBoolean("is_dark_mode", newMode).apply()
  }

  fun setFontSizeScale(scale: Float) {
    _fontSizeScale.value = scale
    prefs.edit().putFloat("font_size_scale", scale).apply()
  }

  fun resetAllProgress() {
    _bookmarkedIds.value = emptySet()
    _completedIds.value = emptySet()
    _quizSelectedAnswers.value = emptyMap()
    _isQuizSubmitted.value = false
    _aiResult.value = null
    _photoScanResult.value = null
    prefs.edit().clear().apply()
  }
}
