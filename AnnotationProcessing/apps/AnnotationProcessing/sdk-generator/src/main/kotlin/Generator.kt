import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import org.example.annotationprocessing.MyConstant
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService
class Generator: AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(MyConstant::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return super.getSupportedSourceVersion()
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        val elementsWithMyConstant = roundEnv?.getElementsAnnotatedWith((MyConstant::class.java))

        // Create Kotlin File
        val packageName = "org.example.sdk-generated"
        val fileName = "MyGeneratedConstant"
        val file = FileSpec.builder(packageName, fileName).build()
        val generatedDirectory = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(generatedDirectory,  "$fileName.kt"))



        return true
    }

    companion object {
        const val  KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

}