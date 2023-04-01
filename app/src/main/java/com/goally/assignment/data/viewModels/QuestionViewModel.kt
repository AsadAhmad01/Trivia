package com.goally.assignment.data.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goally.assignment.data.base.App
import com.goally.assignment.data.dataModels.QuickPlayQuestions
import com.goally.assignment.data.utils.Singleton
import com.goally.assignment.domain.db.AppDataBase
import com.goally.assignment.domain.db.QuestionAttempted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionViewModel() : ViewModel() {


    // Normal Play Data Fields
    private var normalPlayAllQuestions: MutableLiveData<ArrayList<QuestionAttempted>> =
        MutableLiveData()
    private var normalPlayAllCorrectQuestions: MutableLiveData<Int> =
        MutableLiveData()
    private var normalPlayAllIncorrectQuestions: MutableLiveData<Int> =
        MutableLiveData()

    // Quick Play Data Fields
    private var quickPlayAllQuestions: MutableLiveData<ArrayList<QuickPlayQuestions>> =
        MutableLiveData()
    private var quickPlayAllCorrectQuestions: MutableLiveData<Int> = MutableLiveData()
    private var quickPlayAllIncorrectQuestions: MutableLiveData<Int> = MutableLiveData()


    private var errorModel: MutableLiveData<String> = MutableLiveData()
    private var db = AppDataBase.getAppDB(App.context)


    // Normal Play Fields

    fun saveNormalModeQuestionIntoDB(model: QuestionAttempted) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db?.questionDao()?.insertQuestion(model)
            } catch (e: Exception) {
                errorModel.postValue(e.message)
            }
        }
    }

    fun callForAllNormalPlayQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                normalPlayAllQuestions.postValue(
                    db?.questionDao()
                        ?.getAllQuestions(Singleton.userID) as ArrayList<QuestionAttempted>
                )

                normalPlayAllCorrectQuestions.postValue(
                    db?.questionDao()?.getAllCorrectAnswers(Singleton.userID)!!
                )

                normalPlayAllIncorrectQuestions.postValue(
                    db?.questionDao()?.getAllIncorrectAnswers(Singleton.userID)!!
                )
            } catch (e: Exception) {
                errorModel.postValue(e.message)
            }
        }
    }


    fun getNormalPlayQuestionsList(): MutableLiveData<ArrayList<QuestionAttempted>> {
        return normalPlayAllQuestions
    }

    fun getNormalPlayCorrectQuestionsList(): MutableLiveData<Int> {
        return normalPlayAllCorrectQuestions
    }

    fun getNormalPlayIncorrectQuestionsList(): MutableLiveData<Int> {
        return normalPlayAllIncorrectQuestions
    }

    // Quick Play Fields

    fun saveQuickModeQuestionIntoDB(model: QuickPlayQuestions) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db?.quickPlayDao()?.saveIntoDB(model)
            } catch (e: Exception) {
                errorModel.postValue(e.message)
            }
        }
    }


    fun callForAllQuickPlayQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                quickPlayAllQuestions.postValue(
                    db?.quickPlayDao()
                        ?.getAllQuestions(Singleton.userID) as ArrayList<QuickPlayQuestions>
                )

                quickPlayAllCorrectQuestions.postValue(
                    db?.quickPlayDao()?.getAllCorrectAnswers(Singleton.userID)!!
                )

                quickPlayAllCorrectQuestions.postValue(
                    db?.quickPlayDao()?.getAllIncorrectAnswers(Singleton.userID)!!
                )
            } catch (e: Exception) {
                errorModel.postValue(e.message)
            }
        }
    }

    fun getQuickPlayQuestionsList(): MutableLiveData<ArrayList<QuickPlayQuestions>> {
        return quickPlayAllQuestions
    }

    fun getQuickPlayCorrectQuestionsList(): MutableLiveData<Int> {
        return quickPlayAllCorrectQuestions
    }

    fun getQuickPlayIncorrectQuestionsList(): MutableLiveData<Int> {
        return quickPlayAllIncorrectQuestions
    }

    fun getErrorsReported(): MutableLiveData<String> {
        return errorModel
    }

}