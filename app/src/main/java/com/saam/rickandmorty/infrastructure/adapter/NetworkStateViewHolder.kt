package com.saam.rickandmorty.infrastructure.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saam.rickandmorty.R
import kotlinx.android.synthetic.main.item_network_state.view.*

class NetworkStateViewHolder(
    val view: View,
    private val retryCallback: () -> Unit
): RecyclerView.ViewHolder(view) {
    init {
        itemView.retry_button.setOnClickListener { retryCallback() }
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_network_state, parent, false)
            return NetworkStateViewHolder(view, retryCallback)
        }
    }

    fun bindTo(networkState: NetworkState?) {
        if (networkState?.message != null) itemView.error_message.text = networkState.message

        itemView.error_message.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        itemView.retry_button.visibility = if (networkState?.status == NetworkStatus.FAILED) View.VISIBLE else View.GONE
        itemView.loading_bar.visibility = if (networkState?.status == NetworkStatus.RUNNING) View.VISIBLE else View.GONE
    }
}