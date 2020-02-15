package hexagons

import arrow.core.*

data class CubeCoordinate private constructor(val q: Int, val r: Int, val s: Int) {
    companion object {
        fun create(q: Int, r: Int, s: Int): Option<CubeCoordinate> {
            return (q + r + s == 0).maybe { CubeCoordinate(q, r, s) }
        }

        val fromAxial = { axialCoordinate: AxialCoordinate ->
            CubeCoordinate(
                axialCoordinate.q,
                axialCoordinate.r,
                -axialCoordinate.q - axialCoordinate.r
            )
        }
    }
}

val toAxial = { cubeCoordinate: CubeCoordinate -> AxialCoordinate(cubeCoordinate.q, cubeCoordinate.r) }
