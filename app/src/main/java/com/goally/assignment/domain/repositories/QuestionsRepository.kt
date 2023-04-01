package com.goally.assignment.domain.repositories

import com.goally.assignment.domain.retrofit.RestApi

class QuestionsRepository(private val restApi: RestApi) {

    suspend fun getQuickModeQuestions() = restApi.getQuickModeQuestions()
    suspend fun getNormalModeQuestions(cate: Int, difficulty: String, type: String) =
        restApi.getNormalModeQuestions(cate, difficulty, type)
}