import java.awt.image.PixelGrabber
import javax.imageio.ImageIO
import kotlin.math.max
import kotlin.math.min

object Textures {

    fun forSouls() = grabPixels("flare.png")

    fun forNebula(): IntArray {
        val lightMap = IntArray(256 * 256)
        var co = 0
        var rad = 4000.0
        while (rad > 2500) {
            co += 10
            radiate(lightMap, co, rad)
            rad -= 200
        }
        rad = 2500.0
        while (rad > 0) {
            co = max(co - 7, 0)
            radiate(lightMap, co, rad)
            rad -= 250
        }
        return lightMap
    }

    fun forSwarm(): IntArray {
        val lightMap = IntArray(256 * 256)
        var co = 0
        var rad = 4000.0
        while (rad > 2500) {
            co = min(255, co + 24)
            radiate(lightMap, co, rad)
            rad -= 200
        }
        rad = 2500.0
        while (rad > 400) {
            co = max(0, co - 12)
            radiate(lightMap, co, rad)
            rad -= 250
        }
        rad = 400.0
        while (rad > 0) {
            co = min(255, co + 16)
            radiate(lightMap, co, rad)
            rad -= 250
        }
        return lightMap
    }

    private fun radiate(map: IntArray, color: Int, radius: Double) {
        for (y in 0 until 256) {
            val ry = (y - 128).toDouble()
            for (x in 0 until 256) {
                val rx = (x - 128).toDouble()
                if (rx * rx + ry * ry <= radius) {
                    map[y * 256 + x] = color
                }
            }
        }
    }

    private fun grabPixels(name: String): IntArray {
        val image = ImageIO.read(Textures::class.java.classLoader.getResource(name))
        val pixels = IntArray(image.width * image.height)
        PixelGrabber(image, 0, 0, image.width, image.height, pixels, 0, image.width).grabPixels()
        return pixels
    }
}
