package com.example.emojiigame.service.repositories

import androidx.lifecycle.MutableLiveData
import com.example.emojiigame.R
import com.example.emojiigame.service.model.EmojiQuetion
import com.example.emojiigame.service.model.Message
import com.example.emojiigame.service.model.MessageType
import com.example.emojiigame.util.customShuffle
import com.example.emojiigame.util.getString
import kotlin.random.Random

class EmojiRepository {

    // TODO: I ned a way to persist the daa beyond the session of the app

    companion object {
        // list of commands that we check for on the UI side
        val NEXT_QUES = "next-question"
        val questions : ArrayList<EmojiQuetion> by lazy {
            getAllQustions()
        }

        fun getAllQustions() : ArrayList<EmojiQuetion> {
            var questions : ArrayList<EmojiQuetion> = ArrayList<EmojiQuetion>()
            questions.add(EmojiQuetion("Name the drama show: \uD83C\uDF4A\uD83D\uDC49\uD83C\uDD95⚫", "Orange is the new black"));
            questions.add(EmojiQuetion("Name the drama show: ⛰️⛰️", "Twin peaks"));
            questions.add(EmojiQuetion("Name the horror movie: \uD83C\uDF83\uD83C\uDF83\uD83C\uDF83\uD83C\uDF83", "Halloween 4"));
            questions.add(EmojiQuetion("Name the horror movie: \uD83D\uDC40\uD83C\uDF04\uD83C\uDF04\uD83D\uDC40", "The hills have eyes"));
            questions.add(EmojiQuetion("Name the crime movie: \uD83D\uDC7B\uD83D\uDC49\uD83D\uDC1A", "Ghost in the shell"));
            questions.add(EmojiQuetion("Name the crime movie: \uD83D\uDC3A\uD83D\uDCC8\uD83D\uDCC8\uD83D\uDCB0", "Wolf of wall street"));
            questions.add(EmojiQuetion("Name the animated movie: \uD83E\uDD81\uD83D\uDC51", "The lion king"));
            questions.add(EmojiQuetion("Name the celebrity best actress: \uD83D\uDD1D\uD83D\uDD2B", "Kelly Mcgillis"));
            questions.add(EmojiQuetion("Name the musical movie: \uD83D\uDEAA\uD83D\uDEAA", "The doors"));
            questions.add(EmojiQuetion("Name the monster movie: \uD83E\uDD96\uD83D\uDD25\uD83D\uDD25\uD83C\uDFD9", "Godzilla"));

            return questions
        }


        fun getRandomQuestion(): Message {
            var questions = questions.customShuffle()
            var randomQuestion = questions[Random.nextInt(0, questions.size - 1)]
            return Message(randomQuestion.question, MessageType.MESSAGE_RECIEVED)
        }

        fun getWelcomeMessage() : String {
            return getString(R.string.welcome_message)
        }
    }

}