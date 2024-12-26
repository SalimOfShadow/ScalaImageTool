import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File
import java.io.IOException

object ImageUtils {
  @throws[IOException]
  def isAnImage(file: File): Boolean = {
    val image: BufferedImage = ImageIO.read(file)
    image != null
  }

  def saveImage(imageList: List[File], imageType: String): Unit = {
    println("Should save the images")
    val fileExtension = s".${imageType.toLowerCase}"
    val bufferedImageList = imageList.map(ImageIO.read).zipWithIndex
    val convertedImages: Unit = bufferedImageList.foreach((image, index) =>
      ImageIO.write(
        image,
        imageType,
        new File(s"./convertedImage${fileExtension}-${index}")
      )
    )
  }
}
