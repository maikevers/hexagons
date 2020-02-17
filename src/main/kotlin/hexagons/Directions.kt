package hexagons

import arrow.core.Option
import arrow.core.getOption

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

fun oppositeOf(direction: SideDirection): SideDirection = when (direction) {
    is SideDirection.NorthEast -> SideDirection.SouthWest
    is SideDirection.East -> SideDirection.West
    is SideDirection.SouthEast -> SideDirection.NorthWest
    is SideDirection.SouthWest -> SideDirection.NorthEast
    is SideDirection.West -> SideDirection.East
    is SideDirection.NorthWest -> SideDirection.SouthEast
}

fun oppositeOf(direction: CornerDirection): CornerDirection = when (direction) {
    is CornerDirection.NorthEast -> CornerDirection.SouthWest
    is CornerDirection.North -> CornerDirection.South
    is CornerDirection.SouthEast -> CornerDirection.NorthWest
    is CornerDirection.SouthWest -> CornerDirection.NorthEast
    is CornerDirection.South -> CornerDirection.North
    is CornerDirection.NorthWest -> CornerDirection.SouthEast
}

fun fromCubeCoordinate(cubeCoordinate: CubeCoordinate): Option<SideDirection> =
    cubeCoordinateToSideDirectionMap().getOption(cubeCoordinate)

fun sideDirections() = nestedClassesOf<SideDirection>()

fun cornerDirections() = nestedClassesOf<CornerDirection>()

private inline fun <reified T> nestedClassesOf(): Set<T> = T::class.nestedClasses.map { it.objectInstance as T }.toSet()

