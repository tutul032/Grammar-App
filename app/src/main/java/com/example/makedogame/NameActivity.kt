package com.example.makedogame

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*

var personName = ""
// variable for the speech-to-text
private const val REQUEST_CODE_SPEECH_INPUT = 1
var getData = ""

class NameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // making full screen application
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_name)
        submit()
    }

    fun submit(){
        /*
        This function takes the user name as an input and send it to the database.
         */
        setContentView(R.layout.activity_name)
        speech()
        val button: Button = findViewById(R.id.button_submit)
        val editTextL: EditText = findViewById(R.id.editTextL)

        button.setOnClickListener {
            personName = editTextL.text.toString()
            talk.speak("Hello $personName, Thank you very much." +
                    "If you would like to take the test please press test button, or, " +
                    "if you want to practice, please press the practice button.", TextToSpeech.QUEUE_FLUSH, null)
            setContentView(R.layout.activity_subject)
            // delay for the finishing of speech
            //Handler().postDelayed({
                //voiceInput()
            //}, 11000)
        }

    }
    fun takeTest(view: View){
        setContentView(R.layout.activity_test_theme)
        talk.speak("Please select your test that you wish", TextToSpeech.QUEUE_FLUSH, null)
    }

    fun makeDo(view: View){
        getData = "MakeOrDo"
        val buttonExam: Button = findViewById(R.id.makedo)
        buttonExam.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. The quiz is going to be start within few seconds." +
                "You should be careful about the time limit, and pressing the correct answer." +
                "Quiz starts now. Good luck.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }, 13000)

    }
    fun makeDoExercise(view: View){
        getData = "MakeOrDo"
        val buttonPractice: Button = findViewById(R.id.makedo_practice)
        buttonPractice.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. You should press the next button for the next question." +
                "your practise session starts now.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, PractiseActivity::class.java)
            startActivity(intent)
        }, 8000)

    }

    fun presentSC(view: View){
        getData = "PresentSimple"
        val buttonExam: Button = findViewById(R.id.presentSC)
        buttonExam.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. The quiz is going to be start within few seconds." +
                "You should be careful about the time limit, and pressing the correct answer." +
                "Quiz starts now. Good luck.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }, 13000)

    }

    fun pastSCExcercise(view: View){
        getData = "PastSimpleCon"
        val buttonPractice: Button = findViewById(R.id.pastSC_practice)
        buttonPractice.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. You should press the next button for the next question." +
                "your practise session starts now.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, PractiseActivity::class.java)
            startActivity(intent)
        }, 8000)

    }

    fun pastSC(view: View){
        getData = "PastSimpleCon"
        val buttonExam: Button = findViewById(R.id.pastSC)
        buttonExam.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. The quiz is going to be start within few seconds." +
                "You should be careful about the time limit, and pressing the correct answer." +
                "Quiz starts now. Good luck.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }, 13000)

    }

    fun futureSCExcercise(view: View){
        getData = "FutureSimpleCon"
        val buttonPractice: Button = findViewById(R.id.futureSC_practice)
        buttonPractice.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. You should press the next button for the next question." +
                "your practise session starts now.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, PractiseActivity::class.java)
            startActivity(intent)
        }, 8000)

    }

    fun futureSC(view: View){
        getData = "FutureSimpleCon"
        val buttonExam: Button = findViewById(R.id.futureSC)
        buttonExam.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. The quiz is going to be start within few seconds." +
                "You should be careful about the time limit, and pressing the correct answer." +
                "Quiz starts now. Good luck.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }, 13000)

    }

    fun presentSCExcercise(view: View){
        getData = "PresentSimple"
        val buttonPractice: Button = findViewById(R.id.presentSC_practice)
        buttonPractice.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. You should press the next button for the next question." +
                "your practise session starts now.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, PractiseActivity::class.java)
            startActivity(intent)
        }, 8000)

    }

    fun presentPSC(view: View){
        getData = "PresentPerfect"
        val buttonExam: Button = findViewById(R.id.presentPSC)
        buttonExam.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. The quiz is going to be start within few seconds." +
                "You should be careful about the time limit, and pressing the correct answer." +
                "Quiz starts now. Good luck.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }, 13000)

    }

    fun presentPSCExercise(view: View){
        getData = "PresentPerfect"
        val buttonExam: Button = findViewById(R.id.presentPSC_practice)
        buttonExam.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. You should press the next button for the next question." +
                "your practise session starts now.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, PractiseActivity::class.java)
            startActivity(intent)
        }, 8000)

    }

    fun pastPSC(view: View){
        getData = "PastPerfect"
        val buttonExam: Button = findViewById(R.id.pastPSC)
        buttonExam.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. The quiz is going to be start within few seconds." +
                "You should be careful about the time limit, and pressing the correct answer." +
                "Quiz starts now. Good luck.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }, 13000)

    }

    fun pastPSCExercise(view: View){
        getData = "PastPerfect"
        val buttonExam: Button = findViewById(R.id.pastPSC_practice)
        buttonExam.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. You should press the next button for the next question." +
                "your practise session starts now.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, PractiseActivity::class.java)
            startActivity(intent)
        }, 8000)

    }

    fun futurePSC(view: View){
        getData = "FuturePerfect"
        val buttonExam: Button = findViewById(R.id.futurePSC)
        buttonExam.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. The quiz is going to be start within few seconds." +
                "You should be careful about the time limit, and pressing the correct answer." +
                "Quiz starts now. Good luck.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }, 13000)

    }

    fun futurePSCExercise(view: View){
        getData = "FuturePerfect"
        val buttonExam: Button = findViewById(R.id.futurePSC_practice)
        buttonExam.setBackgroundColor(Color.GREEN)
        talk.speak("Thank you very much. You should press the next button for the next question." +
                "your practise session starts now.", TextToSpeech.QUEUE_FLUSH, null)
        // delay for the finishing of speech
        Handler().postDelayed({
            val intent = Intent(this, PractiseActivity::class.java)
            startActivity(intent)
        }, 8000)

    }


    fun practise(view: View){
        setContentView(R.layout.activity_practise_theme)
        talk.speak("Please select your exercise that you wish", TextToSpeech.QUEUE_FLUSH, null)
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
                    //val buttonExam: Button = findViewById(R.id.button_exam)
                    //buttonExam.setBackgroundColor(Color.RED)
                    talk.speak("Thank you very much. The quiz is going to be start within few seconds." +
                            "You should be careful about the time limit, and pressing the correct answer." +
                            "Quiz starts now. Good luck.", TextToSpeech.QUEUE_FLUSH, null)
                    // delay for the finishing of speech
                    Handler().postDelayed({
                        val intent = Intent(this, QuestionActivity::class.java)
                        startActivity(intent)
                    }, 13000)

                }

                if (result == "practice"){
                    val buttonPractise: Button = findViewById(R.id.button_practise)
                    buttonPractise.setBackgroundColor(Color.RED)
                    talk.speak("Thank you very much. You should press the next button for the next question." +
                            "your practise session starts now.", TextToSpeech.QUEUE_FLUSH, null)
                    // delay for the finishing of speech
                    Handler().postDelayed({
                        val intent = Intent(this, PractiseActivity::class.java)
                        startActivity(intent)
                    }, 8000)

                }

                else{
                    // execute if the answer is no
                    talk.speak("Thank you very much for your interest. Have a great day.",
                        TextToSpeech.QUEUE_FLUSH, null)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

            }
        }
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
}