package com.example.model

enum class ScreenSection(
  val id: String,
  val titleBengali: String,
  val titleEnglish: String
) {
  HOME("home", "হোম", "Home"),
  DIAGNOSIS("diagnosis", "সমস্যা শনাক্তকরণ", "Diagnostics"),
  AI_ASSISTANT("ai_assistant", "AI মেরামত সহকারী", "AI Assistant"),
  PHOTO_IDENTIFIER("photo_identifier", "ছবি দিয়ে শনাক্তকরণ", "Visual Identifier"),
  TOOLBOX("toolbox", "টেকনিশিয়ান টুলবক্স", "Technician Toolbox"),
  HARDWARE("hardware", "হার্ডওয়্যার মেরামত", "Hardware Repair"),
  SOFTWARE("software", "সফটওয়্যার মেরামত", "Software Repair"),
  CIRCUITS("circuits", "সার্কিট ডায়াগ্রাম", "Circuit Diagrams"),
  COMPONENTS("components", "কম্পোনেন্ট লাইব্রেরি", "Component Library"),
  QUIZ("quiz", "বাংলা কুইজ", "Bengali Quiz"),
  BOOKMARKS("bookmarks", "বুকমার্ক ও অগ্রগতি", "Bookmarks & Progress"),
  PREMIUM("premium", "প্রিমিয়াম ও বিজ্ঞাপন", "Premium & Ads"),
  OFFLINE_MANAGER("offline_manager", "অফলাইন ম্যানেজার", "Offline Pack"),
  SETTINGS("settings", "সেটিংস", "Settings"),
  LESSON_DETAIL("lesson_detail", "পাঠ বিবরণ", "Lesson Detail")
}

enum class BottomNavTab(
  val section: ScreenSection,
  val titleBengali: String,
  val iconName: String
) {
  HOME(ScreenSection.HOME, "হোম", "home"),
  DIAGNOSIS(ScreenSection.DIAGNOSIS, "সমস্যা নির্ণয়", "build"),
  TOOLBOX(ScreenSection.TOOLBOX, "টুলবক্স", "handyman"),
  COMPONENTS(ScreenSection.COMPONENTS, "কম্পোনেন্ট", "memory"),
  SETTINGS(ScreenSection.SETTINGS, "প্রোফাইল", "person")
}

enum class DifficultyLevel(val labelBengali: String, val labelEnglish: String) {
  BEGINNER("প্রাথমিক", "Beginner"),
  INTERMEDIATE("মধ্যম", "Intermediate"),
  ADVANCED("অ্যাডভান্সড", "Advanced")
}

data class RepairStep(
  val stepNumber: Int,
  val stepTitle: String,
  val instruction: String,
  val importantWarning: String? = null
)

data class Lesson(
  val id: String,
  val title: String,
  val englishTitle: String,
  val section: ScreenSection,
  val shortDescription: String,
  val symptoms: List<String>,
  val causes: List<String>,
  val toolsNeeded: List<String>,
  val steps: List<RepairStep>,
  val safetyTips: List<String>,
  val testingMethod: String,
  val difficulty: DifficultyLevel,
  val estimatedMinutes: Int
)

data class ComponentItem(
  val id: String,
  val bengaliName: String,
  val englishName: String,
  val symbol: String,
  val function: String,
  val appearance: String,
  val damageSigns: String,
  val howToTest: String,
  val goodReading: String,
  val badReading: String
)

data class ComponentDetail(
  val id: String,
  val bengaliName: String,
  val englishName: String,
  val symbol: String,
  val shortFunction: String,
  val howToIdentify: String,
  val multimeterTestingRule: String,
  val commonFaults: String,
  val safetyNotes: String,
  val visualIconType: String
)

data class MultimeterTestScenario(
  val id: String,
  val componentTitle: String,
  val englishTitle: String,
  val properMode: String,
  val probeInstructions: String,
  val healthyReading: String,
  val faultyReading: String,
  val simulatedHealthyValue: Double,
  val simulatedFaultyValue: Double,
  val unitSymbol: String,
  val makesBeepSound: Boolean,
  val explanation: String
)

data class CircuitSection(
  val id: String,
  val titleBengali: String,
  val titleEnglish: String,
  val voltageRange: String,
  val primaryICs: List<String>,
  val keyLines: List<String>,
  val commonFaults: String,
  val colorHex: Long,
  val testPoints: List<String>
)

