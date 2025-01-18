plugins {
    alias(libs.plugins.currencyconversionapp.android.library)
}

android {
    namespace = "com.joeji.core.common"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.runner)
}