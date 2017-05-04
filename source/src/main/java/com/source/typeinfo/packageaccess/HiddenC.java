//: typeinfo/packageaccess/HiddenC.java
package com.source.typeinfo.packageaccess;
import com.source.typeinfo.interfacea.*;
import static com.source.net.mindview.util.Print.*;

class C implements A {
  public void f() { print("public C.f()"); }
  public void g() { print("public C.g()"); }
  void u() { print("package C.u()"); }
  protected void v() { print("protected C.v()"); }
  private void w() { print("private C.w()"); }
}

public class HiddenC {
  public static A makeA() { return new C(); }
} ///:~
