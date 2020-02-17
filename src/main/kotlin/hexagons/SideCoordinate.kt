package hexagons

import arrow.core.Option
import arrow.core.maybe
import arrow.core.singleOrNone

interface SideCoordinate {
    val m: CubeCoordinate
    val n: CubeCoordinate
}

data class SideCoordinateInstance(override val m: CubeCoordinate, override val n: CubeCoordinate) : SideCoordinate{
    override fun equals(other: Any?)
            = (other is SideCoordinate)
            && setOf(m,n) == setOf(other.m, other.n)
}

fun fromCubeCoordinates(m: CubeCoordinate, n: CubeCoordinate): Option<SideCoordinate> =
    areNeighbours(m, n).maybe { SideCoordinateInstance(m, n) }

fun fromCubeAndDirection(cubeCoordinate: CubeCoordinate, direction: SideDirection): SideCoordinate =
    SideCoordinateInstance(cubeCoordinate, neighbor(cubeCoordinate, direction))

fun fromCorner(corner: CornerCoordinate): Set<SideCoordinate> = setOf(
    SideCoordinateInstance(corner.a, corner.b),
    SideCoordinateInstance(corner.a, corner.c),
    SideCoordinateInstance(corner.b, corner.c)
)

fun toSetOfCubeCoordinates(side: SideCoordinate): Set<CubeCoordinate> = setOf(side.m, side.n)

fun distinctCoordinatesOf(a: SideCoordinate, b: SideCoordinate): Set<CubeCoordinate> =
    distinctCoordinatesOf(listOf(a, b))

fun distinctCoordinatesOf(sides: Collection<SideCoordinate>): Set<CubeCoordinate> =
    sides.map { side -> toSetOfCubeCoordinates(side) }.flatten().toSet()

fun coordinatesOf(a: SideCoordinate, b: SideCoordinate): List<CubeCoordinate> = listOf(a.m, a.n, b.m, b.n)

fun areConnected(a: SideCoordinate, b: SideCoordinate): Boolean = distinctCoordinatesOf(a, b).size == 3

fun areCorner(a: SideCoordinate, b: SideCoordinate, c: SideCoordinate): Boolean = distinctCoordinatesOf(listOf(a,b,c)).count() == 3

fun sharedCubeCoordinateOf(a: SideCoordinate, b: SideCoordinate): Option<CubeCoordinate> =
    (coordinatesOf(a, b) subtract distinctCoordinatesOf(a, b)).singleOrNone()