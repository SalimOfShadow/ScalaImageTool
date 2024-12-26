import ImageUtils.{isAnImage, saveImage}
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
import scalafx.scene.input.TransferMode.Link
import scalafx.scene.input.{DragEvent, MouseEvent, TransferMode}
import scalafx.scene.shape.Rectangle

import java.io.FileNotFoundException
import scala.jdk.CollectionConverters.*

object ScalaFxHelloWorld extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      //    initStyle(StageStyle.Unified)
      title = "ScalaFX Hello World"
      resizable = false
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

        rectangleBox.onDragOver = (e: DragEvent) => handleFileDrag(e)

        def handleFileDrag(e: DragEvent): Unit = {
          try {
            val draggedFiles = e.getDragboard.getFiles.asScala.toList
            val imageList = draggedFiles.filter(isAnImage)
            if (!e.getDragboard.hasFiles || imageList.isEmpty) {
              rectangleBox.fill = Red
              println("The file you've dropped was not an image.")
            } else {
              rectangleBox.fill = Green
              val filesQuantity: Int = imageList.length
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
                rectangleBox.fill = Aqua
                result match {
                  case Some(ProceedButton) => println("User decided to proceed")
                  case Some(ExitButton) =>
                    println("User aborted the operation")
                    return
                  case _ => println("User chose CANCEL or closed the dialog")
                }
              }
              println(s"Trying to pass ${filesQuantity} file.")
              println(imageList)
              e.acceptTransferModes(Link)
            }

          } catch {
            case e: Exception =>
              rectangleBox.fill = Red
              println(
                "Exception occured...Please make sure the selected file/files are images"
              )
          }
        }
        rectangleBox.onDragExited = (e: DragEvent) => {
          val draggedFiles = e.getDragboard.getFiles.asScala.toList
          val imageList = draggedFiles.filter(isAnImage)
          saveImage(imageList, "jpeg")
          rectangleBox.fill = Aqua
        }
        content = List(testButton, rectangleBox)
      }
    }
  }

}
