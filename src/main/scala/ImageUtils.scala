import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File
import java.io.IOException

object PossibleImageFormat {
  val PNG = "png"
  val JPEG = "jpeg"
  val GIF = "gif"
  val BMP = "bmp"
  val TIFF = "tiff"
  val WEBP = "webp"
}

object ImageUtils {
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
}
