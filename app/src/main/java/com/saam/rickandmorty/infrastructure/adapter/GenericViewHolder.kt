package com.saam.rickandmorty.infrastructure.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class GenericViewHolder<T>(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bindTo(model: T?)
}