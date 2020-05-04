organization := "com.iservport"

name := "sbt-iservport"

scalaVersion := "2.12.7"

sbtPlugin := true

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.7.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-web"             % "1.4.4")

enablePlugins(SbtPlugin)

resolvers += Resolver.typesafeIvyRepo("releases")

resolvers += Resolver.mavenLocal

resolvers += "jitpack" at "https://jitpack.io"

publishMavenStyle := true

credentials += Credentials(Path.userHome / ".sbt" / ".githubcredentials")

publishTo := Some("GitHub Package Registry" at "https://maven.pkg.github.com/iservport/sbt-iservport")

