package utils

import utils.ImageUtils.{isAnImage, saveImage}

import scalafx.scene.{Node, Scene}

import scalafx.scene.input.TransferMode.Link
import scalafx.scene.input.{DragEvent, MouseEvent, TransferMode}

import java.io.File
import scala.jdk.CollectionConverters.*

object DragAndDropUtils {
  def handleFileDrag(e: DragEvent, draggedFiles: List[File]): Unit = {
    try {
      val draggedFiles = e.getDragboard.getFiles.asScala.toList
      val imageList = draggedFiles.filter(isAnImage)
      if (!e.getDragboard.hasFiles || imageList.isEmpty) {
        println("The file you've dropped was not an image.")
      } else {
        val filesQuantity: Int = imageList.length
        println(s"Trying to pass ${filesQuantity} file.")
        println(imageList)
      }

    } catch {
      case e: Exception =>
        println(
          "Exception occured...Please make sure the selected file/files are images"
        )
    }
  }
}
