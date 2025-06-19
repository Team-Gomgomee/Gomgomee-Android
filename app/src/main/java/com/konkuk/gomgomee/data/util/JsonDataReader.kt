package com.konkuk.gomgomee.data.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konkuk.gomgomee.data.local.entity.*

class JsonDataReader(private val context: Context) {
    private val gson = Gson()

    fun readUsers(): List<UserEntity> {
        val jsonString = context.assets.open("initial_data/users.json")
            .bufferedReader()
            .use { it.readText() }
        
        val type = object : TypeToken<List<UserEntity>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    fun readChecklistItems(): List<ChecklistItemEntity> {
        val jsonString = context.assets.open("initial_data/checklist_items.json")
            .bufferedReader()
            .use { it.readText() }
        
        val type = object : TypeToken<List<ChecklistItemEntity>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    fun readTestQuestions(): List<TestQuestionEntity> {
        val jsonString = context.assets.open("initial_data/test_questions.json")
            .bufferedReader()
            .use { it.readText() }
        
        val type = object : TypeToken<List<TestQuestionEntity>>() {}.type
        return gson.fromJson(jsonString, type)
    }

} 