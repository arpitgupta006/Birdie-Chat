package com.example.birdiechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRV : RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef : DatabaseReference

    var senderRoom: String? = null
    var receiverRoom: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

      //  val intent = Intent()
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        mDbRef = FirebaseDatabase.getInstance().getReference()
        val senderUid = FirebaseAuth.getInstance().currentUser!!.uid
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar!!.title = name
        chatRV = findViewById(R.id.chatRV)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this , messageList)

        chatRV.layoutManager = LinearLayoutManager(this)
        chatRV.adapter = messageAdapter

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()
                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        buSend.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message , senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }
    }
}