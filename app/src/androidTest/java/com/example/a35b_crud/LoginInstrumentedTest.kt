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
import com.example.a35b_crud.ui.activity.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginInstrumentedTest {
    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun checkLogin() {
        onView(withId(R.id.editEmail)).perform(
            typeText("sandis@gmail.com")
        )

        onView(withId(R.id.editPassword)).perform(
            typeText("password")
        )

        closeSoftKeyboard()

        Thread.sleep(1500)

        onView(withId(R.id.btnLogin)).perform(
            click()
        )

        Thread.sleep(1500)
        onView(withId(R.id.instrumentedCheck)).check(matches(withText("Login success")))
    }

}