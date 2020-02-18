import arrow.core.getOrElse
import graphs.*
import hexagons.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

class TestBreadthFirstSearch {
    @Test
    fun `The shortest distance to self is zero`(){
        val node = fromAxial(0, 0)
        val maxDepth = 1
        val maybeDistance = shortestDistance(node, node, ::neighborsOf, maxDepth)
        assert(maybeDistance.isDefined())
        assertEquals(0, maybeDistance.getOrElse { throw Exception() })
    }

    @TestFactory
    fun `The shortest distance to neighbor is one`(): Collection<DynamicTest> =
        sideDirections().map {
            DynamicTest.dynamicTest("The shortest distance to neighbor in direction $it is one") {
                val startNode = fromAxial(0, 0)
                val endNode = neighborOf(startNode, it)
                val maxDepth = 1
                val maybeDistance = shortestDistance(startNode, endNode, ::neighborsOf, maxDepth)
                assert(maybeDistance.isDefined())
                assertEquals(1, maybeDistance.getOrElse { throw Exception() })
            }
        }

    @TestFactory
    fun `The shortest distance to 2nd neighbor is two`(): Collection<DynamicTest> =
        sideDirections().map {
            DynamicTest.dynamicTest("The shortest distance to neighbor in direction $it is one") {
                val startNode = fromAxial(0, 0)
                val endNode = neighborOf(neighborOf(startNode, it), it)
                val maxDepth = 2
                val maybeDistance = shortestDistance(startNode, endNode, ::neighborsOf, maxDepth)
                assert(maybeDistance.isDefined())
                assertEquals(2, maybeDistance.getOrElse { throw Exception() })
            }
        }

    @Test
    fun `The shortest distance to 2nd neighbor with 1st neighbor blocked is 3`(){
        val startNode = fromAxial(0, 0)
        val blockingNode = neighborOf(startNode, SideDirection.East)
        val endNode = neighborOf(blockingNode, SideDirection.East)
        val maxDepth = 3
        val neighborsOfExceptBlockingNode = {node: CubeCoordinate -> neighborsOf(node).filter { c -> c != blockingNode }.toSet()}

        val maybeDistance = shortestDistance(startNode, endNode, neighborsOfExceptBlockingNode, maxDepth)

        assert(maybeDistance.isDefined())
        assertEquals(3, maybeDistance.getOrElse { throw Exception() })
    }

    @Test
    fun `The shortest unobstructed distance to node at longer distance is as expected`(){
        val startNode = fromAxial(0, 0)
        val endNode = fromAxial(10, 10)
        val maxDepth = 25
        val expectedDistance = distanceBetween(startNode, endNode)

        val maybeDistance = shortestDistance(startNode, endNode, ::neighborsOf, maxDepth)

        assert(maybeDistance.isDefined())
        assertEquals(expectedDistance, maybeDistance.getOrElse { throw Exception() })
    }
}