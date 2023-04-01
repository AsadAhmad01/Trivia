package com.arhamsoft.online.trivizia.db

import androidx.room.*
import com.goally.assignment.data.utils.Singleton
import com.goally.assignment.domain.db.QuestionAttempted

@Dao
interface QuestionDAO {


    @Query("SELECT * FROM ${Singleton.QUESTION_ATTEMPTED} WHERE U_ID =:id")
    fun getAllQuestions(id: Int): List<QuestionAttempted>?

    @Query("SELECT SUM(${Singleton.Q_POINT}) FROM ${Singleton.QUESTION_ATTEMPTED} WHERE U_ID =:id")
    fun getAllPoints(id: Int): Int

    @Query("SELECT COUNT(${Singleton.Q_STATE}) FROM ${Singleton.QUESTION_ATTEMPTED} WHERE ${Singleton.Q_STATE} = 1 AND U_ID =:id ")
    fun getAllCorrectAnswers(id: Int): Int

    @Query("SELECT COUNT(${Singleton.Q_STATE}) FROM ${Singleton.QUESTION_ATTEMPTED} WHERE ${Singleton.Q_STATE} = 0 AND U_ID =:id")
    fun getAllIncorrectAnswers(id: Int): Int

    @Query("SELECT SUM(${Singleton.Q_POINT}) FROM ${Singleton.QUESTION_ATTEMPTED} WHERE ${Singleton.Q_CATEGORY} = :cate AND U_ID =:id")
    fun getPointsByCategories(cate: String, id: Int): Int

    @Insert
    fun insertQuestion(questionAttempted: QuestionAttempted)

    @Query("DELETE FROM ${Singleton.QUESTION_ATTEMPTED} WHERE U_ID = :id")
    fun clearAllQuestions(id: Int)

    @Delete
    fun deleteQuestion(questionAttempted: QuestionAttempted)

    @Update
    fun updateQuestion(questionAttempted: QuestionAttempted)


}