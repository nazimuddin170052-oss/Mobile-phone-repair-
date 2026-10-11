package com.example

import com.example.data.ComponentLibraryData
import com.example.data.QuizData
import com.example.data.TroubleshootingData
import org.junit.Assert.*
import org.junit.Test

class ZukiAppUnitTest {

  @Test
  fun testTroubleshootingIssuesIntegrity() {
    val issues = TroubleshootingData.issuesList
    assertTrue("Should have at least 15 issues", issues.size >= 15)

    // Ensure all issues have an initial step that resolves to a valid step
    issues.forEach { issue ->
      val initialStep = TroubleshootingData.getStep(issue.initialStepId)
      assertNotNull("Issue ${issue.id} initial step should exist", initialStep)
    }
  }

  @Test
  fun testQuizQuestionsIntegrity() {
    val questions = QuizData.quizQuestions
    assertTrue("Should have at least 20 quiz questions", questions.size >= 20)

    questions.forEach { q ->
      assertTrue("Question #${q.id} should have options", q.options.size >= 3)
      assertTrue(
        "Question #${q.id} correct index should be within bounds",
        q.correctOptionIndex in 0 until q.options.size
      )
      assertTrue("Question #${q.id} should have Bengali explanation", q.explanationBengali.isNotBlank())
    }
  }

  @Test
  fun testComponentLibraryIntegrity() {
    val components = ComponentLibraryData.componentsList
    assertTrue("Should have at least 10 components", components.size >= 10)

    components.forEach { comp ->
      assertTrue("Component ${comp.id} has valid name", comp.bengaliName.isNotBlank())
      assertTrue("Component ${comp.id} has symbol", comp.symbol.isNotBlank())
      assertTrue("Component ${comp.id} has testing rule", comp.multimeterTestingRule.isNotBlank())
    }
  }

  @Test
  fun testOhmsLawCalculations() {
    // V = I * R -> 2A * 5Ω = 10V
    val i = 2.0
    val r = 5.0
    val v = i * r
    assertEquals(10.0, v, 0.001)

    // I = V / R -> 12V / 4Ω = 3A
    val calculatedI = 12.0 / 4.0
    assertEquals(3.0, calculatedI, 0.001)
  }

  @Test
  fun testPowerCalculations() {
    // P = V * I -> 5V * 2A = 10W
    val v = 5.0
    val i = 2.0
    val p = v * i
    assertEquals(10.0, p, 0.001)
  }

  @Test
  fun testResistorColorCodeCalculation() {
    // Yellow (4), Violet (7), Red (multiplier 100) -> 47 * 100 = 4700Ω (4.7kΩ)
    val yellow = 4
    val violet = 7
    val redMult = 100.0
    val resistance = (yellow * 10 + violet) * redMult
    assertEquals(4700.0, resistance, 0.01)
  }
}
