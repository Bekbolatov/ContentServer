import play.sbt.PlayScala

name := """content-server-client"""
organization := "com.sparkydots.contentservice"
version := "1.0-SNAPSHOT"
scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += ws

unmanagedSourceDirectories in Compile += baseDirectory.value / "src" / "main" / "scala"