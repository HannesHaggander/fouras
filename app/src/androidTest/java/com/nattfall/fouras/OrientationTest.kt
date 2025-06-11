package com.nattfall.fouras

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrientationTest {

    @get:Rule
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
            = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun verifyOrientationChangesAvailable() {
        composeTestRule.onOrientation(
            onPortrait = {
                onNodeWithText("Fouras").assertIsDisplayed()
            },
            onLandscape = {
                onNodeWithText("Fouras").assertIsDisplayed()
            },
        )
    }

    private fun <R : Activity, T : ComponentActivity>
            AndroidComposeTestRule<ActivityScenarioRule<R>, T>.onOrientation(
        onPortrait: AndroidComposeTestRule<ActivityScenarioRule<R>, T>.() -> Unit,
        onLandscape: AndroidComposeTestRule<ActivityScenarioRule<R>, T>.() -> Unit,
    ) {
        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        waitForIdle()
        onPortrait(this)

        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        waitForIdle()
        onLandscape(this)
    }
}