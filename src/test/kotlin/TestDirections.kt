import hexagons.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

class TestDirections {
    @Test
    fun `There are six side directions`(){
        assertEquals(6, sideDirections().count())
    }

    @Test
    fun `There are six corner directions`(){
        assertEquals(6, cornerDirections().count())
    }

    @TestFactory
    fun `Twice opposite side direction should be starting direction`() : Collection<DynamicTest> =
        sideDirections().map {
            DynamicTest.dynamicTest("Converting $it to opposite direction and back should be $it") {
                val twiceOpposite = oppositeOf(oppositeOf(it))
                assertEquals(it, twiceOpposite)
            }
        }

    @TestFactory
    fun `Twice opposite corner direction should be starting direction`() : Collection<DynamicTest> =
        cornerDirections().map {
            DynamicTest.dynamicTest("Converting $it to opposite direction and back should be $it") {
                val twiceOpposite = oppositeOf(oppositeOf(it))
                assertEquals(it, twiceOpposite)
            }
        }
}