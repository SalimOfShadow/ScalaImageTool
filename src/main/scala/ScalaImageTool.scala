import utils.DragAndDropUtils.handleFileDrag
import utils.ImageUtils.{IconAction, getIconImage, isAnImage, saveImage, swapIconImage}
import utils.ImageUtils.IconAction.*
import scalafx.application.JFXApp3
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.*
import scalafx.Includes.*
import scalafx.animation.FadeTransition
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.scene.{Node, Scene}
import scalafx.scene.control.*
import scalafx.geometry.{Insets, Pos}
import scalafx.geometry.Pos
import scalafx.scene.layout.StackPane
import scalafx.scene.image.ImageView
import scalafx.scene.control.Alert.AlertType.Warning
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.{DragEvent, MouseEvent, TransferMode}
import scalafx.scene.layout.StackPane
import scalafx.scene.shape.Rectangle
import scalafx.util.Duration

import java.nio.file.{Path, Paths}
import scala.jdk.CollectionConverters.*

object ScalaImageTool extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      //    initStyle(StageStyle.Unified)
      title = "ScalaImageTool"
      resizable = false
      scene = new Scene(700, 700) {
        stylesheets += this.getClass.getResource("/style.css").toExternalForm
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
          styleClass += "format-selector"
        }
        // Handle drags
        val rectangleWidth = 500
        val rectangleHeight = 300
        val rectangleBox: Rectangle = new Rectangle {
          width = 500
          height = 300
          fill = White
          stroke = Color.Black
          strokeDashArray.addAll(10, 5)
        }
        rectangleBox.x = (width() - rectangleWidth) / 2
        rectangleBox.y = (height() - rectangleHeight) / 2
        // Drag related events
        rectangleBox.onDragEntered = (e: DragEvent) => {
          swapIconImage(ATTACH, iconView)
          handleFileDrag(e, e.getDragboard.getFiles.asScala.toList)
        }
        rectangleBox.onDragOver = (e: DragEvent) => {
          if (e.getDragboard.hasFiles) {
            e.acceptTransferModes(TransferMode.Link) // Accept drag-and-drop
          }
          e.consume() // Consume the event
        }
        rectangleBox.onDragDropped = (e: DragEvent) => {
          println("Drag dropped fired")
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
        }
        rectangleBox.onDragExited = (e: DragEvent) => {
          swapIconImage(DROP,iconView)
        }

        // Drop File Icon
        val iconView: ImageView = new ImageView(getIconImage(DROP)) {
          styleClass += "icon-button"
        }

        val stackPane: StackPane = new StackPane() {
          children = iconView
          layoutX = 280
          layoutY = 280
        };
        stackPane.styleClass += "icon-button"

        // Displayed content array
        content = List(rectangleBox, choiceBox, stackPane)
      }
//      centerOnScreen() // Figure out how it works

    }
  }

}
