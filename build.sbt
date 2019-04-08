name := "skuber-spark-operator"

version := "0.1-SNAPSHOT"

organization := "com.stacktome"

//scalaVersion := "2.11.12"

crossScalaVersions := Seq("2.11.12", "2.12.6")

libraryDependencies ++= Seq(
  "io.skuber" %% "skuber" % "2.1.1"
)