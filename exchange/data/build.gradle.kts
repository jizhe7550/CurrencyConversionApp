plugins {
    alias(libs.plugins.currencyconversionapp.android.library)
    alias(libs.plugins.currencyconversionapp.jvm.ktor)
    alias(libs.plugins.currencyconversionapp.android.test)
}

android {
    namespace = "com.joeji.exchange.data"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.database)

    implementation(projects.exchange.domain)
    implementation(projects.core.common)

    testImplementation(projects.core.testing)
}