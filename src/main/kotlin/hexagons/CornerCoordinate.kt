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

    override fun hashCode() = setOf(a, b, c).hashCode()
}

fun fromCubeAndDirection(cubeCoordinate: CubeCoordinate, cornerDirection: CornerDirection): CornerCoordinate =
    CornerCoordinateInstance(
        cubeCoordinate,
        neighborOf(cubeCoordinate, cornerDirection.someSide),
        neighborOf(cubeCoordinate, cornerDirection.otherSide)
    )

fun fromCubeCoordinates(a: CubeCoordinate, b: CubeCoordinate, c: CubeCoordinate): Option<CornerCoordinate> =
    (areNeighbors(a, b) && areNeighbors(a, c) && areNeighbors(b, c)).maybe { CornerCoordinateInstance(a, b, c) }

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

fun fromSideCoordinates(a: SideCoordinate, b: SideCoordinate): Option<CornerCoordinate> {
    val distinctCoordinates = distinctCoordinatesOf(listOf(a, b)).toList()
    return (distinctCoordinates.count() == 3).maybe {
        CornerCoordinateInstance(
            distinctCoordinates[0],
            distinctCoordinates[1],
            distinctCoordinates[2]
        )
    }
}

fun cornersOf(sides: Collection<SideCoordinate>): Set<CornerCoordinate> = sides.map { cornersOf(it) }.flatten().toSet()

fun cornersOf(sideCoordinate: SideCoordinate): Set<CornerCoordinate> {
    val cornersOfM = cornersOf(sideCoordinate.m)
    val cornersOfN = cornersOf(sideCoordinate.n)
    return cornersOfM.intersect(cornersOfN).toSet()
}

fun sidesOf(cornerCoordinate: CornerCoordinate): Set<SideCoordinate> = fromCorner(cornerCoordinate)

fun nextSides(atCorner: CornerCoordinate, fromSide: SideCoordinate): Set<SideCoordinate> =
    sidesOf(atCorner).filter { it != fromSide }.toSet()

fun nextCorners(atCorner: CornerCoordinate, fromSide: SideCoordinate): Set<CornerCoordinate> =
    cornersOf(nextSides(atCorner, fromSide)).toSet()

fun neighborsOf(cornerCoordinate: CornerCoordinate): Set<CornerCoordinate> =
    cornersOf(sidesOf(cornerCoordinate)).filter { it != cornerCoordinate }.toSet()