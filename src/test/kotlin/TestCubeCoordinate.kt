import arrow.core.None
import arrow.core.Some
import arrow.core.getOrElse
import arrow.syntax.function.partially1
import hexagons.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

class TestCubeCoordinate {
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
    fun `A hexagon has six neighbours`() {
        val cubeCoordinate = fromAxial(0, 0)
        val neighbors = neighborsOf(cubeCoordinate)
        assertEquals(6, neighbors.count())
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

    @Test
    fun `Move by 1 should return neighbor`() {
        val cubeCoordinate = fromAxial(0, 0)
        val neighbor = neighborOf(cubeCoordinate, SideDirection.East)

        val moved = move(cubeCoordinate, SideDirection.East, 1)

        assertEquals(neighbor, moved)
    }

    @TestFactory
    fun `Moving in one direction and then in opposing direction should return to start coordinate`(): Collection<DynamicTest> =
        listOf(
            SideDirection.NorthEast to SideDirection.SouthWest,
            SideDirection.East to SideDirection.West,
            SideDirection.SouthEast to SideDirection.NorthWest
        ).map {
            dynamicTest("Moving ${it.first} and then ${it.second} should return to start coordinate") {
                val startCoordinate = createCubeCoordinate(0, 0)
                val movedThereAndBack = neighborOf(neighborOf(startCoordinate, it.first), it.second)
                assertEquals(startCoordinate, movedThereAndBack)
            }
        }

    @TestFactory
    fun `Neighbours should be neighbours`(): Collection<DynamicTest> =
        sideDirections().map {
            dynamicTest("Neighbour in direction $it should be neighbour") {
                val startCubeCoordinate = fromAxial(0, 0)
                val neighbour = neighborOf(startCubeCoordinate, it)
                val areNeighbours = areNeighbors(startCubeCoordinate, neighbour)
                assertTrue(areNeighbours)
            }
        }

    @TestFactory
    fun `Directions should yield expected cube coordinates`(): Collection<DynamicTest> =
        listOf(
            SideDirection.NorthEast to fromAxial(1, 0),
            SideDirection.East to fromAxial(1, -1),
            SideDirection.SouthEast to fromAxial(0, -1),
            SideDirection.SouthWest to fromAxial(-1, 0),
            SideDirection.West to fromAxial(-1, 1),
            SideDirection.NorthWest to fromAxial(0, 1)
        ).map {
            dynamicTest("Direction ${it.first} should be ${it.second}") {
                val cubeFromSideDirection = fromSideDirection(it.first)
                assertEquals(it.second, cubeFromSideDirection)
            }
        }

    @TestFactory
    fun `Conversion of sideDirection to cube coordinate and back yields same sideDirection`(): Collection<DynamicTest> =
        sideDirections().map {
            dynamicTest("Converting $it to cube coordinate and back should yield $it") {
                val convertedThereAndBack = fromCubeCoordinate(fromSideDirection(it))
                assertTrue(convertedThereAndBack.isDefined())
                assertEquals(it, convertedThereAndBack.getOrElse { throw Exception() })
            }
        }

    @TestFactory
    fun `Neighbourhood should be expected size`(): Collection<DynamicTest> =
        (0..3).associateBy({ it }, { neighborHoodSize(it) }).map {
            dynamicTest("Neighbourhood of ${it.key} steps should be of size ${it.value}") {
                val cubeCoordinate = fromAxial(0, 0)
                val neighbourhood = neighborhoodOf(cubeCoordinate, it.key)
                assertEquals(it.value, neighbourhood.count())
            }
        }
}
