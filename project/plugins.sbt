logLevel := Level.Warn

addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.0.8-0.6")

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value