package com.example.aplikasigeneratorsoal
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig




val generativeModel = GenerativeModel(
//    modelName = "gemini-1.5-flash",
//    modelName = "gemini-2.5-pro",
    modelName = "gemini-2.5-flash-lite-preview-06-17",
    apiKey = "",
    generationConfig = generationConfig {
        temperature = 1f
        topK = 64
        topP = 0.95f
        maxOutputTokens = 8192
        responseMimeType = "application/json"
    },
    systemInstruction = content { text("format permanen : [\n  {\n    \"pertanyaan\": \"pertanyaan\";\n    \"pilihan\": [\"pilihan1\", \"pilihan2\", \"pilihan3\", \"pilihan4\"];\n    \"jawaban\": \"jawaban\"\n  },\n  {\n    \"pertanyaan\": \"pertanyaan\";\n    \"pilihan\": [\"pilihan1\", \"pilihan2\", \"pilihan3\", \"pilihan4\"];\n    \"jawaban\": \"jawaban\"\n}\n]\n ") }
)