package com.intuisoft.emojiigame.presentation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.intuisoft.emojiigame.R
import com.intuisoft.emojiigame.framework.EmojiiViewModelFactory
import com.intuisoft.emojiigame.presentation.adapters.RecyclerViewAdapter
import com.intuisoft.emojiigame.presentation.util.Preferences
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    val VOICE_REQ_CODE = 100

    private lateinit var layoutManager : LinearLayoutManager;
    private lateinit var adapter : RecyclerViewAdapter;
    private val messagesViewModel: MainActivityViewModel by lazy {
        ViewModelProviders.of(this, EmojiiViewModelFactory)
            .get(MainActivityViewModel::class.java)
            .onCreate()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        answerEditText.setOnKeyListener(View.OnKeyListener(
            fun (view : View, keyCode : Int, event : KeyEvent) : Boolean {
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true

                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    hideKeyboard()
                    gameLoop()
                    return true
                }

                return false
            }
        ))

        messagesViewModel.getLiveData()!!.observe(this, Observer { items->
            adapter.submitList(items)
            messageList.smoothScrollToPosition(items.size)
        })

        layoutManager = LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        messageList.layoutManager = layoutManager;
        adapter =
            RecyclerViewAdapter(this)
        messageList.adapter = adapter

        if(Preferences.isUsersFirstOpen()) {
            Preferences.setFirstOpenEnabled(false)
            messagesViewModel.postWelcomeMessage()
        } else {
            messagesViewModel.postMessage("Welcome Back \uD83D\uDE4F!")
        }

        messagesViewModel.postQuestion()
    }



    fun hideKeyboard() {
        val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        imm!!.hideSoftInputFromWindow(answerEditText.windowToken, 0)
    }

    fun gameLoop() {
        val answer = answerEditText.text.toString()

        answerEditText.setText("")
        messagesViewModel.gameLoop(answer)
    }

    fun onVoiceBtnClicked(view : View) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Tap to stop listening")
        try {
            startActivityForResult(intent, VOICE_REQ_CODE)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                applicationContext,
                "Sorry your device does not support speech-to-text",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
             VOICE_REQ_CODE -> {
                if (resultCode == RESULT_OK && data != null) {
                    val result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result != null)
                        answerEditText.setText(answerEditText.text.toString() + result.get(0));
                        answerEditText.setSelection(answerEditText.text.toString().length)
                        answerEditText.requestFocus()
                }
            }
        }
    }
}
