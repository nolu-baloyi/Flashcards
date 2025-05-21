package com.example.flashcards

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val questions = arrayOf(
        "Nelson Mandela was the first black president of South Africa.",
        "Apartheid ended in 1990.",
        "The Anglo-Zulu War occurred in the 18th century.",
        "South Africa became a republic in 1961.",
        "Steve Biko founded the African National Congress."
    )

    private val answers = arrayOf(true, false, false, true, false)

    private var currentIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showWelcomeScreen()
    }

    private fun showWelcomeScreen() {
        setContentView(R.layout.activity_welcome)

        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            currentIndex = 0
            score = 0
            showQuestionScreen()
        }
    }

    private fun showQuestionScreen() {
        setContentView(R.layout.activity_question)

        val questionText = findViewById<TextView>(R.id.questionText)
        val trueButton = findViewById<Button>(R.id.trueButton)
        val falseButton = findViewById<Button>(R.id.falseButton)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val feedbackText = findViewById<TextView>(R.id.feedbackText)

        questionText.text = questions[currentIndex]

        var answered = false

        fun checkAnswer(userAnswer: Boolean) {
            if (answered) return
            val correct = answers[currentIndex]
            feedbackText.text = if (userAnswer == correct) {
                score++
                "Correct!"
            } else {
                "Incorrect!"
            }
            answered = true
        }

        trueButton.setOnClickListener { checkAnswer(true) }
        falseButton.setOnClickListener { checkAnswer(false) }

        nextButton.setOnClickListener {
            currentIndex++
            if (currentIndex < questions.size) {
                showQuestionScreen()
            } else {
                showScoreScreen()
            }
        }
    }

    private fun showScoreScreen() {
        setContentView(R.layout.activity_score)

        val scoreText = findViewById<TextView>(R.id.scoreText)
        val reviewText = findViewById<TextView>(R.id.reviewText)
        val exitButton = findViewById<Button>(R.id.exitButton)
        val reviewButton = findViewById<Button>(R.id.reviewButton)

        val feedback = if (score >= 3) "Great job!" else "Keep practicing!"
        scoreText.text = "You scored $score out of ${questions.size}.\n$feedback"

        reviewButton.setOnClickListener {
            val review = StringBuilder()
            for (i in questions.indices) {
                review.append("${i + 1}. ${questions[i]} - Answer: ${if (answers[i]) "True" else "False"}\n")
            }
            reviewText.text = review.toString()
        }

        exitButton.setOnClickListener {
            finishAffinity()
        }
    }
}