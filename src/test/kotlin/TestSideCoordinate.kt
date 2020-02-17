import arrow.core.getOrElse
import hexagons.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

class TestSideCoordinate {
    @Test
    fun `Side coordinate equality should not care about order of constructor parameters`() {
        val cubeCoordinateA = fromAxial(0, 0)
        val side = fromCubeAndDirection(cubeCoordinateA, SideDirection.SouthWest)
        val sideWithReverseConstructorParameters = fromCubeCoordinates(side.n, side.m).getOrElse { throw Exception() }
        assertEquals(side, sideWithReverseConstructorParameters)
    }

    @TestFactory
    fun `Opposing sides of cube coordinate should share that cube coordinate`(): Collection<DynamicTest> =
        sideDirections().map {
            DynamicTest.dynamicTest("Converting $it to side coordinates and back should yield $it") {
                val cube = fromAxial(0, 0)
                val side = fromCubeAndDirection(cube, it)
                val opposingSide = fromCubeAndDirection(cube, oppositeOf(it))
                val sharedCube = sharedCubeCoordinateOf(side, opposingSide).getOrElse { throw Exception() }
                assertEquals(cube, sharedCube)
            }
        }

    @Test
    fun `A hexagon has six sides`() {
        val cubeCoordinate = fromAxial(0, 0)
        val sides = sidesOf(cubeCoordinate)
        assertEquals(6, sides.count())
    }
}