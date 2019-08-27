package com.plus8.parcelpal.graphql_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.plus8.parcelpal.graphql_kotlin.api.ApiClient
import com.plus8.parcelpal.graphql_kotlin.type.RegisterInput
import com.plus8.parcelpal.graphql_kotlin.utils.CustomValidator
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var customValidator: CustomValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        customValidator = CustomValidator()
        btn_register.setOnClickListener {
            if (customValidator.checkEmptyString(
                    ed_name,
                    "Enter name"
                ) && customValidator.checkEmail(ed_email) && customValidator.checkEmptyString(
                    ed_password,
                    "Enter password"
                )
            ) {
                userRegister()
            }
        }
    }

    private fun userRegister() {
        val registerInput = RegisterInput.builder().name(ed_name.text.toString()).email(ed_email.text.toString())
            .password(ed_password.text.toString()).build()
        val registerMutation = RegisterMutation.builder().registerInput(registerInput).build()

        ApiClient().setUpApolloClient().mutate(registerMutation).enqueue(object :
            ApolloCall.Callback<RegisterMutation.Data>() {
            override fun onFailure(e: ApolloException) {
                Toast.makeText(this@RegisterActivity, "Error in register please enter valid details", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onResponse(response: Response<RegisterMutation.Data>) {
                if (response.data()?.register == null) {
                    runOnUiThread {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Error in register please enter valid details",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
        })

    }
}
