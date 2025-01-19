plugins {
    alias(libs.plugins.currencyconversionapp.android.library)
    alias(libs.plugins.currencyconversionapp.jvm.ktor)
    alias(libs.plugins.currencyconversionapp.hilt)
}

android {
    namespace = "com.joeji.exchange.data"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.database)

    implementation(projects.exchange.domain)
    implementation(projects.core.common)

    testImplementation(projects.core.testing)
    testImplementation(platform(libs.junit5.bom))
    testImplementation(libs.bundles.junit5.api)
    testImplementation(libs.bundles.unit.test)

    androidTestImplementation(projects.core.uiTesting)
    androidTestImplementation(libs.bundles.unit.test)
    androidTestImplementation(libs.bundles.hilt.android.test)
    kspAndroidTest(libs.hilt.android.compiler)
}