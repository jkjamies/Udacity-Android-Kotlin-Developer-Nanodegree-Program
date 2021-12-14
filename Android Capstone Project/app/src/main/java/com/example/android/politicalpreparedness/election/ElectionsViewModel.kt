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

package com.example.android.politicalpreparedness.election

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch
import timber.log.Timber

class ElectionsViewModel(private val database: ElectionDao, private val service: CivicsApiService) :
    ViewModel() {

    val elections = MutableLiveData<List<Election>>()
    val savedElections = MutableLiveData<List<Election>>()

    private val _navigateToElection = MutableLiveData<Election>()
    val navigateToElection: LiveData<Election>
        get() = _navigateToElection

    val loading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getElectionsFromApiService()
        getSavedElectionsFromDatabase()
        Timber.d("refreshing election data")
    }

    private fun getElectionsFromApiService() {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = service.getElections()
                elections.value = response.elections
                loading.value = false
            } catch (e: Exception) {
                Timber.e("Error: %s", e.localizedMessage)
                elections.value = ArrayList()
                loading.value = false
            }
        }
    }

    private fun getSavedElectionsFromDatabase() {
        viewModelScope.launch {
            savedElections.value = database.getElections()
        }
    }

    fun refreshFollowedElections() {
        getSavedElectionsFromDatabase()
    }

    fun goToElection(election: Election) {
        _navigateToElection.value = election
    }

    fun goToElectionComplete() {
        _navigateToElection.value = null
    }
}