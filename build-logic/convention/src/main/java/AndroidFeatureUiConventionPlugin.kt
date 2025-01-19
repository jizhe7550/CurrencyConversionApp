import com.joeji.convention.addUiLayerDependencies
import com.joeji.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureUiConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("currencyconversionapp.android.library.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                "implementation"(project(":core:presentation:ui"))
                "implementation"(project(":core:presentation:designsystem"))

                "implementation"(project.libs.findLibrary("androidx.hilt.navigation.compose").get())
                "implementation"(project.libs.findBundle("compose").get())
                "debugImplementation"(project.libs.findBundle("compose.debug").get())
                "androidTestImplementation"(project.libs.findLibrary("androidx.compose.ui.test.junit4").get())
                "debugImplementation"(project.libs.findLibrary("androidx.compose.ui.test.manifest").get())

                "testImplementation"(libs.findLibrary("androidx.navigation.testing").get())
            }
        }
    }
}