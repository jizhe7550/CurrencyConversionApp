import com.android.build.api.dsl.ApplicationExtension
import com.joeji.convention.ExtensionType
import com.joeji.convention.configureBuildTypes
import com.joeji.convention.configureKotlinAndroid
import com.joeji.convention.libs
import com.joeji.convention.useJUnit5
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()

                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()

                    testInstrumentationRunner = "com.joeji.core.ui_testing.HiltTestRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                packaging {
                    resources.excludes.addAll(
                        listOf(
                            "META-INF/LICENSE.md",
                            "META-INF/LICENSE-notice.md",
                            "META-INF/NOTICE.md",
                            "META-INF/gradle/incremental.annotation.processors",
                            "kotlin/coroutines/coroutines.kotlin_builtins",
                        )
                    )
                }

                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.APPLICATION
                )
            }
            useJUnit5()
            dependencies {
                "androidTestImplementation"(libs.findLibrary("kotlin.test").get())
                "testImplementation"(libs.findLibrary("kotlin.test").get())

                "implementation"(libs.findLibrary("androidx.tracing.ktx").get())
            }
        }
    }

}