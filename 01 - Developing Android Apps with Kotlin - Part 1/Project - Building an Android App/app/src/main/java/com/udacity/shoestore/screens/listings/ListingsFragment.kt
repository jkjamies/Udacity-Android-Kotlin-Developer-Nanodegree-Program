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
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ListingsFragmentBinding
import com.udacity.shoestore.databinding.ShoeListItemBinding
import timber.log.Timber

/**
 * Fragment for the starting or title screen of the app
 */
class ListingsFragment : Fragment() {

    private val viewModel: ShoeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Timber.i("onCreateView called")

        // Inflate the layout for this fragment
        val binding: ListingsFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.listings_fragment, container, false
        )

        // Ensure ActionBar showing for listing and details flow
        (activity as AppCompatActivity).supportActionBar?.show()

        // Observe the shoe list
        viewModel.shoeList.observe(viewLifecycleOwner, Observer { shoes ->
            shoes.forEach {
                val newBindingView: ShoeListItemBinding =
                    DataBindingUtil.inflate(inflater, R.layout.shoe_list_item, container, false)
                newBindingView.shoeModel = it
                binding.listingsLinearLayout.addView(newBindingView.root)
            }
        })

        // listings details FAB goes to the details fragment
        binding.listingsDetailsFAB.setOnClickListener {
            Timber.i("listingsDetailsFAB onClick called")
            viewModel.init()
            findNavController().navigate(ListingsFragmentDirections.actionListingsFragmentToDetailsFragment())
        }

        // Set fragment has options menu
        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * Inflate the menu for logout to show up only on this fragment
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Timber.i("onCreateOptionsMenu called")
        inflater.inflate(R.menu.menu, menu)
    }

    /**
     * Return true if NavigationUI.onNavDestinationSelected returns true, else return super.onOptionsItemSelected.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.i("onOptionsItemSelected - Logout - called")
        viewModel.clearAllShoes()
        // Using a trick where logout is using the id of loginFragment (the idea is meh, but it's a small project)
        // it's also allowing for backwards navigation from login after logout, but again, no true authentication happening here
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }
}
