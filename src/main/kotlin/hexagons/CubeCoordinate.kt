package hexagons

import arrow.core.*

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

fun toSideCoordinate(direction: Direction): SideCoordinate = SideCoordinate(0, 0)

fun fromDirection(direction: Direction): CubeCoordinate = when (direction) {
    is NorthEast -> CubeCoordinateInstance(1, 0, -1)
    is East -> CubeCoordinateInstance(1, -1, 0)
    is SouthEast -> CubeCoordinateInstance(0, -1, 1)
    is SouthWest -> CubeCoordinateInstance(-1, 0, 1)
    is West -> CubeCoordinateInstance(-1, 1, 0)
    is NorthWest -> CubeCoordinateInstance(0, 1, -1)
}

fun move(cubeCoordinate: CubeCoordinate, direction: Direction, steps: Int): CubeCoordinate =
    multiply(cubeCoordinate, scale(fromDirection(direction), steps))

fun neighbor(cubeCoordinate: CubeCoordinate, direction: Direction): CubeCoordinate = move(cubeCoordinate, direction, 1)