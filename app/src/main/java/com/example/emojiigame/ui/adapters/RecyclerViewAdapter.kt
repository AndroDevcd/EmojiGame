package com.example.emojiigame.ui.adapters

import android.app.Activity
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.RecyclerView
import com.example.emojiigame.R
import com.example.emojiigame.service.model.Message
import com.example.emojiigame.service.model.MessageType


class RecyclerViewAdapter(val context : Activity, var results: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var lastPosition = results.size - 1
    class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        var messageBody: TextView = v.findViewById(R.id.messageBody)
        var messageContainer: RelativeLayout = v.findViewById(R.id.messageContainer)
    }

    override fun getItemViewType(position: Int): Int {
        val msg = results[position]

        return if(msg.type == MessageType.MESAGE_SENT) {
            MessageType.MESAGE_SENT.value
        } else {
            MessageType.MESSAGE_RECIEVED.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)

            .inflate(if(viewType == MessageType.MESSAGE_RECIEVED.value)
                R.layout.message_recieve_layout else R.layout.message_send_layout, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val msg = results[position]

        if(msg.type == MessageType.MESAGE_SENT) {
            holder.messageBody.text = msg.message
            setAnim(holder.messageContainer, R.anim.right_left_anim, position)
        } else {
            holder.messageBody.text = msg.message
            setAnim(holder.messageContainer, R.anim.left_right_anim, position)
        }
    }

    private fun setAnim(view : View, @AnimRes layout : Int, position: Int) {

        if(position > lastPosition) {
            lastPosition++
            val anim = AnimationUtils.loadAnimation(context, layout)
            anim.interpolator = DecelerateInterpolator()
            anim.duration = 250
            anim.startOffset = 400
            anim.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })

            view.startAnimation(anim)
        }
    }

    fun onUpdate(messages : ArrayList<Message>) {
        results = messages
        notifyItemInserted(results.size)
    }



    override fun getItemCount(): Int {
        return results.size
    }

}