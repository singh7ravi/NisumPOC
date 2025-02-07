package com.example.nisumpoc.domain.utils

import com.example.nisumpoc.data.model.User


object Converter {

    fun filterUsersItemsByNamePrefix(cryptoItems: List<User>, prefix: String): List<User> {
        return cryptoItems.filter { it.name.first.startsWith(prefix, ignoreCase = true)
                || it.name.last.startsWith(prefix, ignoreCase = true)
          }
    }

    fun getFilterListByOption(users: List<User>, filters: List<FilterType>): List<User> {
        return users.filter { user ->
            val userGender = user.gender.lowercase() // Convert user gender to lowercase for comparison
            filters.any { filter -> filter.name.lowercase() == userGender } // Compare with filter types
        }
    }
}