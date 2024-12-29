package Utils

import scalafx.scene.image.Image

import java.awt.image.BufferedImage
import java.io.{File, IOException}
import javax.imageio.ImageIO


object ImageUtils {
  sealed trait IconAction

  object IconAction {
    case object DROP extends IconAction

    case object ERROR extends IconAction

    case object SUCCESS extends IconAction

    case object WARNING extends IconAction
  }
  
  @throws[IOException]
  def isAnImage(file: File): Boolean = {
    val image: BufferedImage = ImageIO.read(file)
    image != null
  }

  def saveImage(imageList: List[File], imageType: String): Unit = {
    println("Should save the images")
    val fileNames =
      imageList.map(file =>
        file.getName.substring(0, file.getName.lastIndexOf("."))
      )
    val bufferedImageList = imageList.map(ImageIO.read).zip(fileNames)
    val fileExtension = s".${imageType.toLowerCase}"
    val convertedImages: Unit = bufferedImageList.foreach((image, fileName) =>
      ImageIO.write(
        image,
        imageType,
        new File(s"./converted-images/${fileName}${fileExtension}")
      )
    )
  }

  def getIconImage(action: IconAction): Image = {
    val imageUrl = getClass.getResource(s"/icons/${action.toString.toLowerCase}-icon.png").toString
    new Image(
      url = imageUrl,
      requestedWidth = 130,
      requestedHeight = 130,
      preserveRatio = true,
      smooth = true
    )
  }
}
