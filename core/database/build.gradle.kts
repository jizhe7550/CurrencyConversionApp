plugins {
    alias(libs.plugins.currencyconversionapp.android.library)
    alias(libs.plugins.currencyconversionapp.android.room)
    alias(libs.plugins.currencyconversionapp.hilt)
}

android {
    namespace = "com.joeji.core.database"
}

dependencies {
    implementation(projects.core.domain)
}