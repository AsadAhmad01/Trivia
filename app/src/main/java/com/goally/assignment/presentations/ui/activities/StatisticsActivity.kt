package com.goally.assignment.presentations.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.goally.assignment.R
import com.goally.assignment.data.base.BaseActivity
import com.goally.assignment.data.dataModels.QuickPlayQuestions
import com.goally.assignment.data.dataModels.UserModel
import com.goally.assignment.data.viewModels.QuestionViewModel
import com.goally.assignment.data.viewModels.UserViewModel
import com.goally.assignment.databinding.ActivityStatisticsBinding
import com.goally.assignment.domain.db.QuestionAttempted

class StatisticsActivity : BaseActivity() {
    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var questionViewModel: QuestionViewModel
    private var userModel: UserModel = UserModel()
    private var questionsListNormal: ArrayList<QuestionAttempted> = ArrayList()
    private var correctQuestionsListNormal: Int = 0
    private var incorrectQuestionsListNormal: Int = 0


    private var questionsListQuick: ArrayList<QuickPlayQuestions> = ArrayList()
    private var correctQuestionsListQuick: Int = 0
    private var incorrectQuestionsListQuick: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]

        handleDataFromDB()
        setViews()

    }

    private fun handleDataFromDB() {

        userViewModel.callForSingleCurrent()

        // Normal Play
        questionViewModel.callForAllNormalPlayQuestions()

        // Quick Play
        questionViewModel.callForAllQuickPlayQuestions()



        userDataHandler()
        normalPlayQuestionHandler()
        quickPlayQuestionHandler()

        errorHandler()

    }


    private fun errorHandler() {
        questionViewModel.getErrorsReported().observe(this)
        {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        userViewModel.getErrors().observe(this)
        {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun quickPlayQuestionHandler() {
        questionViewModel.getQuickPlayQuestionsList().observe(this) {
            if (it != null) {
                questionsListQuick = it
                setQuickPlayData()
            }
        }

        questionViewModel.getQuickPlayCorrectQuestionsList().observe(this) {
            if (it != null) {
                correctQuestionsListQuick = it
                setQuickPlayData()
            }
        }

        questionViewModel.getQuickPlayIncorrectQuestionsList().observe(this) {
            if (it != null) {
                incorrectQuestionsListQuick = it
                setQuickPlayData()
            }
        }


    }

    private fun normalPlayQuestionHandler() {
        questionViewModel.getNormalPlayQuestionsList().observe(this) {
            if (it != null) {
                questionsListNormal = it
                setNormalPlayData()
            }
        }
        questionViewModel.getNormalPlayCorrectQuestionsList().observe(this) {
            if (it != null) {
                correctQuestionsListNormal = it
                setNormalPlayData()
            }
        }

        questionViewModel.getNormalPlayIncorrectQuestionsList().observe(this) {
            if (it != null) {
                incorrectQuestionsListNormal = it
                setNormalPlayData()
            }
        }


    }

    private fun userDataHandler() {
        userViewModel.getCurrentUser().observe(this) {
            if (it != null) {
                userModel = it
                setUserDataIntoViews()
            } else {
                Toast.makeText(this, "No user\nPlease re-login your account", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setNormalPlayData() {
        binding.txtIncorrectAns.text = incorrectQuestionsListNormal.toString()
        binding.txtcorrectAns.text = correctQuestionsListNormal.toString()
        binding.txtTotalQuestions.text = questionsListNormal.size.toString()
    }


    private fun setQuickPlayData() {
        binding.txtIncorrectAnsQuickPlay.text = incorrectQuestionsListQuick.toString()
        binding.txtCorrectAnsQuickPlay.text = correctQuestionsListQuick.toString()
        binding.txtTotalQuestionsQuickPlay.text = questionsListQuick.size.toString()
    }

    private fun setUserDataIntoViews() {

        // Normal Play
        binding.txtTotalAttempts.text = userModel.userAttempts.toString()
        binding.txtTotalPoints.text = userModel.userPoints.toString()

        // Quick Play
        binding.txtTotalAttemptsQuickPlay.text = userModel.userQuickAttempts.toString()
        binding.txtTotalPointsQuickPlay.text = userModel.userQuickPoints.toString()

    }

    private fun setViews() {
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.Logout.setOnClickListener {
            finish()
        }
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(
            0, R.anim.slide_out_right
        )
    }
}