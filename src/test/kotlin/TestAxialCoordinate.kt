import hexagons.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestAxialCoordinate {
    @Test
    fun `Roundtrip conversion from axial to cube coordinates should return the same coordinate`(){
        val axialCoordinate = AxialCoordinate(1,2)

        val convertedCube = toCube(axialCoordinate)
        val convertedAxial = fromCube(convertedCube)

        assertEquals(axialCoordinate, convertedAxial)
    }
}