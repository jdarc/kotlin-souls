import kotlin.math.max
import kotlin.math.min

class Renderer(private val pixels: IntArray, private val width: Int, private val height: Int) {
    private val texture = IntArray(256 * 256)

    fun setTexture(texels: IntArray) = System.arraycopy(texels, 0, texture, 0, texture.size)

    fun render(flare: Flare) {
        val alpha = 255 shl 24
        val mask = 256 * 256 - 256
        val minX = max(flare.left, 0)
        val minY = max(flare.top, 0)
        val maxX = min(flare.right, width)
        val maxY = min(flare.bottom, height)
        val flareRed = (flare.intensity * flare.color.red).toInt()
        val flareGrn = (flare.intensity * flare.color.green).toInt()
        val flareBlu = (flare.intensity * flare.color.blue).toInt()
        val dx = (16777216.0 / flare.width).toInt()
        val dy = (16777216.0 / flare.height).toInt()
        val ttx = flare.left * dx
        val tty = flare.top * dy
        val wx = minX * dx - ttx
        var wy = minY * dy - tty
        for (y in minY until maxY) {
            var lwx = wx
            for (x in minX until maxX) {
                val texel = texture[mask and wy.shr(8) or (lwx shr 16)] and 255
                val rgb = pixels[y * width + x]
                val red = ((flareRed * texel).shr(8) + red(rgb)).coerceAtMost(255)
                val grn = ((flareGrn * texel).shr(8) + grn(rgb)).coerceAtMost(255)
                val blu = ((flareBlu * texel).shr(8) + blu(rgb)).coerceAtMost(255)
                pixels[y * width + x] = alpha or (red shl 16) or (grn shl 8) or blu
                lwx += dx
            }
            wy += dy
        }
    }

    private fun blu(rgb: Int) = 0x0000FF and rgb
    private fun grn(rgb: Int) = 0x00FF00 and rgb shr 8
    private fun red(rgb: Int) = 0xFF0000 and rgb shr 16
}
