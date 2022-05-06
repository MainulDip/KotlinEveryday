package com.websolverpro.jetpackintro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.websolverpro.jetpackintro.ui.theme.JetpackIntroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContent(content = { MyApp() })
//        setContent(){ MyApp() }
//        setContent{ MyApp() }
        /**
         *  if the last parameter of a function is a function, then a lambda expression passed as the corresponding argument can be placed outside the parentheses
         */
        setContent{
            MyApp{
                MyScreenContent()
            }
        }
    }
}

/**
 * To re-use ui view on both activity and preview we can define composable
 * function and inject the element inside that as content
 */
@Composable
fun MyScreenContent(){
    Row(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth(),horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)) {
            Greeting(name = "Lambda Calling")
            Divider()
            Greeting(name = "Another Calling")
            Greeting(name = "Lambda Calling")
            Divider()
            Greeting(name = "Another Calling")
            Greeting(name = "Lambda Calling")
            Divider()
            Greeting(name = "Another Calling")
            Greeting(name = "Lambda Calling")
            Divider()
            Greeting(name = "Another Calling")
            Greeting(name = "Lambda Calling")
            Divider()
            Greeting(name = "Another Calling")
            Greeting(name = "Lambda Calling")
            Divider()
            Greeting(name = "Another Calling")
        }
    }

}

@Composable
fun MyApp(content: @Composable ()-> Unit){
    // JetpackIntroTheme defined inside ui.theme/Theme.kt
    JetpackIntroTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            //Greeting("Android")
            content()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    androidx.compose.material.Surface(color = Color.LightGray) {
        Text(text = "Hello $name!", modifier = Modifier.padding(27.dp, 27.dp))
        Text(text = "Again Hello $name!", modifier = modifier.padding(27.dp, 47.dp), color = Color.Blue)
        Counter()
    }
}


/**
 * State management
 */
@Composable
fun Counter(){
    var counter by remember {
        mutableStateOf(value = 0)
    }
    Button(onClick = { counter++ }, modifier = Modifier.padding(top = 77.dp, start = 27.dp)) {
        Text(text = "It's been clicked $counter times")
    }
}

//@Preview(name = "font scale 1.5",fontScale = 1.5f)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    JetpackIntroTheme {
//        Greeting("Some Texts")
//    }
    MyScreenContent()
}