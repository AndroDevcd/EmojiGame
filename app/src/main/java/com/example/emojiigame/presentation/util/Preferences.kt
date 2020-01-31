package com.example.emojiigame.presentation.util

import android.content.Context
import com.example.emojiigame.framework.EmojiGameApplication
import com.example.emojiigame.R

class Preferences {

    companion object {
        fun getSharedPref() = EmojiGameApplication.ctx.getSharedPreferences(EmojiGameApplication.ctx.getString(R.string.EMOJIGM_PREFERENCE), Context.MODE_PRIVATE)
        fun edit() = getSharedPref().edit()

        fun getBool(id : Int, def : Boolean) : Boolean {
            return getSharedPref().getBoolean(EmojiGameApplication.ctx.getString(id), def);
        }

        fun isUsersFirstOpen() : Boolean {
            return getBool(R.string.pref_first_open, true)
        }

        fun setFirstOpenEnabled(enable : Boolean) = edit().putBoolean(EmojiGameApplication.ctx.getString(R.string.pref_first_open), enable).commit()

        fun isUsersFirstQuestion() : Boolean {
            return getBool(R.string.pref_first_question, true)
        }

        fun setFirstQuestionEnabled(enable : Boolean) = edit().putBoolean(EmojiGameApplication.ctx.getString(R.string.pref_first_question), enable).commit()

    }
}