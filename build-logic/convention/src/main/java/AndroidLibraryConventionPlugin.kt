import com.android.build.api.dsl.LibraryExtension
import com.joeji.convention.ExtensionType
import com.joeji.convention.configureBuildTypes
import com.joeji.convention.configureKotlinAndroid
import com.joeji.convention.libs
import com.joeji.convention.useJUnit5
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            useJUnit5()
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.LIBRARY
                )
                defaultConfig {
                    testInstrumentationRunner = "com.joeji.core.ui_testing.HiltTestRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                packaging {
                    resources.excludes.addAll(
                        listOf(
                            "META-INF/LICENSE.md",
                            "META-INF/LICENSE-notice.md",
                            "META-INF/NOTICE.md",
                            "META-INF/gradle/incremental.annotation.processors",
                            "kotlin/coroutines/coroutines.kotlin_builtins",
                            "kotlin/kotlin.kotlin_builtins",
                            "kotlin/internal/internal.kotlin_builtins",
                            "kotlin/ranges/ranges.kotlin_builtins",
                            "kotlin/reflect/reflect.kotlin_builtins",
                            "kotlin/collections/collections.kotlin_builtins",
                            "kotlin/annotation/annotation.kotlin_builtins",
                        )
                    )
                }
            }
            dependencies {
                "androidTestImplementation"(libs.findLibrary("kotlin.test").get())
                "testImplementation"(libs.findLibrary("kotlin.test").get())

                "implementation"(libs.findLibrary("androidx.tracing.ktx").get())
            }
        }
    }
}