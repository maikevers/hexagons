import arrow.core.None
import arrow.core.getOrElse
import hexagons.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

class TestCornerCoordinate {
    @Test
    fun `Corner coordinate equality should not care about order of constructor parameters`() {
        val cubeCoordinate = fromAxial(0, 0)
        val corner = fromCubeAndDirection(cubeCoordinate, CornerDirection.North)
        val cornerWithReverseConstructorParameters =
            fromCubeCoordinates(corner.c, corner.b, corner.a).getOrElse { throw Exception() }
        assertEquals(corner, cornerWithReverseConstructorParameters)
    }

    @Test
    fun `Corner coordinate hashcode should not care about order of constructor parameters`() {
        val cubeCoordinate = fromAxial(0, 0)
        val corner = fromCubeAndDirection(cubeCoordinate, CornerDirection.North)
        val cornerWithReverseConstructorParameters =
            fromCubeCoordinates(corner.c, corner.b, corner.a).getOrElse { throw Exception() }
        assertEquals(corner.hashCode(), cornerWithReverseConstructorParameters.hashCode())
    }

    @Test
    fun `A hexagon has six corners`() {
        val cubeCoordinate = fromAxial(0, 0)
        val corners = cornersOf(cubeCoordinate)
        assertEquals(6, corners.count())
    }

    @Test
    fun `Corners have 3 neighboring corners`() {
        val cubeCoordinate = fromAxial(0, 0)
        val corner = fromCubeAndDirection(cubeCoordinate, CornerDirection.North)
        val neighbours = neighborsOf(corner)
        assertEquals(3, neighbours.count())
    }

    @Test
    fun `Sides have 2 corners`() {
        val cubeCoordinate = fromAxial(0, 0)
        val side = fromCubeAndDirection(cubeCoordinate, SideDirection.West)
        val corners = cornersOf(side)
        assertEquals(2, corners.count())
    }

    @Test
    fun `Neighboring corners of south corner should contain southwest and southeast corners`() {
        val cubeCoordinate = fromAxial(0, 0)
        val southCorner = fromCubeAndDirection(cubeCoordinate, CornerDirection.South)
        val neighbours = neighborsOf(southCorner)
        val expectedNeighborsToContain = setOf(
            fromCubeAndDirection(cubeCoordinate, CornerDirection.SouthWest),
            fromCubeAndDirection(cubeCoordinate, CornerDirection.SouthEast)
        )
        assert(neighbours.containsAll(expectedNeighborsToContain))
    }

    @TestFactory
    fun `Converting corner coordinate to sides and back should yield same corner coordinate`(): Collection<DynamicTest> =
        cornerDirections().map {
            DynamicTest.dynamicTest("Converting $it to side coordinates and back should yield $it") {
                val corner = fromCubeAndDirection(fromAxial(0, 0), it)
                val sides = sidesOf(corner).toList()
                val cornerConvertedThereAndBack =
                    fromSideCoordinates(sides[0], sides[1], sides[2]).getOrElse { throw Exception() }
                assertEquals(corner, cornerConvertedThereAndBack)
            }
        }

    @Test
    fun `Invalid corner should be none`() {
        val a = fromAxial(0, 0)
        val b = neighborOf(a, SideDirection.East)
        val c = neighborOf(a, SideDirection.West)
        val invalidCorner = fromCubeCoordinates(a, b, c)
        assertEquals(None, invalidCorner)
    }

    @Test
    fun `Corner coordinate sides should be corner`() {
        val cubeCoordinate = fromAxial(0, 0)
        val corner = fromCubeAndDirection(cubeCoordinate, CornerDirection.North)
        val sides = sidesOf(corner).toList()
        assert(areCorner(sides[0], sides[1], sides[2]))
    }

    @Test
    fun `Corner of two sides of a corner should be the same corner`() {
        val cubeCoordinate = fromAxial(0, 0)
        val corner = fromCubeAndDirection(cubeCoordinate, CornerDirection.North)
        val sides = sidesOf(corner).toList()
        val cornerOfSides = fromSideCoordinates(sides[0], sides[1]).getOrElse { throw Exception() }
        assertEquals(corner, cornerOfSides)
    }

    @Test
    fun `Corner coordinate sides should be connected to each other`() {
        val cubeCoordinate = fromAxial(0, 0)
        val corner = fromCubeAndDirection(cubeCoordinate, CornerDirection.North)
        val sides = sidesOf(corner).toList()
        val sideA = sides[0]
        val sideB = sides[1]
        val sideC = sides[2]

        val areConnectedAB = areConnected(sideA, sideB)
        val areConnectedAC = areConnected(sideA, sideC)
        val areConnectedBC = areConnected(sideB, sideC)

        assert(areConnectedAB && areConnectedAC && areConnectedBC)
    }

    @Test
    fun `Next corners of south corner comming from southeast should include southwest corner`() {
        val cubeCoordinate = fromAxial(0,0)
        val southCorner = fromCubeAndDirection(cubeCoordinate, CornerDirection.South)
        val southWestCorner = fromCubeAndDirection(cubeCoordinate, CornerDirection.SouthWest)
        val southEastSide = fromCubeAndDirection(cubeCoordinate, SideDirection.SouthEast)

        val nextCorners = nextCorners(southCorner, southEastSide)

        assert(nextCorners.contains(southWestCorner))
    }
}