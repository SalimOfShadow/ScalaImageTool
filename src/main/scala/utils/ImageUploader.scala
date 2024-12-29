package utils

import scalafx.scene.image.Image

case class ImageUploader (image: Image,uploadingSite: String) {
  def retrieveEndpoint(domain:String): Unit = {
    domain match{
      case "imgbb" =>  println("imgbb")
      case "imgur" =>  println("imgur")
      case _ =>  println("Invalid Url Domain")
    }
  }


}