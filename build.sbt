name := "hellower"

version := "1.0"

scalaVersion := "2.11.7"

val akkaVersion = "2.4.0"
val scalaTestVersion = "2.2.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
