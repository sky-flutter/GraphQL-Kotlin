package com.plus8.parcelpal.graphql_kotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.plus8.parcelpal.graphql_kotlin.adapter.UserListAdapter
import com.plus8.parcelpal.graphql_kotlin.api.ApiClient
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    lateinit var userListAdapter : UserListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        userListAdapter = UserListAdapter()
        rv_user_list.layoutManager =  LinearLayoutManager(this)
        rv_user_list.adapter  = userListAdapter
        getUserList()
    }

    fun getUserList() {
        val userListQuery = GetUserListQuery.builder().build()
        ApiClient().setUpApolloClient().query(userListQuery)
            .enqueue(object : ApolloCall.Callback<GetUserListQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Toast.makeText(this@HomeActivity, e.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(response: Response<GetUserListQuery.Data>) {
                    if (response.data() != null) {
                        userListAdapter.addData(response.data()?.userList)
                    }
                }

            })
    }
}
