package utils

import sttp.client4.quick.*
import sttp.client4.Response

import com.typesafe.config.{Config, ConfigFactory}

import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Files
import javax.imageio.ImageIO

case class ImageUploader(
    image: File,
    uploadingSite: String,
    imageName: Option[String]
) {
  private def retrieveApiKey(): String = {
    ??? // TODO - Write logic to store and retrieve api keys
  }

  private def retrieveEndpoint(): String = {
    uploadingSite match {
      case "imgbb" =>
        "https://api.imgbb.com/1/upload" // Actual endpoints go here
      case "imgur" => "imgur"
      case _       => "Invalid Url Domain"
    }
  }

  def uploadPicture(): Unit = {
    try {
      val endpoint: String = retrieveEndpoint()
      //    val apiKey: String = retrieveApiKey()
      val imageToUpload = image
      val imageBytes = Files.readAllBytes(imageToUpload.toPath)
      val configPath = getClass.getResource("/application.conf").getPath
      val config = ConfigFactory.load()
      val apiKey = config.getString(s"api.${uploadingSite}")
      // Create the request
      val response = basicRequest
        .post(uri"https://api.imgbb.com/1/upload?key=${apiKey}")
        .multipartBody(
          multipart("image", imageBytes).fileName("image.png")
        ) // Binary data with a file name
        .send()
      val responseMessage: String = response.body match {
        case Right(succ) => { s"Successfully uploaded the image! ${succ}" }
        case Left(err) => { s"The image upload has failed. ${err}" }
      }
      println(responseMessage)

    } catch {
      case e: Exception => {
        if (e.getLocalizedMessage.contains("No configuration setting found")) {
          println(
            "Please provide an API key inside ./resources/application.conf"
          )
          println(e.getLocalizedMessage)
        }
      }
    }
  }
}
object TestApp extends App {
  val filePath = getClass.getResource("/icons/success-icon.png").getPath
  val imageFile: File = new File(filePath)
  val imageUploader = ImageUploader(imageFile, "imgbb", Some("Test"))
  imageUploader.uploadPicture()
}
