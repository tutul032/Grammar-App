package com.example.makedogame

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


var qNum = 0 // change the question numbers
var db_ques = 0
var score = 0 // stores individual team/group score
private lateinit var database : DatabaseReference
private const val REQUEST_CODE_SPEECH_INPUT = 1
var feedback = ""
var QueShuffle = (1..totalQuesNum).toMutableList()

private var timeLeftInMillis = 0L
var countDownTimer: CountDownTimer? = null
private var timerIsRunning = false
private var remainingTimeInMillis = examTime.toLong()
var warning = 0
var warningSpeech = false


open class QuestionActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // making full screen application
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_exam)
        changeQuestion()

    }

    fun exit(view: View){
        database.child("Users").child(personName).child("score").setValue(score)
        database.child("Users").child(personName).child("rating").setValue(feedback)
        talk.speak("By bye $personName see you soon", TextToSpeech.QUEUE_FLUSH, null)
        QueShuffle = QueShuffle.shuffled() as MutableList<Int> // shuffling the question number
        score = 0
        personName = ""
        feedback = ""
        db_ques = 0
        qNum = 0
        warningSpeech = false
        mistakes = 0
        practiseQues = 1
        byeAnim()

    }

    fun startOrStopTimer() {
        val value:TextView = findViewById(R.id.textView12)
        if (!timerIsRunning) {
            countDownTimer?.cancel()
            countDownTimer = object : CountDownTimer(remainingTimeInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    remainingTimeInMillis = millisUntilFinished
                    timeLeftInMillis = millisUntilFinished
                    timerIsRunning = true
                    value.text = remainingTimeInMillis.convertToTimeFormat()

                    if (warning == 5 && qNum <= quizQuesNum && !warningSpeech){
                        talk.speak("5 seconds left", TextToSpeech.QUEUE_ADD, null)
                    }
                    if (warning == 1 && qNum < quizQuesNum){
                        changeQuestion()
                        stopOrResetTimer()
                        startOrStopTimer()
                    }

                }
                override fun onFinish() {
                    timerIsRunning = false
                    remainingTimeInMillis = 0L
                    timeLeftInMillis = 0L
                    value.text = "Finished"
                    if (!warningSpeech)
                    {
                        talk.speak("you have finished the test, " +
                                "please press the next button to see your score", TextToSpeech.QUEUE_FLUSH, null)
                        // Disabling button press on finish
                        val ansBtn = mutableListOf<Button>(
                            findViewById(R.id.button4), findViewById(R.id.button5),
                            findViewById(R.id.button6), findViewById(R.id.button7)
                        )
                        ansBtn[0].isEnabled = false
                        ansBtn[1].isEnabled = false
                        ansBtn[2].isEnabled = false
                        ansBtn[3].isEnabled = false
                    }


                }
            }.start()
        } else {
            countDownTimer?.cancel()
            timerIsRunning = false
        }
    }

    private fun stopOrResetTimer() {
        countDownTimer?.cancel()
        timerIsRunning = false
        timeLeftInMillis = examTime.toLong() // time for a question
        remainingTimeInMillis = examTime.toLong() // time for a question
        val value:TextView = findViewById(R.id.textView12)
        value.text = remainingTimeInMillis.convertToTimeFormat()
    }

    private fun Long.convertToTimeFormat(): String {
        val minutes = (this / 1000).toInt() / 60
        val seconds = (this / 1000).toInt() % 60
        warning = seconds

        return java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    private fun examResult(){
        setContentView(R.layout.activity_result)
        warningSpeech = true
        val playScore:TextView = findViewById(R.id.textView_score)
        val playerName:TextView = findViewById(R.id.textView8)
        val rateView:TextView = findViewById(R.id.textViewRate)
        val ratingStar: RatingBar = findViewById(R.id.ratingBar)
        ratingStar.rating = 0f
        ratingStar.stepSize = 1f
        ratingStar.numStars = 5
        ratingStar.setOnRatingBarChangeListener { _, rating, _ ->
            rateView.text = "${rating.toInt()} / 5"
            feedback = rating.toString()
            talk.speak("Thanks for your rating", TextToSpeech.QUEUE_FLUSH, null)
        }
        playScore.text = score.toString()
        playerName.text = personName
        talk.speak("Congratulations $personName you have finished the game and your score is $score ," +
                "It was great to play the game with you and I really enjoyed myself, Have a great day", TextToSpeech.QUEUE_FLUSH, null)

    }

    fun nextExamQuestion(view: View){
        if (qNum < quizQuesNum){
            db_ques++
            changeQuestion()
            stopOrResetTimer()
            startOrStopTimer()
        }
        else{
            examResult()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun changeQuestion(){
        // This function runs the "questions()" function every given milli seconds
        stopOrResetTimer()
        setContentView(R.layout.activity_exam)
        startOrStopTimer()
        val quesView: TextView = findViewById(R.id.quesViewPractise)
        val totalQuesView: TextView = findViewById(R.id.textView2)
        val quesNum1View: TextView = findViewById(R.id.quesNumPrac)
        //val ques = QueShuffle[db_ques] // read ques from the number 1
        // accessing database for the questions and answers
        database = FirebaseDatabase.getInstance().getReference("MakeDo")
        database.child(getData).child(QueShuffle[db_ques].toString()).get().addOnSuccessListener {

            val answerA = it.child("A").value // reads correct answers
            val answerB = it.child("B").value
            val answerC = it.child("C").value
            val answerD = it.child("D").value
            val question = it.child("Q").value  // reads questions
            quesView.text = question.toString()
            // replacing __ to a blank word
            talk.speak(question.toString().replace("__", "blank"),
                TextToSpeech.QUEUE_FLUSH, null)
            quesNum1View.text = qNum.toString()
            totalQuesView.text = quizQuesNum.toString()

            // answer button names
            val btnName = mutableListOf(answerA.toString(), answerB.toString(),
                answerC.toString(), answerD.toString()).shuffled()

            // list of answer buttons
            val ansBtn = mutableListOf<Button>(
                findViewById(R.id.button4), findViewById(R.id.button5),
                findViewById(R.id.button6), findViewById(R.id.button7)
            )

            // setting the text of answer button
            for (i in ansBtn.indices) {
                ansBtn[i].text = btnName[i]
            }

            val inAns = btnName.indexOf(answerA) // gives index of "make" from ans button
            // display response of an answer
            val ansView: TextView = findViewById(R.id.textView19)

            ansBtn[0].setOnClickListener {

                if (answerA == btnName[0]) {
                    ansView.text = "Correct answer"
                    // text set to green color
                    ansBtn[0].setBackgroundColor(Color.GREEN)
                    ansBtn[0].isEnabled = false
                    ansBtn[1].isEnabled = false
                    ansBtn[2].isEnabled = false
                    ansBtn[3].isEnabled = false
                    score += 5
                    talk.speak("Correct answer, well done $personName", TextToSpeech.QUEUE_FLUSH, null)
                    upAnim()
                }
                else {
                    ansView.text = "Wrong answer"
                    // text set to red color
                    ansBtn[0].setBackgroundColor(Color.RED)
                    ansBtn[inAns].setBackgroundColor(Color.GREEN)
                    ansBtn[0].isEnabled = false
                    ansBtn[1].isEnabled = false
                    ansBtn[2].isEnabled = false
                    ansBtn[3].isEnabled = false
                    talk.speak(" Not correct, try next one", TextToSpeech.QUEUE_FLUSH, null)
                    downAnim()

                }

            }

            ansBtn[1].setOnClickListener {

                if (answerA == btnName[1]) {
                    ansView.text = "Correct answer"
                    // text set to green color
                    ansBtn[1].setBackgroundColor(Color.GREEN)
                    ansBtn[0].isEnabled = false
                    ansBtn[1].isEnabled = false
                    ansBtn[2].isEnabled = false
                    ansBtn[3].isEnabled = false
                    score += 5
                    talk.speak("Correct answer, very good", TextToSpeech.QUEUE_FLUSH, null)
                    upAnim()
                }
                else {
                    ansView.text = "Wrong answer"
                    // text set to red color
                    ansBtn[1].setBackgroundColor(Color.RED)
                    ansBtn[inAns].setBackgroundColor(Color.GREEN)
                    ansBtn[0].isEnabled = false
                    ansBtn[1].isEnabled = false
                    ansBtn[2].isEnabled = false
                    ansBtn[3].isEnabled = false
                    talk.speak("Not Correct but quite close ", TextToSpeech.QUEUE_FLUSH, null)
                    downAnim()

                }

            }

            ansBtn[2].setOnClickListener {
                //ansBtn[i].setTextColor(0xFFFFFF)


                if (answerA == btnName[2]) {
                    ansView.text = "Correct answer"
                    // text set to green color
                    ansBtn[2].setBackgroundColor(Color.GREEN)
                    ansBtn[0].isEnabled = false
                    ansBtn[1].isEnabled = false
                    ansBtn[2].isEnabled = false
                    ansBtn[3].isEnabled = false
                    score += 5
                    talk.speak("Correct answer, you are doing pretty well", TextToSpeech.QUEUE_FLUSH, null)
                    upAnim()
                }
                else {
                    ansView.text = "Wrong answer"
                    // text set to red color
                    ansBtn[2].setBackgroundColor(Color.RED)
                    ansBtn[inAns].setBackgroundColor(Color.GREEN)
                    ansBtn[0].isEnabled = false
                    ansBtn[1].isEnabled = false
                    ansBtn[2].isEnabled = false
                    ansBtn[3].isEnabled = false
                    talk.speak("Not correct, be careful $personName", TextToSpeech.QUEUE_FLUSH, null)
                    downAnim()

                }
            }

            ansBtn[3].setOnClickListener {
                //ansBtn[i].setTextColor(0xFFFFFF)

                if (answerA == btnName[3]) {
                    ansView.text = "Correct answer"
                    // text set to green color
                    ansBtn[3].setBackgroundColor(Color.GREEN)
                    ansBtn[0].isEnabled = false
                    ansBtn[1].isEnabled = false
                    ansBtn[2].isEnabled = false
                    ansBtn[3].isEnabled = false
                    score += 5
                    talk.speak("Wonderful, you’re doing very well!", TextToSpeech.QUEUE_FLUSH, null)
                    upAnim()
                }
                else {
                    ansView.text = "Wrong answer"
                    // text set to red color
                    ansBtn[3].setBackgroundColor(Color.RED)
                    ansBtn[inAns].setBackgroundColor(Color.GREEN)
                    ansBtn[0].isEnabled = false
                    ansBtn[1].isEnabled = false
                    ansBtn[2].isEnabled = false
                    ansBtn[3].isEnabled = false
                    talk.speak("It’s OK to make mistakes! That’s how you learn!", TextToSpeech.QUEUE_FLUSH, null)
                    downAnim()

                }

            }


        }.addOnFailureListener{
            quesView.text = "Data Read Failed"
        }

        val scoreView:TextView = findViewById(R.id.textViewScore)
        scoreView.text = score.toString()
        qNum++
        db_ques++

    }

    private fun upAnim(){
        val intent = Intent(this, UpActivity::class.java)
        startOrStopTimer()
        Handler().postDelayed({
            startOrStopTimer()
        }, 5000)
        startActivity(intent)
    }
    private fun downAnim(){
        val intent = Intent(this, DownActivity::class.java)
        startOrStopTimer()
        Handler().postDelayed({
            startOrStopTimer()
        }, 5000)
        startActivity(intent)
    }

    private fun byeAnim(){
        val intent = Intent(this, ByeActivity::class.java)
        startActivity(intent)
    }

}