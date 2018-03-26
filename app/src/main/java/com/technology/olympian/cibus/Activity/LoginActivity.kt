package com.technology.olympian.cibus.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.technology.olympian.cibus.R



class LoginActivity : AppCompatActivity() {

    private var mAuth:FirebaseAuth? = null
    private var currenUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()



        var username = findViewById<EditText>(R.id.emailText)
        var uid = username.text.toString().trim()

        var password = findViewById<EditText>(R.id.passwordText)
        var pwd = password.text.toString().trim()


        var login = findViewById<Button>(R.id.loginButton)
        login.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name", uid)
            startActivity(intent)

//            loggedIn(uid,pwd)



        }


    }


//    override fun onStart() {
//        super.onStart()
//
//        currenUser = mAuth!!.currentUser
//
//        if(currenUser!=null)
//            Toast.makeText(this,"Logged In",Toast.LENGTH_SHORT).show()
//        else
//            Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show()
//
//    }

    private fun loggedIn(uid:String,pwd:String){
        if(!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(pwd)) {
            mAuth!!.signInWithEmailAndPassword(uid, pwd)
                    .addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            var intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("name", uid)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Invalid Username/Password", Toast.LENGTH_SHORT).show()
                        }
                    }
        }

        else
            Toast.makeText(this, "Enter Valid Email-id/Password", Toast.LENGTH_SHORT).show()

    }

}

