package org.abstractmeta.toolbox.compilation.compiler;


import org.abstractmeta.toolbox.compilation.compiler.registry.JavaFileObjectRegistry;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Java source code compiler. It compiles given java class sources in memory and loads them into memory.
 * It utilises Java Compiler API.
 * <p><b>Usage:</b>
 * <ul>
 *  <li>Simple source compilation</li>
 *  <code><pre>
 *  JavaSourceCompiler javaSourceCompiler = new JavaSourceCompilerImpl();
 *  JavaSourceCompiler.CompilationUnit compilationUnit = javaSourceCompiler.createCompilationUnit();
 *  String javaSourceCode =  "package com.test.foo;\n" +
 *    "public class Foo {\n" +
 *    "        public static void main(String [] args) {\n" +
 *    "            System.out.println(\"Hello world\");\n" +
 *    "        }\n" +
 *    "    }";
 *
 *  compilationUnit.addJavaSource("com.test.foo.Foo", sourceCode);
 *  ClassLoader classLoader = javaSourceCompiler.compile(compilationUnit);
 *  Class fooClass = classLoader.loadClass("com.test.foo.Foo");
 *  </pre></code>
 *
 *  <li>Java sources compilation with dependencies</li>
 *
 *  <code><pre>
 *  JavaSourceCompiler javaSourceCompiler = new JavaSourceCompilerImpl();
 *  JavaSourceCompiler.CompilationUnit compilationUnit = javaSourceCompiler.createCompilationUnit();
 *
 *  compilationUnit.addClassPathEntry("/Users/xxxx/.m2/repository/javax/inject/javax.inject/1/javax.inject-1.jar");
 *
 *  String iBarSource =  "package com.test;\n" +
 *     "    public interface IBar {\n" +
 *     "         public void execute();\n" +
 *     "    }";
 *
 *  compilationUnit.addJavaSource("com.test.IBar", iBarSource);
 *
 *
 *  String barSource =  "package com.test;\n" +
 *    "import javax.inject.Inject;\n\n" +
 *    "public class Bar implements IBar {\n" +
 *    "        private final String message;\n" +
 *    "\n" +
 *    "        @Inject\n" +
 *    "        public Bar(String message) {\n" +
 *    "            this.message = message;\n" +
 *    "        }\n" +
 *    "\n" +
 *    "\n" +
 *    "        @Override\n" +
 *    "        public void execute() {\n" +
 *    "            System.out.println(message);\n" +
 *    "        }\n" +
 *    "    }";
 *
 *  compilationUnit.addJavaSource("com.test.IBar", barSource);
 *
 *  ClassLoader classLoader = javaSourceCompiler.compile(compilationUnit);
 *  Class iBar = classLoader.loadClass("com.test.IBar");
 *  Class bar = classLoader.loadClass("com.test.Bar");
 *
 *
 *  </ul>
 *  </p>
 * @author Adrian Witas
 */

public interface JavaSourceCompiler {

    /**
     * Creates a compilation unit
     * @return compilation unit
     */
    CompilationUnit createCompilationUnit();

    /**
     * Create a compilation unit for the supplied output class directory.
     * @param outputClassDirectory output class directory
     * @return compilation unit
     */
    CompilationUnit createCompilationUnit(File outputClassDirectory);

    /**
     * Compiles given compilation unit with the supplier compiler options and returns class loader for the compiled sources.
     * @param compilationUnit compilation unit
     * @param compilerOptions compiler options
     * @return class loader for the compiled classes
     */
    ClassLoader compile(CompilationUnit compilationUnit, String... compilerOptions);

    /**
        * Compiles given compilation unit with the supplier compiler options and returns class loader for the compiled sources.
        * @param parentClassLoader parent class loader for the new class loader created for this compilation
        * @param compilationUnit compilation unit
        * @param compilerOptions compiler options
        * @return class loader for the compiled classes
    */
    ClassLoader compile(ClassLoader parentClassLoader, CompilationUnit compilationUnit, String... compilerOptions);

    /**
     * By default source code and compiled classes are stored in memory, this however could be a limitation
     * for libraries that scans dynamically packages, and this method persists compiled classes to be accessible
     * by package scanners on the file system.
     * @param compilationUnit compilation unit.
     */
    void persistCompiledClasses(CompilationUnit compilationUnit);

    /**
     * Represents an individual compilation unit.
     * It isRegistered multiple java sources and jar dependencies.
     */
    interface CompilationUnit {

        void addClassPathEntry(String classPathEntry);

        void addClassPathEntries(Collection<String> classPathEntries);

        void addJavaSource(String className, String source);

        JavaFileObjectRegistry getRegistry();

        List<String> getClassPathsEntries();

        File getOutputClassDirectory();

    }

}
