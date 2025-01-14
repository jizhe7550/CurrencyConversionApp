plugins {
    alias(libs.plugins.currencyconversionapp.android.feature.ui)
}

android {
    namespace = "com.joeji.exchange.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.exchange.domain)
}