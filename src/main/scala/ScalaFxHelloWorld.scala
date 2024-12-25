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
import scalafx.scene.control.Alert.AlertType.Warning
import scalafx.scene.input.{DragEvent, MouseEvent}
import scalafx.scene.shape.Rectangle

import scala.jdk.CollectionConverters.*

object ScalaFxHelloWorld extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      //    initStyle(StageStyle.Unified)
      title = "ScalaFX Hello World"

      scene = new Scene(700, 700) {
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
        val rectangleBox: Rectangle = new Rectangle {
          width = 600
          height = 200
          fill = Aqua
        }
        rectangleBox.onDragOver = (e: DragEvent) => { handleFileDrag(e) }
        def handleFileDrag(e: DragEvent): Unit = {
          if (e.getDragboard.hasFiles) {
            val files = e.getDragboard.getFiles.asScala.toList
            val filesQuantity: Int = files.length
            if (filesQuantity > 3) {
              val ProceedButton = new ButtonType("Proceed")
              val ExitButton = new ButtonType("Exit")

              val alert = new Alert(Warning) {
                title = "Warning"
                headerText = "Files Quantity Warning"
                contentText =
                  "You've selected more than 3 files. This may yield unexpected results."
                buttonTypes = Seq(ProceedButton, ExitButton)
              }
              val result = alert.showAndWait()
              result match {
                case Some(ProceedButton) => println("User decided to proceed")
                case Some(ExitButton) =>
                  println("User aborted the operation")
                  return
                case _ => println("User chose CANCEL or closed the dialog")
              }
            }
            Thread.sleep(1000)
            println(s"Trying to pass ${files.length} file.")
            println(files)
          }
        }
        content = List(testButton, rectangleBox)
      }
    }
  }

}
