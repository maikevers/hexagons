package hexagons

import arrow.core.*
import kotlin.math.absoluteValue

private data class CubeCoordinateInstance(override val q: Int, override val r: Int, override val s: Int) :
    CubeCoordinate

interface CubeCoordinate {
    val q: Int
    val r: Int
    val s: Int
}

fun toAxial(cubeCoordinate: CubeCoordinate): AxialCoordinate = AxialCoordinate(cubeCoordinate.q, cubeCoordinate.r)

fun createCubeCoordinate(q: Int, r: Int, s: Int): Option<CubeCoordinate> =
    (q + r + s == 0).maybe { CubeCoordinateInstance(q, r, s) }

fun createCubeCoordinate(q: Int, r: Int): CubeCoordinate = fromAxial(q, r)

fun fromAxial(axial: AxialCoordinate): CubeCoordinate = CubeCoordinateInstance(axial.q, axial.r, -axial.q - axial.r)

fun fromAxial(q: Int, r: Int): CubeCoordinate = CubeCoordinateInstance(q, r, -q - r)

fun add(a: CubeCoordinate, b: CubeCoordinate): CubeCoordinate = CubeCoordinateInstance(a.q + b.q, a.r + b.r, a.s + b.s)

fun subtract(a: CubeCoordinate, b: CubeCoordinate): CubeCoordinate =
    CubeCoordinateInstance(a.q - b.q, a.r - b.r, a.s - b.s)

fun multiply(a: CubeCoordinate, b: CubeCoordinate): CubeCoordinate =
    CubeCoordinateInstance(a.q * b.q, a.r * b.r, a.s * b.s)

fun scale(cubeCoordinate: CubeCoordinate, factor: Int): CubeCoordinate =
    CubeCoordinateInstance(cubeCoordinate.q * factor, cubeCoordinate.r * factor, cubeCoordinate.s * factor)

fun fromSideDirection(direction: SideDirection): CubeCoordinate = when (direction) {
    is SideDirection.NorthEast -> CubeCoordinateInstance(1, 0, -1)
    is SideDirection.East -> CubeCoordinateInstance(1, -1, 0)
    is SideDirection.SouthEast -> CubeCoordinateInstance(0, -1, 1)
    is SideDirection.SouthWest -> CubeCoordinateInstance(-1, 0, 1)
    is SideDirection.West -> CubeCoordinateInstance(-1, 1, 0)
    is SideDirection.NorthWest -> CubeCoordinateInstance(0, 1, -1)
}

fun sidesOf(cubeCoordinate: CubeCoordinate): Set<SideCoordinate> =
    sideDirections().map { fromCubeAndDirection(cubeCoordinate, it) }.toSet()

fun cornersOf(cubeCoordinate: CubeCoordinate): Set<CornerCoordinate> =
    cornerDirections().map { fromCubeAndDirection(cubeCoordinate, it) }.toSet()

fun neighboursOf(cubeCoordinate: CubeCoordinate): Set<CubeCoordinate> =
    sideDirections().map { neighbor(cubeCoordinate, it)}.toSet()

fun sideDirectionToCubeMap(): Map<SideDirection, CubeCoordinate> =
    sideDirections().associateBy({ it }, { fromSideDirection(it) })

fun areNeighbours(someCube: CubeCoordinate, otherCube: CubeCoordinate): Boolean =
    distanceBetween(someCube, otherCube) == 1

fun distanceBetween(a: CubeCoordinate, b: CubeCoordinate): Int = lengthOf(subtract(a, b))

fun lengthOf(cubeCoordinate: CubeCoordinate): Int =
    (cubeCoordinate.q.absoluteValue + cubeCoordinate.r.absoluteValue + cubeCoordinate.s.absoluteValue) / 2

fun move(cubeCoordinate: CubeCoordinate, direction: SideDirection, steps: Int): CubeCoordinate =
    add(cubeCoordinate, scale(fromSideDirection(direction), steps))

fun neighbor(cubeCoordinate: CubeCoordinate, direction: SideDirection): CubeCoordinate =
    move(cubeCoordinate, direction, 1)