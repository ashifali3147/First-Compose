package com.example.firstcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstcomposeapp.ui.theme.FirstComposeAppTheme

class CalculatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorView("Android")
                }
            }
        }
    }
}

@Composable
fun CalculatorView(name: String, modifier: Modifier = Modifier) {
    var inputText by remember {
        mutableStateOf("0")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Divider(
            modifier = Modifier.height(50.dp), color = Color.Transparent
        )
        TextField(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(20.dp, 0.dp)
            .background(Color.Transparent), textStyle = TextStyle(
            fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold
        ), value = inputText, onValueChange = {
            inputText = it
        })

        Divider(
            modifier = Modifier.height(20.dp), color = Color.Transparent
        )

        val modifier = Modifier.weight(1f)

        CalRow(modifier = modifier, inputText)
    }
}

@Composable
fun CalRow(modifier: Modifier, inputText: String) {
    Column {
        Row() {
            RoundedButton(modifier, "AC", 2, inputText)
            RoundedButton(modifier, "%", 2, inputText)
            RoundedButton(modifier, "<-", 2, inputText)
            RoundedButton(modifier, "/", 2, inputText)
        }

        Row() {
            RoundedButton(modifier, "7", 1, inputText)
            RoundedButton(modifier, "8", 1, inputText)
            RoundedButton(modifier, "9", 1, inputText)
            RoundedButton(modifier, "*", 2, inputText)
        }

        Row() {
            RoundedButton(modifier, "4", 1, inputText)
            RoundedButton(modifier, "5", 1, inputText)
            RoundedButton(modifier, "6", 1, inputText)
            RoundedButton(modifier, "-", 2, inputText)
        }

        Row() {
            RoundedButton(modifier, "1", 1, inputText)
            RoundedButton(modifier, "2", 1, inputText)
            RoundedButton(modifier, "3", 1, inputText)
            RoundedButton(modifier, "+", 2, inputText)
        }

        Row() {
            RoundedButton(modifier, "0.0", 1, inputText)
            RoundedButton(modifier, "0", 1, inputText)
            RoundedButton(modifier, ".", 1, inputText)
            RoundedButton(modifier, "=", 2, inputText)
        }
    }
}

@Composable
fun RoundedButton(modifier: Modifier, text: String, type: Int, inputText: String) {
    Column(
        modifier = modifier
            .aspectRatio(1f)
            .padding(5.dp)
            .clip(shape = CircleShape)
            .background(if (type == 1) Color.LightGray else Color.Black)
            .clickable {

            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = if (type == 2) Color.White else Color.Black,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    FirstComposeAppTheme {
        CalculatorView("Android")
    }
}