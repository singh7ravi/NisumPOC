package com.example.nisumpoc

import com.example.nisumpoc.domain.utils.FilterType
import com.example.nisumpoc.domain.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {
    @Test
    fun `filterUsersByGender should return correct FilterType list`() {
        val input = listOf("MALE", "FEMALE", "OTHER")
        val expectedOutput = listOf(FilterType.MALE, FilterType.FEMALE, FilterType.OTHER)

        val actualOutput = Utils.filterUsersByGender(input)

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `filterUsersByGender should ignore invalid values`() {
        val input = listOf("MALE", "INVALID", "FEMALE")
        val expectedOutput = listOf(FilterType.MALE, FilterType.FEMALE)

        val actualOutput = Utils.filterUsersByGender(input)

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `filterUsersByGender should be case insensitive`() {
        val input = listOf("male", "FeMale", "OTHER")
        val expectedOutput = listOf(FilterType.MALE, FilterType.FEMALE, FilterType.OTHER)

        val actualOutput = Utils.filterUsersByGender(input)

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `filterUsersByGender should return empty list for invalid input`() {
        val input = listOf("INVALID", "WRONG", "UNKNOWN")
        val expectedOutput = emptyList<FilterType>()

        val actualOutput = Utils.filterUsersByGender(input)

        assertEquals(expectedOutput, actualOutput)
    }
}