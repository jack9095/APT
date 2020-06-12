package com.mobile.apt_compiler;

import com.google.auto.service.AutoService;
import com.mobile.inject_annotation.BindView;
import java.io.*;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

/**
 * @author tony.wang
 * @time 2020/5/44
 * @description AnnotationsCompiler 编译注解
 */
@AutoService(Processor.class)
public class AnnotationsCompiler extends AbstractProcessor {

    // inject library 下 IBinder 的包名，这里写错编译的时候会报错
    private static final String PACKAGE_NAME_BINDER = "com.mobile.inject";

    /**
     * Java 文件输出类（非常重要）生成它是用来 java 文件
     */
    private Filer filer;

    /**
     * 支持的 Java 版本
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 支持的注解 当前注解处理器
     * 返回的是 set 字符串集合
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    /**
     * javac 编译器  遇到含有 BindView 注解的 java 文件时，就会调用这个 process 方法
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);

        // 类：TypeElement
        // 方法：ExecutableElement
        // 属性：VariableElement

        Map<String, List<VariableElement>> map = new HashMap<>();

        for (Element element : elements) {
            VariableElement variableElement = (VariableElement) element;
            String activityName = variableElement.getEnclosingElement().getSimpleName().toString();
            List<VariableElement> variableElements = map.get(activityName);
            if (variableElements == null) {
                variableElements = new ArrayList<>();
                map.put(activityName, variableElements);
            }
            variableElements.add(variableElement);
        }

        if (map.size() > 0) {
            Writer writer = null;
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String activityName = iterator.next();
                List<VariableElement> variableElements = map.get(activityName);

                //获取包名
                TypeElement typeElement = (TypeElement) variableElements.get(0).getEnclosingElement();
                String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).toString();

                try {
                    JavaFileObject sourceFile =
                            filer.createSourceFile(packageName + "." + activityName + "_ViewBinding");
                    writer = sourceFile.openWriter();
                    writer.write("package " + packageName + ";\n");
                    writer.write("import " + PACKAGE_NAME_BINDER + ".IBinder;\n");
                    writer.write("public class "+activityName+"_ViewBinding implements IBinder<"+packageName+"."+activityName+">{\n");
                    writer.write("@Override\n");
                    writer.write("public void bind("+packageName+"."+activityName+" target){\n");
                    for(VariableElement variableElement:variableElements) {
                        // 获取名字
                        String variableName = variableElement.getSimpleName().toString();
                        // 获取ID
                        int id = variableElement.getAnnotation(BindView.class).value();
                        // 得到类型
                        TypeMirror typeMirror = variableElement.asType();
                        writer.write("target."+variableName+"=("+typeMirror+")target.findViewById("+id+");\n");
                    }
                    writer.write("\n}\n}");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    if(writer!=null)
                    {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return false;
    }
}
