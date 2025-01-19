import com.joeji.convention.configureKotlinJvm
import com.joeji.convention.useJUnit5
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            useJUnit5()
            configureKotlinJvm()
        }
    }
}