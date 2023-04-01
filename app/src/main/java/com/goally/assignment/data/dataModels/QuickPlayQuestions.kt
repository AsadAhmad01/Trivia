package com.goally.assignment.data.dataModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.goally.assignment.data.utils.Singleton

@Entity(tableName = Singleton.QUICK_PLAY)
class QuickPlayQuestions {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Singleton.Q_ID)
    var id: Int = 0

    @ColumnInfo(name = Singleton.U_ID)
    var userID: Int = 0

    @ColumnInfo(name = Singleton.Q_Q)
    var question: String = ""

    @ColumnInfo(name = Singleton.Q_DIFFICULTY)
    var difficulty: String = ""

    @ColumnInfo(name = Singleton.Q_POINT)
    var point: Int = 0

    @ColumnInfo(name = Singleton.Q_C_ANS)
    var correctAns: String = ""

    @ColumnInfo(name = Singleton.Q_AT_OPT)
    var attemptedAns: String = ""

    @ColumnInfo(name = Singleton.Q_CATEGORY)
    var questionCategory: String = ""

    @ColumnInfo(name = Singleton.Q_STATE)
    var questionState: Boolean = false
}