package com.example.assignment_1_cos30017_jan2026

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Espresso UI Tests for Gymnastics Scoring Logic
 */
@RunWith(AndroidJUnit4::class)
class ScoringLogicTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // INITIAL STATE
    @Test
    fun testInitialState_scoreIsZero() {
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
    }

    @Test
    fun testInitialState_elementIsZeroOfTen() {
        onView(withId(R.id.tvElement)).check(matches(withText("0 / 10")))
    }

    // PERFORM BUTTON
    @Test
    fun testPerform_firstElement_addsOnePoint() {
        onView(withId(R.id.btnPerform)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("1")))
        onView(withId(R.id.tvElement)).check(matches(withText("1 / 10")))
    }

    @Test
    fun testPerform_basicZone_addsOnePointEach() {
        repeat(3) { onView(withId(R.id.btnPerform)).perform(click()) }
        
        onView(withId(R.id.tvScore)).check(matches(withText("3")))
        onView(withId(R.id.tvElement)).check(matches(withText("3 / 10")))
    }

    @Test
    fun testPerform_intermediateZone_addsTwoPointsEach() {
        repeat(3) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnPerform)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("5")))
        onView(withId(R.id.tvElement)).check(matches(withText("4 / 10")))
    }

    @Test
    fun testPerform_advancedZone_addsThreePointsEach() {
        repeat(7) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.tvScore)).check(matches(withText("11")))
        
        onView(withId(R.id.btnPerform)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("14")))
        onView(withId(R.id.tvElement)).check(matches(withText("8 / 10")))
    }

    @Test
    fun testPerform_completeRoutine_maxScoreIs20() {
        repeat(10) { onView(withId(R.id.btnPerform)).perform(click()) }
        
        onView(withId(R.id.tvScore)).check(matches(withText("20")))
        onView(withId(R.id.tvElement)).check(matches(withText("10 / 10")))
    }

    @Test
    fun testPerform_afterRoutineComplete_doesNothing() {
        repeat(10) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnPerform)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("20")))
        onView(withId(R.id.tvElement)).check(matches(withText("10 / 10")))
    }

    //DEDUCTION BUTTON
    @Test
    fun testDeduction_beforeFirstElement_doesNothing() {
        onView(withId(R.id.btnDeduction)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
        onView(withId(R.id.tvElement)).check(matches(withText("0 / 10")))
    }

    @Test
    fun testDeduction_afterFirstElement_subtractsTwoPoints() {
        onView(withId(R.id.btnPerform)).perform(click())
        onView(withId(R.id.btnDeduction)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
    }

    @Test
    fun testDeduction_afterMultipleElements_subtractsTwoPoints() {
        repeat(5) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.tvScore)).check(matches(withText("7")))
        
        onView(withId(R.id.btnDeduction)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("5")))
    }

    @Test
    fun testDeduction_endsRoutine_cannotContinuePerforming() {
        repeat(3) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnDeduction)).perform(click())
        onView(withId(R.id.tvScore)).check(matches(withText("1")))
        
        onView(withId(R.id.btnPerform)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("1")))
        onView(withId(R.id.tvElement)).check(matches(withText("3 / 10")))
    }

    @Test
    fun testDeduction_cannotDeductTwice() {
        repeat(5) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnDeduction)).perform(click())
        onView(withId(R.id.tvScore)).check(matches(withText("5")))
        
        onView(withId(R.id.btnDeduction)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("5")))
    }

    @Test
    fun testDeduction_afterRoutineComplete_doesNothing() {
        repeat(10) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnDeduction)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("20")))
    }

    // RESET BUTTON
    @Test
    fun testReset_afterPerforming_returnsToInitialState() {
        repeat(5) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnReset)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
        onView(withId(R.id.tvElement)).check(matches(withText("0 / 10")))
    }

    @Test
    fun testReset_afterDeduction_returnsToInitialState() {
        repeat(3) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnDeduction)).perform(click())
        onView(withId(R.id.btnReset)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
        onView(withId(R.id.tvElement)).check(matches(withText("0 / 10")))
    }

    @Test
    fun testReset_afterCompleteRoutine_returnsToInitialState() {
        repeat(10) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnReset)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
        onView(withId(R.id.tvElement)).check(matches(withText("0 / 10")))
    }

    @Test
    fun testReset_allowsNewRoutine() {
        repeat(3) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.btnReset)).perform(click())
        onView(withId(R.id.btnPerform)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("1")))
        onView(withId(R.id.tvElement)).check(matches(withText("1 / 10")))
    }

    // SCORE LIMITS
    @Test
    fun testScore_neverGoesNegative() {
        onView(withId(R.id.btnPerform)).perform(click())
        onView(withId(R.id.btnDeduction)).perform(click())
        
        onView(withId(R.id.tvScore)).check(matches(withText("0")))
    }

    @Test
    fun testScore_neverExceeds20() {
        repeat(10) { onView(withId(R.id.btnPerform)).perform(click()) }
        
        onView(withId(R.id.tvScore)).check(matches(withText("20")))
    }
}
