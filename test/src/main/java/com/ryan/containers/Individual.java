package com.ryan.containers;

import com.ryan.holding.MapOfList;
import com.source.typeinfo.pets.Pet;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by MUFCRyan on 2017/5/4.
 * Book Page497 Chapter 17.9.3 覆盖 hashCode()
 */

public class Individual implements Comparable<Individual>{
    private static long counter = 0;
    private final long id = counter++;
    private String name;
    public Individual(){

    }
    public Individual(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + (name == null? "" : " " + name);
    }

    public long id(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Individual && id == ((Individual)o).id;
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (name != null)
            result = result * 37 + name.hashCode();
        result = result * 37 + (int) id;
        return result;
    }

    @Override
    public int compareTo(Individual individual) {
        // Compare by class name first
        String first = getClass().getSimpleName();
        String individualFirst = individual.getClass().getSimpleName();
        int firstCompare = first.compareTo(individualFirst);
        if (firstCompare != 0)
            return firstCompare;
        if (name != null && individual.name != null){
            int secondCompare = name.compareTo(individual.name);
            if (secondCompare != 0)
                return secondCompare;
        }
        return individual.id < id ? -1 :(individual.id == id ? 0 : 1);
    }
}

class IndividualTest{
    public static void main(String[] args){
        Set<Individual> pets = new TreeSet<>();
        for (List<? extends Pet> list : MapOfList.petPeople.values()) {
            for (Pet pet : list) {
                //pets.add(pet);
            }
        }
    }
}
