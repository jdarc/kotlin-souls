import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt
import javax.swing.JPanel

class DemoContainer : JPanel() {
    private var image: BufferedImage
    private var pixels: IntArray
    private var renderer: Renderer
    private var flares = emptyArray<Flare>()

    var controller: Controller = Souls(64, 64)
        set(value) {
            field = value
            flares = emptyArray()
        }

    @Override
    override fun paintComponent(g: Graphics) {
        g as Graphics2D
        g.drawImage(image, null, 0, 0)
    }

    fun tick() {
        controller.update(flares)
        blend()
        render()
        repaint()
    }

    private fun render() {
        if (flares.isEmpty()) {
            renderer.setTexture(controller.buildTexture())
            flares = controller.buildFlares()
        }
        flares.toList().parallelStream().forEach { renderer.render(it) }
    }

    init {
        size = Dimension(1280, 800)
        preferredSize = size

        image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE)
        pixels = (image.raster.dataBuffer as DataBufferInt).data
        renderer = Renderer(pixels, width, height)

        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                controller.dragging(e)
            }
        })
    }

    private fun blend() {
        val alpha = 255 shl 24
        for (i in pixels.indices) {
            val rgb = pixels[i]
            val red = 6 * (0xFF0000 and rgb shr 16) shr 3
            val grn = 6 * (0x00FF00 and rgb shr 8) shr 3
            val blu = 6 * (0x0000FF and rgb) shr 3
            pixels[i] = alpha or (red shl 16) or (grn shl 8) or blu
        }
    }
}
