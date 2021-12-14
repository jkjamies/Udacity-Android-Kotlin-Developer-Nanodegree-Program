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
 * Copyright (c) 2020 Jason Jamieson
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

package com.udacity.shoestore.screens.listings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.DetailsFragmentBinding
import timber.log.Timber

/**
 * Fragment for the starting or title screen of the app
 */
class DetailsFragment : Fragment() {

    private val viewModel: ShoeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Timber.i("onCreateView called")

        // Inflate the layout for this fragment
        val binding: DetailsFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.details_fragment, container, false
        )

        // binding viewModel to layout - let binding know about viewModel using layout shoeViewModel variable
        binding.shoeViewModel = viewModel
        // sets lifecycle owner to this (fragment) to allow live data to automatically update data binding layouts
        binding.lifecycleOwner = this

        // If shoeDetailsCompleted, go back to listings screen and reset flag in viewModel
        viewModel.shoeDetailsCompleted.observe(
            viewLifecycleOwner,
            Observer { shoeDetailsCompleted ->
                Timber.i("shoeDetailsCompleted Observed")
                // If newShoeAdded is true, the shoe was added successfully, wait for it to flip to false to navigate
                if (shoeDetailsCompleted) {
                    Timber.i("shoeDetailsCompleted Observed true")
                    findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToListingsFragment())
                }
            })

        // When save is pressed save the shoe details
        binding.detailsShoeSaveMaterialButton.setOnClickListener {
            Timber.i("detailsShoeSaveMaterialButton onClick called")
            viewModel.addShoe()
        }
        return binding.root
    }

    /**
     * The flag for the completed details must be reset when the details fragment is destroyed so
     * it is not preserved when re-entering the details screen again.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetAddShoeComplete()
    }
}
