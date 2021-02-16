import java.awt.Color
import java.awt.geom.Rectangle2D
import kotlin.math.floor
import kotlin.math.sqrt

class Flare(private val radius: Double) {
    private val bounds = Rectangle2D.Double()

    private var particle = Particle()
    private var screen = Vector3.ZERO

    var position
        get() = particle.position
        set(value) {
            particle.position = value
        }

    var intensity = 0.0
        private set

    var color: Color = Color.WHITE

    val left get() = floor(bounds.minX).toInt()
    val top get() = floor(bounds.minY).toInt()
    val right get() = floor(bounds.maxX).toInt()
    val bottom get() = floor(bounds.maxY).toInt()
    val width get() = floor(bounds.width).toInt()
    val height get() = floor(bounds.height).toInt()

    fun transform(matrix: Matrix4) {
        screen = matrix * position
    }

    fun physics(cursor: Vector3, friction: Double) {
        val distance = cursor - particle.position
        particle.clearForces()
        particle.accelerate(Vector3.normalize(distance) * (0.98 / distance.length))
        particle.integrate(1.0)
        particle.dampen(friction, friction, 1.0)
    }

    fun updateBounds(mx: Int, my: Int, scale: Double, offset: Double) {
        val dz = scale / (screen.z + offset)
        bounds.x = mx + (screen.x - radius) * dz
        bounds.y = my + (screen.y - radius) * dz
        bounds.width = (mx + (screen.x + radius) * dz) - bounds.x
        bounds.height = (my + (screen.y + radius) * dz) - bounds.y
    }

    fun computeIntensity(distance: Double, factor: Double) {
        val x = screen.x
        val y = screen.y
        val z = screen.z + distance
        intensity = (1.0 - sqrt(x * x + y * y + z * z) / factor).coerceIn(0.0, 1.0)
    }
}
