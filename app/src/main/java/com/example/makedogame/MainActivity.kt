package com.example.makedogame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

// variable for the text-to-speech
lateinit var talk:TextToSpeech
// variable for the speech-to-text
private const val REQUEST_CODE_SPEECH_INPUT = 1
private lateinit var databaseQ : DatabaseReference
var totalQuesNum = 0 // total number of question in the database
var quizQuesNum = 0 // Number of total quiz questions can be set from database
                    // that will be displayed on the screen
var examTime = 0

class MainActivity : AppCompatActivity(), RobotLifecycleCallbacks{

    override fun onCreate(savedInstanceState: Bundle?) {
        // this method calls the main layout with full screen
        super.onCreate(savedInstanceState)
        // making full screen application
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        // get the total number of questions from the database
        databaseQ = FirebaseDatabase.getInstance().getReference("MakeDo")
        databaseQ.child("data").get().addOnSuccessListener {

            val qNum = it.child("totalQ").value.toString()
            val quizQNum = it.child("quizQnum").value.toString()
            val examDuration = it.child("exam_time").value.toString()
            val app_name = it.child("app_name").value.toString()
            val about_app = it.child("about_app").value.toString()
            val appName: TextView = findViewById(R.id.textView_appName)
            val appAbout: TextView = findViewById(R.id.textView_appAbout)
            appName.text = app_name
            appAbout.text = about_app
            quizQuesNum = quizQNum.toInt()
            totalQuesNum = qNum.toInt()
            examTime = (examDuration.toInt() * 1000)
        }
        // registers robot
        QiSDK.register(this, this)

    }


    private fun speech(){
        // This function used for the text-to-speech implementation
        talk = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS){
                //if there is no error then set language
                talk.language = Locale.CANADA
                //talk.setPitch(0.95F)
            }
        })
    }

    private fun voiceInput(){
        // This function asks for the voice input
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to text")
        startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // this functions defines the task after receiving the voice input
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {

            if (resultCode == RESULT_OK && data != null) {
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                val result =  Objects.requireNonNull(res)[0]

                // executes if the answer is yes
                if (result == "yes"){
                    talk.speak("Please enter your name and press the submit button.", TextToSpeech.QUEUE_FLUSH, null)
                    val intent = Intent(this, NameActivity::class.java)
                    startActivity(intent)

                }

                else{
                    // execute if the answer is no
                    setContentView(R.layout.activity_resultpractise)
                    talk.speak("Thanks for chatting with me. See you later. bye bye.",
                        TextToSpeech.QUEUE_FLUSH, null)
                }

            }
        }
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {

        // This function used to start the game with a greetings to the user
        speech()
        val start:Button = findViewById(R.id.button_play)
        talk.stop()
        // animation for greetings
        val animation: Animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
            .withResources(R.raw.hello_a005).build() // Set the animation resource.

        // Build the action.
        val animate: Animate = AnimateBuilder.with(qiContext)
            .withAnimation(animation)
            .build()

        start.setOnClickListener {

            // animation starts with speech
            animate.async().run()

            talk.speak("Hello. My name is Pepper and I am very pleased to meet you," /*+
                    "May I know your name? " + "Please say yes or no"*/,
                TextToSpeech.QUEUE_FLUSH, null)

            // delay for the finishing of speech
            Handler().postDelayed({
                //voiceInput()
                talk.speak("May I know your name? Please type your name and press the submit button,", TextToSpeech.QUEUE_FLUSH, null)
                val intent = Intent(this, NameActivity::class.java)
                startActivity(intent)
            }, 4000)
        }

    }

    override fun onRobotFocusLost() {
        // Remove on started listeners from the animate action.
    }

    override fun onRobotFocusRefused(reason: String?) {

        // nothing

    }


}