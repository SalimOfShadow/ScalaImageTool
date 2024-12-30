ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "ScalaImgTool"
  )
libraryDependencies += "org.scalafx" %% "scalafx" % "23.0.1-R34"
libraryDependencies += "com.softwaremill.sttp.client4" %% "core" % "4.0.0-M20"

libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.11.3"

libraryDependencies ++= {
  // Determine OS version of JavaFX binaries
  lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux") => "linux"
    case n if n.startsWith("Mac") => "mac"
    case n if n.startsWith("Windows") => "win"
    case _ => throw new Exception("Unknown platform!")
  }
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)
}