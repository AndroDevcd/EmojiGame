package com.example.emojiigame.presentation.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.AnimRes
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.emojiigame.R
import com.example.emojiigame.domain.Message
import com.example.emojiigame.domain.MessageType


class RecyclerViewAdapter(val context : Activity) :
    PagedListAdapter<Message, RecyclerViewAdapter.ViewHolder>(
        diff
    ) {

    private var lastPosition = 0
    class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        var messageBody: TextView = v.findViewById(R.id.messageBody)
        var messageContainer: RelativeLayout = v.findViewById(R.id.messageContainer)
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.type == newItem.type && oldItem.message == newItem.message
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val msg = getItem(position)

        return if(msg?.type == MessageType.MESAGE_SENT) {
            MessageType.MESAGE_SENT.value.toInt()
        } else {
            MessageType.MESSAGE_RECIEVED.value.toInt()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)

            .inflate(if(viewType == MessageType.MESSAGE_RECIEVED.value.toInt())
                R.layout.message_recieve_layout else R.layout.message_send_layout, parent, false)
        return ViewHolder(
            v
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val msg = getItem(position)

        if(msg?.type == MessageType.MESAGE_SENT) {
            holder.messageBody.text = msg.message
            setAnim(holder, R.anim.right_left_anim, position)
        } else {
            holder.messageBody.text = msg?.message
            setAnim(holder, R.anim.left_right_anim, position)
        }
    }

    private fun setAnim(holder : ViewHolder, @AnimRes layout : Int, position: Int) {

        if(position > lastPosition) {
            lastPosition = itemCount - 1
            val anim = AnimationUtils.loadAnimation(context, layout)
            anim.interpolator = DecelerateInterpolator()
            anim.duration = 250
            anim.startOffset = 400
            anim.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    holder.messageContainer.visibility = View.VISIBLE
                }
                override fun onAnimationEnd(animation: Animation) {
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })

            holder.messageContainer.startAnimation(anim)
        } else {
            holder.messageContainer.visibility = View.VISIBLE
        }
    }
}
