import com.joeji.convention.configureKotlinJvm
import com.joeji.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class JvmLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            configureKotlinJvm()

            dependencies {
                "testImplementation"(kotlin("test"))
                "testImplementation"(libs.findBundle("unit.test").get())
                "testImplementation"(libs.findBundle("junit5.api").get())
            }
        }
    }
}