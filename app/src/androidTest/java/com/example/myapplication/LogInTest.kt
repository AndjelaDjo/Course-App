package com.example.myapplication
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogInTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity_Dashboard::class.java)
        Thread.sleep(2000)
    }

    @Test
    fun testLoginSuccess() {
        // Valid test data
        Espresso.onView(withId(R.id.emailEditText))
            .perform(ViewActions.typeText("email@gmail.com"), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.passwordEditText))
            .perform(ViewActions.typeText("1111111"), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.loginButton)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.backgroundImageView)).check(matches(isDisplayed()))
    }
}
