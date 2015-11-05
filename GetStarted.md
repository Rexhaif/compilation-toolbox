# Introduction #

Java runtime source code compiler and loader. It simply compiles a given source code and loads its byte code into memory. It utilizes Java Compiler API (javax.tools).
This library can be used by any dynamic java application, that needs
compile and load java sources at runtime.


# Usage: #

## Simple source compilation ##

```
    JavaSourceCompiler javaSourceCompiler = new JavaSourceCompilerImpl();
    JavaSourceCompiler.CompilationUnit compilationUnit = javaSourceCompiler.createCompilationUnit();
    String javaSourceCode =  "package com.test.foo;\n" +
      "public class Foo {\n" +
      "        public static void main(String [] args) {\n" +
      "            System.out.println("Hello world");\n" +
      "        }\n" +
      "    }";
    compilationUnit.addJavaSource("com.test.foo.Foo", sourceCode);
    ClassLoader classLoader = javaSourceCompiler.compile(compilationUnit);
    Class fooClass = classLoader.loadClass("com.test.foo.Foo");

```



## Java sources compilation with dependencies ##

```
    JavaSourceCompiler javaSourceCompiler = new JavaSourceCompilerImpl();
    JavaSourceCompiler.CompilationUnit compilationUnit = javaSourceCompiler.createCompilationUnit();
  
    compilationUnit.addClassPathEntry("/Users/xxxx/.m2/repository/javax/inject/javax.inject/1/javax.inject-1.jar");
  
    String iBarSource =  "package com.test;\n" +
       "    public interface IBar {\n" +
       "         public void execute();\n" +
       "    }";
  
    compilationUnit.addJavaSource("com.test.IBar", iBarSource);
  
  
    String barSource =  "package com.test;\n" +
      "import javax.inject.Inject;\n" +
      "public class Bar implements IBar {\n" +
      "        private final String message;\n" +
      "\n" +
      "        @Inject\n" +
      "        public Bar(String message) {\n" +
      "            this.message = message;\n" +
      "        }\n" +
      "\n" +
      "\n" +
      "        @Override\n" +
      "        public void execute() {\n" +
      "            System.out.println(message);\n" +
      "        }\n" +
      "    }";
  

    compilationUnit.addJavaSource("com.test.IBar", barSource);
  
    ClassLoader classLoader = javaSourceCompiler.compile(compilationUnit);
    Class iBar = classLoader.loadClass("com.test.IBar");
    Class bar = classLoader.loadClass("com.test.Bar");
  
```


## Build ##

### Maven ###

This project is managed by Maven Central Repository.

Just the add the following dependency to your maven project.
```
<dependency>
    <groupId>org.abstractmeta</groupId>
    <artifactId>compilation-toolbox</artifactId>
    <version>0.3.1</version>
</dependency>
```
### Ant ###

Latest library version can be downloaded from this project.