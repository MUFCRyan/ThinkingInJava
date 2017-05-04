package com.source.generics;//: generics/SimplerPets.java
import com.source.typeinfo.pets.*;
import java.util.*;
import com.source.net.mindview.util.*;

public class SimplerPets {
  public static void main(String[] args) {
    Map<Person, List<? extends Pet>> petPeople = New.map();
    // Rest of the code is the same...
  }
} ///:~
