package hexagons

import arrow.core.Option
import arrow.core.singleOrNone

sealed class SideDirection {
    object NorthEast : SideDirection()
    object East : SideDirection()
    object SouthEast : SideDirection()
    object SouthWest : SideDirection()
    object West : SideDirection()
    object NorthWest : SideDirection()
}

sealed class CornerDirection {
    abstract val someSide: SideDirection
    abstract val otherSide: SideDirection

    object South : CornerDirection() {
        override val someSide: SideDirection = SideDirection.SouthWest
        override val otherSide: SideDirection = SideDirection.SouthEast
    }

    object North : CornerDirection() {
        override val someSide: SideDirection = SideDirection.NorthWest
        override val otherSide: SideDirection = SideDirection.NorthEast
    }

    object NorthEast : CornerDirection() {
        override val someSide: SideDirection = SideDirection.East
        override val otherSide: SideDirection = SideDirection.NorthEast
    }

    object NorthWest : CornerDirection() {
        override val someSide: SideDirection = SideDirection.West
        override val otherSide: SideDirection = SideDirection.NorthWest
    }

    object SouthEast : CornerDirection() {
        override val someSide: SideDirection = SideDirection.East
        override val otherSide: SideDirection = SideDirection.SouthEast
    }

    object SouthWest : CornerDirection() {
        override val someSide: SideDirection = SideDirection.West
        override val otherSide: SideDirection = SideDirection.SouthWest
    }
}

fun fromCubeCoordinate(cubeCoordinate: CubeCoordinate): Option<SideDirection> =
    allDirections().toList().singleOrNone { t -> t.second == cubeCoordinate }.map { t -> t.first }

fun sideDirections() = SideDirection::class.nestedClasses.map { it.objectInstance as SideDirection }

fun cornerDirections() = CornerDirection::class.nestedClasses.map { it.objectInstance as CornerDirection }