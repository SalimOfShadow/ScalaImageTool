import ImageUtils.{isAnImage, saveImage}
import PossibleImageFormat.*
import scalafx.application.JFXApp3
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.*
import scalafx.Includes.*
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.scene.{Node, Scene}
import scalafx.scene.control.*
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control.Alert.AlertType.Warning
import scalafx.scene.input.TransferMode.Link
import scalafx.scene.input.{DragEvent, MouseEvent, TransferMode}
import scalafx.scene.shape.Rectangle

import scala.jdk.CollectionConverters.*

object ScalaImageTool extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      //    initStyle(StageStyle.Unified)
      title = "ScalaImageTool"
      resizable = false
      scene = new Scene(700, 700) {
        // Dropdown menu
        val choiceBox: ComboBox[String] = new ComboBox(
          List("PNG", "JPEG", "GIF", "BMP", "TIFF", "WEBP")
        ) {
          promptText = "Please Select A File Format." // Initial label
          layoutX = 100
          layoutY = 50
          padding = Insets(5, 15, 5, 15)
          prefWidth = 250
          prefHeight = 10
          style =
            "-fx-font-size: 13;\n    -fx-font-family: \"Times New Roman\";\n    -fx-background-color: '#91959c';"
        }

        // Handle drags
        val rectangleBox: Rectangle = new Rectangle {
          width = 600
          height = 200
          fill = Aqua
        }

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
              println(s"Trying to pass ${filesQuantity} file.")
              println(imageList)
              e.acceptTransferModes(Link) // Is it needed?
            }

          } catch {
            case e: Exception =>
              rectangleBox.fill = Red
              println(
                "Exception occured...Please make sure the selected file/files are images"
              )
          }
        }
        // Drag related events
        rectangleBox.onDragOver = (e: DragEvent) => handleFileDrag(e)
        rectangleBox.onDragDropped = (e: DragEvent) => {
          val draggedFiles = e.getDragboard.getFiles.asScala.toList
          val imageList = draggedFiles.filter(isAnImage)
          val selectedImageFormat: String =
            Option(choiceBox.value.value).map(_.toLowerCase).getOrElse("")

          if (selectedImageFormat != "") {
            println(selectedImageFormat)
            saveImage(imageList, selectedImageFormat)
          } else {
            val ProceedButton = new ButtonType("OK")
            val alert = new Alert(Warning) {
              title = "Warning"
              headerText = "Invalid File Format"
              contentText =
                "Please select a valid file format before proceeding."
              buttonTypes = Seq(ProceedButton)
            }
            val result = alert.showAndWait()
          }
          rectangleBox.fill = Aqua
        }
        rectangleBox.onDragExited = (e: DragEvent) => {
          rectangleBox.fill = Aqua // Return to previous idle state
        }

        content = List(rectangleBox, choiceBox)
      }
    }
  }

}
