package generics;

public class JavaGenerics {
    public static void main(String[] args) {
        System.out.println("Hello World Again");
    }
}

interface Collection<E> {
    void addAll(Collection<E> items);
}

class SomeClass implements Collection {

    @Override
    public void addAll(Collection items) {

    }
}



// cd into this directory and run "java .\JavaGenerics.java"