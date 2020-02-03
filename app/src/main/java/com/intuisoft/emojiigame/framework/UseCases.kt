package com.intuisoft.emojiigame.framework

import com.intuisoft.core.usecases.*

data class UseCases (
    val buildReceiveMessage: BuildRecieveMessage,
    val buildSendMessage: BuildSendMessage,
    val getAllQuestions: GetAllQuestions,
    val getLiveData: GetLiveData,
    val getLocalQuestions: GetLocalQuestions,
    val getRandomQuestion: GetRandomQuestion,
    val getWelcomeMessage: GetWelcomeMessage,
    val sendMessage: SendMessage,
    val setupQuestions: SetupQuestions
)
