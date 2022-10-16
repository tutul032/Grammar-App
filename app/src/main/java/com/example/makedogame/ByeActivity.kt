package com.example.makedogame

import android.content.Intent
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

class ByeActivity : AppCompatActivity(), RobotLifecycleCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        // making full screen application
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_bye)
        Handler().postDelayed({
            //finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 5000)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        // animation for greetings
        val animationG: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.hello_a001).build() // Set the animation resource.

        // Build the action.
        val animateG: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animationG)
            .build()
        animateG.async().run()

    }

    override fun onRobotFocusLost() {

    }

    override fun onRobotFocusRefused(reason: String?) {

    }
}