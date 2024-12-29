package utils

import scalafx.scene.image.Image

case class ImageUploader (image: Image,uploadingSite: String) {
  def retrieveEndpoint(): Unit = {
    uploadingSite match{
      case "imgbb" =>  println("imgbb")  // Actual endpoints go here
      case "imgur" =>  println("imgur")
      case _ =>  println("Invalid Url Domain")
    }
  }

  def uploadPicture() = ???
}