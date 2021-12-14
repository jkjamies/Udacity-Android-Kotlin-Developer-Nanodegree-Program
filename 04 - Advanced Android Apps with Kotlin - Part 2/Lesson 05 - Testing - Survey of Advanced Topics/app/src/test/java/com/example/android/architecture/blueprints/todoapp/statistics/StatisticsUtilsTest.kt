package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test

//http://hamcrest.org/
//https://truth.dev/

class StatisticsUtilsTest {

    // If there's 0 completed tasks and 1 active tasks,
    // then there are 0% completed tasks and 100% active tasks.
    @Test
    fun getActiveAndCompletedStats_notCompleted_returnsHundredZero() {
        // GIVEN
        val tasks = listOf<Task>(
                Task("title", "description", isCompleted = false)
        )

        // WHEN
        // When the list of tasks is computed with an active task
        val result = getActiveAndCompletedStats(tasks)

        // THEN
        // Then the percentages are 100 and 0
        assertThat(result.activeTasksPercent, `is`(100f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    // If there's 1 completed tasks and 0 active tasks,
    // then there are 100% completed tasks and 0% active tasks.
    @Test
    fun getActiveAndCompletedStats_noActive_returnsZeroHundred() {
        val tasks = listOf(
                Task("title", "description", isCompleted = true)
        )
        // When the list of tasks is computed with a completed task
        val result = getActiveAndCompletedStats(tasks)

        // Then the percentages are 0 and 100
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(100f))
    }

    // If there's 2 completed tasks and 3 active tasks,
    // then there are 40% completed tasks and 60% active tasks.
    @Test
    fun getActiveAndCompletedStats_notCompleted_returnsFortySixty() {
        // Given 3 completed tasks and 2 active tasks
        val tasks = listOf<Task>(
                Task("title", "description", isCompleted = true),
                Task("title", "description", isCompleted = true),
                Task("title", "description", isCompleted = true),
                Task("title", "description", isCompleted = false),
                Task("title", "description", isCompleted = false)
        )

        // When the list of tasks is computed
        val result = getActiveAndCompletedStats(tasks)

        // Then the result is 40-60
        assertThat(result.activeTasksPercent, `is`(40f))
        assertThat(result.completedTasksPercent, `is`(60f))
    }

    // If there's 0 completed tasks and 0 active tasks,
    // then there are 0% completed tasks and 0% active tasks.
    @Test
    fun getActiveAndCompletedStats_notCompleted_returnsZeros() {
        // When there are no tasks
        val result = getActiveAndCompletedStats(emptyList())

        // Both active and completed tasks are 0
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }


    // If tasks are null, then there are 0% completed tasks and 0% active tasks.
    @Test
    fun getActiveAndCompletedStats_error_returnsZeros() {
        // When there's an error loading stats
        val result = getActiveAndCompletedStats(null)

        // Both active and completed tasks are 0
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }
}