data class CircuitDiagramItem(
  val id: String,
  val titleBengali: String,
  val titleEnglish: String,
  val voltageRange: String,
  val description: String,
  val keyComponents: List<String>,
  val testLines: List<Pair<String, String>>,
  val safetyCaution: String
)

data class ResistorBandColor(
  val nameBengali: String,
  val nameEnglish: String,
  val hexColor: Long,
  val digitValue: Int?,
  val multiplier: Double,
  val tolerancePercent: Double?
)

data class QuizQuestion(
  val id: Int,
  val questionBengali: String,
  val options: List<String>,
  val correctOptionIndex: Int,
  val explanationBengali: String,
  val category: String
)

data class TroubleIssue(
  val id: String,
  val titleBengali: String,
  val titleEnglish: String,
  val category: String,
  val initialStepId: String,
  val iconName: String,
  val rootCauses: List<String>,
  val requiredTools: List<String>,
  val safetyWarning: String
)

data class DiagnosticStep(
  val id: String,
  val stepNumber: Int,
  val titleBengali: String,
  val instructionBengali: String,
  val testingMethod: String,
  val toolsNeeded: List<String>,
  val observationQuestion: String,
  val yesOptionLabel: String = "হ্যাঁ, এটি ঘটছে",
  val yesNextStepId: String? = null,
  val yesOutcomeDescription: String? = null,
  val noOptionLabel: String = "না, স্বাভাবিক দেখাচ্ছে",
  val noNextStepId: String? = null,
  val noOutcomeDescription: String? = null,
  val intermediateOptionLabel: String? = null,
  val intermediateNextStepId: String? = null,
  val intermediateOutcomeDescription: String? = null,
  val finalResolution: String? = null,
  val isTerminalResolution: Boolean = false,
  val requiresProfessionalService: Boolean = false
)

// --- New Features Models ---

enum class ConfidenceLevel(val labelBengali: String, val colorHex: Long) {
  CERTAIN("নিশ্চিত (High Certainty)", 0xFF10B981),
  PROBABLE("সম্ভাব্য (Probable)", 0xFFF59E0B),
  UNCERTAIN("অনিশ্চিত (Uncertain / Needs Lab Test)", 0xFFEF4444)
}

data class AIRepairQuery(
  val brand: String = "",
  val model: String = "",
  val category: String = "পাওয়ার ও চালু না হওয়া",
  val description: String = "",
  val durationHistory: String = "",
  val priorTestsDone: String = "",
  val multimeterReading: String = "",
  val imageBase64: String? = null
)

data class AIRepairResult(
  val summary: String,
  val rootCauses: List<String>,
  val firstSafeTest: String,
  val toolsNeeded: List<String>,
  val stepInstructions: List<String>,
  val expectedOutcomes: String,
  val nextSteps: String,
  val whenToConsultPro: String,
  val confidenceLevel: ConfidenceLevel,
  val rawTimestamp: Long = System.currentTimeMillis()
)

data class ComponentIdentificationResult(
  val componentName: String,
  val bengaliFunction: String,
  val confidenceScore: Int,
  val confidenceLevel: ConfidenceLevel,
  val visualEvidence: String,
  val alternativeCandidates: List<String>,
  val multimeterTestingGuide: String,
  val safetyCaution: String,
  val userFeedbackGiven: Boolean = false
)

enum class SubscriptionTier(val labelBengali: String) {
  FREE("ফ্রি সংস্করণ (Basic)"),
  PREMIUM("প্রিমিয়াম প্রো (Pro Technician)")
}

data class SubscriptionPlan(
  val id: String,
  val titleBengali: String,
  val priceBengali: String,
  val periodBengali: String,
  val features: List<String>,
  val isPopular: Boolean = false
)

data class UserEntitlement(
  val tier: SubscriptionTier = SubscriptionTier.FREE,
  val isPremiumActive: Boolean = false,
  val aiQueriesToday: Int = 0,
  val maxFreeAiQueriesPerDay: Int = 3,
  val photoScansToday: Int = 0,
  val maxFreePhotoScansPerDay: Int = 3,
  val adsEnabled: Boolean = true
)

data class DownloadedContentInfo(
  val packId: String,
  val packName: String,
  val sizeMb: Double,
  val isDownloaded: Boolean,
  val lessonCount: Int
)
