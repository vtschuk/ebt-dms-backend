package de.ass73.ebt.tst.efile.backend.puzzles

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

data class MyClass(
    val date: Date
)

class TestObjektMapper {
    val objektMapper = jacksonObjectMapper()
    val myClass = MyClass(Date(Calendar.getInstance().time.time))


    @Test
    fun convertToJon() {
        objektMapper.setDateFormat(SimpleDateFormat("yyy-MM-dd"))
        println(objektMapper.writeValueAsString(myClass))
    }
}