name := "skuber-spark-operator"

organization := "com.stacktome"

crossScalaVersions := Seq("2.11.12", "2.12.6")

libraryDependencies ++= Seq(
  "io.skuber" %% "skuber" % "2.1.1"
)

credentials ++= Seq(Credentials(Path.userHome / ".ivy2" / ".credentials"))

isSnapshot := true

enablePlugins(GitVersioning)

git.baseVersion := "0.1"
git.formattedShaVersion := git.gitHeadCommit.value map { sha =>
  if(isSnapshot.value) {
    git.baseVersion.value ++ "-" ++ sha.take(8) ++ "-SNAPSHOT"
  }else {
    git.baseVersion.value ++ "-" ++ sha.take(8)
  }
}

updateOptions := updateOptions.value.withGigahorse(false)
publishTo := {
  val nexus = "https://nexus.stacktome.com"

  if (isSnapshot.value)
    Some("snapshots" at nexus + "/repository/maven-snapshots")
  else
    Some("releases"  at nexus + "/repository/maven-releases")
}