package sample


data class CubeCoordinate private constructor(val q: Int, val r: Int, val s: Int){
    companion object{
        fun create(q: Int, r: Int, s: Int): CubeCoordinate {
            return CubeCoordinate(q,r,s)
        }
    }
}

fun CreateCubeCoordinate(q: Int, r: Int, s: Int): CubeCoordinate {
    return CubeCoordinate.create(1,2,3)
}
