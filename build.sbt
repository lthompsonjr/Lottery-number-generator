name := "LotteryGen"

version := "1.0"

scalaVersion := "2.12.4"


libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4",
  "org.apache.commons" % "commons-email" % "1.5",
  "org.json4s" % "json4s-jackson_2.12" % "3.5.3",
  "com.typesafe.akka" %% "akka-actor" % "2.5.6",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.6" % Test
)

import ReleaseTransformations._


releaseProcess := Seq[ReleaseStep](
  //checkSnapshotDependencies,              // : ReleaseStep
  //inquireVersions,                        // : ReleaseStep
  //runClean,                               // : ReleaseStep
  //runTest,                                // : ReleaseStep
  //setReleaseVersion,                      // : ReleaseStep
  //commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  //tagRelease,                             // : ReleaseStep
  //publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
  //setNextVersion,                         // : ReleaseStep
  //commitNextVersion,                      // : ReleaseStep
  //pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)