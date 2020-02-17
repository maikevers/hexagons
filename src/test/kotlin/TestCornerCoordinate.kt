import arrow.core.None
import arrow.core.getOrElse
import hexagons.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

class TestCornerCoordinate {
    @Test
    fun `Corner coordinate equality should not care about order of constructor parameters`(){
        var cubeCoordinate = fromAxial(0,0)
        var corner = fromCubeAndDirection(cubeCoordinate, CornerDirection.North)
        var cornerWithReverseConstructorParameters = fromCubeCoordinates(corner.c, corner.b, corner.a).getOrElse { throw Exception() }
        assertEquals(corner, cornerWithReverseConstructorParameters)
    }

    @TestFactory
    fun `Converting corner coordinate to sides and back should yield same corner coordinate`() : Collection<DynamicTest> =
        cornerDirections().map {
            DynamicTest.dynamicTest("Converting $it to side coordinates and back should yield $it") {
                var corner = fromCubeAndDirection(fromAxial(0,0), it)
                var sides = toSides(corner).toList()
                var cornerConvertedThereAndBack = fromSideCoordinates(sides[0], sides[1], sides[2]).getOrElse { throw Exception() }
                assertEquals(corner, cornerConvertedThereAndBack)
            }
        }

    @Test
    fun `Invalid corner should be none`(){
        var a = fromAxial(0,0)
        var b = neighbor(a, SideDirection.East)
        var c = neighbor(a, SideDirection.West)
        var invalidCorner = fromCubeCoordinates(a,b,c)
        assertEquals(None, invalidCorner)
    }

    @Test
    fun `Corner coordinate sides should be corner`(){
        var cubeCoordinate = fromAxial(0,0)
        var corner = fromCubeAndDirection(cubeCoordinate, CornerDirection.North)
        var sides = toSides(corner).toList()
        assert(areCorner(sides[0], sides[1], sides[2]))
    }

    @Test
    fun `Corner coordinate sides are connected`() {
        var cubeCoordinate = fromAxial(0, 0)
        var corner = fromCubeAndDirection(cubeCoordinate, CornerDirection.North)
        var sides = toSides(corner).toList()
        var sideA = sides[0]
        var sideB = sides[1]
        var sideC = sides[2]

        var areConnectedAB = areConnected(sideA, sideB)
        var areConnectedAC = areConnected(sideA, sideC)
        var areConnectedBC = areConnected(sideB, sideC)

        assert(areConnectedAB && areConnectedAC && areConnectedBC)
    }
}