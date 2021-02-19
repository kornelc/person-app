name := "scala-course"

version := "0.1"

scalaVersion := "2.13.4"

val AkkaVersion = "2.6.12"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test"
libraryDependencies += "org.json4s" % "json4s-native_2.13" % "3.7.0-M8"
libraryDependencies += "com.typesafe" % "config" % "1.4.0"
libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
