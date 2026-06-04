package com.example.aplikasigeneratorsoal.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigeneratorsoal.R
import com.example.aplikasigeneratorsoal.editsoal.Question



class QuizAdapter(
    private val questions: List<Question>,
    private val onOptionSelected: (Int, String) -> Unit
) : RecyclerView.Adapter<QuizAdapter.QuestionViewHolder>() {

    val selectedOptions  = mutableMapOf<Int, String>()

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        val optionsRadioGroup: RadioGroup = itemView.findViewById(R.id.optionsRadioGroup)

        fun bind(question: Question, position: Int) {
            questionTextView.text = question.pertanyaan
            optionsRadioGroup.removeAllViews()

            question.pilihan.forEachIndexed { index, option ->
                val radioButton = RadioButton(itemView.context).apply {
                    text = option
                    textSize = 18f
                    id = View.generateViewId() // Set a unique ID for each RadioButton
                }
                optionsRadioGroup.addView(radioButton)

                // Check if this option was selected
                if (selectedOptions[position] == option) {
                    radioButton.isChecked = true
                }


                // Set up click listener
                radioButton.setOnClickListener {
                    selectedOptions[position] = option
                    onOptionSelected(position, option)
                }

                optionsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                    val selectedRadioButton = itemView.findViewById<RadioButton>(checkedId)
                    val selectedOption  = selectedRadioButton.text.toString()
                    selectedOptions[position] = selectedOption
                    onOptionSelected(position, selectedOption)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions[position], position)
    }

    override fun getItemCount(): Int = questions.size

//    fun resetSelection() {
//        selectedOptions = -1
//        notifyDataSetChanged()
//    }
}