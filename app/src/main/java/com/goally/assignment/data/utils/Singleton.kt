package com.goally.assignment.data.utils


object Singleton {
    // Database Name
    const val DB: String = "Trivia_Upgrade"

    //Tables
    const val QUESTION_ATTEMPTED: String = "Question_Attempted"
    const val USERS: String = "Users"
    const val QUICK_PLAY: String = "Quick_Play"

    //Columns

    // Table Questions && Quick Play Questions
    const val Q_ID: String = "Q_ID"
    const val U_ID: String = "U_ID"
    const val Q_Q: String = "Question"
    const val Q_C_ANS: String = "Correct_Ans"
    const val Q_AT_OPT: String = "Attempted_Opt"
    const val Q_CATEGORY: String = "Category"
    const val Q_STATE: String = "State"
    const val Q_DIFFICULTY: String = "Difficulty"
    const val Q_POINT: String = "Points"

    // Table Users
    const val U_NAME: String = "U_Name"
    const val U_EMAIL: String = "U_Email"
    const val U_PHONE: String = "U_Phone"
    const val U_QUICK_POINTS: String = "U_Quick_Points"
    const val U_POINTS: String = "U_Points"
    const val U_QUICK_ATTEMPTS: String = "U_Quick_Attempts"
    const val U_PASSWORD: String = "U_Password"
    const val U_ATTEMPTS: String = "U_Attempts"

    // Objects
    var userID: Int = 0
    var url = "https://opentdb.com/api.php?amount=50"
}