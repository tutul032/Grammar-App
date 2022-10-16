package com.example.makedogame

import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder

class DownActivity : AppCompatActivity(), RobotLifecycleCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        QiSDK.register(this, this)
        // making full screen application
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_down)
        Handler().postDelayed({
            finish()
        }, 5000)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        // animation for greetings
        val animationG_1: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.sad_a003).build() // Set the animation resource.
        val animationG_2: Animation = AnimationBuilder.with(qiContext)
            .withResources(R.raw.dizzy_a002).build()
        val animationG_3: Animation = AnimationBuilder.with(qiContext)
            .withResources(R.raw.furious_a001).build()

        val randomNumber = listOf(animationG_1, animationG_2, animationG_3)
        val randomAnim = randomNumber.random()

        // Build the action.
        val animateG: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(randomAnim)
            .build()
        animateG.async().run()

    }

    override fun onRobotFocusLost() {

    }

    override fun onRobotFocusRefused(reason: String?) {

    }
}