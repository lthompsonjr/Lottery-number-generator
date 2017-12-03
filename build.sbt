scalaVersion := "2.12.4"

val packagingType = {
  sys.props += "packaging.type" -> "jar"
  ()
}

lazy val commonSettings =

  libraryDependencies ++= Seq(
    "com.typesafe.akka" % "akka-http_2.12" % "10.0.10",
    "com.typesafe.akka" %% "akka-testkit" % "2.5.6" % Test,
    "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
    "net.logstash.logback" % "logstash-logback-encoder" % "4.11",
    "ch.qos.logback" % "logback-classic" % "1.2.3"
  )


import ReleaseTransformations._
import sbtrelease.versionFormatError
import sbtrelease.ReleasePlugin.autoImport.ReleaseKeys.versions

val allTags: Seq[String] = Process("git tag").lines.toList

val lastTag = allTags.sorted.last

releaseVersion     := { _ => sbtrelease.Version(lastTag).map(_.bumpMinor.withoutQualifier.string).getOrElse(versionFormatError)}

val incrementVersion  = ReleaseStep { state: State =>

  println(allTags.sorted)
  println(lastTag)

  if(lastTag.isEmpty){
    sys.error("missing tag, cannot proceed")
  }
  val releaseVersion: String = sbtrelease.Version(lastTag).map(_.bumpMinor.withoutQualifier.string).getOrElse(versionFormatError)
  println(releaseVersion)

  state.put(versions, (releaseVersion, "N/A"))

}




releaseProcess := Seq[ReleaseStep](
  //checkSnapshotDependencies,              // : ReleaseStep
  //inquireVersions,                        // : ReleaseStep
  runClean,
  incrementVersion,// : ReleaseStep
  //runTest,                                // : ReleaseStep
  setReleaseVersion,
  //setNextVersion,// : ReleaseStep
  //commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  //tagRelease,
  releaseStepCommand("package")
  // : ReleaseStep
  //publishArtifacts                       // : ReleaseStep, checks whether `publishTo` is properly set up
  //setNextVersion,                         // : ReleaseStep
  //commitNextVersion,                      // : ReleaseStep
  //pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)


lazy val docs = project
  .settings(commonSettings: _*)

lazy val service = project
  .settings(commonSettings: _*)