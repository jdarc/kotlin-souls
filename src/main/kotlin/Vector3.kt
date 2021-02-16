import kotlin.math.sqrt

class Vector3(val x: Double, val y: Double, var z: Double) {

    val length = sqrt(x * x + y * y + z * z)

    operator fun plus(rhs: Vector3) = Vector3(x + rhs.x, y + rhs.y, z + rhs.z)

    operator fun minus(rhs: Vector3) = Vector3(x - rhs.x, y - rhs.y, z - rhs.z)

    operator fun times(scalar: Double) = Vector3(scalar * x, scalar * y, scalar * z)

    operator fun div(scalar: Double) = Vector3(x / scalar, y / scalar, z / scalar)

    companion object {
        val ZERO = Vector3(0.0, 0.0, 0.0)

        fun normalize(vec: Vector3) = vec / vec.length
    }
}
