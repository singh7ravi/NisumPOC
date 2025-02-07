package com.example.nisumpoc.domain.utils

import com.example.nisumpoc.data.model.User


data class ViewState(
    val filterList: List<User> = emptyList()
)
