package com.example.brainbusters

object Routes {
    // Rotte senza parametri
    val homeScreen = "homeScreen"
    val loginScreen = "loginScreen"
    val registerStepOne = "registerStepOne"
    val registerStepTwo = "registerStepTwo"
    val settings = "settings"
    val profile = "profile"
    val scoreboard = "scoreboard"
    val notifications = "notifications"

    // Rotte con parametri
    fun quizScreen(quizId: Int, quizTitle: String) = "quizScreen/$quizId/$quizTitle"
    fun scoreScreen(score: Int, quizTitle: String) = "scoreScreen/$score/$quizTitle"
}