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
import com.example.android.politicalpreparedness.network.models.AdministrationBody
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class VoterInfoViewModel(private val database: ElectionDao, private val service: CivicsApiService) :
    ViewModel() {

    private val _isFollowed = MutableLiveData(false)
    val isFollowed: LiveData<Boolean>
        get() = _isFollowed

    private val _election = MutableLiveData<Election>()
    val election: LiveData<Election>
        get() = _election

    private val _voterInfoResponse = MutableLiveData<VoterInfoResponse>()
    val voterInfoResponse: LiveData<VoterInfoResponse>
        get() = _voterInfoResponse

    private val _electionAdministrationBody = MutableLiveData<AdministrationBody>()
    val electionAdministrationBody: LiveData<AdministrationBody>
        get() = _electionAdministrationBody

    private val _url = MutableLiveData<String>()
    val url: LiveData<String>
        get() = _url

    val address: LiveData<String> = Transformations.map(_voterInfoResponse) {
        it.state?.first()?.electionAdministrationBody?.correspondenceAddress?.toFormattedString()
            ?: ""
    }

    val loading: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchVoterInfo(electionId: Int, division: Division) {
        loading.value = true
        viewModelScope.launch {
            try {
                val election = database.getElection(electionId)
                _isFollowed.value = election != null

                _voterInfoResponse.value =
                    service.getVoterInfo(electionId, "${division.state},${division.country}")

                _election.value = _voterInfoResponse.value?.election
                _electionAdministrationBody.value =
                    voterInfoResponse.value?.state?.first()?.electionAdministrationBody
                loading.value = false
            } catch (e: Exception) {
                Timber.e("Error fetching voter info: ${e.message}")
                loading.value = false
            }
        }
    }

    fun navigateToUrl(url: String) {
        _url.value = url
    }

    fun navigateToUrlCompleted() {
        _url.value = null
    }

    fun toggleElectionFollow() {
        viewModelScope.launch {
            _election.value?.let {
                if (_isFollowed.value == true) {
                    database.removeElection(it)
                    _isFollowed.value = false
                } else {
                    database.insertElection(it)
                    _isFollowed.value = true
                }
            }
        }
    }
}