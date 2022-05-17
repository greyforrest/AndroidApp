package ch.bbbaden.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.messengerapplication.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class UebersichtAppActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Firebase.firestore
        auth = Firebase.auth
        if (auth.currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }
        setContentView(R.layout.uebersicht_app)
        title = "Nutzer dieser App"
        val contacts: ArrayList<Kontakt> = Kontakt.getKontakt()
        val users = db.collection("user")
        val data = hashMapOf(
            "username" to auth.currentUser?.displayName.toString(),
            "photoUrl" to auth.currentUser?.photoUrl.toString()
        )
        users.document(auth.currentUser?.uid.toString()).set(data)
        /*db.collection("user").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val inputData = document.data
                if (document.id != auth.currentUser?.uid) {
                    contacts.add(
                        Kontakt(
                            document.id,
                            inputData["username"].toString(),
                            inputData["photoUrl"].toString()
                        )
                    )
                }
            }
        }.addOnFailureListener { exception ->
            Log.d("Test", "Error getting documents", exception)
        } */
        val listView: ListView = findViewById(R.id.listViewÜbersicht)
        val adapter = KontaktAdapter(this, contacts)
        listView.adapter = adapter

        val context = this
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedContact = contacts[position]

            val detailIntent = KontaktDetailActivity.newIntent(context, selectedContact)
            startActivity(detailIntent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }
    }

    fun signOut(view: View) {
        Firebase.auth.signOut()
        startActivity(Intent(this, SignInActivity::class.java))
    }
}
