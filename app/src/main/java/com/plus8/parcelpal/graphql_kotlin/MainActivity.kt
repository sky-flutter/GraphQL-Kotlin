package com.plus8.parcelpal.graphql_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.plus8.parcelpal.graphql_kotlin.api.ApiClient
import com.plus8.parcelpal.graphql_kotlin.utils.CustomValidator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var customValidator: CustomValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        customValidator = CustomValidator()
        tv_register_user.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        btn_login.setOnClickListener {
            if (customValidator.checkEmail(ed_email) && customValidator.checkEmptyString(
                    ed_password,
                    "Enter password"
                )
            ) {
                checkUserLogin()
            }
        }
    }

    private fun checkUserLogin() {
        val loginMutation =
            LoginMutation.builder().email(ed_email.text.toString()).password(ed_password.text.toString()).build()

        ApiClient().setUpApolloClient().mutate(loginMutation).enqueue(object :
            ApolloCall.Callback<LoginMutation.Data>() {
            override fun onResponse(response: Response<LoginMutation.Data>) {
                if (response.data()?.login == null) {
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "Error in login please enter valid credentials",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(e: ApolloException) {
                Toast.makeText(this@MainActivity, "Error in login please enter valid credentials", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }
}
