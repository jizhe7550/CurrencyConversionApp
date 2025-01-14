plugins {
    alias(libs.plugins.currencyconversionapp.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}