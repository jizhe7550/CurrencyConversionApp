import com.joeji.convention.addUiLayerDependencies
import com.joeji.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureUiConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("currencyconversionapp.android.library.compose")
                apply("currencyconversionapp.hilt")
            }

            dependencies {
                addUiLayerDependencies(target)
            }
        }
    }
}