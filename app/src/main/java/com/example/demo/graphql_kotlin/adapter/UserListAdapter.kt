package com.example.demo.graphql_kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.graphql_kotlin.GetUserListQuery
import com.example.demo.graphql_kotlin.R
import kotlinx.android.synthetic.main.item_user_list.view.*

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    lateinit var mContext: Context
    private val listUser = ArrayList<GetUserListQuery.GetUserList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        mContext = parent.context
        val mView = LayoutInflater.from(mContext).inflate(R.layout.item_user_list, parent, false)
        return UserViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userList = listUser[position]
        holder.tvEmail.text = userList.email()
        holder.tvName.text = userList.name()
    }

    fun addData(userList: List<GetUserListQuery.GetUserList>?) {
        listUser.addAll(userList!!)
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvEmail: TextView = itemView.tv_email
        var tvName: TextView = itemView.tv_name
    }
}