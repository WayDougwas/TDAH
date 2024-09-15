package com.example.tdah.util

import java.time.LocalDate
import java.time.Period

object Age {
    fun calculateAge(birthDate: LocalDate, currentDate: LocalDate = LocalDate.now()): Int {
        require(birthDate <= currentDate) { "Birth date must not be in the future." }
        return Period.between(birthDate, currentDate).years
    }
}
