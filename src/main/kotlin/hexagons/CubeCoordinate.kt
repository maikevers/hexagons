package hexagons

import arrow.core.*

private data class CubeCoordinateInstance(override val q: Int, override val r: Int, override val s: Int) :
    CubeCoordinate

interface CubeCoordinate {
    val q: Int
    val r: Int
    val s: Int
}

val toAxial = { cubeCoordinate: CubeCoordinate -> AxialCoordinate(cubeCoordinate.q, cubeCoordinate.r) }

val createCubeCoordinate: (Int, Int, Int) -> Option<CubeCoordinate> =
    { q, r, s -> (q + r + s == 0).maybe { CubeCoordinateInstance(q, r, s) } }

var fromAxial: (AxialCoordinate) -> CubeCoordinate =
    { axial -> CubeCoordinateInstance(axial.q, axial.r, -axial.q - axial.r) }

var createFromAxial: (Int, Int) -> CubeCoordinate ={
    q, r -> CubeCoordinateInstance(q,r,-q-r)
}

val add: (CubeCoordinate, CubeCoordinate) -> CubeCoordinate =
    { a, b -> CubeCoordinateInstance(a.q + b.q, a.r + b.r, a.s + b.s) }

val subtract: (CubeCoordinate, CubeCoordinate) -> CubeCoordinate =
    { a, b -> CubeCoordinateInstance(a.q - b.q, a.r - b.r, a.s - b.s) }

val multiply: (CubeCoordinate, CubeCoordinate) -> CubeCoordinate =
    { a, b -> CubeCoordinateInstance(a.q * b.q, a.r * b.r, a.s * b.s) }
