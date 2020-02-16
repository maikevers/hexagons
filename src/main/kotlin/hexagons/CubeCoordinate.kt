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

fun fromAxial(axial: AxialCoordinate): CubeCoordinate = CubeCoordinateInstance(axial.q, axial.r, -axial.q - axial.r)

fun fromAxial(q: Int, r: Int): CubeCoordinate = CubeCoordinateInstance(q, r, -q - r)

fun add(a: CubeCoordinate, b: CubeCoordinate): CubeCoordinate = CubeCoordinateInstance(a.q + b.q, a.r + b.r, a.s + b.s)

fun subtract(a: CubeCoordinate, b: CubeCoordinate): CubeCoordinate =
    CubeCoordinateInstance(a.q - b.q, a.r - b.r, a.s - b.s)

fun multiply (a: CubeCoordinate, b: CubeCoordinate): CubeCoordinate =
    CubeCoordinateInstance(a.q * b.q, a.r * b.r, a.s * b.s)
