scalaVersion := "2.11.8"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.5" % "test"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.2"
libraryDependencies += "com.typesafe" % "config" % "1.2.1"

fork in Test := true // allow to apply extra setting to Test

javaOptions in Test += "-Dconfig.resource=test.conf" // apply extra setting here