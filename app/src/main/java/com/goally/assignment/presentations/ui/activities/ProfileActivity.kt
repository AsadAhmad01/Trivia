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
import com.goally.assignment.databinding.ActivityProfileBinding
import com.goally.assignment.domain.db.QuestionAttempted

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var questionViewModel: QuestionViewModel
    private var name: String = ""
    private var userModel: UserModel = UserModel()
    private var questionsListNormal: ArrayList<QuestionAttempted> = ArrayList()
    private var questionsListQuick: ArrayList<QuickPlayQuestions> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]

        handleDataFromDB()
        setViews()

    }

    private fun setViews() {
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.Logout.setOnClickListener {
            finish()
        }
    }

    private fun handleDataFromDB() {
        userViewModel.callForSingleCurrent()
        questionViewModel.callForAllNormalPlayQuestions()
        questionViewModel.callForAllQuickPlayQuestions()


        userDataHandler()
        normalPlayQuestionHandler()
        quickPlayQuestionHandler()

        errorHandler()

    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(
            0, R.anim.slide_out_right
        )
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
                "${questionsListNormal.size + questionsListQuick.size}".also {
                    binding.txtTotalQuestions.text = it
                }
            }
        }
    }

    private fun normalPlayQuestionHandler() {
        questionViewModel.getNormalPlayQuestionsList().observe(this) {
            if (it != null) {
                questionsListNormal = it
                "${questionsListNormal.size + questionsListQuick.size}".also {
                    binding.txtTotalQuestions.text = it
                }
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

    private fun setUserDataIntoViews() {
        binding.txtIncorrectAns.text = userModel.userPhone
        binding.txtcorrectAns.text = userModel.userEmail
        binding.txtUserName.text = userModel.userName
        "${userModel.userPoints + userModel.userQuickPoints}".also {
            binding.txtTotalPoints.text = it
        }
        binding.txtNormalPlayed.text = userModel.userAttempts.toString()
        binding.txtQuickPlayPlayed.text = userModel.userQuickAttempts.toString()
    }
}