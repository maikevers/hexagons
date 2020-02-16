package hexagons

data class AxialCoordinate(val q: Int, val r: Int)

fun toCube(axial: AxialCoordinate): CubeCoordinate = fromAxial(axial)

fun fromCube(cubeCoordinate: CubeCoordinate): AxialCoordinate = toAxial(cubeCoordinate)