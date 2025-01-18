import com.android.build.api.dsl.LibraryExtension
import com.joeji.convention.ExtensionType
import com.joeji.convention.configureBuildTypes
import com.joeji.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.LIBRARY
                )

                defaultConfig {
                    testInstrumentationRunner =
                        "com.joeji.core.common.androidtest.InstrumentationTestRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                packaging {
                    resources.excludes.addAll(
                        listOf(
                            "META-INF/LICENSE.md",
                            "META-INF/LICENSE-notice.md",
                            "META-INF/NOTICE.md"
                        )
                    )
                }
            }
        }
    }
}