import arrow.core.None
import arrow.core.Some
import arrow.syntax.function.partially1
import hexagons.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

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

    @TestFactory
    fun `Moving in one direction and then in opposing direction should return to start coordinate`() : Collection<DynamicTest> =
        listOf(
            NorthEast to SouthWest,
            East to West,
            SouthEast to NorthWest
        ).map {
            dynamicTest("Moving ${it.first} and then ${it.second} should return to start coordinate") {
                val startCoordinate = createCubeCoordinate(0, 0)
                val movedThereAndBack = neighbor(neighbor(startCoordinate, it.first), it.second)
                assertEquals(startCoordinate, movedThereAndBack)
            }
        }

}
