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
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Medium Test to test the repository
@MediumTest
class RemindersLocalRepositoryTest {

    private lateinit var database: RemindersDatabase
    private lateinit var repository: RemindersLocalRepository

    val reminder = ReminderDTO(
        "title",
        "description",
        "location",
        42.97419826734443,
        -85.68307958930033
    )

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = RemindersLocalRepository(database.reminderDao(), Dispatchers.Main)
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun saveReminder_successful() = runBlocking {
        // save reminder
        repository.saveReminder(reminder)

        // get reminder
        val result = repository.getReminders()

        // ensure result as success has size of 1
        result as Result.Success
        assertThat(result.data).hasSize(1)
    }

    @Test
    fun getReminder_successful() = runBlocking{
        // save reminder
        repository.saveReminder(reminder)

        // fetch reminder by id
        val fetchedReminder = repository.getReminder(reminder.id) as Result.Success<ReminderDTO>

        // ensure fetched reminder data is not null, has correct title, description, location, latitude, and longitude
        assertThat(fetchedReminder).isNotNull()
        assertThat(fetchedReminder.data.title).isEqualTo(reminder.title)
        assertThat(fetchedReminder.data.description).isEqualTo(reminder.description)
        assertThat(fetchedReminder.data.location).isEqualTo(reminder.location)
        assertThat(fetchedReminder.data.latitude).isEqualTo(reminder.latitude)
        assertThat(fetchedReminder.data.longitude).isEqualTo(reminder.longitude)
    }

    @Test
    fun getReminder_errorNoReminderFound() = runBlocking {
        // get reminder by id when repository empty
        val result = repository.getReminder(reminder.id)

        // ensure result as error equal to message from app
        result as Result.Error
        assertThat(result.message).isEqualTo("Reminder not found!")
    }

    @Test
    fun deleteReminders_successful() = runBlocking {
        // save reminder
        repository.saveReminder(reminder)

        // delete reminders
        repository.deleteAllReminders()

        // get reminders
        val result = repository.getReminders()

        // ensure result as success equal to empty
        result as Result.Success
        assertThat(result.data).isEmpty()
    }
}