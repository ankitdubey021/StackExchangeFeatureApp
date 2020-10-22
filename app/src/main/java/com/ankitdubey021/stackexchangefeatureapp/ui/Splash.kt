package com.ankitdubey021.stackexchangefeatureapp.ui

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.ankitdubey021.stackexchangefeatureapp.R
import com.ankitdubey021.stackexchangefeatureapp.databinding.ActivitySplashBinding
import com.ankitdubey021.stackexchangefeatureapp.extensions.launchActivity
import com.ankitdubey021.stackexchangefeatureapp.ui.search.Search
import kotlinx.coroutines.delay

class Splash : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableFullScreenMode()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)

        lifecycleScope.launchWhenResumed {
            delay(2000)
            binding.motionLayout.transitionToEnd()
        }

        setMotionAnimationEndListener()
    }

    private fun setMotionAnimationEndListener() {
        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                proceedToHome()
            }
        })
    }

    private fun proceedToHome() {
        launchActivity<Home> { }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    private fun enableFullScreenMode() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}