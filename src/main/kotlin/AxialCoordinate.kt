package hexagons

data class AxialCoordinate(val q: Int, val r: Int)

val toCube = CubeCoordinate.fromAxial

val fromCube = toAxial