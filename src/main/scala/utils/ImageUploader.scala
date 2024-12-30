package utils

import sttp.client4.quick.*
import sttp.client4.Response

import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Files
import javax.imageio.ImageIO

case class ImageUploader (image: File,uploadingSite: String,imageName: Option[String]) {
  private def retrieveApiKey(): String = {
    ??? // TODO - Write logic to store and retrieve api keys
  }
  
  private def retrieveEndpoint(): String = {
    uploadingSite match {
      case "imgbb" => "https://api.imgbb.com/1/upload" // Actual endpoints go here
      case "imgur" => "imgur"
      case _ => "Invalid Url Domain"
    }
  }

  def uploadPicture(): Unit = {
    val endpoint: String = retrieveEndpoint()
    val apiKey: String = retrieveApiKey()
    val imageToUpload = image
    val imageBytes = Files.readAllBytes(imageToUpload.toPath)

    // Create the request
    val response = basicRequest
      .post(uri"https://api.imgbb.com/1/upload?key=${apiKey}")
      .multipartBody(multipart("image", imageBytes).fileName("image.png")) // Binary data with a file name
      .send()

    println(response.statusText)
    println(response.body)
  }
}
object  TestApp extends App {
  val filePath = getClass.getResource("/icons/success-icon.png").getPath
  val imageFile: File = new File(filePath)
  val imageUploader = ImageUploader(imageFile,"imgbb",Some("Test"))
  imageUploader.uploadPicture()
}