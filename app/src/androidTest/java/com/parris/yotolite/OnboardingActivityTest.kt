package com.parris.yotolite

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.parris.yotolite.onboarding.OnboardingActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

/**
 * Instrumentation smoke test for OnboardingActivity.
 * Verifies basic UI elements are present and interactive.
 */
@RunWith(AndroidJUnit4::class)
class OnboardingActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(OnboardingActivity::class.java)

    @Test
    fun testOnboardingActivityDisplaysWelcomeMessage() {
        onView(withId(R.id.tvWelcome))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testNfcCheckboxIsDisplayed() {
        onView(withId(R.id.cbNfc))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testMediaCheckboxIsDisplayed() {
        onView(withId(R.id.cbMedia))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testContinueButtonIsDisplayed() {
        onView(withId(R.id.btnContinue))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testContinueButtonIsClickable() {
        onView(withId(R.id.btnContinue))
            .check(matches(isClickable()))
    }
}
