/*
 * Copyright (C) 2006 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.gatech.cc.jcrasher.writer;

import static edu.gatech.cc.jcrasher.Constants.NL;
import static edu.gatech.cc.jcrasher.Constants.TAB;
import junit.framework.TestCase;
import edu.gatech.cc.jcrasher.Loadee;

/**
 * Tests edu.gatech.cc.jcrasher.writer.AbstractJUnitTestWriter
 * 
 * @author csallner@gatech.edu (Christoph Csallner)
 */
public class AbstractJUnitTestWriterTest extends TestCase {

  protected Class inDefaultPackage = null;
  protected AbstractJUnitTestWriter junitTestWriter = null;

  
  @Override
  protected void setUp() throws Exception { 
    super.setUp();
    
    junitTestWriter = new ConcreteJUnitTestWriter();
    inDefaultPackage = Class.forName("InDefaultPackage");
  }
  
  
  public void testGetPackageHeader() {
    try {
      junitTestWriter.getPackageHeader(null);
      fail("getPackageHeader(null) not allowed");
    }
    catch(RuntimeException e) {  //expected
    }
    
    assertEquals("", junitTestWriter.getPackageHeader(inDefaultPackage));
    assertEquals(
        "package edu.gatech.cc.jcrasher;"+NL+NL,
        junitTestWriter.getPackageHeader(Loadee.class));
    assertEquals(
        "package edu.gatech.cc.jcrasher;"+NL+NL,
        junitTestWriter.getPackageHeader(Loadee.StaticMember.class));
    assertEquals(
        "package edu.gatech.cc.jcrasher;"+NL+NL,
        junitTestWriter.getPackageHeader(Loadee.Inner.class));
  }


  public void testJavadocComment() {
    assertEquals(
        "/**"+            NL+
        " * Hello"+       NL+
        " */"+            NL,
        junitTestWriter.getJavaDocComment("Hello", ""));
    
    assertEquals(
        "/**"+            NL+
        " * Hello"+       NL+
        " */"+            NL,
        junitTestWriter.getJavaDocComment("Hello", null));
    
    assertEquals(
        TAB+"/**"+            NL+
        TAB+" * Hello"+       NL+
        TAB+" */"+            NL,
        junitTestWriter.getJavaDocComment("Hello", TAB));
    
    assertEquals(
        TAB+TAB+"/**"+            NL+
        TAB+TAB+" * Hello"+       NL+
        TAB+TAB+" */"+            NL,
        junitTestWriter.getJavaDocComment("Hello", TAB+TAB));
    
    assertEquals(
        TAB+TAB+"/**"+            NL+
        TAB+TAB+" * Hello"+       NL+
        TAB+TAB+" * World"+       NL+
        TAB+TAB+" */"+            NL,
        junitTestWriter.getJavaDocComment("Hello"+NL+"World", TAB+TAB));    

    assertEquals(
        "/**"+                  NL+
        " * TODO: comment"+     NL+
        " */"+                  NL,
        junitTestWriter.getJavaDocComment("", ""));
  }

  
  public class ConcreteJUnitTestWriter extends AbstractJUnitTestWriter {
    /* Empty */
  }

}
