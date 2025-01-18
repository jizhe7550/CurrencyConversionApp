plugins {
    alias(libs.plugins.currencyconversionapp.android.library)
    alias(libs.plugins.currencyconversionapp.hilt)
}

android {
    namespace = "com.joeji.core.common"
}

dependencies {
    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android.testing)
}