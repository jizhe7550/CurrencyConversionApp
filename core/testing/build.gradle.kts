plugins {
    alias(libs.plugins.currencyconversionapp.jvm.library)
}

dependencies {
    //dep on project
    implementation(projects.exchange.domain)
    //dep on libs
    implementation(libs.kotlinx.coroutines.core)
    //dep on bundles

    implementation(platform(libs.junit5.bom))
    implementation(libs.bundles.junit5.api)
    implementation(libs.coroutines.test)
}