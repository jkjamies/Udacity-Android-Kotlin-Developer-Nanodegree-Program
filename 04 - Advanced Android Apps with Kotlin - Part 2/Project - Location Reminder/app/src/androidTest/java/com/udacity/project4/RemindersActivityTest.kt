/*
 *
 * PROJECT LICENSE
 *
 * This project was submitted by Jason Jamieson as part of the Android Kotlin Developer Nanodegree At Udacity.
 *
 * As part of Udacity Honor code, your submissions must be of your own work.
 * Submission of this project will cause you to break the Udacity Honor Code
 * and possible suspension of your account.
 *
 * I, Jason Jamieson, the author of the project, allow you to check this code as reference, but if
 * used as submission, it's your responsibility if you are expelled.
 *
 * Copyright (c) 2021 Jason Jamieson
 *
 * Besides the above notice, the following license applies and this license notice
 * must be included in all works derived from this project.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.udacity.project4

import android.app.Application
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.udacity.project4.locationreminders.RemindersActivity
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.local.LocalDB
import com.udacity.project4.locationreminders.data.local.RemindersLocalRepository
import com.udacity.project4.locationreminders.reminderslist.RemindersListViewModel
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel
import com.udacity.project4.util.DataBindingIdlingResource
import com.udacity.project4.util.ToastMatcher
import com.udacity.project4.util.monitorActivity
import com.udacity.project4.utils.EspressoIdlingResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get


@RunWith(AndroidJUnit4::class)
@LargeTest
//END TO END test to black box test the app
class RemindersActivityTest :
    AutoCloseKoinTest() {// Extended Koin Test - embed autoclose @after method to close Koin after every test

    private lateinit var repository: ReminderDataSource
    private lateinit var appContext: Application
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun registerIdlingResource(): Unit = IdlingRegistry.getInstance().run {
        register(EspressoIdlingResource.countingIdlingResource)
        register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource(): Unit = IdlingRegistry.getInstance().run {
        unregister(EspressoIdlingResource.countingIdlingResource)
        unregister(dataBindingIdlingResource)
    }

    @Before
    fun init() {
        stopKoin()

        appContext = getApplicationContext()

        val myModule = module {
            viewModel {
                RemindersListViewModel(
                    appContext,
                    get() as ReminderDataSource
                )
            }
            single {
                SaveReminderViewModel(
                    appContext,
                    get() as ReminderDataSource
                )
            }
            single { RemindersLocalRepository(get()) as ReminderDataSource }
            single { LocalDB.createRemindersDao(appContext) }
        }

        startKoin { modules(listOf(myModule)) }

        repository = get()

        runBlocking { repository.deleteAllReminders() }
    }

    @Test
    fun addReminder_displaysOnScreen() = runBlocking {
        val activityScenario = ActivityScenario.launch(RemindersActivity::class.java)

        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Press add reminder FAB
        onView(withId(R.id.addReminderFAB)).perform(click())

        // enter the title and description fields
        onView(withId(R.id.reminderTitle)).perform(typeText("foo"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.reminderDescription)).perform(
            typeText("bar"),
            ViewActions.closeSoftKeyboard()
        )

        // Click on set location
        onView(withId(R.id.selectLocation)).perform(click())

        // Click on a location on the map
        onView(withId(R.id.selectLocationMap)).perform(click())

        // Press save location button
        onView(withId(R.id.selectLocationSaveButton)).perform(click())

        // Press save reminder
        onView(withId(R.id.saveReminder)).perform(click())

        // Works on api < 30 - Ensure toast is displayed
        onView(withText(R.string.reminder_saved)).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        // Ensure title and description are displayed
        onView(withText("foo")).check(matches(isDisplayed()))
        onView(withText("bar")).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun addReminderReturnError_noLocation() = runBlocking {
        val activityScenario = ActivityScenario.launch(RemindersActivity::class.java)

        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Press add reminder FAB
        onView(withId(R.id.addReminderFAB)).perform(click())

        // enter the title and description fields
        onView(withId(R.id.reminderTitle)).perform(typeText("foo"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.reminderDescription)).perform(
            typeText("bar"),
            ViewActions.closeSoftKeyboard()
        )

        // Press save reminder
        onView(withId(R.id.saveReminder)).perform(click())

        // Check to see the error toast is displayed
        onView(withText("Please select location")).check(matches(isDisplayed()))
        // Displays matching text to expectation using AndroidX
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("Please select location")))
        // Is Visible
        onView(withText("Please select location"))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        activityScenario.close()

        delay(500)
    }

}
