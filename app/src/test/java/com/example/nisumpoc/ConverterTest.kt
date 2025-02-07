package com.example.nisumpoc

import com.example.nisumpoc.data.model.Location
import com.example.nisumpoc.data.model.Name
import com.example.nisumpoc.data.model.Picture
import com.example.nisumpoc.data.model.Street
import com.example.nisumpoc.domain.utils.Converter
import com.example.nisumpoc.domain.utils.FilterType
import com.example.nisumpoc.presentation.screens.User
import org.junit.Assert.assertEquals
import org.junit.Test

class ConverterTest {
    private val users = listOf(
        User("male", Name("Rahul", "Gandhi", "Mr."), "rahul@example.com", "1234567890", Location(Street(1, "Amethi"), "Delhi", ""), Picture("url", "")),
        User("female", Name("Mamata", "Banarji", "Mrs."), "mamata@example.com", "1122334455", Location(Street(3, "Kolkata"), "", "Bengal"), Picture("url", "")),
        User("male", Name("Narendra", "Modi", "Mr."), "narendra@example.com", "0987654321", Location(Street(2, "Varanasi"), "Ahemdabad", ""), Picture("url", ""))
    )

    @Test
    fun `filterUsersItemsByNamePrefix should return users with matching name prefix`() {
        val prefix = "Ra"
        val expectedOutput = listOf(users[0])

        val actualOutput = Converter.filterUsersItemsByNamePrefix(users, prefix)

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `filterUsersItemsByNamePrefix should be case insensitive`() {
        val prefix = "mA"
        val expectedOutput = listOf(users[1])

        val actualOutput = Converter.filterUsersItemsByNamePrefix(users, prefix)

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `filterUsersItemsByNamePrefix should return empty list if no match`() {
        val prefix = "Xy"
        val expectedOutput = emptyList<User>()

        val actualOutput = Converter.filterUsersItemsByNamePrefix(users, prefix)

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `getFilterListByOption should return filtered users based on gender`() {
        val filters = listOf(FilterType.MALE)
        val expectedOutput = listOf(users[0], users[2])

        val actualOutput = Converter.getFilterListByOption(users, filters)

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `getFilterListByOption should be case insensitive`() {
        val filters = listOf(FilterType.FEMALE)
        val expectedOutput = listOf(users[1])

        val actualOutput = Converter.getFilterListByOption(users, filters)

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `getFilterListByOption should return empty list if no match`() {
        val filters = listOf(FilterType.OTHER)
        val expectedOutput = emptyList<User>()

        val actualOutput = Converter.getFilterListByOption(users, filters)

        assertEquals(expectedOutput, actualOutput)
    }
}