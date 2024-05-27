package de.ass73.ebt.tst.efile.backend.puzzles

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.modelmapper.ModelMapper

annotation class NoArg

@NoArg
data class ExampleEntity(
    var id: Long,
    var str: String
)

@NoArg
data class ExampleModel(
    var id: Long,
    var str: String
)

class TestMapperInKotlin {
    private val modelMapper = ModelMapper()

    @Test
    fun testMappingModel() {
        val entity = ExampleEntity(1, "Test")
        val model = ExampleModel(1, "Test")
        val retModel = modelMapper.map(entity, ExampleModel::class.java)
        Assertions.assertEquals(model.id, retModel.id)
        Assertions.assertEquals(model.str, retModel.str)
    }
}