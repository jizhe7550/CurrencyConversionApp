plugins {
    alias(libs.plugins.currencyconversionapp.android.library)
    alias(libs.plugins.currencyconversionapp.jvm.ktor)
}

android {
    namespace = "com.joeji.exchange.data"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.exchange.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.database)
//    testImplementation(projects.core.testing)
}