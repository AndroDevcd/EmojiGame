package com.intuisoft.core.data

import com.intuisoft.core.domain.Question

interface QuestionDataSource {

    suspend fun getAll() : List<Question>?

    suspend fun randomQuestion() : Question;

    suspend fun setupQuestions() : ArrayList<Question>

    suspend fun getLocalQuestions() : List<Question>

}