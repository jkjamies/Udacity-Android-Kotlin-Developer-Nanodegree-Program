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

package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RemindersDatabase
    private lateinit var dao: RemindersDao

    val reminder = ReminderDTO(
        "title",
        "description",
        "location",
        42.97419826734443,
        -85.68307958930033
    )

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), RemindersDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.reminderDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun saveReminderSuccessful() = runBlockingTest {
        // save reminder
        dao.saveReminder(reminder)

        // ensure reminder is in db
        assertThat(dao.getReminders()).contains(reminder)
    }

    @Test
    fun getReminderSuccessful() = runBlockingTest {
        // save reminder
        dao.saveReminder(reminder)

        // get reminder by id of saved reminder
        val fetchedReminder = dao.getReminderById(reminder.id)

        // ensure reminder is not null and has correct title, description, location, latitude, and longitude
        assertThat(fetchedReminder).isNotNull()
        assertThat(fetchedReminder?.title).isEqualTo(reminder.title)
        assertThat(fetchedReminder?.description).isEqualTo(reminder.description)
        assertThat(fetchedReminder?.location).isEqualTo(reminder.location)
        assertThat(fetchedReminder?.latitude).isEqualTo(reminder.latitude)
        assertThat(fetchedReminder?.longitude).isEqualTo(reminder.longitude)
    }

    @Test
    fun deleteRemindersSuccessful() = runBlockingTest {
        // save reminder
        dao.saveReminder(reminder)

        // ensure reminder was saved
        assertThat(dao.getReminders()).isNotEmpty()

        // delete all reminders
        dao.deleteAllReminders()

        // ensure reminders are empty
        assertThat(dao.getReminders()).isEmpty()
    }

}