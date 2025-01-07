package utils

import com.typesafe.config.ConfigFactory

import sttp.client4.quick.*
import io.circe._, io.circe.parser._
import java.io.File
import java.nio.file.Files

case class ImageUploader(
                          imageList: List[File],
                          uploadingSite: String,
                          imageName: Option[String]
                        ) {
  private def retrieveApiKey(): String = {
    try {
      val configPath = getClass.getResource("/application.conf").getPath
      val config = ConfigFactory.load()
      val apiKey = config.getString(s"api.$uploadingSite")
      apiKey
    } catch
      case e =>
        val errorMessage: String = "Encountered an error.Please make sure the API key is correctly placed inside application.conf"
        println(errorMessage)
        errorMessage
  }

  private def retrieveEndpoint(): String = {
    uploadingSite match {
      case "imgbb" =>
        "https://api.imgbb.com/1/upload" // Actual endpoints go here
      case _ => "Invalid Url Domain"
    }
  }

  def uploadPicture(): String = {
    try {
      println(imageList)
      val endpoint: String = retrieveEndpoint()
      val apiKey: String = retrieveApiKey()
      if (!apiKey.contains("Encountered an error.")) {
        val parsedImagesResponse: String = imageList.map(image => {
          val imageToUpload = image
          val imageBytes = Files.readAllBytes(imageToUpload.toPath)
          // Create the request
          val response = basicRequest
            .post(uri"https://api.imgbb.com/1/upload?key=$apiKey")
            .multipartBody(
              multipart("image", imageBytes).fileName("image.png")
            ) // Binary data with a file name
            .send()
          val responseMessage: String = response.body match {
            case Right(succ) =>
              val jsonResponse = parse(succ)
              val urlList = List()
              val response = jsonResponse match {
                case Left(err) => "Failed parsing the JSON response"
                case Right(json) =>
                  val cursor = json.hcursor
                  val url = cursor
                    .downField("data")
                    .get[String]("url_viewer")
                    .getOrElse("No url was found")
                  url
              }
              println(
                s"Successfully uploaded the image! $response"
              )
              val resultURLs = s"${response.mkString("")}      "
              resultURLs
            case Left(err) =>
              println(s"The image upload has failed. $err")
              s"The image upload has failed. $err"
          }
          responseMessage
        }).mkString("")
        if (parsedImagesResponse.contains("https://")){
          s"Successfully uploaded the image! $parsedImagesResponse"
        }else{
          s"Error: ${parsedImagesResponse}"
        }
      } else {
        "The image was not uploaded successfully"
      }
    } catch {
      case e: Exception =>
        if (e.getLocalizedMessage.contains("No configuration setting found")) {
          println(
            "Please provide an API key inside ./resources/application.conf"
          )
          println(e.getLocalizedMessage)
          "Please provide an API key inside ./resources/application.conf"
        } else {
          "Encountered an error while uploading the picture"
        }
    }
  }
}

object TestApp extends App {
  private val filePath = getClass.getResource("/icons/success-icon.png").getPath
  private val imageFile: File = new File(filePath)
  private val imageUploader = ImageUploader(List(imageFile), "imgbb", Some("Test"))
  imageUploader.uploadPicture()
}
