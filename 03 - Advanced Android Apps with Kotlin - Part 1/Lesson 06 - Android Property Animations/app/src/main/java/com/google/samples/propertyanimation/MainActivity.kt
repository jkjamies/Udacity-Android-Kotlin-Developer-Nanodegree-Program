/*
 * Copyright (C) 2019 The Android Open Source Project
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

package com.google.samples.propertyanimation

import android.animation.*
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

//https://developer.android.com/reference/android/animation/ObjectAnimator
//https://developer.android.com/reference/android/animation/AnimatorSet
//https://developer.android.com/reference/android/view/animation/LinearInterpolator.html
//https://developer.android.com/reference/android/animation/PropertyValuesHolder

class MainActivity : AppCompatActivity() {

    lateinit var star: ImageView
    lateinit var rotateButton: Button
    lateinit var translateButton: Button
    lateinit var scaleButton: Button
    lateinit var fadeButton: Button
    lateinit var colorizeButton: Button
    lateinit var showerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        star = findViewById(R.id.star)
        rotateButton = findViewById<Button>(R.id.rotateButton)
        translateButton = findViewById<Button>(R.id.translateButton)
        scaleButton = findViewById<Button>(R.id.scaleButton)
        fadeButton = findViewById<Button>(R.id.fadeButton)
        colorizeButton = findViewById<Button>(R.id.colorizeButton)
        showerButton = findViewById<Button>(R.id.showerButton)

        rotateButton.setOnClickListener {
            rotater()
        }

        translateButton.setOnClickListener {
            translater()
        }

        scaleButton.setOnClickListener {
            scaler()
        }

        fadeButton.setOnClickListener {
            fader()
        }

        colorizeButton.setOnClickListener {
            colorizer()
        }

        showerButton.setOnClickListener {
            shower()
        }
    }

//    Note: Repetition is a way of telling animations to do the same task again and again. You can
//    specify how many times to repeat (or just tell it to run infinitely). You can also specify the
//    repetition behavior, either REVERSE (for reversing the direction every time it repeats) or
//    RESTART (for animating from the original start value to the original end value, thus repeating
//    in the same direction every time).

    private fun rotater() {
        val animator = ObjectAnimator.ofFloat(star, View.ROTATION, -360f, 0f)
        // default for all animation times is 300ms, set to 1000ms
        animator.duration = 1000
        // to prevent jank during quick successive presses (pressing while already animating),
        // disable the button while animating to prevent restarting the animation
        animator.disableViewDuringAnimation(rotateButton)
        animator.start()
    }

//    Note: Repetition is a way of telling animations to do the same task again and again. You can
//    specify how many times to repeat (or just tell it to run infinitely). You can also specify the
//    repetition behavior, either REVERSE (for reversing the direction every time it repeats) or
//    RESTART (for animating from the original start value to the original end value, thus repeating
//    in the same direction every time).

    private fun translater() {
        val animator = ObjectAnimator.ofFloat(star, View.TRANSLATION_X,
            200f)
        // repeat once to return back to original location, reverse it (to do so)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        // to prevent jank during quick successive presses (pressing while already animating),
        // disable the button while animating to prevent restarting the animation
        animator.disableViewDuringAnimation(translateButton)
        animator.start()
    }

//    Note: There is no single property that scales in both the x and y dimensions, so animations
//    that scale in both x and y need to animate both of these separate properties in parallel.

//    Note: A PropertyValuesHolder holds information about a single property, along the with the
//    values that that property animates between. An ObjectAnimator can hold multiple
//    PropertyValuesHolder objects, which will all animate together, in parallel, when the
//    ObjectAnimator starts. The target that these PropertyValueHolder objects animate is specified
//    by the ObjectAnimator. The ideal use case for ObjectAnimators which use PropertyValuesHolder
//    parameters is when you need to animate several properties on the same object in parallel.

//    Note: These PropertyValuesHolder objects look similar to the ObjectAnimators you created
//    previously, but they only hold the property and value information for the animation, not the
//    target. So even if you wanted to run these as an animation, you could not; there’s not enough
//    information because you haven’t told the system which target object(s) to animate.

    private fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            star, scaleX, scaleY)
        // repeat once to scale back down to normal size, then reverse it (to do so)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        // to prevent jank during quick successive presses (pressing while already animating),
        // disable the button while animating to prevent restarting the animation
        animator.disableViewDuringAnimation(scaleButton)
        animator.start()
    }

//    Note: Fading items can be a very useful way to transition them into or out of a UI. For example,
//    when removing an item from a list, you might fade out the contents first, before closing the gap
//    that it leaves. Or if new information appears in a UI, you might fade it in. This effect not
//    only avoids discontinuous motion, with UI elements snapping in and out in front of the user, but
//    it helps alert the user that there is a change happening, instead of just removing or adding
//    items and making them guess what just happened.

//    Note: “alpha” is a term generally used, especially in computer graphics, to denote the amount
//    of opacity in an object. A value of 0 indicates that the object is completely transparent, and
//    a value of 1 indicates that the object is completely opaque. View objects have a default value
//    of 1. Animations that fade views in or out animate the alpha value between 0 and 1.

    private fun fader() {
        val animator = ObjectAnimator.ofFloat(star, View.ALPHA, 0f)
        // repeat once to fade back in to original state, then reverse it (to do so)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(fadeButton)
        animator.start()
    }

//    Note: The ofArgb() method is the reason that this app builds against minSdk 21; the rest of
//    the functionality of the app can be run on earlier SDKs, but ofArgb() was introduced in the
//    Lollipop release. It is also possible to animate color values on earlier releases, involving
//    TypeEvaluators, and the use of ArgbEvaluator specifically. We used ofArgb() in this lesson
//    instead for simplicity.

    private fun colorizer() {
        val animator = ObjectAnimator.ofArgb(star.parent,
            "backgroundColor", Color.BLACK, Color.RED)
        // default for all animation times is 300ms, set to 500ms
        animator.duration = 500
        // repeat once to color back to original color, then reverse it (to do so)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(colorizeButton)
        animator.start()
    }

//    Note: There are several interpolators in the Android system, some more powerful and flexible
//    than others, such as PathInterpolator. You can experiment with them to see which ones you
//    like for your UI animations.

//    Remember: It should seem obvious that when a view is not needed anymore, it should be removed.
//    But complex animations can make us forget about the simple things. Use animation listeners to
//    handle important tasks for bringing views onto or off of the screen, like we’re doing here, to
//    remove a view when the animation to move it off the screen is complete.

    private fun shower() {

        // Create a new star view in a random X position above the container.
        // Make it rotateButton about its center as it falls to the bottom.

        // Local variables we'll need in the code below
        val container = star.parent as ViewGroup
        val containerW = container.width
        val containerH = container.height
        var starW: Float = star.width.toFloat()
        var starH: Float = star.height.toFloat()

        // Create the new star (an ImageView holding our drawable) and add it to the container
        val newStar = AppCompatImageView(this)
        newStar.setImageResource(R.drawable.ic_star)
        newStar.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT)
        container.addView(newStar)

        // Scale the view randomly between 10-160% of its default size
        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
        newStar.scaleY = newStar.scaleX
        starW *= newStar.scaleX
        starH *= newStar.scaleY

        // Position the view at a random place between the left and right edges of the container
        newStar.translationX = Math.random().toFloat() * containerW - starW / 2

        // Create an animator that moves the view from a starting position right about the container
        // to an ending position right below the container. Set an accelerate interpolator to give
        // it a gravity/falling feel
        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)

        // Create an animator to rotateButton the view around its center up to three times
        val rotator = ObjectAnimator.ofFloat(newStar, View.ROTATION,
            (Math.random() * 1080).toFloat())
        rotator.interpolator = LinearInterpolator()

        // Use an AnimatorSet to play the falling and rotating animators in parallel for a duration
        // of a half-second to two seconds
        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 1500 + 500).toLong()

        // When the animation is done, remove the created view from the container
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                container.removeView(newStar)
            }
        })

        // Start the animation
        set.start()
    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        // This extension method listens for start/end events on an animation and disables
        // the given view for the entirety of that animation.
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

}
