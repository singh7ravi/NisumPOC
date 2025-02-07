package com.example.nisumpoc.domain.utils

class Utils {
    companion object {
        fun filterUsersByGender(list: List<String>): List<FilterType> {
            return list.mapNotNull { filter ->
                try {
                    FilterType.valueOf(filter.uppercase()) // Convert string to enum
                } catch (e: IllegalArgumentException) {
                    null // Ignore invalid values
                }
            }
        }
    }
}