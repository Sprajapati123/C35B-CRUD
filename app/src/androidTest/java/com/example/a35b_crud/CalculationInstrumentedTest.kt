package com.example.a35b_crud

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.a35b_crud.ui.activity.CalculationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(AndroidJUnit4::class)
class CalculationInstrumentedTest {
    @get:Rule
    val testRule = ActivityScenarioRule(CalculationActivity::class.java)
    @Test
    fun checkSum() {
        onView(withId(R.id.firstValue)).perform(
            typeText("1")
        )

        onView(withId(R.id.secondValue)).perform(
            typeText("1")
        )

        closeSoftKeyboard()

        Thread.sleep(1500)

        onView(withId(R.id.btnCalulate)).perform(
            click()
        )

        Thread.sleep(1500)
        onView(withId(R.id.displaySum)).check(matches(withText("2")))
    }

}