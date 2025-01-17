plugins {
    alias(libs.plugins.currencyconversionapp.jvm.library)
    alias(libs.plugins.currencyconversionapp.jvm.test)
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(projects.core.testing)
}