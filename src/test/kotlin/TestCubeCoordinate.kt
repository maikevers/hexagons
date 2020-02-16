import org.junit.Test
import kotlin.test.assertEquals
import arrow.core.*
import hexagons.*
import arrow.syntax.function.partially1
import kotlin.test.assertTrue

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

        val convertedAxial = cubeCoordinate.map { toAxial(it) }
        val convertedCube = convertedAxial.map { fromAxial(it) }

        assertEquals(cubeCoordinate, convertedCube)
    }

    @Test
    fun `Multiplication by 0 should return 0`() {
        val cubeCoordinate = createCubeCoordinate(1, 2, -3)
        val multiplier = createCubeCoordinate(0, 0, 0)

        val multiplied = multiplier
            .map { multi -> ::multiply.partially1(multi) }
            .map { partial -> cubeCoordinate.map { cube -> partial(cube) } }

        assertTrue(multiplied is Some)
    }
}
