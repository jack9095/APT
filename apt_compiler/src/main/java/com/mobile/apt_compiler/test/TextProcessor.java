//package com.mobile.apt_compiler;
//
//import com.google.auto.service.AutoService;
//import com.squareup.javapoet.JavaFile;
//import com.squareup.javapoet.MethodSpec;
//import com.squareup.javapoet.TypeSpec;
//
//import java.io.IOException;
//import java.util.Set;
//
//import javax.annotation.processing.AbstractProcessor;
//import javax.annotation.processing.Filer;
//import javax.annotation.processing.Messager;
//import javax.annotation.processing.ProcessingEnvironment;
//import javax.annotation.processing.RoundEnvironment;
//import javax.annotation.processing.SupportedAnnotationTypes;
//import javax.annotation.processing.SupportedSourceVersion;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.Modifier;
//import javax.lang.model.element.TypeElement;
//import javax.tools.Diagnostic;
//
///**
// * 生成for循环代码块快捷键 https://www.jianshu.com/p/cd8b1691d647
// * 注解处理器
// * apt 可以在编译的时候采集信息，例如通过注解
// */
//@AutoService(Process.class)
//// 允许注解处理器处理的注解
//@SupportedAnnotationTypes({"com.example.annotation.InjectView"})
//// 指定编译使用的 JAVA 版本
//@SupportedSourceVersion(SourceVersion.RELEASE_7)
//public class TextProcessor extends AbstractProcessor {
//
//    // 打印日志工具
//    private Messager messager;
//    // 文件工具
//    private Filer filer;
//
//    /**
//     * 初始化方法
//     *
//     * @param processingEnv
//     */
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnv) {
//        super.init(processingEnv);
//        messager = processingEnv.getMessager();
//        filer = processingEnv.getFiler();
//    }
//
//    /**
//     * 注解处理器可以 debug 调试 在 Run 中有个 Edit Configurations 选项,一般很慢，不建议使用
//     *
//     * @param annotations 使用了当前注解处理器的节点集合
//     * @param roundEnv    环境
//     * @return
//     */
//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        // 遍历注解如 InjectView 的节点
//        for (TypeElement typeElement : annotations) {
//
//            // 添加注解的控件或者方法的节点集合，如给 TextView 添加了注解 这里就是 TextView 的节点集合
//            Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(typeElement);
//            for (Element element : elementsAnnotatedWith) {
//                messager.printMessage(Diagnostic.Kind.NOTE,element.getSimpleName() + "wangfei");
//                // 获取 TextView 的父节点 ，如果在 Activity 中的 TextView，父节点就是 Activity
//                Element enclosingElement = element.getEnclosingElement();
//                String className = element.getSimpleName() + "_Binding";
//
//                // 方法（函数），这里是 main 函数
//                MethodSpec main = MethodSpec.methodBuilder("test")
//                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC) // 用 PUBLIC 和 STATIC 修饰
//                        .returns(void.class) // 返回值是 void
//                        .addParameter(String[].class, "args") // 形参
//                        .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!") // 输出语句
//                        .build();
//
//                TypeSpec helloWorld = TypeSpec.classBuilder(className) // 类的名字 HelloWorld
//                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL) // 用 PUBLIC 和 FINAL 修饰
//                        .addMethod(main)  // 添加了 main 函数
//                        .build();
//
//                JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
//                        .build();
//
//                try {
//                    javaFile.writeTo(filer);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//                /*
//                例子
//                public final class HelloWorld {
//                  public static void main(String[] args) {
//                    System.out.println("Hello, JavaPoet!");
//                  }
//                }
//
//
//                // 方法（函数），这里是 main 函数
//                MethodSpec main = MethodSpec.methodBuilder("main")
//                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC) // 用 PUBLIC 和 STATIC 修饰
//                        .returns(void.class) // 返回值是 void
//                        .addParameter(String[].class, "args") // 形参
//                        .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!") // 输出语句
//                        .build();
//
//                TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld") // 类的名字 HelloWorld
//                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL) // 用 PUBLIC 和 FINAL 修饰
//                        .addMethod(main)  // 添加了 main 函数
//                        .build();
//
//                JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
//                        .build();
//
//                // javaFile.writeTo(System.out);
//                javaFile.writeTo(filer);
//
//                 */
//            }
//        }
//        return false; // true 表示注解处理器不会处理了
//    }
//}
