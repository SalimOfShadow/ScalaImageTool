package utils

sealed trait SaveOption
object SaveOption {
  case object Local extends SaveOption
  case object Imgbb extends SaveOption

  def fromString(value: String): Option[SaveOption] = value.toLowerCase match {
    case "local" => Some(Local)
    case "imgbb" => Some(Imgbb)
    case _       => None // Return None for any invalid string
  }
}
