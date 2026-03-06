// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.google.gms.services) apply false
    alias(libs.plugins.sonarqube)
   // id("org.sonarqube") version "5.1.0.4882"

}

sonar {
    properties {
        property("sonar.projectKey", "Abhijeet41_JetFoodReceipeMultiModule")
        property("sonar.projectName", "abhijeet41")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.token", "464400f561f2760537e7a47e3b81844801900404")

        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.android.lint.report", "build/reports/lint-results.xml")
    }
}
