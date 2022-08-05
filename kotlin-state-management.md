## State management in kotlin (android and native)

### Singleton Pattern:
Kotlin uses object based storage pattern to act like Singleton in Java. Kotlin object can’t have any constructor, but init blocks are allowed if some initialization code is needed.
```kt
object SomeSingleton {
    init {
        println("init complete")
    }
}
```
The object will be instantiated and its init blocks will be executed lazily upon first access, in a thread-safe way. To achieve this, a Kotlin object actually relies on a Java static initialization block. The above Kotlin object will be compiled to the following equivalent Java code :
```java
public final class SomeSingleton {
   public static final SomeSingleton INSTANCE;

   private SomeSingleton() {
      INSTANCE = (SomeSingleton)this;
      System.out.println("init complete");
   }

   static {
      new SomeSingleton();
   }
}
```

### Android State Management:
```kt
@Composable
fun Counter(){
    var counter by remember { // delegations, delegated property
        mutableStateOf(value = 0)
    }
    Button(onClick = { counter++ }, modifier = Modifier.padding(top = 77.dp, start = 27.dp)) {
        Text(text = "It's been clicked $counter times")
    }
}
```