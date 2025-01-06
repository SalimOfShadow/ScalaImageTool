package utils

sealed trait SaveOption
object SaveOption {
  case object Local extends SaveOption
  case object Imgbb extends SaveOption
  case object Imgur extends SaveOption

  def fromString(value: String): Option[SaveOption] = value.toLowerCase match {
    case "local" => Some(Local)
    case "imgbb" => Some(Imgbb)
    case "imgur" => Some(Imgur)
    case _ => None // Return None for any invalid string
  }
}