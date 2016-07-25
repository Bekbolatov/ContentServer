name := """content-server"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)


libraryDependencies += "com.sparkydots" %% "service-discovery" % "1.0-SNAPSHOT"
libraryDependencies += "com.sparkydots" %% "content-service-client" % "1.0-SNAPSHOT"
libraryDependencies += "com.sparkydots" %% "latex-service-client" % "1.0-SNAPSHOT"


resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

TwirlKeys.templateImports += "model._"
