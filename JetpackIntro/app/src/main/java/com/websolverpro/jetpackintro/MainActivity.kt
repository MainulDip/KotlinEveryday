package com.websolverpro.jetpackintro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
            MyApp()
        }
    }
}

@Composable
fun MyApp(){
    JetpackIntroTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Greeting("Android")
        }
    }
}

@Composable
fun Greeting(name: String, modifire: Modifier = Modifier) {
    androidx.compose.material.Surface(color = Color.LightGray) {
        Text(text = "Hello $name!", modifier = Modifier.padding(27.dp, 27.dp))
        Text(text = "Hello $name!", modifier = Modifier.padding(27.dp, 47.dp), color = Color.Blue)

    }

}

//@Preview(name = "font scale 1.5",fontScale = 1.5f)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackIntroTheme {
        Greeting("Android")
    }
}