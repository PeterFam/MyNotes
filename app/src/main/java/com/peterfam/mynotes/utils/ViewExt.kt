package com.peterfam.mynotes.utils

import androidx.appcompat.widget.SearchView

inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit){

    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?) = true

        override fun onQueryTextChange(newText: String?): Boolean {
          listener(newText.orEmpty())
            return true
        }
    })
}