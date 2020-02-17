package hexagons

import arrow.core.Option
import arrow.core.maybe

interface CornerCoordinate {
    val a: CubeCoordinate
    val b: CubeCoordinate
    val c: CubeCoordinate
}

private data class CornerCoordinateInstance(
    override val a: CubeCoordinate,
    override val b: CubeCoordinate,
    override val c: CubeCoordinate
) : CornerCoordinate {
    override fun equals(other: Any?) = (other is CornerCoordinate)
            && setOf(a, b, c) == setOf(other.a, other.b, other.c)
}

fun fromCubeAndDirection(cubeCoordinate: CubeCoordinate, cornerDirection: CornerDirection): CornerCoordinate =
    CornerCoordinateInstance(
        cubeCoordinate,
        neighbor(cubeCoordinate, cornerDirection.someSide),
        neighbor(cubeCoordinate, cornerDirection.otherSide)
    )

fun fromCubeCoordinates(a: CubeCoordinate, b: CubeCoordinate, c: CubeCoordinate): Option<CornerCoordinate> =
    (areNeighbours(a, b) && areNeighbours(a, c) && areNeighbours(b, c)).maybe { CornerCoordinateInstance(a, b, c) }

fun fromSideCoordinates(a: SideCoordinate, b: SideCoordinate, c: SideCoordinate): Option<CornerCoordinate> {
    val distinctCoordinates = distinctCoordinatesOf(listOf(a, b, c)).toList()
    return (distinctCoordinates.count() == 3).maybe {
        CornerCoordinateInstance(
            distinctCoordinates[0],
            distinctCoordinates[1],
            distinctCoordinates[2]
        )
    }
}

fun toSides(cornerCoordinate: CornerCoordinate): Set<SideCoordinate> = fromCorner(cornerCoordinate)

