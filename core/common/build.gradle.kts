plugins {
    alias(libs.plugins.currencyconversionapp.android.library)
    alias(libs.plugins.currencyconversionapp.jvm.ktor)
    alias(libs.plugins.currencyconversionapp.android.test)
}

android {
    namespace = "com.joeji.core.common"
}

dependencies {
    implementation(libs.bundles.koin)
}