plugins {
    alias(libs.plugins.currencyconversionapp.android.library)
    alias(libs.plugins.currencyconversionapp.android.room)
    alias(libs.plugins.currencyconversionapp.hilt)
}

android {
    namespace = "com.joeji.core.ui_testing"
}

dependencies {
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.exchange.domain)
    implementation(projects.exchange.data)

    implementation(platform(libs.junit5.bom))
    implementation(libs.bundles.junit5.api)

    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android.testing)
}