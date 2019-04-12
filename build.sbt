name := "skuber-spark-operator"

version := "0.1-SNAPSHOT"

organization := "com.stacktome"

//scalaVersion := "2.11.12"

crossScalaVersions := Seq("2.11.12", "2.12.6")

libraryDependencies ++= Seq(
  "io.skuber" %% "skuber" % "2.1.1"
)

credentials ++= Seq(Credentials(Path.userHome / ".ivy2" / ".credentials"))

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
buildInfoPackage := "stacktome"

publishTo := Some("My Nexus" at "https://nexus.stacktome.com/repository/maven-snapshots/")

enablePlugins(BuildInfoPlugin)