package com.intuisoft.emojiigame.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.intuisoft.emojiigame.R
import com.intuisoft.core.domain.Message
import com.intuisoft.core.domain.MessageCommand
import com.intuisoft.core.domain.MessageType
import com.intuisoft.core.domain.Question
import com.intuisoft.emojiigame.framework.BaseViewModel
import com.intuisoft.emojiigame.framework.EmojiiViewModelFactory
import com.intuisoft.emojiigame.framework.UseCases
import com.intuisoft.emojiigame.presentation.util.getWordAccuracyPercent
import kotlinx.coroutines.*
import kotlin.random.Random

class MainActivityViewModel(application: Application, useCases: UseCases) : BaseViewModel(application, useCases) {

    private var data : LiveData<PagedList<Message>>? = null;
    private val coroutineCtx = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun getLiveData() : LiveData<PagedList<Message>>? {
        return data
    }

    fun postWelcomeMessage() {
        appendMsg(useCases.getWelcomeMessage())
    }

    fun onCreate() : MainActivityViewModel {
        data = useCases.getLiveData()
        return this
    }

    fun postMessage(msg : String, type : MessageType = MessageType.MESSAGE_RECIEVED, command : MessageCommand? = null) {
        coroutineCtx.launch {
            appendMsg(
                Message(
                    msg,
                    type
                ), command
            )
        }
    }

    fun postQuestion() {
//        data.value = Resource.loading(data.value)
        coroutineCtx.launch {
            delay(Random.nextLong(500, 1000))

            appendMsg(useCases.buildReceiveMessage(useCases.getRandomQuestion().question))
        }
    }


    /**
     * @msg - the message the user sees
     * @responseMsg - the message the game inspects to check for various commands
     */
    private fun appendMsg(msg : Message, command : MessageCommand? = null) {
        when(command) {
            MessageCommand.NEXT_QUESTION -> {
                postQuestion()
            }
        }

        coroutineCtx.launch {
            useCases.sendMessage(msg)
        }
    }

    fun gameLoop(userAnswer : String) {

        postMessage(userAnswer, MessageType.MESAGE_SENT)
//        data.value = Resource.loading(data.value)
        coroutineCtx.launch {
            delay(Random.nextLong(500, 2000))

            run {
                var questionList = useCases.getLocalQuestions()
                var chatHist = data?.value?.snapshot()
                var gameQuestion = "";

                if (chatHist != null) {

                    for (i in chatHist.size - 1 downTo 0) {
                        if (chatHist[i].type == MessageType.MESSAGE_RECIEVED && chatHist[i].message.startsWith(
                                "Name the"
                            )
                        ) {
                            gameQuestion = chatHist[i].message
                            break
                        }
                    }

                    var emojiQuestion = questionList.find { it.question == gameQuestion }
                    if (userAnswer.contains("help")) {
                        postMessage(application.getString(R.string.help_message))
                    } else if (getWordAccuracyPercent("hi", userAnswer) >= 85 || getWordAccuracyPercent("hello", userAnswer) >= 85) {
                        postMessage(application.getString(R.string.hello_message))
                    } else if (userAnswer.contains("next question")
                        || userAnswer.contains("next")
                    ) {
                        postMessage(
                            msg = "Got it! Here's your next question.",
                            command = MessageCommand.NEXT_QUESTION
                        )
                    } else if (userAnswer.contains("reveal")) {
                        postMessage(
                            msg = "Here's the answer to the question: " + emojiQuestion?.answer,
                            command = MessageCommand.NEXT_QUESTION
                        )
                    } else {
                        if (emojiQuestion != null) {
                            checkAnswer(emojiQuestion, userAnswer)
                        } else {
                            postMessage("It appears that my developer likes bugs \uD83D\uDE14, unfortunately I am unable to respond to your request.")
                        }
                    }
                } else {
                    postMessage("I am sorry, I do not understand. Please text \"help\" for more info on how to play the game.")
                }
            }
        }
    }

    /**
     * The purpose of this function is to simply check the answer based on the user's input
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
    fun checkAnswer(question : Question, usrAnswer : String) {
        if(usrAnswer.contains(question.answer)) {
            correctAnswer()
        } else {
            val userAccuracy = getWordAccuracyPercent(question.answer, usrAnswer)

            when (userAccuracy) {
                in 90..100 -> {
                    correctAnswer()
                }

                in 70..89 -> {
                    postMessage("You are really close \uD83D\uDC4C, but not quite the answer I was looking for, try modifying your answer slightly.")
                }

                in 30..69 -> {
                    postMessage("You're on the right track \uD83D\uDC4D but not exactly what I was looking for.")
                }

                in 0..29 -> {
                    postMessage("You are no where near the answer \uD83E\uDD26. If you need help, simply text 'help' to view a list of commands that could potentially help you.")
                }

                else -> {
                    postMessage("Either my developer really likes bugs \uD83E\uDD37, or you just really missed the mark on this one!")
                }
            }
        }
    }

    private fun correctAnswer() {
        postMessage(
            msg = "Awesome you're correct \uD83D\uDE00!",
            command = MessageCommand.NEXT_QUESTION
        )
    }
}