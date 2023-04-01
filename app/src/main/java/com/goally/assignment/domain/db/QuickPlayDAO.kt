package com.goally.assignment.domain.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.goally.assignment.data.dataModels.QuickPlayQuestions
import com.goally.assignment.data.utils.Singleton

@Dao
interface QuickPlayDAO {

    @Query("SELECT * FROM ${Singleton.QUICK_PLAY} WHERE ${Singleton.U_ID} = :id")
    fun getAllQuestions(id: Int): List<QuickPlayQuestions>

    @Query("SELECT COUNT(${Singleton.Q_STATE}) FROM ${Singleton.QUICK_PLAY} WHERE ${Singleton.Q_STATE} = 1 AND U_ID =:id ")
    fun getAllCorrectAnswers(id: Int): Int

    @Query("SELECT COUNT(${Singleton.Q_STATE}) FROM ${Singleton.QUICK_PLAY} WHERE ${Singleton.Q_STATE} = 0 AND U_ID =:id")
    fun getAllIncorrectAnswers(id: Int): Int

    @Insert
    fun saveIntoDB(questions: QuickPlayQuestions)

    @Update
    fun updateInDB(questions: QuickPlayQuestions)

    @Query("DELETE FROM ${Singleton.QUICK_PLAY} WHERE ${Singleton.U_ID} = :id")
    fun deleteAll(id: Int)

}