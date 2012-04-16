/**
 * Copyright 2011 Adrian Witas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.abstractmeta.toolbox.compilation.compiler.impl;


import org.abstractmeta.toolbox.compilation.compiler.JavaSourceCompiler;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.File;
import java.lang.reflect.Method;
import java.util.*;


@Test
public class JavaSourceCompilerImplTest {

    @SuppressWarnings("unchecked")
    public void testJavaSourceCompilerImpl() throws Exception {
        JavaSourceCompiler javaSourceCompiler = new JavaSourceCompilerImpl();
        JavaSourceCompiler.CompilationUnit compilationUnit = javaSourceCompiler.createCompilationUnit();
        String javaSourceCode = "   " +
                "package com.test.foo;" +
                "public class Foo {\n" +
                "        public static void main(String [] args) {\n" +
                "            System.out.println(\"Hello world\");\n" +
                "        }\n" +
                "}";

        compilationUnit.addJavaSource("com.test.foo.Foo", javaSourceCode);
        ClassLoader classLoader = javaSourceCompiler.compile(compilationUnit);
        Class clazz = classLoader.loadClass("com.test.foo.Foo");
        Assert.assertEquals(clazz.getName(), "com.test.foo.Foo");
        Object foo = clazz.newInstance();
        Method main = clazz.getMethod("main", String[].class);
        String args = null;
        main.invoke(foo, args);
        javaSourceCompiler.persistCompiledClasses(compilationUnit);
    }


    @Test(expectedExceptions = IllegalStateException.class)
    public void testInvalidJavaSource() {
        JavaSourceCompiler javaSourceCompiler = new JavaSourceCompilerImpl();
        JavaSourceCompiler.CompilationUnit compilationUnit = javaSourceCompiler.createCompilationUnit();
        String javaSourceCode = "   " +
                "package com.test.foo;" +
                "public class FooI {\n" +
                "        public static void main(String [] args) {\n" +
                "            System. FOO println(\"Hello world\");\n" +
                "        }\n" +
                "    }";

        compilationUnit.addJavaSource("com.test.foo.FooI", javaSourceCode);
        javaSourceCompiler.compile(compilationUnit);
    }


    public void testEmptyCompile() {
        JavaSourceCompiler javaSourceCompiler = new JavaSourceCompilerImpl();
        JavaSourceCompiler.CompilationUnit compilationUnit = javaSourceCompiler.createCompilationUnit();
        javaSourceCompiler.compile(compilationUnit);
    }


    @SuppressWarnings("unchecked")
    public void testCompileWithClassPath() throws Exception {
        String injectJarFile = TestHelper.getResourcePath("javax", "inject");
        String testNgJarFile = TestHelper.getResourcePath("org", "testng");
        JavaSourceCompiler javaSourceCompiler = new JavaSourceCompilerImpl();
        JavaSourceCompiler.CompilationUnit compilationUnit = javaSourceCompiler.createCompilationUnit();
        compilationUnit.addClassPathEntries(Arrays.asList(injectJarFile));
        compilationUnit.addClassPathEntry(testNgJarFile);
        compilationUnit.addJavaSource("com.test.IFoo", "   \n" +
                "    package com.test;\n" +
                "    \n" +
                "    public interface IFoo {\n" +
                "        void bar();\n" +
                "    }");

        compilationUnit.addJavaSource("com.test.Foo", "" +
                "package com.test;\n" +
                "import javax.inject.Inject; " +
                "       public class Foo implements IFoo {\n" +
                "\n" +
                "        private final String bar;\n" +
                "\n" +
                "        @Inject\n" +
                "        public Foo(String bar) {\n" +
                "            this.bar = bar;\n" +
                "        }\n" +
                "\n" +
                "        @Override\n" +
                "        public void bar() {\n" +
                "            System.out.println(bar);\n" +
                "        }\n" +
                "    }");
        javaSourceCompiler.compile(compilationUnit, "-classpath", "src/target/classes");

    }


    @SuppressWarnings("unchecked")
    public void testPersistCompiledClasses() throws Exception {
        JavaSourceCompiler javaSourceCompiler = new JavaSourceCompilerImpl();
        File compilationRoot = new File("target/compilation-root");
        compilationRoot.mkdirs();
        JavaSourceCompiler.CompilationUnit compilationUnit = javaSourceCompiler.createCompilationUnit(compilationRoot);
        String javaSourceCode = "   " +
                "package com.test.foo;" +
                "public class Foo {\n" +
                "        public static void main(String [] args) {\n" +
                "            System.out.println(\"Hello world\");\n" +
                "        }\n" +
                "}";

        compilationUnit.addJavaSource("com.test.foo.Foo", javaSourceCode);
        javaSourceCompiler.compile(compilationUnit);
        javaSourceCompiler.persistCompiledClasses(compilationUnit);
        Assert.assertTrue(compilationRoot.list().length > 0);

    }

    @SuppressWarnings("unchecked")
    public void testPersistCompiledClassesWithNonExistingRoot() throws Exception {
        JavaSourceCompiler javaSourceCompiler = new JavaSourceCompilerImpl();
        File compilationRoot = new File("target/non-existing-compilation-root");
        JavaSourceCompiler.CompilationUnit compilationUnit = javaSourceCompiler.createCompilationUnit(compilationRoot);
        {
            String javaSourceCode = "   " +
                    "package com.test.foo;" +
                    "public class Foo {\n" +
                    "        public static void main(String [] args) {\n" +
                    "            System.out.println(\"Hello world\");\n" +
                    "        }\n" +
                    "}";
            compilationUnit.addJavaSource("com.test.foo.Foo", javaSourceCode);
        }
         {
            String javaSourceCode = "   " +
                    "package com.test.foo.bar;" +
                    "public class Bar {\n" +
                    "        public static void main(String [] args) {\n" +
                    "            System.out.println(\"Hello world\");\n" +
                    "        }\n" +
                    "}";
            compilationUnit.addJavaSource("com.test.foo.bar.Bar", javaSourceCode);
        }
        javaSourceCompiler.compile(compilationUnit);
        javaSourceCompiler.persistCompiledClasses(compilationUnit);
        Assert.assertTrue(!compilationRoot.exists());

    }



}
