package com.example.birdiechat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.sent.view.*

class MessageAdapter(val context: Context, val messageList : ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val ITEM_RECIEVE = 1
    val ITEM_SENT = 2
    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val sentMessage = itemView.findViewById<TextView>(R.id.tvsentMes)
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val recieveMessage = itemView.findViewById<TextView>(R.id.tvreceiveMes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view = LayoutInflater.from(context).inflate(R.layout.receive , parent , false)
            return ReceiveViewHolder(view)
        }else{
            val view = LayoutInflater.from(context).inflate(R.layout.sent, parent , false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
        }else{
            val viewHolder = holder as ReceiveViewHolder
            holder.recieveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
       if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
           return ITEM_SENT
       } else{
           return ITEM_RECIEVE
       }
    }

    override fun getItemCount(): Int {
       return messageList.size
    }
}