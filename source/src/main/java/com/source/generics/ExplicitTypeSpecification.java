package com.source.generics;//: generics/ExplicitTypeSpecification.java
import com.source.typeinfo.pets.*;
import java.util.*;
import com.source.net.mindview.util.*;

public class ExplicitTypeSpecification {
  static void f(Map<Person, List<Pet>> petPeople) {}
  public static void main(String[] args) {
    f(New.<Person, List<Pet>>map());
  }
} ///:~
