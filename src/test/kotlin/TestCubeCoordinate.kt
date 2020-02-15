import hexagons.CubeCoordinate
import hexagons.toAxial
import org.junit.Test
import kotlin.test.assertEquals
import arrow.core.*

class CubeCoordinateTests {
    @Test
    fun `Creating CubeCoordinate with coordinates that don't sum to zero should return none`() {
        var cubeCoordinate = CubeCoordinate.create(1,1,1)
        assertEquals(None, cubeCoordinate)
    }

    @Test
    fun `Creating CubeCoordinate with coordinates that sum to zero should be defined`() {
        var cubeCoordinate = CubeCoordinate.create(1,1,-2)
        assert(cubeCoordinate.isDefined())
    }

    @Test
    fun `Roundtrip conversion from cube to axial coordinates should return the same coordinate`(){
        var cubeCoordinate = CubeCoordinate.create(1,1,-2)

        var convertedAxial = cubeCoordinate.map(toAxial)
        var convertedCube = convertedAxial.map(CubeCoordinate.fromAxial)

        assertEquals(cubeCoordinate, convertedCube)
    }
}