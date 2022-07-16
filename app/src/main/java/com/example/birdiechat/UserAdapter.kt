package com.arpit.birdiechat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.birdiechat.ChatActivity
import com.example.birdiechat.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.user_layout.view.*

class UserAdapter(val context: Context , val userList : ArrayList<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtName = itemView.tvName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_layout , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.txtName.text = currentUser.name
        holder.itemView.setOnClickListener {
            val intent = Intent(context , ChatActivity::class.java)
            intent.putExtra("name" , currentUser.name)
            intent.putExtra("uid" , currentUser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}