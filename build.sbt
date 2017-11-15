import scala.collection.mutable.ListBuffer

name := "LotteryGen"

scalaVersion := "2.12.4"


libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4",
  "org.apache.commons" % "commons-email" % "1.5",
  "org.json4s" % "json4s-jackson_2.12" % "3.5.3",
  //"com.typesafe.akka" % "akka-actor_2.12" % "2.5.6",
  "io.swagger" % "swagger-annotations" % "2.0.0-rc2",
  "io.swagger" % "swagger-core" % "2.0.0-rc2",
  "io.swagger" % "swagger-models" % "2.0.0-rc2",
  "io.swagger" % "swagger-jaxrs2" % "2.0.0-rc2",
  //"com.typesafe.akka" % "akka-stream_2.12" % "2.5.6",
  "com.typesafe.akka" % "akka-http_2.12" % "10.0.10",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.6" % Test,
  "javax.ws.rs" % "javax.ws.rs-api" % "2.1"

)

val packagingType = {
  sys.props += "packaging.type" -> "jar"
  ()
}



import ReleaseTransformations._
import sbtrelease.versionFormatError
import sbtrelease.ReleasePlugin.autoImport.ReleaseKeys.versions

import scala.collection.mutable



def theLists: mutable.Buffer[Int] = {

  val mutableList = mutable.Buffer(1,2,3,4,5,6)

  mutableList += 7
  mutableList += 8
  mutableList += 9
  mutableList += 10
  mutableList += 11
  mutableList(0) = 100

  println(mutableList)

  mutableList
}

def sortMinorsFromTag(s: Seq[String]): Seq[(String,String)] = {

  val myList: mutable.ListBuffer[(String,String)] = mutable.ListBuffer[(String,String)]()
  val myValues: mutable.ListBuffer[(String)] = mutable.ListBuffer[(String)]()

  s.foreach { tag =>
    val value = tag.split('.').last
    myValues.append(value)
  }

  s.foreach { tag =>
    val key = (tag.split('.').head, "")
    myList.append(key)
  }

  //val testing: mutable.Map[(String,Int)] = mutable.Map[(String,Int)]()
  println("aaaaaaaaaaaaaaaaaaa" + myList.toMap)
  myList
  //val t = s.split('.')
  //(t.head,t.last.toInt)
}

def sortMinorsFromTag1(s: String) = {
  val t = s.split('.')
  t.last.toInt
}



//val lastTag: String = Process("git describe --abbrev=0 --tags").lines.sorted.last.replace('v', ' ').trim
val allTags: Seq[String] = Process("git tag").lines.toList

val lastTag = allTags.sorted.last


val myMap = scala.collection.mutable.Map[String, Int]()

//def sortedMinors = allTags.map(tag => )

def tr = for ((k,v) <- myMap) println((k, v))



releaseVersion     := { _ => sbtrelease.Version(lastTag).map(_.bumpMinor.withoutQualifier.string).getOrElse(versionFormatError)}

val incrementVersion  = ReleaseStep { state: State =>

  sortMinorsFromTag(allTags)

  println(allTags.sorted)
  println(lastTag)
 // println(">>>>>>>>>>>>>>>>"+ sortedMinors)
  //sortedMinors
  tr

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