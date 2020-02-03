package com.intuisoft.emojiigame.framework

import android.content.Context
import com.intuisoft.core.data.MessageDataSource
import com.intuisoft.core.data.QuestionDataSource
import com.intuisoft.core.domain.Question
import com.intuisoft.emojiigame.framework.db.EmojiiGameDatabase
import kotlin.random.Random


class RoomQuestionDataSource(val context: Context) :
    QuestionDataSource {

    private var localQuestions: ArrayList<Question>? = null
    private val questionDao = EmojiiGameDatabase.getInstance(context).questionDao()

    override suspend fun getAll(): List<Question>  {
        var dbQuestions : ArrayList<Question>? =
            questionDao.getAllQuestions()?.map { it ->
                Question(
                    it.question,
                    it.answer
                )
            } as ArrayList<Question>

        if(dbQuestions == null || dbQuestions.isEmpty()) {
            dbQuestions = setupQuestions()
            questionDao.insertQuestion(dbQuestions.map { q -> EmojiiGameDatabase.getInstance(context).toDb(q) })
        }

        return dbQuestions
    }
    override suspend fun randomQuestion(): Question {
        if(localQuestions == null)
            localQuestions = ArrayList(getAll())
        return localQuestions!!.get(
            Random.nextInt(0, localQuestions!!.size - 1))
    }

    override suspend fun setupQuestions() : ArrayList<Question> {
        var ques = arrayListOf<Question>()
        ques.add(
            Question(
                "Name the drama show: \uD83C\uDF4A\uD83D\uDC49\uD83C\uDD95⚫",
                "Orange is the new black"
            )
        );
        ques.add(
            Question(
                "Name the drama show: ⛰️⛰️",
                "Twin peaks"
            )
        );
        ques.add(
            Question(
                "Name the horror movie: \uD83C\uDF83\uD83C\uDF83\uD83C\uDF83\uD83C\uDF83",
                "Halloween 4"
            )
        );
        ques.add(
            Question(
                "Name the horror movie: \uD83D\uDC40\uD83C\uDF04\uD83C\uDF04\uD83D\uDC40",
                "The hills have eyes"
            )
        );
        ques.add(
            Question(
                "Name the crime movie: \uD83D\uDC7B\uD83D\uDC49\uD83D\uDC1A",
                "Ghost in the shell"
            )
        );
        ques.add(
            Question(
                "Name the crime movie: \uD83D\uDC3A\uD83D\uDCC8\uD83D\uDCC8\uD83D\uDCB0",
                "Wolf of wall street"
            )
        );
        ques.add(
            Question(
                "Name the animated movie: \uD83E\uDD81\uD83D\uDC51",
                "The lion king"
            )
        );
        ques.add(
            Question(
                "Name the celebrity best actress: \uD83D\uDD1D\uD83D\uDD2B",
                "Kelly Mcgillis"
            )
        );
        ques.add(
            Question(
                "Name the musical movie: \uD83D\uDEAA\uD83D\uDEAA",
                "The doors"
            )
        );
        ques.add(
            Question(
                "Name the monster movie: \uD83E\uDD96\uD83D\uDD25\uD83D\uDD25\uD83C\uDFD9",
                "Godzilla"
            )
        );

        return ques
    }

    override suspend fun getLocalQuestions(): List<Question> {
        if(localQuestions == null)
            localQuestions = ArrayList(getAll())
        return localQuestions as ArrayList<Question>
    }

}