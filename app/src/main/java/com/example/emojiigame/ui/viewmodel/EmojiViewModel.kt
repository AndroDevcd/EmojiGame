package com.example.emojiigame.ui.viewmodel

import android.app.Application
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.emojiigame.R
import com.example.emojiigame.service.model.EmojiQuetion
import com.example.emojiigame.service.model.Message
import com.example.emojiigame.service.model.MessageType
import com.example.emojiigame.service.repositories.EmojiRepository
import com.example.emojiigame.service.repositories.Resource
import com.example.emojiigame.service.repositories.Status
import com.example.emojiigame.util.getString
import com.example.emojiigame.util.getWordAccuracyPercent
import kotlin.random.Random

class EmojiViewModel(application: Application) : AndroidViewModel(application) {

    private val data : MutableLiveData<Resource<ArrayList<Message>>> = MutableLiveData();

    fun getLiveData() : LiveData<Resource<ArrayList<Message>>> {
        return data
    }

    fun postWelcomeMessage() {
        EmojiRepository.getWelcomeMessage(data);
    }

    fun postMessage(msg : String) {
        appendMsg(
            Message(
                msg,
                MessageType.MESSAGE_RECIEVED
            )
        )
    }

    fun postQuestion() {
        data.value = Resource.loading(data.value)
        doLater(Random.nextLong(500, 1000), null, fun (args : Any?) {
            appendMsg(EmojiRepository.getRandomQuestion())
        })
    }

    /**
     * @msg - the message the user sees
     * @responseMsg - the message the game inspects to check for various commands
     */
    private fun appendMsg(msg : Message, responseMsg : String? = null) {
        var msgs = data.value?.data
        if(msgs != null) msgs.add(msg) else msgs = arrayListOf(msg)
        data.value = Resource.success(msgs, responseMsg)
    }

    fun gameLoop(userAnswer : String) {
        appendMsg(
            Message(
                userAnswer,
                MessageType.MESAGE_SENT
            )
        )
        data.value = Resource.loading(data.value)
        doLater(Random.nextLong(500, 2000), null, fun (args : Any?) {
            var questionList = EmojiRepository.questions
            var chatHist = data.value?.data
            var gameQuestion = "";

            if(chatHist != null) {

                for (i in chatHist.size - 1 downTo 0) {
                    if(chatHist[i].type == MessageType.MESSAGE_RECIEVED && chatHist[i].message.startsWith("Name the")) {
                        gameQuestion = chatHist[i].message
                        break
                    }
                }

                var emojiQuestion = questionList.find { it.question == gameQuestion }
                if(userAnswer.contains("help")) {
                    appendMsg(
                        Message(
                            getString(R.string.help_message),
                            MessageType.MESSAGE_RECIEVED
                        )
                    )
                } else if(userAnswer.contains("next question") || userAnswer.contains("next")) {
                    appendMsg(
                        Message(
                            "Got it! Here's your next question.",
                            MessageType.MESSAGE_RECIEVED
                        ), EmojiRepository.NEXT_QUES
                    )
                } else if(userAnswer.contains("reveal")) {
                    appendMsg(
                        Message(
                            "Here's the answer to the question: " + emojiQuestion?.answer,
                            MessageType.MESSAGE_RECIEVED
                        ), EmojiRepository.NEXT_QUES
                    )
                } else {
                    if (emojiQuestion != null) {
                        checkAnswer(emojiQuestion, userAnswer)
                    } else {
                        appendMsg(
                            Message(
                                "It appears that my developer likes bugs \uD83D\uDE14, unfortunately I am unable to respond to your request.",
                                MessageType.MESSAGE_RECIEVED
                            )
                        )
                    }
                }
            } else {
                appendMsg(Message("I am sorry, I do not understand. Please text \"help\" for more info on how to play the game.", MessageType.MESSAGE_RECIEVED))
            }
        })
    }

    /**
     * The purpose of this unction is to simply check the answer based on the user's input
     *
     * I will use the @getWordAccuracyPercent() function in the util file to get a rough percentage on
     * how close the user was to answering the question. Percentages returned will be graded on a simple scale of 4 levels
     * as listed below:
     *
     * - level 1: (90-100%){A} This represents that you have a really close or an exact answer to the question
     * - level 2:  (70-90%){B} This represents you being very close to the answer but not quite there yet
     * - level 3:  (30-70%){C} This represents you being on track to getting the right answer but kind of far from it
     * - level 4:   (0-30%){F} You just have no idea what your talking about
     */
    fun checkAnswer(question : EmojiQuetion, usrAnswer : String) {
        val userAccuracy = getWordAccuracyPercent(question.answer, usrAnswer)

        when(userAccuracy){
            in 90..100 -> {
                appendMsg(
                    Message(
                        "Awesome you're correct \uD83D\uDE00!",
                        MessageType.MESSAGE_RECIEVED
                    ), EmojiRepository.NEXT_QUES
                )
            }

            in 70..89 -> {
                appendMsg(
                    Message(
                        "You are really close \uD83D\uDC4C, but not quite the answer I was looking for, try modifying your answer slightly.",
                        MessageType.MESSAGE_RECIEVED
                    )
                )
            }

            in 30..69 -> {
                appendMsg(
                    Message(
                        "You're on the right track \uD83D\uDC4D but not exactly what I was looking for.",
                        MessageType.MESSAGE_RECIEVED
                    )
                )
            }

            in 0..29 -> {
                appendMsg(
                    Message(
                        "You are no where near the answer \uD83E\uDD26. If you need help, simply text 'help' to view a list of commands that could potentially help you.",
                        MessageType.MESSAGE_RECIEVED
                    )
                )
            }

            else -> {
                appendMsg(
                    Message(
                        "Either my developer really likes bugs \uD83E\uDD37, or you just really missed the mark on this one!",
                        MessageType.MESSAGE_RECIEVED
                    )
                )
            }
        }
    }

    /**
     * This function aims to "simulate" loading on responses from the game
     */
    private fun doLater(timeInMills : Long, args : Any?, work: (args: Any?) -> Unit) {

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                work(args)
            }
        }, timeInMills)
    }
}