import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.Timer

class MainFrame : JFrame("Souls") {

    private val demo: DemoContainer

    private fun createInterface(): JPanel {
        val buttonPanel = JPanel()
        val soulsButton = JButton("Souls")
        val nebulaButton = JButton("Nebula")
        val swarmButton = JButton("Swarm")
        soulsButton.addActionListener { demo.controller = Souls(demo.width, demo.height); title = "Souls" }
        nebulaButton.addActionListener { demo.controller = Nebula(demo.width, demo.height); title = "Nebula" }
        swarmButton.addActionListener { demo.controller = Swarm(demo.width, demo.height); title = "Swarm" }
        buttonPanel.add(soulsButton)
        buttonPanel.add(nebulaButton)
        buttonPanel.add(swarmButton)
        return buttonPanel
    }

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout()
        demo = DemoContainer()
        demo.controller = Souls(demo.width, demo.height)
        contentPane.add(demo, BorderLayout.CENTER)
        contentPane.add(createInterface(), BorderLayout.SOUTH)
        pack()
        isResizable = false
        isVisible = true
        setLocationRelativeTo(null)
        Timer(16) { demo.tick() }.start()
    }
}
