package hexagons

interface CornerCoordinate {
    val a: CubeCoordinate
    val b: CubeCoordinate
    val c: CubeCoordinate
}

private data class CornerCoordinateInstance(
    override val a: CubeCoordinate,
    override val b: CubeCoordinate,
    override val c: CubeCoordinate
) : CornerCoordinate

fun fromCubeAndCornerDirection(cubeCoordinate: CubeCoordinate, cornerDirection: CornerDirection): CornerCoordinate =
    CornerCoordinateInstance(
        cubeCoordinate,
        neighbor(cubeCoordinate, cornerDirection.someSide),
        neighbor(cubeCoordinate, cornerDirection.otherSide)
    )

fun fromCube(a: CubeCoordinate, b: CubeCoordinate, c: CubeCoordinate){

}