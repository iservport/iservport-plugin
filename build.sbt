organization := "com.iservport"

name := "sbt-iservport"

scalaVersion := "2.12.7"

sbtPlugin := true

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.8.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-web"             % "1.4.4")

enablePlugins(SbtPlugin)

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.mavenLocal

resolvers += "jitpack" at "https://jitpack.io"

// credentials += Credentials(Path.userHome / ".sbt" / ".githubcredentials")

publishTo := Some("GitHub iservport Apache Maven Packages" at "https://maven.pkg.github.com/iservport/iservport-concurrency")
publishMavenStyle := true
credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  "iservport",
  System.getenv("PUBLISH_TOKEN")
)
