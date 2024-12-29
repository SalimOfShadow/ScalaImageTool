package utils

import scalafx.scene.image.Image

case class ImageUploader (image: Image,uploadingSite: String) {
  def retrieveEndpoint(): String = {
    uploadingSite match {
      case "imgbb" => "imgbb" // Actual endpoints go here
      case "imgur" => "imgur"
      case _ => "Invalid Url Domain"
    }
  }

  def uploadPicture(): Unit = {
    val endpoint: String = retrieveEndpoint()


  }
}