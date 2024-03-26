plugins {
    alias(libs.plugins.androidApplication)
    id 'jacoco'
    id 'org.sonarqube' version '4.4.1.3373'
}

android {
    namespace = "com.example.gruppe03"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gruppe03"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    testOptions {
        unitTests.all {
            useJUnitPlatform()
            finalizedBy jacocoTestReport
        }
    }
}
tasks.register('jacocoTestReport', JacocoReport) {
    dependsOn 'testDebugUnitTest'

    reports {
        xml.required = true
        xml.destination file("${project.projectDir}/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
    }

    def fileFilter = ['**/R.class', '**/R$*.class', '**/BuildConfig.*', '**/Manifest*.*', '**/*Test*.*', 'android/**/*.*']
    def debugTree = fileTree(dir: "${project.layout.buildDirectory.get().asFile}/intermediates/javac/debug", excludes: fileFilter)
    def mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.from = files([mainSrc])
    classDirectories.from = files([debugTree])
    executionData.from = files("${project.layout.buildDirectory.get().asFile}/jacoco/testDebugUnitTest.exec")
}
sonar {
    properties {
        property "sonar.projectKey", "Gruppe-III_gruppe03"
        property "sonar.organization", "gruppe-iii"
        property "sonar.host.url", "https://sonarcloud.io"
        property "sonar.java.coveragePlugin", "jacoco"
        property "sonar.coverage.jacoco.xmlReportPaths", "${project.projectDir}/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation libs.ext.junit
    androidTestImplementation libs.espresso.core
}