import java.awt.Color
import java.awt.event.MouseEvent
import java.awt.geom.Point2D
import java.util.concurrent.ThreadLocalRandom

class Swarm(private val width: Int, private val height: Int) : Controller {
    private val camera = Vector3(0.0, 0.0, -200.0)
    private var cursor = Point2D.Double()

    @Override
    override fun buildTexture() = Textures.forSwarm()

    @Override
    override fun buildFlares(): Array<Flare> {
        val rnd = ThreadLocalRandom.current()
        return (0 until 65536).map {
            val flare = Flare(0.8)
            flare.color = Color(rnd.nextInt(200, 200 + 55), rnd.nextInt(0, 145), rnd.nextInt(0, 80))
            val x = rnd.nextDouble(-80.0, 80.0)
            val y = rnd.nextDouble(-80.0, 80.0)
            val z = rnd.nextDouble(-80.0, 80.0)
            flare.position = Vector3(x, y, z)
            flare
        }.toTypedArray()
    }

    @Override
    override fun update(flares: Array<Flare>) {
        for (flare in flares) {
            flare.physics(Vector3(cursor.x, cursor.y, camera.z + 200), 0.97)
            flare.transform(Matrix4.IDENTITY)
            flare.updateBounds(width shr 1, height shr 1, 120.0, 30.0)
            flare.computeIntensity(30.0, 300.0)
        }
    }

    @Override
    override fun dragging(e: MouseEvent) {
        cursor.x = (e.x - width * 0.5) / 4.0
        cursor.y = (e.y - height * 0.5) / 4.0
    }
}
