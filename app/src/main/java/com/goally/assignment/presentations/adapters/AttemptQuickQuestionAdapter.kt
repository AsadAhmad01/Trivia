package com.goally.assignment.presentations.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goally.assignment.R
import com.goally.assignment.data.dataModels.QuickPlayQuestions
import com.goally.assignment.databinding.LayoutAttemptedQuestionBinding

class AttemptQuickQuestionAdapter(context: Context, val attemptedList: List<QuickPlayQuestions>) :
    RecyclerView.Adapter<AttemptQuickQuestionAdapter.AttemptViewHolder>() {

    private lateinit var binding: LayoutAttemptedQuestionBinding

    class AttemptViewHolder(val bind: LayoutAttemptedQuestionBinding) :
        RecyclerView.ViewHolder(bind.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttemptViewHolder {
        binding = LayoutAttemptedQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return AttemptViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttemptViewHolder, position: Int) {
        holder.bind.txtAttemptedAnsAttemptTxt.text = attemptedList[position].attemptedAns
        holder.bind.txtcorrectAnsAttemptTxt.text = attemptedList[position].correctAns
        holder.bind.txtQuestionAttempt.text = attemptedList[position].question

        if (attemptedList[position].questionState) {

            holder.bind.imgAttemptedAns.setImageResource(R.drawable.ic_baseline_check_true_24)

        } else {

            holder.bind.imgAttemptedAns.setImageResource(R.drawable.ic_baseline_check_false)

        }
    }

    override fun getItemCount(): Int {
        return attemptedList.size
    }
}