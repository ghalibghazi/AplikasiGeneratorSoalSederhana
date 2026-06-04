package com.example.aplikasigeneratorsoal.quiz

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigeneratorsoal.R
import com.example.aplikasigeneratorsoal.daftartulisan.JsonFile
import com.example.aplikasigeneratorsoal.daftartulisan.TextJsonFileActivity

class QuizJsonFileAdapter(val jsonFiles: List<JsonFile>, val context: Context) : RecyclerView.Adapter<QuizJsonFileAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_json_file, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val jsonFile = jsonFiles[position]
        holder.button.text = jsonFile.fileName
        holder.button.setOnClickListener {
            val jsonFile = jsonFiles[position]
            val intent = Intent(context, TextJsonFileActivity::class.java)
            intent.putExtra("json_file_name", jsonFile.fileName)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = jsonFiles.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button: Button = itemView.findViewById(R.id.button)
    }



}