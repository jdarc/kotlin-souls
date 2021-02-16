import java.awt.event.MouseEvent

interface Controller {
    fun buildTexture(): IntArray
    fun buildFlares(): Array<Flare>
    fun update(flares: Array<Flare>)
    fun dragging(e: MouseEvent) {}
}
