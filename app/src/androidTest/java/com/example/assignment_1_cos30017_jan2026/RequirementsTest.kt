package com.example.assignment_1_cos30017_jan2026

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RequirementsTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // Verify score persists after rotation
    @Test
    fun testRotation_preservesScore() {
        repeat(3) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.tvScore)).check(matches(withText("3")))

        activityRule.scenario.onActivity { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
        Thread.sleep(500)

        onView(withId(R.id.tvScore)).check(matches(withText("3")))
    }

    // Verify current element count persists after rotation
    @Test
    fun testRotation_preservesElement() {
        repeat(5) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.tvElement)).check(matches(withText("5 / 10")))

        activityRule.scenario.onActivity { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
        Thread.sleep(500)

        onView(withId(R.id.tvElement)).check(matches(withText("5 / 10")))
    }

    // Verify dialog remains visible after rotation
    @Test
    fun testRotation_preservesDialogState() {
        repeat(10) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.tvDialogTitle)).inRoot(isDialog()).check(matches(isDisplayed()))

        activityRule.scenario.onActivity { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
        Thread.sleep(1000)

        onView(withId(R.id.tvDialogTitle)).inRoot(isDialog()).check(matches(isDisplayed()))
    }

    // Verify dialog appears when routine is complete
    @Test
    fun testDialog_showsOnRoutineComplete() {
        repeat(10) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.tvDialogTitle)).check(matches(isDisplayed()))
    }

    // Verify dialog appears when deduction occurs
    @Test
    fun testDialog_showsOnDeduction() {
        onView(withId(R.id.btnPerform)).perform(click())
        onView(withId(R.id.btnDeduction)).perform(click())
        onView(withId(R.id.tvDialogTitle)).check(matches(isDisplayed()))
    }

    // Verify success dialog shows correct title
    @Test
    fun testDialog_successShowsCorrectTitle() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedTitle = context.getString(R.string.dialog_title_success)
        
        repeat(10) { onView(withId(R.id.btnPerform)).perform(click()) }
        onView(withId(R.id.tvDialogTitle)).check(matches(withText(expectedTitle)))
    }

    // Verify failure dialog shows correct title
    @Test
    fun testDialog_failureShowsCorrectTitle() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedTitle = context.getString(R.string.dialog_title_failure)
        
        onView(withId(R.id.btnPerform)).perform(click())
        onView(withId(R.id.btnDeduction)).perform(click())
        onView(withId(R.id.tvDialogTitle)).check(matches(withText(expectedTitle)))
    }

    // Verify Deduct button is initially disabled
    @Test
    fun testButtons_deductDisabledInitially() {
        onView(withId(R.id.btnDeduction)).check(matches(not(isEnabled())))
    }

    // Verify Reset button is initially disabled
    @Test
    fun testButtons_resetDisabledInitially() {
        onView(withId(R.id.btnReset)).check(matches(not(isEnabled())))
    }

    // Verify buttons enable after first action
    @Test
    fun testButtons_enableAfterPerform() {
        onView(withId(R.id.btnPerform)).perform(click())
        onView(withId(R.id.btnDeduction)).check(matches(isEnabled()))
        onView(withId(R.id.btnReset)).check(matches(isEnabled()))
    }

    // Verify buttons disable again after reset
    @Test
    fun testButtons_disableAfterReset() {
        onView(withId(R.id.btnPerform)).perform(click())
        onView(withId(R.id.btnReset)).perform(click())
        onView(withId(R.id.btnDeduction)).check(matches(not(isEnabled())))
        onView(withId(R.id.btnReset)).check(matches(not(isEnabled())))
    }

    // Verify App Title is displayed correctly
    @Test
    fun testUI_titleDisplayed() {
        onView(withId(R.id.tvAppTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvAppTitle)).check(matches(withText("GYMNASTICS APP")))
    }

    // Verify Score is displayed
    @Test
    fun testUI_scoreDisplayed() {
        onView(withId(R.id.tvScore)).check(matches(isDisplayed()))
    }

    // Verify Element count is displayed
    @Test
    fun testUI_elementDisplayed() {
        onView(withId(R.id.tvElement)).check(matches(isDisplayed()))
    }

    // Verify all buttons are displayed
    @Test
    fun testUI_allButtonsDisplayed() {
        onView(withId(R.id.btnPerform)).check(matches(isDisplayed()))
        onView(withId(R.id.btnDeduction)).check(matches(isDisplayed()))
        onView(withId(R.id.btnReset)).check(matches(isDisplayed()))
    }
}
