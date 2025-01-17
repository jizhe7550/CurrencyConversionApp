plugins {
    `kotlin-dsl`
}

group = "com.joeji.currencyconversionapp.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "currencyconversionapp.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "currencyconversionapp.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "currencyconversionapp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "currencyconversionapp.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeatureUi") {
            id = "currencyconversionapp.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }
        register("androidRoom") {
            id = "currencyconversionapp.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidDynamicFeature") {
            id = "currencyconversionapp.android.dynamic.feature"
            implementationClass = "AndroidDynamicFeatureConventionPlugin"
        }
        register("jvmLibrary") {
            id = "currencyconversionapp.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("jvmKtor") {
            id = "currencyconversionapp.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }
        register("jvmTest") {
            id = "currencyconversionapp.jvm.test"
            implementationClass = "JvmTestConventionPlugin"
        }
        register("androidTest") {
            id = "currencyconversionapp.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
    }
}