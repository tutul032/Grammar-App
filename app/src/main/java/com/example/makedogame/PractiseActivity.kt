package com.example.makedogame

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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

private lateinit var databasePractise : DatabaseReference
var practiseQues = 1
var mistakes = 0


class PractiseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // making full screen application
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_practise)
        practiseQuestions()

    }
    @SuppressLint("SetTextI18n")
    private fun practiseResult(){
        setContentView(R.layout.activity_resultpractise)
        val practiseMistake:TextView = findViewById(R.id.textView_mistake)
        practiseMistake.text = mistakes.toString()
        val name:TextView = findViewById(R.id.textView_name)
        name.text = personName
        val rateViewP:TextView = findViewById(R.id.textView_ratep)
        val ratingStarP: RatingBar = findViewById(R.id.ratingBarMst)
        ratingStarP.rating = 0f
        ratingStarP.stepSize = 1f
        ratingStarP.numStars = 5
        ratingStarP.setOnRatingBarChangeListener { _, rating, _ ->
            rateViewP.text = "${rating.toInt()} / 5"
            feedback = rating.toString()
            talk.speak("Thanks for your rating", TextToSpeech.QUEUE_FLUSH, null)
        }
    }


    fun nextPractiseQuestion(view: View){
        if (practiseQues < 3 /*totalQuesNum*/){
            practiseQues++
            practiseQuestions()
        }
        else{
            practiseResult()
        }

    }

    private fun practiseQuestions(){
        setContentView(R.layout.activity_practise)
        val quesView: TextView = findViewById(R.id.quesViewPractise)
        val quesNum1View: TextView = findViewById(R.id.quesNumPrac)
        databasePractise = FirebaseDatabase.getInstance().getReference("MakeDo")
        databasePractise.child(getData).child(practiseQues.toString()).get().addOnSuccessListener {

            val answerA = it.child("A").value // reads correct answers
            val answerB = it.child("B").value
            val answerC = it.child("C").value
            val answerD = it.child("D").value
            val question = it.child("Q").value  // reads questions
            quesView.text = question.toString()
            // replacing __ to a blank
            talk.speak(question.toString().replace("__", "blank"),
                TextToSpeech.QUEUE_FLUSH, null)
            quesNum1View.text = practiseQues.toString()

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
                    mistakes++
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
                    mistakes++
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
                    mistakes++
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
                    mistakes++
                    downAnim()

                }

            }

        }.addOnFailureListener{
            quesView.text = "Data Read Failed"
        }


    }

    fun exitPractise(view: View){
        databasePractise.child("Users").child(personName).child("mistake").setValue(mistakes)
        databasePractise.child("Users").child(personName).child("rating").setValue(feedback)
        talk.speak("By bye $personName see you soon", TextToSpeech.QUEUE_FLUSH, null)
        practiseQues = 1
        mistakes = 0
        personName = ""
        feedback = ""
        score = 0
        db_ques = 0
        qNum = 0
        warningSpeech = false
        byeAnim()

    }
    fun homePractise(view: View){
        practiseQues = 1
        mistakes = 0
        personName = ""
        feedback = ""
        score = 0
        db_ques = 0
        qNum = 0
        warningSpeech = false
        talk.speak("You are closing your practice session ", TextToSpeech.QUEUE_FLUSH, null)
        byeAnim()


    }

    private fun upAnim(){
        val intent = Intent(this, UpActivity::class.java)
        startActivity(intent)
    }
    private fun downAnim(){
        val intent = Intent(this, DownActivity::class.java)
        startActivity(intent)
    }
    private fun byeAnim(){
        val intent = Intent(this, ByeActivity::class.java)
        startActivity(intent)
    }
}
