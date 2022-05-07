## State management in kotlin (android and native)

### Singleton:

### Android:
```kt
@Composable
fun Counter(){
    var counter by remember {
        mutableStateOf(value = 0)
    }
    Button(onClick = { counter++ }, modifier = Modifier.padding(top = 77.dp, start = 27.dp)) {
        Text(text = "It's been clicked $counter times")
    }
}
```