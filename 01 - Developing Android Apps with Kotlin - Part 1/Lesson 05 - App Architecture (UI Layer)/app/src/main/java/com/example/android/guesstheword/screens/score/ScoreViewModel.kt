package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {

    // The current score with Kotlin backing property (one mutable private internal only variable,
    // and one public LiveData [<-the important part] variable that is read only)
    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // Flag/event when play game again event with Kotlin backing property (one mutable private internal only variable,
    // and one public LiveData [<-the important part] variable that is read only)
    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    init {
        Log.i("ScoreViewModel", "Final score is $finalScore")
        _score.value = finalScore
    }

    /**
     * Play game again
     */
    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    /**
     * Play game again event reset
     */
    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }
}