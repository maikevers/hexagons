import org.junit.Test
import kotlin.test.assertEquals
import arrow.core.*
import hexagons.*

class CubeCoordinateTests {
    @Test
    fun `Creating CubeCoordinate with coordinates that don't sum to zero should return none`() {
        val cubeCoordinate = createCubeCoordinate(1, 1, 1)
        assertEquals(None, cubeCoordinate)
    }

    @Test
    fun `Creating CubeCoordinate with coordinates that sum to zero should be defined`() {
        val cubeCoordinate = createCubeCoordinate(1, 1, -2)
        assert(cubeCoordinate.isDefined())
    }

    @Test
    fun `Roundtrip conversion from cube to axial coordinates should return the same coordinate`() {
        val cubeCoordinate = createCubeCoordinate(1, 1, -2)

        val convertedAxial = cubeCoordinate.map(toAxial)
        val convertedCube = convertedAxial.map(fromAxial)

        assertEquals(cubeCoordinate, convertedCube)
    }

    @Test
    fun `Multiplication by 0 should return 0`() {
        val cubeCoordinate = createFromAxial(1, 2)
        val multiplier = createCubeCoordinate(0, 0, 0).getOrElse { throw Exception() }

        var multiplied = multiply(cubeCoordinate, multiplier)
        assertEquals(multiplier, multiplied)
    }
}
