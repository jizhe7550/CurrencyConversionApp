plugins {
    alias(libs.plugins.currencyconversionapp.jvm.library)
    alias(libs.plugins.currencyconversionapp.hilt)
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(projects.core.testing)

    testImplementation(platform(libs.junit5.bom))
    testImplementation(libs.bundles.unit.test)
    testImplementation(libs.bundles.junit5.api)
}