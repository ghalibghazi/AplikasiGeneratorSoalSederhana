package com.example.aplikasigeneratorsoal.daftarsiswa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigeneratorsoal.R

class SiswaAdapter(private val studentList: List<Siswa>) : RecyclerView.Adapter<SiswaAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.siswa_nama)
        val scoreTextView: TextView = itemView.findViewById(R.id.siswa_poin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_siswa, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        holder.nameTextView.text = student.nama
        holder.scoreTextView.text = student.poin.toString()
    }

    override fun getItemCount() = studentList.size
}