package com.rivero.daniel.randomco.presentation.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.rivero.daniel.randomco.R
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.presentation.base.utils.showCircleImage


class UserListAdapter : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private var originalUserList: MutableList<User> = mutableListOf()
    private var userList: MutableList<User> = mutableListOf()
    var onItemClick: ((User) -> Unit)? = null
    var onClickDelete: ((User) -> Unit)? = null
    var onClickFavorite: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(userList[position])

    override fun getItemCount(): Int = userList.size

    fun swapData(userList: List<User>) {
        this.originalUserList = userList.toMutableList()
        this.userList = userList.toMutableList()
        notifyDataSetChanged()
    }

    fun notifyItemDeleted(user: User) {
        val index = userList.indexOfFirst { it.username == user.username }
        userList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun notifyItemModified(user: User) {
        val index = userList.indexOfFirst { it.username == user.username }
        userList[index] = user
        notifyItemChanged(index)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var name: TextView = itemView.findViewById(R.id.textName)
        private var email: TextView = itemView.findViewById(R.id.textEmail)
        private var phone: TextView = itemView.findViewById(R.id.textPhone)
        private var imageProfile: ImageView = itemView.findViewById(R.id.imageProfile)
        private var textOptionMenu: TextView = itemView.findViewById(R.id.textOptionMenu)

        fun bind(user: User) {
            name.text = user.getCompleteName()
            email.text = user.email
            phone.text = user.phone
            imageProfile.showCircleImage(itemView.context, user.picture?.medium)

            itemView.setOnClickListener { onItemClick?.invoke(user) }

            val menu = configureMenu(user)
            textOptionMenu.setOnClickListener { menu.show() }
        }

        private fun configureMenu(user: User): PopupMenu {
            val popupMenu = PopupMenu(itemView.context, textOptionMenu)
            popupMenu.inflate(R.menu.menu_user_item)

            if (user.favorite) popupMenu.menu.findItem(R.id.item_favorite).isVisible = false

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item_delete -> {
                        onClickDelete?.invoke(user)
                        true
                    }
                    R.id.item_favorite -> {
                        onClickFavorite?.invoke(user)
                        true
                    }
                    else -> false
                }
            }
            return popupMenu
        }

    }

}