## State management in kotlin (android and native)

### Singleton Pattern:
Kotlin uses object based storage pattern to act like Singleton in Java
### Android:
```kt
@Composable
fun Counter(){
    var counter by remember { // delegations
        mutableStateOf(value = 0)
    }
    Button(onClick = { counter++ }, modifier = Modifier.padding(top = 77.dp, start = 27.dp)) {
        Text(text = "It's been clicked $counter times")
    }
}
```