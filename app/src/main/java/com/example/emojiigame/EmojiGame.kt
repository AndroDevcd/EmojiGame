package com.example.emojiigame

import android.app.Application
import android.content.Context

class EmojiGame : Application() {

    companion object {
        lateinit var ctx : Context
    }

    override fun onCreate() {
        super.onCreate()

        ctx = this
    }
}