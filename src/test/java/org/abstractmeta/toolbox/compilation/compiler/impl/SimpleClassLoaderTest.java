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
import org.abstractmeta.toolbox.compilation.compiler.registry.JavaFileObjectRegistry;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

@Test
public class SimpleClassLoaderTest {

    public void testSimpleClassLoader() {
        SimpleClassLoader classLoader = new SimpleClassLoader(null, getRegistry(), new File("target/simple-compiler"));
        Assert.assertNotNull(classLoader);
    }


    public void testFindClassInClassPath() throws ClassNotFoundException {
        SimpleClassLoader classLoader = new SimpleClassLoader(null, getRegistry(), new File("target/simple-compiler"));
        classLoader.addClassPathEntry("target/test-classes/");
        Class clazz = classLoader.findClass(SimpleClassLoaderTest.class.getName());
        Assert.assertNotNull(clazz.getName(), SimpleClassLoaderTest.class.getName());
    }


    public void testFindClassInJarFile() throws Exception {
        String injectJarFile = TestHelper.getResourcePath("javax", "inject");
        SimpleClassLoader classLoader = new SimpleClassLoader(null, getRegistry(), new File("target/simple-compiler"));
        classLoader.addClassPathEntry(injectJarFile);
        classLoader.addClassPathEntry("target/test-classes/");
        Class clazz = classLoader.findClass(Inject.class.getName());
        Assert.assertNotNull(clazz.getName(), Inject.class.getName());
    }


    @Test(expectedExceptions = ClassNotFoundException.class)
    public void testClassNotFoundException() throws ClassNotFoundException {
        SimpleClassLoader classLoader = new SimpleClassLoader(null, getRegistry(), new File("target/simple-compiler"));
        classLoader.addClassPathEntry("target/test-classes/");
        classLoader.findClass(SimpleClassLoaderTest.class.getName() + "foo");
    }



    public void testFindResource() throws Exception, IOException {
        SimpleClassLoader classLoader = new SimpleClassLoader(getClass().getClassLoader(), getRegistry(), new File("target/simple-compiler"));
        classLoader.addClassPathEntry("target/test-classes/");
        String injectJarFile = TestHelper.getResourcePath("javax", "inject");
        classLoader.addClassPathEntry(injectJarFile);
        Assert.assertTrue(Collections.list(classLoader.findResources("com")).size() > 0);
        Assert.assertFalse(Collections.list(classLoader.findResources("dummiesF")).size() > 0);

    }




    protected JavaFileObjectRegistry getRegistry() {
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
        javaSourceCompiler.compile(compilationUnit);
        return compilationUnit.getRegistry();

    }


}

