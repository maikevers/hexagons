package graphs

import arrow.core.Option
import arrow.core.getOption
import java.util.*

fun <T> shortestDistance(
    startNode: T,
    endNode: T,
    traversableNextNodesFrom: (T) -> Set<T>,
    maxDepth: Int = Int.MAX_VALUE
): Option<Int> {

    val visitedMap = mutableListOf<T>()
    val depthMap = mutableMapOf<T, Int>()
    val queue: Deque<T> = LinkedList()

    startNode.also { node ->
        queue.add(node)
        depthMap[node] = 0
    }

    while (queue.isNotEmpty()) {
        val currentNode = queue.remove()
        val depth = depthMap[currentNode]!!

        if (depth < maxDepth && !visitedMap.contains(currentNode)) {

            visitedMap.add(currentNode)

            traversableNextNodesFrom(currentNode).forEach { node ->
                val depthAtNode = depth + 1
                queue.add(node)
                if (!depthMap.containsKey(node) || depthMap[node]!! > depthAtNode) {
                    depthMap[node] = depthAtNode
                }
            }
        }
        if (currentNode == endNode){
            break
        }
    }
    return depthMap.getOption(endNode)
}