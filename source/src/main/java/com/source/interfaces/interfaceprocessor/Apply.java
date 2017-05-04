//: interfaces/interfaceprocessor/Apply.java
package com.source.interfaces.interfaceprocessor;
import static com.source.net.mindview.util.Print.*;

public class Apply {
  public static void process(Processor p, Object s) {
    print("Using Processor " + p.name());
    print(p.process(s));
  }
} ///:~
