package com.goally.assignment.data.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goally.assignment.data.dataModels.ResponseData
import com.goally.assignment.domain.repositories.QuestionsRepository

class QuizViewModel(private val repo: QuestionsRepository) : ViewModel() {

    private var quickQuestionsListFromAPI: MutableLiveData<ResponseData> = MutableLiveData()
    private var normalQuestionsListFromAPI: MutableLiveData<ResponseData> = MutableLiveData()
    private var errorModel: MutableLiveData<String> = MutableLiveData()


    suspend fun apiCallForQuickModeQuestions() {

        try {
            quickQuestionsListFromAPI.postValue(repo.getQuickModeQuestions())
        } catch (e: Exception) {
            errorModel.postValue(e.message)
        }

    }

    suspend fun apiCallForNormalModeQuestions(cate: Int, difficulty: String, type: String) {

        try {
            normalQuestionsListFromAPI.postValue(
                repo.getNormalModeQuestions(
                    cate,
                    difficulty,
                    type
                )
            )
        } catch (e: Exception) {
            errorModel.postValue(e.message)
        }

    }


    fun getNormalModeDataFromAPI(): MutableLiveData<ResponseData> {
        return normalQuestionsListFromAPI
    }

    fun getQuickModeDataFromAPI(): MutableLiveData<ResponseData> {
        return quickQuestionsListFromAPI
    }

    fun getError(): MutableLiveData<String> {
        return errorModel
    }


}