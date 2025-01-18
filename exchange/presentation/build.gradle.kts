plugins {
    alias(libs.plugins.currencyconversionapp.android.feature.ui)
    alias(libs.plugins.currencyconversionapp.android.test)
}

android {
    namespace = "com.joeji.exchange.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.exchange.domain)

    testImplementation(projects.core.testing)
}