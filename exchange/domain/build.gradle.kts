plugins {
    alias(libs.plugins.currencyconversionapp.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.kotlinx.coroutines.core)
}
