package ch.bbbaden.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.messengerapplication.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class KontaktDetailActivity : AppCompatActivity() {
    private lateinit var facingContact : Kontakt
    override fun onCreate(savedInstanceState: Bundle?) {
        val auth = Firebase.auth
        val db = Firebase.firestore
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kontakt_detail)
        val contactName = intent.extras?.getString(EXTRA_NAME)
        facingContact = Kontakt(intent.extras?.getString(EXTRA_ID),intent.extras?.getString(EXTRA_NAME), intent.extras?.getString(
            EXTRA_PIC))
        title = contactName
        val usersForDB = mutableListOf(auth.currentUser?.uid, facingContact.id)
        var messageLog = "Message Log:"
        db.collection("messages").whereIn("from",usersForDB).orderBy("timestamp").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val data = document.data
                if (data["to"] in usersForDB) {
                    val fromUser: String = if (data["from"] == auth.currentUser?.uid) {
                        auth.currentUser?.displayName.toString()
                    } else {
                        facingContact.name
                    }
                    messageLog += "\n" + fromUser + ": " + data["message"]
                }
            }
        }.addOnFailureListener { exception ->
            Log.d("Test", "Error getting documents", exception) }
        val textView: TextView = findViewById(R.id.textViewMessageLog) as TextView
        textView.text = messageLog
    }

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_ID = "userid"
        const val EXTRA_PIC = "photoUrl"

        fun newIntent(context: Context, contact : Kontakt) : Intent {
            val detailIntent = Intent(context, KontaktDetailActivity::class.java)
            detailIntent.putExtra(EXTRA_NAME, contact.name)
            detailIntent.putExtra(EXTRA_ID, contact.id)
            detailIntent.putExtra(EXTRA_PIC, contact.imageURL)

            return detailIntent

        }
    }

    fun sendMessage(view : View) {
        val auth = Firebase.auth
        val db = Firebase.firestore
        val databaseRef = db.collection("messages")
        val data = hashMapOf(
            "from" to auth.currentUser?.uid,
            "message" to findViewById<TextInputEditText>(R.id.textfieldMessage).text.toString(),
            "to" to facingContact.id,
            "timestamp" to System.currentTimeMillis()
        )

        databaseRef.add(data)
        findViewById<TextInputEditText>(R.id.textfieldMessage).text?.clear()

        val usersForDB = mutableListOf(auth.currentUser?.uid, facingContact.id)
        var messageLog = "Message Log:"
        db.collection("messages").whereIn("from",usersForDB).orderBy("timestamp").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val data = document.data
                if (data["to"] in usersForDB) {
                    val fromUser: String = if (data["from"] == auth.currentUser?.uid) {
                        auth.currentUser?.displayName.toString()
                    } else {
                        facingContact.name
                    }
                    messageLog += "\n" + fromUser + ": " + data["message"]
                }
            }
        }.addOnFailureListener { exception ->
            Log.d("Test", "Error getting documents", exception) }
        val textView: TextView = findViewById(R.id.textViewMessageLog) as TextView
        textView.text = messageLog
    }


}