import arrow.core.getOrElse
import hexagons.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestSideCoordinate {
    @Test
    fun `Side coordinate equality should not care about order of constructor parameters`(){
        var cubeCoordinateA = fromAxial(0,0)
        var side = fromCubeAndDirection(cubeCoordinateA, SideDirection.SouthWest)
        var sideWithReverseConstructorParameters = fromCubeCoordinates(side.n, side.m).getOrElse { throw Exception() }
        assertEquals(side, sideWithReverseConstructorParameters)
    }
}