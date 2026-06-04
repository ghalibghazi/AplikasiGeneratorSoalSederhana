package com.example.aplikasigeneratorsoal.editsoal

data class Question(
    val pertanyaan: String,
    val pilihan: List<String>,
    var jawaban: String
)