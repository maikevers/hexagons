package hexagons

import arrow.core.Option
import arrow.core.maybe

interface SideCoordinate {
    val m: CubeCoordinate
    val n: CubeCoordinate
}

data class SideCoordinateInstance(override val m: CubeCoordinate, override val n: CubeCoordinate) : SideCoordinate

fun fromCubeCoordinate(m: CubeCoordinate, n: CubeCoordinate): Option<SideCoordinate> =
    areNeighbours(m, n).maybe { SideCoordinateInstance(m, n) }