package com.goally.assignment.presentations.ui.activities

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.goally.assignment.data.base.BaseActivity
import com.goally.assignment.data.dataModels.CategoryModel
import com.goally.assignment.data.dataModels.UserModel
import com.goally.assignment.data.utils.CommonMethods
import com.goally.assignment.data.utils.CustomDialogs
import com.goally.assignment.data.utils.Singleton
import com.goally.assignment.data.viewModels.QuizViewModel
import com.goally.assignment.data.viewModels.UserViewModel
import com.goally.assignment.databinding.ActivityGameStartBinding
import com.goally.assignment.domain.repositories.QuestionsRepository
import com.goally.assignment.domain.retrofit.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class GameStartActivity : BaseActivity() {

    private lateinit var binding: ActivityGameStartBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var quizViewModel: QuizViewModel
    private var cate: Int = 0
    private var type: String = ""
    private var diffi: String = ""
    private var attempts: Int = 0
    private lateinit var userModel: UserModel
    private val cateList = CategoryModel.setValues()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        quizViewModel = QuizViewModel(QuestionsRepository(RetrofitClient.getInstance()))

        initData()
        initViews()
    }

    private fun initViews() {

        //setSpinnerItems
        setSpinnerItems()

        //OnclickEvents
        setClickListeners()
    }

    private fun setSpinnerItems() {
        setCategories()
        setDifficulty()
        setQuestionType()
    }

    override fun finish() {

        if (!binding.loadingLottieAnimation.isAnimating) {
            super.finish()
            this.overridePendingTransition(
                0, com.goally.assignment.R.anim.slide_out_right
            )
        } else {
            Toast.makeText(this, "Wait!!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setQuestionType() {
        val quesTypeList = ArrayList<String>()

        quesTypeList.add("Any Type")
        quesTypeList.add("Multiple Choice")
        quesTypeList.add("True / False")

        val adapter = ArrayAdapter(
            this, com.goally.assignment.R.layout.simple_spinner, quesTypeList
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerType.adapter = adapter

        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> type = ""
                    1 -> type = "multiple"
                    2 -> type = "boolean"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }
    }

    private fun setDifficulty() {

        val diffList = ArrayList<String>()
        diffList.add("Any Difficulty")
        diffList.add("Easy")
        diffList.add("Medium")
        diffList.add("Hard")

        val adapter = ArrayAdapter(
            this,
            com.goally.assignment.R.layout.simple_spinner, diffList
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerDifficulty.adapter = adapter

        binding.spinnerDifficulty.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> diffi = ""
                        1 -> diffi = "easy"
                        2 -> diffi = "medium"
                        3 -> diffi = "hard"
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }


            }

    }

    private fun setCategories() {

        val list: ArrayList<String> = ArrayList()

        for (i in 0 until cateList.size) {
            list.add(cateList[i].name)
        }

        val adapter =
            ArrayAdapter(this, com.goally.assignment.R.layout.simple_spinner, list)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter

        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    cate = cateList[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

    }


    private fun setClickListeners() {

        binding.btnPlayNow.setOnClickListener {
            binding.loadingLottieAnimation.visibility = View.VISIBLE
            getResponseFromAPI()

        }

        binding.Logout.setOnClickListener {
            finish()

        }

    }


    private fun getResponseFromAPI() {

        lifecycleScope.launch(Dispatchers.IO) {
            if (CommonMethods.isNetworkAvailable(this@GameStartActivity)) {
                quizViewModel.apiCallForNormalModeQuestions(cate, diffi, type)
            } else {
                withContext(Dispatchers.Main) {
                    binding.loadingLottieAnimation.visibility = View.GONE
                    CustomDialogs.showDialog(
                        this@GameStartActivity,
                        2,
                        "There is Something Wrong! \nMake sure your device is Connected to Internet Thank You",
                        "OK",
                        "GAME"
                    )
                }
            }
        }

    }

    private fun initData() {
        userViewModel.callForSingleCurrent()

        handleDataFromAPI()
        handleDataFromDB()
        handleErrors()
    }

    private fun handleErrors() {
        userViewModel.getErrors().observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        quizViewModel.getError().observe(this) {
            binding.loadingLottieAnimation.visibility = View.GONE
            Toast.makeText(this@GameStartActivity, "Error is: $it", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun saveIntoDB(attempts: Int) {
        userModel.id = Singleton.userID
        userModel.userAttempts = attempts
        runBlocking {
            userViewModel.updateUserIntoDB(userModel)
        }

    }

    private fun handleDataFromAPI() {
        quizViewModel.getNormalModeDataFromAPI().observe(this) {
            if (it.results.isNullOrEmpty()) {
                binding.loadingLottieAnimation.visibility = View.GONE
                CustomDialogs.showDialog(
                    this@GameStartActivity,
                    2,
                    "No Questions Found in your match...!",
                    "OK",
                    "GAME"
                )
            } else {

                binding.loadingLottieAnimation.visibility = View.GONE
                attempts++
                saveIntoDB(attempts)
                var cat = ""
                for (i in 0 until cateList.size) {
                    if (cate == cateList[i].id) {
                        cat = cateList[i].name

                    }
                }

                val i = Intent(this@GameStartActivity, PlayActivity::class.java)

                i.putExtra("CATEGORY", cat)
                i.putExtra("json", Gson().toJson(it))
                startActivity(i)
                this@GameStartActivity.overridePendingTransition(
                    com.goally.assignment.R.anim.slide_in_right,
                    com.goally.assignment.R.anim.slide_out_left
                )
                customPreferences.save("ATTEMPT", attempts)
            }
        }
    }

    private fun handleDataFromDB() {
        userViewModel.getCurrentUser().observe(this) {
            userModel = it
        }
    }
}