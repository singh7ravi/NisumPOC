package com.example.nisumpoc.domain.utils

import com.example.nisumpoc.data.model.FilterCategory


const val FILTER_MALE = "MALE"
const val FILTER_FEMALE = "FEMALE"
const val FILTER_OTHER = "OTHER"
val filterCategories = listOf(
    FilterCategory(FILTER_MALE, false),
    FilterCategory(FILTER_FEMALE, false),
    FilterCategory(FILTER_OTHER, false),
)
enum class FilterType {
    MALE,
    FEMALE,
    OTHER
}
const val BASE_URL = "https://randomuser.me/"