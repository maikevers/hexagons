import arrow.core.getOrElse
import hexagons.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestCornerCoordinate {
    @Test
    fun `Corner coordinate equality should not care about order of constructor parameters`(){
        var cubeCoordinate = fromAxial(0,0)
        var corner = fromCubeAndDirection(cubeCoordinate, CornerDirection.North)
        var cornerWithReverseConstructorParameters = fromCubeCoordinates(corner.c, corner.b, corner.a).getOrElse { throw Exception() }
        assertEquals(corner, cornerWithReverseConstructorParameters)
    }
}