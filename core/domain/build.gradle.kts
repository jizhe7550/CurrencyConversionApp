plugins {
    alias(libs.plugins.currencyconversionapp.jvm.library)
    alias(libs.plugins.currencyconversionapp.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}