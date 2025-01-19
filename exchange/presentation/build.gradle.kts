plugins {
    alias(libs.plugins.currencyconversionapp.android.feature.ui)
    alias(libs.plugins.currencyconversionapp.hilt)
}

android {
    namespace = "com.joeji.exchange.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.exchange.domain)

    testImplementation(projects.core.testing)

    testImplementation(platform(libs.junit5.bom))
    testImplementation(libs.bundles.unit.test)
    testImplementation(libs.bundles.junit5.api)

    androidTestImplementation(projects.core.testing)
    androidTestImplementation(projects.core.uiTesting)
    androidTestImplementation(libs.bundles.unit.test)
    androidTestImplementation(libs.bundles.hilt.android.test)
    kspAndroidTest(libs.hilt.android.compiler)
}