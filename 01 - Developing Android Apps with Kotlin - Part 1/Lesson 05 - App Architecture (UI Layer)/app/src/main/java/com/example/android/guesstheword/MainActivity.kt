/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//https://github.com/android/architecture-samples
//https://medium.com/upday-devs/android-architecture-patterns-part-2-model-view-presenter-8a6faaae14a5
//https://developer.android.com/jetpack/docs/guide
//https://developer.android.com/topic/libraries/architecture/saving-states
//https://kotlinlang.org/docs/reference/properties.html#backing-properties
//https://developer.android.com/kotlin/style-guide#backing-properties
//https://developer.android.com/topic/libraries/architecture/livedata#observe_livedata_objects
//https://developer.android.com/topic/libraries/data-binding/expressions?authuser=1
//https://developer.android.com/topic/libraries/data-binding/architecture#viewmodel
//https://developer.android.com/reference/kotlin/androidx/lifecycle/Transformations#map(androidx.lifecycle.LiveData,%20androidx.arch.core.util.Function)
//https://developer.android.com/reference/kotlin/androidx/lifecycle/Transformations#switchMap(androidx.lifecycle.LiveData,%20androidx.arch.core.util.Function)
//https://developer.android.com/reference/kotlin/androidx/lifecycle/MediatorLiveData

/**
 * Creates an Activity that hosts all of the fragments in the app
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

}
