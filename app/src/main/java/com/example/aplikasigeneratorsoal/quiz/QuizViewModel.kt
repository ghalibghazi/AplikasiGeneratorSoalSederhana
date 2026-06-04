package com.example.aplikasigeneratorsoal.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel: ViewModel() {
    val selectedOptions = MutableLiveData<MutableMap<Int, String>>(mutableMapOf())
    var score = MutableLiveData<Int>(0)
}