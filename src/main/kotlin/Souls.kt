import java.awt.Color
import java.util.concurrent.ThreadLocalRandom

class Souls(private val width: Int, private val height: Int) : Controller {

    @Override
    override fun buildTexture() = Textures.forSouls()

    @Override
    override fun buildFlares(): Array<Flare> {
        val rnd = ThreadLocalRandom.current()
        return (0 until 128).map {
            val flare = Flare(96.0)
            flare.color = Color(rnd.nextInt(16, 92), rnd.nextInt(16, 92), rnd.nextInt(16, 92))
            val x = rnd.nextDouble(-90.0, 90.0)
            val y = rnd.nextDouble(-70.0, 70.0)
            val z = rnd.nextDouble(-400.0, 400.0)
            flare.position = Vector3(x, y, z)
            flare
        }.toTypedArray()
    }

    @Override
    override fun update(flares: Array<Flare>) {
        val speed = -4.0
        for (flare in flares) {
            flare.position.z += speed
            if (flare.position.z < -400) flare.position.z = 400.0
            flare.transform(Matrix4.IDENTITY)
            flare.updateBounds(width shr 1, height shr 1, 200.0, 400.0)
            flare.computeIntensity(200.0, 400.0)
        }
    }
}
