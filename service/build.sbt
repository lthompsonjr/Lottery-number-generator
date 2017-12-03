name := """lottery-generator-service"""

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4",
  "org.apache.commons" % "commons-email" % "1.5",
  "org.json4s" % "json4s-jackson_2.12" % "3.5.3"
)