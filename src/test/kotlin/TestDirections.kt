import hexagons.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DirectionTests {
    @Test
    fun `There are six side directions`(){
        assertEquals(6, sideDirections().count())
    }

    @Test
    fun `There are six corner directions`(){
        assertEquals(6, cornerDirections().count())
    }
}