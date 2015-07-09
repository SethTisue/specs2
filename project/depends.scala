import sbt._
import Keys._

object depends {

  lazy val scalazVersion = settingKey[String]("defines the current scalaz version")

  lazy val classycle = Seq("org.specs2" % "classycle" % "1.4.3")

  def isScalaPost210(scalaVersion: String) = !scalaVersion.startsWith("2.10.") // we don't even consider anything older than 2.10

  def compiler(scalaVersion: String) = Seq("org.scala-lang" % "scala-compiler" % scalaVersion)

  def reflect(scalaVersion: String) = Seq("org.scala-lang" % "scala-reflect" % scalaVersion)

  def scalaz(scalazVersion: String) =
    Seq("org.scalaz"        %% "scalaz-core"      ,
        "org.scalaz"        %% "scalaz-concurrent").map(_ % scalazVersion) ++
      (if (scalazVersion.startsWith("7.1")) Seq("org.scalaz.stream" %% "scalaz-stream" % "0.7a")
       else                                 Seq("org.scalaz.stream" %% "scalaz-stream" % "0.7"))

  lazy val mockito       = Seq("org.mockito"    % "mockito-core"  % "1.9.5")
  lazy val junit         = Seq("junit"          % "junit"         % "4.12")
  lazy val hamcrest      = Seq("org.hamcrest"   % "hamcrest-core" % "1.3")

  def shapeless(scalaVersion: String) =
    if (isScalaPost210(scalaVersion)) Seq("com.chuusai" %% "shapeless" % "2.1.0")
    else                              Seq("com.chuusai" % ("shapeless_"+scalaVersion) % "2.1.0")

  lazy val pegdown = Seq("org.pegdown" % "pegdown" % "1.2.1")

  lazy val testInterface = Seq("org.scala-sbt"  % "test-interface" % "1.0")

  lazy val tagsoup = "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2"

  def paradise(scalaVersion: String) =
    if (isScalaPost210(scalaVersion)) Nil
    else Seq(compilerPlugin("org.scalamacros" %% "paradise"    % "2.0.1" cross CrossVersion.full),
                            "org.scalamacros" %% "quasiquotes" % "2.0.1")

  lazy val resolvers =
    Seq(updateOptions := updateOptions.value.withCachedResolution(true)) ++ {
      sbt.Keys.resolvers ++=
      Seq(
        Resolver.sonatypeRepo("releases"),
        Resolver.sonatypeRepo("snapshots"),
        Resolver.typesafeIvyRepo("releases"),
        "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases")
    }

}


