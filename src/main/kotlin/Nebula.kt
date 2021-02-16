import java.awt.Color
import java.util.concurrent.ThreadLocalRandom

class Nebula(private val width: Int, private val height: Int) : Controller {

    @Override
    override fun buildTexture() = Textures.forNebula()

    @Override
    override fun buildFlares(): Array<Flare> {
        val rnd = ThreadLocalRandom.current()
        return (0 until 256).map {
            val flare = Flare(rnd.nextDouble(4.0, 40.0))
            flare.color = Color(rnd.nextInt(23, 78), rnd.nextInt(3, 89), rnd.nextInt(134, 189))
            val x = rnd.nextDouble(-50.0, 50.0)
            val y = rnd.nextDouble(-90.0, 90.0)
            val z = rnd.nextDouble(-50.0, 50.0)
            flare.position = Vector3(x, y, z)
            flare
        }.toTypedArray()
    }

    @Override
    override fun update(flares: Array<Flare>) {
        val transform = Matrix4.createRotationY(System.nanoTime() / 700000000.0)
        for (flare in flares) {
            flare.transform(transform)
            flare.updateBounds(width / 2, height / 2, 800.0, 200.0)
            flare.computeIntensity(40.0, 100.0)
        }
    }
}
