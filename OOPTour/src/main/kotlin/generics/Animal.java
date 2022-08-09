package generics;

import java.util.ArrayList;

class Animal { }
class Dog2 extends Animal { }

class AnimalPrinter {
    public void print(ArrayList<Animal> animals) {
        for (Animal animal : animals) {
            System.out.println("animal = " + animal);
        }
    }

    public static void main(String[] args) {
        ArrayList<Dog2> dogs =
                new ArrayList<Dog2>(new Dog2, new Dog2);
//        print(dogs); // type error
    }
}
