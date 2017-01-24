name := """Redbubble"""

version := "1.0"

scalaVersion := "2.12.1"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
libraryDependencies += "com.typesafe" % "config" % "1.2.1"
libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % "2.35.0" % "test"

fork in Test := true // allow to apply extra setting to Test

javaOptions in Test += "-Dconfig.resource=test.conf"