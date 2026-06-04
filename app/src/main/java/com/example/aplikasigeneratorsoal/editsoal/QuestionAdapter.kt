package com.example.aplikasigeneratorsoal.editsoal
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigeneratorsoal.R

class QuestionAdapter(private val questions: List<Question>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
        holder.soalTextView.text = question.pertanyaan
        holder.pilihanTextView.removeAllViews()
        var selectedId = View.generateViewId()

        var selectedRadioId: Int? = null

        Log.d("QuestionDebug", "Soal: ${question.pertanyaan}, Jawaban: ${question.jawaban}, Pilihan: ${question.pilihan}")


        question.pilihan.forEach { option ->
            val radioButton = RadioButton(holder.itemView.context).apply {
                id = View.generateViewId()
                text = option
            }

            Log.d("RadioDebug", "Adding option: '$option', matches jawaban: ${option == question.jawaban}")
            holder.pilihanTextView.addView(radioButton)

            // Save the correct button ID if this is the selected answer
            if (option == question.jawaban) {
                selectedId = radioButton.id
            }

            // Set click listener
            radioButton.setOnClickListener {
                questions[position].jawaban = option
            }
        }
        holder.pilihanTextView.check(selectedId)

        selectedRadioId?.let {
            holder.pilihanTextView.check(it)
        }

//        for (option in question.pilihan) {
//            val radioButton = RadioButton(holder.itemView.context).apply {
//                text = option
//                isChecked = option == question.jawaban
//            }
//
//            // Set listener to update `jawaban` when changed
//            radioButton.setOnClickListener {
//                questions[position].jawaban = option
//            }
//
//            holder.pilihanTextView.addView(radioButton)
//        }

//        holder.pilihanTextView.text = question.pilihan.joinToString(", ")
//        holder.jawabanTextView.text = question.jawaban
    }

    override fun getItemCount(): Int = questions.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val soalTextView: TextView = view.findViewById(R.id.pertanyaan_textview)
        val pilihanTextView: RadioGroup = view.findViewById(R.id.pilihan_textview)
//        val jawabanTextView: RadioButton = view.findViewById(R.id.jawaban_textview)
    }
}