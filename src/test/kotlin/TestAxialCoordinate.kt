import hexagons.*
import org.junit.Test
import kotlin.test.assertEquals

class AxialCoordinateTests {
    @Test
    fun `Roundtrip conversion from axial to cube coordinates should return the same coordinate`(){
        val axialCoordinate = AxialCoordinate(1,2)

        val convertedCube = toCube(axialCoordinate)
        val convertedAxial = fromCube(convertedCube)

        assertEquals(axialCoordinate, convertedAxial)
    }
}