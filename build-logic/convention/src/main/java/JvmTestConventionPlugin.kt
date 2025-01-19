import com.joeji.convention.configureTestingDependencies
import com.joeji.convention.enableParallelTest
import com.joeji.convention.libs
import com.joeji.convention.useJUnit5
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class JvmTestConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("org.jetbrains.kotlin.jvm")
            }
            useJUnit5()
            enableParallelTest()
            setTestReportPath()
            configureTestingDependencies()

            dependencies {
                "testImplementation"(libs.findLibrary("kotlin.test").get())
            }
        }
    }

    private fun Project.setTestReportPath() = tasks.withType<Test> {
        reports {
            val path = "${layout.buildDirectory.get()}/unittest"

            html.required.set(true)
            html.outputLocation.set(file("$path/html"))

            junitXml.required.set(true)
            junitXml.outputLocation.set(file("$path/junit"))
        }
    }
}