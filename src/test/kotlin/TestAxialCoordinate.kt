import hexagons.*
import org.junit.Test
import kotlin.test.assertEquals

class AxialCoordinateTests {
    @Test
    fun `Roundtrip conversion from axial to cube coordinates should return the same coordinate`(){
        var axialCoordinate = AxialCoordinate(1,2)

        var convertedCube = toCube(axialCoordinate)
        var convertedAxial = fromCube(convertedCube)

        assertEquals(axialCoordinate, convertedAxial)
    }
}