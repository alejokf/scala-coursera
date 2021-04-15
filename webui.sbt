lazy val root = project in file(".")

lazy val webUI = project.in(file("web-ui")).
  enablePlugins(ScalaJSPlugin).
  settings(
    scalaVersion := "2.13.4",
    // Add the sources of the calculator project
    unmanagedSourceDirectories in Compile +=
      (scalaSource in (root, Compile)).value / "calculator",
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.1.0",
    scalaJSUseMainModuleInitializer := true
  )
