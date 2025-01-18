plugins {
    alias(libs.plugins.currencyconversionapp.android.library)
    alias(libs.plugins.currencyconversionapp.jvm.ktor)
    alias(libs.plugins.currencyconversionapp.hilt)
}

android {
    namespace = "com.joeji.core.data"
}

dependencies {
    implementation(libs.timber)
    implementation(libs.androidx.datastore.preferences)

    implementation(projects.core.domain)
    implementation(projects.core.common)
}