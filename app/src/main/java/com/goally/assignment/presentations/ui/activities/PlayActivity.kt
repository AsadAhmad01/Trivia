package com.goally.assignment.presentations.ui.activities

import android.animation.Animator
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.goally.assignment.R
import com.goally.assignment.data.base.BaseActivity
import com.goally.assignment.data.dataModels.QuestionClass
import com.goally.assignment.data.dataModels.ResponseData
import com.goally.assignment.data.dataModels.UserModel
import com.goally.assignment.data.utils.CustomDialogs
import com.goally.assignment.data.utils.Singleton
import com.goally.assignment.data.viewModels.QuestionViewModel
import com.goally.assignment.data.viewModels.UserViewModel
import com.goally.assignment.databinding.ActivityPlayBinding
import com.goally.assignment.domain.db.QuestionAttempted
import com.google.gson.Gson

class PlayActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPlayBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var questionViewModel: QuestionViewModel
    private lateinit var questionList: List<QuestionClass>

    private var currentIndex: Int = 0
    private var currentScore: Int = 0
    private var gameEnd: Boolean = false
    private lateinit var userModel: UserModel
    private lateinit var category: String
    private var animEnded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]


        initData()
        initViews()
    }

    private fun initViews() {
        //Count Down Animation
        countDownAnimation()

        // Get List of Questions Class From Intent
        getListFromIntent()

        // Set data
        setQuestionIntoLayout()

        //Onclick listeners
        setOnclickListeners()
    }

    private fun countDownAnimation() {

        binding.countDownLottieAnimation.visibility = View.VISIBLE

        binding.countDownLottieAnimation.addAnimatorListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(p0: Animator) {
                binding.txtQuestion.visibility = View.GONE
                binding.optionLayout.visibility = View.GONE
                binding.bottomBar.visibility = View.GONE
            }

            override fun onAnimationEnd(p0: Animator) {
                binding.countDownLottieAnimation.visibility = View.GONE
                binding.txtQuestion.visibility = View.VISIBLE
                binding.optionLayout.visibility = View.VISIBLE
                binding.bottomBar.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(p0: Animator) {

            }

            override fun onAnimationRepeat(p0: Animator) {

            }

        })


    }

    private fun setOnclickListeners() {

        binding.txtOption1.setOnClickListener(this)
        binding.txtOption2.setOnClickListener(this)
        binding.txtOption3.setOnClickListener(this)
        binding.txtOption4.setOnClickListener(this)

        binding.Logout.setOnClickListener {
            finish()
        }




        binding.leaveGame.setOnClickListener {

            CustomDialogs.showDialog(
                this@PlayActivity,
                1,
                "Are you sure you want to leave?",
                "YES",
                "HOME"
            )
        }

    }

    private fun setQuestionIntoLayout() {


        if (questionList.isNotEmpty()) {
            val qN = 1 + currentIndex
            val decoded: String = HtmlCompat
                .fromHtml(questionList[currentIndex].question!!, HtmlCompat.FROM_HTML_MODE_COMPACT)
                .toString()
            (qN.toString() + "/" + questionList.size).also { binding.txtQuesCounter.text = it }
            binding.txtQuestion.text = decoded


            //Get Options
            getAnswersList()

            //shuffle the options
            shuffleAnswers()

            // Set Options
            setOptionsIntoFields()

            //set Score
            "$currentScore Points".also { binding.txtGamePoints.text = it }
            if (gameEnd) {
                savePointsIntoDB(currentScore)
            }
        } else {
            Toast.makeText(this, "No Question!!!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun savePointsIntoDB(points: Int) {
        userModel.id = Singleton.userID
        val p = userModel.userPoints
        userModel.userPoints = points + p

        userViewModel.updateUserIntoDB(userModel)

    }


    private fun getAnswersList(): ArrayList<String> {
        val answersList: ArrayList<String> = ArrayList()
        val op1 = questionList[currentIndex].correctAnswer
        answersList.add(op1!!)

        val incorrectListSize = questionList[currentIndex].incorrectAnswers!!.size
        for (i in 0 until incorrectListSize) {
            val incorect = questionList[currentIndex].incorrectAnswers!![i]
            answersList.add(incorect)
        }
        answersList.shuffle()
        return answersList
    }


    private fun setOptionsIntoFields() {

        val answersList: ArrayList<String> = getAnswersList()
        if (questionList[currentIndex].type == "multiple") {

            //visible Options
            binding.txtOption3.visibility = View.VISIBLE
            binding.txtOption4.visibility = View.VISIBLE

            //set values
            ("A: " + decoderString(answersList[0])).also { binding.txtOption1.text = it }
            ("B: " + decoderString(answersList[1])).also { binding.txtOption2.text = it }
            ("C: " + decoderString(answersList[2])).also { binding.txtOption3.text = it }
            ("D: " + decoderString(answersList[3])).also { binding.txtOption4.text = it }

        } else {

            //Gone Options
            binding.txtOption3.visibility = View.GONE
            binding.txtOption4.visibility = View.GONE

            //set values
            "A: True".also { binding.txtOption1.text = it }
            "B: False".also { binding.txtOption2.text = it }
        }

    }

    private fun shuffleAnswers() {
        getAnswersList().shuffle()
        getAnswersList().shuffle()
    }

    override fun onClick(p0: View) {

        when (p0.id) {

            R.id.txtOption1 -> gameStarter(
                decoderString(binding.txtOption1.text.toString()).replace(
                    "A: ",
                    ""
                )
            )
            R.id.txtOption2 -> gameStarter(
                decoderString(binding.txtOption2.text.toString()).replace(
                    "B: ",
                    ""
                )
            )
            R.id.txtOption3 -> gameStarter(
                decoderString(binding.txtOption3.text.toString()).replace(
                    "C: ",
                    ""
                )
            )
            R.id.txtOption4 -> gameStarter(
                decoderString(binding.txtOption4.text.toString()).replace(
                    "D: ",
                    ""
                )
            )

        }

    }

    private fun gameStarter(selectedAnswer: String) {

        if (selectedAnswer == decoderString(questionList[currentIndex].correctAnswer!!)) {

            if (currentIndex < 9) {

                saveQuestionIntoDB(selectedAnswer, true, questionList[currentIndex].difficulty!!)
                when (questionList[currentIndex].difficulty) {
                    "easy" -> currentScore += 1
                    "medium" -> currentScore += 2
                    "hard" -> currentScore += 3
                }

                startAnimations(1)
                currentIndex++

            } else {
                saveQuestionIntoDB(selectedAnswer, true, questionList[currentIndex].difficulty!!)
                startAnimations(1)
                when (questionList[currentIndex].difficulty) {
                    "easy" -> currentScore += 1
                    "medium" -> currentScore += 2
                    "hard" -> currentScore += 3
                }
                if (animEnded)
                    gameEnd = true
                CustomDialogs.showDialog(
                    this@PlayActivity,
                    2,
                    "You Scored $currentScore in this Attempt!",
                    "OK",
                    "HOME"
                )
            }
        } else {
            Log.e("check", "gameStarter: " + "wrong")
            if (currentIndex < 9) {
                saveQuestionIntoDB(selectedAnswer, false, questionList[currentIndex].difficulty!!)
                startAnimations(0)
                currentIndex++
            } else {
                saveQuestionIntoDB(selectedAnswer, false, questionList[currentIndex].difficulty!!)
                startAnimations(0)
                if (animEnded)
                    gameEnd = true
                CustomDialogs.showDialog(
                    this@PlayActivity,
                    2,
                    "You Scored $currentScore in this Attempt!",
                    "OK",
                    "HOME"
                )
            }
        }


    }


    private fun textViewsBorderChange() {
        binding.txtOption1.background =
            ContextCompat.getDrawable(
                this,
                R.drawable.borders_textview_disable
            )
        binding.txtOption2.background =
            ContextCompat.getDrawable(
                this,
                R.drawable.borders_textview_disable
            )
        binding.txtOption3.background =
            ContextCompat.getDrawable(
                this,
                R.drawable.borders_textview_disable
            )
        binding.txtOption4.background =
            ContextCompat.getDrawable(
                this,
                R.drawable.borders_textview_disable
            )

        // text colors
        binding.txtOption1.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        binding.txtOption2.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        binding.txtOption3.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        binding.txtOption4.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))


    }


    private fun setBackTextViewsBorderChange() {
        binding.txtOption1.background = ContextCompat.getDrawable(
            this,
            R.drawable.borders_textview
        )
        binding.txtOption2.background = ContextCompat.getDrawable(
            this,
            R.drawable.borders_textview
        )
        binding.txtOption3.background = ContextCompat.getDrawable(
            this,
            R.drawable.borders_textview
        )
        binding.txtOption4.background = ContextCompat.getDrawable(
            this,
            R.drawable.borders_textview
        )

        // text colors
        binding.txtOption1.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        binding.txtOption2.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        binding.txtOption3.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        binding.txtOption4.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )


        setQuestionIntoLayout()

    }

    private fun setOptionsDisable() {
        binding.txtOption1.isEnabled = false
        binding.txtOption1.isActivated = false
        binding.txtOption1.isClickable = false

        binding.txtOption2.isEnabled = false
        binding.txtOption2.isActivated = false
        binding.txtOption2.isClickable = false

        binding.txtOption3.isEnabled = false
        binding.txtOption3.isActivated = false
        binding.txtOption3.isClickable = false

        binding.txtOption4.isEnabled = false
        binding.txtOption4.isActivated = false
        binding.txtOption4.isClickable = false

        textViewsBorderChange()
    }

    private fun setOptionsEnable() {

        binding.txtOption1.isEnabled = true
        binding.txtOption1.isActivated = true
        binding.txtOption1.isClickable = true

        binding.txtOption2.isEnabled = true
        binding.txtOption2.isActivated = true
        binding.txtOption2.isClickable = true

        binding.txtOption3.isEnabled = true
        binding.txtOption3.isActivated = true
        binding.txtOption3.isClickable = true

        binding.txtOption4.isEnabled = true
        binding.txtOption4.isActivated = true
        binding.txtOption4.isClickable = true


        setBackTextViewsBorderChange()

    }

    override fun finish() {
        if (!binding.countDownLottieAnimation.isAnimating) {
            super.finish()
            this.overridePendingTransition(
                0, R.anim.slide_out_right
            )
        } else {
            Toast.makeText(this, "Wait!!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun saveQuestionIntoDB(selectedAnswer: String, q_state: Boolean, diffi: String) {

        val ques = QuestionAttempted()
        val decoded: String = HtmlCompat
            .fromHtml(questionList[currentIndex].question!!, HtmlCompat.FROM_HTML_MODE_COMPACT)
            .toString()
        ques.question = decoded
        ques.userID = Singleton.userID
        ques.correctAns = decoderString(questionList[currentIndex].correctAnswer!!)
        ques.attemptedAns = selectedAnswer
        ques.questionCategory = category
        ques.questionState = q_state
        ques.difficulty = diffi

        if (q_state) {
            when (diffi) {
                "easy" -> ques.point = 1
                "medium" -> ques.point = 2
                "hard" -> ques.point = 3
            }
        }

        questionViewModel.saveNormalModeQuestionIntoDB(ques)

    }

    private fun startAnimations(id: Int) {
        if (id == 0) {
            binding.imgIndicator.visibility = View.VISIBLE
            binding.imgIndicator.setImageResource(R.drawable.ic_baseline_check_false)

            binding.imgIndicator.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.bounceb
                )
            )
            binding.imgIndicator.animation.setAnimationListener(object :
                Animation.AnimationListener {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onAnimationStart(p0: Animation?) {

                    setOptionsDisable()
                    animEnded = false
                }

                @RequiresApi(Build.VERSION_CODES.N)
                override fun onAnimationEnd(p0: Animation?) {
                    animEnded = true
                    binding.imgIndicator.visibility = View.INVISIBLE
                    setOptionsEnable()
                }

                override fun onAnimationRepeat(p0: Animation?) {
                    TODO("Not yet implemented")
                }

            })
        } else {
            binding.imgIndicator.visibility = View.VISIBLE
            binding.imgIndicator.setImageResource(R.drawable.ic_baseline_check_true_24)
            binding.imgIndicator.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.bounceb
                )
            )
            binding.imgIndicator.animation.setAnimationListener(object :
                Animation.AnimationListener {

                @RequiresApi(Build.VERSION_CODES.N)
                override fun onAnimationStart(p0: Animation?) {

                    setOptionsDisable()
                    animEnded = false
                }

                @RequiresApi(Build.VERSION_CODES.N)
                override fun onAnimationEnd(p0: Animation?) {

                    animEnded = true
                    binding.imgIndicator.visibility = View.INVISIBLE
                    setOptionsEnable()
                }

                override fun onAnimationRepeat(p0: Animation?) {
                    TODO("Not yet implemented")
                }

            })
        }
    }


    private fun decoderString(str: String): String {
        return HtmlCompat
            .fromHtml(str, HtmlCompat.FROM_HTML_MODE_COMPACT)
            .toString()
    }


    private fun getListFromIntent() {
        category = intent.getStringExtra("CATEGORY").toString()
        val json = intent.getStringExtra("json")
        Log.e("String: ", json!!)
        val json2 = Gson().fromJson(json, ResponseData::class.java)
        questionList = json2.results!!
    }


    private fun initData() {

        userViewModel.callForSingleCurrent()

        handleDataFromDB()
        handleErrors()
    }

    private fun handleErrors() {
        userViewModel.getErrors().observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleDataFromDB() {
        userViewModel.getCurrentUser().observe(this) {
            userModel = it
        }
    }

}