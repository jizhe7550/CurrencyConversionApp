import com.joeji.convention.configureTestOptionsIfAndroidApplication
import com.joeji.convention.configureTestOptionsIfAndroidLibrary
import com.joeji.convention.configureTestingDependencies
import com.joeji.convention.enableParallelTest
import com.joeji.convention.useJUnit5
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Convention plugin for Android tests.
 */
class AndroidTestConventionPlugin : Plugin<Project> {

    /**
     * Apply this plugin to the given target object.
     *
     * @param target The target object
     */
    override fun apply(target: Project) {
        with(target) {
            useJUnit5()
            configureTestOptionsIfAndroidLibrary()
            configureTestOptionsIfAndroidApplication()
            enableParallelTest()
            configureTestingDependencies()
        }
    }
}