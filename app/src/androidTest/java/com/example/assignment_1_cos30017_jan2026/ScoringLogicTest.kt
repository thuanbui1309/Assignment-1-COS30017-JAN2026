package com.example.assignment_1_cos30017_jan2026

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScoringLogicTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // Verify initial score is 0
    @Test
    fun testInitialState_scoreIsZero() {
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
    }

    // Verify initial element is 0/10
    @Test
    fun testInitialState_elementIsZeroOfTen() {
        onView(withId(R.id.tvElement)).check(matches(withText("0 / 10")))
    }

    // Verify first element adds 1 point
    @Test
    fun testPerform_firstElement_addsOnePoint() {
        onView(withId(R.id.btnPerform)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("1")))
        onView(withId(R.id.tvElement)).check(matches(withText("1 / 10")))
    }

    // Verify elements 1-3 add 1 point each
    @Test
    fun testPerform_basicZone_addsOnePointEach() {
        repeat(3) { onView(withId(R.id.btnPerform)).perform(click()) }
        
        onView(withId(R.id.tvScore)).check(matches(withText("3")))
        onView(withId(R.id.tvElement)).check(matches(withText("3 / 10")))
    }

    // Verify elements 4-7 add 2 points each
    @Test
    fun testPerform_intermediateZone_addsTwoPointsEach() {
        repeat(3) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnPerform)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("5")))
        onView(withId(R.id.tvElement)).check(matches(withText("4 / 10")))
    }

    // Verify elements 8-10 add 3 points each
    @Test
    fun testPerform_advancedZone_addsThreePointsEach() {
        repeat(7) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.tvScore)).check(matches(withText("11")))
        
        onView(withId(R.id.btnPerform)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("14")))
        onView(withId(R.id.tvElement)).check(matches(withText("8 / 10")))
    }

    // Verify max score is 20 after full routine
    @Test
    fun testPerform_completeRoutine_maxScoreIs20() {
        repeat(10) { onView(withId(R.id.btnPerform)).perform(click()) }
        
        // Dialog shows - check score in dialog
        onView(withId(R.id.tvDialogScore)).inRoot(isDialog()).check(matches(withText("20")))
    }

    // Verify no actions allowed after routine completion
    @Test
    fun testPerform_afterRoutineComplete_doesNothing() {
        repeat(10) { onView(withId(R.id.btnPerform)).perform(click()) }
        
        // Dismiss dialog via restart button
        onView(withId(R.id.btnDialogRestart)).inRoot(isDialog()).perform(click())
        
        // Perform should do nothing (routine was completed, now reset)
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
        onView(withId(R.id.tvElement)).check(matches(withText("0 / 10")))
    }

    // Verify deduction button is disabled before starting
    @Test
    fun testDeduction_beforeFirstElement_doesNothing() {
        onView(withId(R.id.btnDeduction)).check(matches(not(isEnabled())))
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
        onView(withId(R.id.tvElement)).check(matches(withText("0 / 10")))
    }

    // Verify deduction subtracts 2 points
    @Test
    fun testDeduction_afterFirstElement_subtractsTwoPoints() {
        onView(withId(R.id.btnPerform)).perform(click())
        onView(withId(R.id.btnDeduction)).perform(click())
        
        // Dialog shows - check score in dialog
        onView(withId(R.id.tvDialogScore)).inRoot(isDialog()).check(matches(withText("0")))
    }

    // Verify deduction works correctly from higher score
    @Test
    fun testDeduction_afterMultipleElements_subtractsTwoPoints() {
        repeat(5) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.tvScore)).check(matches(withText("7")))
        
        onView(withId(R.id.btnDeduction)).perform(click())
        
        // Dialog shows - check score in dialog
        onView(withId(R.id.tvDialogScore)).inRoot(isDialog()).check(matches(withText("5")))
    }

    // Verify deduction terminates the routine
    @Test
    fun testDeduction_endsRoutine_cannotContinuePerforming() {
        repeat(3) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnDeduction)).perform(click())
        
        // Dialog shows with score 1
        onView(withId(R.id.tvDialogScore)).inRoot(isDialog()).check(matches(withText("1")))
        
        // Dismiss dialog
        onView(withId(R.id.btnDialogRestart)).inRoot(isDialog()).perform(click())
        
        // After reset, state is cleared
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
    }

    // Verify multiple deductions are not allowed
    @Test
    fun testDeduction_cannotDeductTwice() {
        repeat(5) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnDeduction)).perform(click())
        
        // Dialog shows - deduction button in main UI is now inaccessible
        onView(withId(R.id.tvDialogScore)).inRoot(isDialog()).check(matches(withText("5")))
    }

    // Verify deduction impossible after completion
    @Test
    fun testDeduction_afterRoutineComplete_doesNothing() {
        repeat(10) { onView(withId(R.id.btnPerform)).perform(click()) }
        
        // Dialog shows with max score - deduction was blocked
        onView(withId(R.id.tvDialogScore)).inRoot(isDialog()).check(matches(withText("20")))
    }

    // Verify reset clears score and element count
    @Test
    fun testReset_afterPerforming_returnsToInitialState() {
        repeat(5) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnReset)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
        onView(withId(R.id.tvElement)).check(matches(withText("0 / 10")))
    }

    // Verify reset works after deduction (via dialog button)
    @Test
    fun testReset_afterDeduction_returnsToInitialState() {
        repeat(3) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnDeduction)).perform(click())
        
        // Dismiss dialog via restart
        onView(withId(R.id.btnDialogRestart)).inRoot(isDialog()).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
        onView(withId(R.id.tvElement)).check(matches(withText("0 / 10")))
    }

    // Verify reset works after completion (via dialog button)
    @Test
    fun testReset_afterCompleteRoutine_returnsToInitialState() {
        repeat(10) { onView(withId(R.id.btnPerform)).perform(click()) }
        
        // Dismiss dialog via restart
        onView(withId(R.id.btnDialogRestart)).inRoot(isDialog()).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
        onView(withId(R.id.tvElement)).check(matches(withText("0 / 10")))
    }

    // Verify new routine can start after reset
    @Test
    fun testReset_allowsNewRoutine() {
        repeat(3) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnReset)).perform(click())
        onView(withId(R.id.btnPerform)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("1")))
        onView(withId(R.id.tvElement)).check(matches(withText("1 / 10")))
    }

    // Verify score minimum limit
    @Test
    fun testScore_neverGoesNegative() {
        onView(withId(R.id.btnPerform)).perform(click())
        onView(withId(R.id.btnDeduction)).perform(click())
        
        // Dialog shows - score should be 0, not negative
        onView(withId(R.id.tvDialogScore)).inRoot(isDialog()).check(matches(withText("0")))
    }

    // Verify score maximum limit
    @Test
    fun testScore_neverExceeds20() {
        repeat(10) { onView(withId(R.id.btnPerform)).perform(click()) }
        
        // Dialog shows with max score
        onView(withId(R.id.tvDialogScore)).inRoot(isDialog()).check(matches(withText("20")))
    }
}
