import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.*
import scalafx.scene.text.Text
import scalafx.Includes.*
import scalafx.scene.Scene
import scalafx.scene.{Node, Scene}
import scalafx.scene.control.*
import scalafx.event.ActionEvent
import scalafx.scene.input.MouseEvent

object ScalaFxHelloWorld extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      //    initStyle(StageStyle.Unified)
      title = "ScalaFX Hello World"

      scene = new Scene(300, 200) {
        val testButton: Button = new Button("Test Button") {
          layoutX = 50
          layoutY = 50
          onAction = (e: ActionEvent) => {
            println("Button clicked")

          }
          onDragDetected = (e: MouseEvent) => {
            println("Drag Detected")
          }
        }
        content = List(testButton)
      }
    }
  }
}
