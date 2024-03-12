package com.example.firstcomposeapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.firstcomposeapp.model.CategoryItem
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.network.ApiManager
import com.example.firstcomposeapp.ui.theme.FirstComposeAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
class MainActivity : ComponentActivity() {
    var category_list by mutableStateOf(mutableListOf<CategoryItem>())
    var isLoading by mutableStateOf(false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Ashif")
                    MainUI(Modifier.fillMaxSize())

                    getCategoryData()
                }
            }
        }
    }

    private fun getCategoryData() {
        isLoading = true
        lifecycleScope.launch {
            val api_response = ApiManager.service.getMenuCategoryListing("15657", 2489, 245723)
            isLoading = false
            if (api_response.isSuccessful) {
                if (api_response.body() != null) {
                    val api_body = api_response.body()
                    if (api_body!!.error) {
                        Toast.makeText(this@MainActivity, api_body.message, Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        category_list.clear()
                        category_list.addAll(api_body.data!!.category_list)
                    }
                } else {
//                    sendUiEvent(UiEvent.ShowToast(NetworkConstants.API_FAILURE))
                }
            } else {
//                sendUiEvent(UiEvent.ShowToast(NetworkConstants.API_FAILURE))
            }
        }
    }

    @Composable
    fun MainUI(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        var amount = ""
        Box(modifier = modifier) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Divider(
                    modifier = Modifier.height(10.dp), color = Color.Transparent
                )
                Text(
                    text = "Hello $name",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Divider(
                    modifier = Modifier.height(20.dp), color = Color.Transparent
                )
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    value = "",
                    onValueChange = {
                        amount = it
                    },
                    label = { Text(text = "Ammount") })
                Button(

                    onClick = { pay(context) },
                    shape = CutCornerShape(10.dp),
                ) {
                    Text(text = "Pay")
                }

                Button(onClick = {
                    startActivity(Intent(this@MainActivity, CalculatorActivity::class.java))
                }) {
                    Text("Calculator")
                }
                val painter = painterResource(id = R.drawable.ic_launcher_background)

                if (category_list.size > 0) {
                    LazyColumn {
                        items(
                            count = category_list.size,
                            key = {
                                category_list[it].id
                            },
                            itemContent = { index ->
                                val category = category_list[index]
                                CustomGridView(category = category)
                            }
                        )
                    }
                }
//        Column(Modifier.verticalScroll(state = ScrollState(5))) {
//            for (i in 1..10){
//                CustomGridView(painter)
//            }
//        }
            }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(50.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    strokeWidth = 5.dp,
                )
            }
        }
    }


    fun pay(context: Context) {
        Toast.makeText(context, "Working", Toast.LENGTH_SHORT).show()
    }

    @Composable
    fun CustomGridView(category: CategoryItem) {
        Row {
            CustomImageCard(
                imageLink = category.image_url,
                title = category.name,
                description = category.name,
                modifier = Modifier.weight(1f)
            )
//            CustomImageCard(
//                imageLink = category.image_url,
//                title = category.name,
//                description = category.name,
//                modifier = Modifier.weight(1f)
//            )
        }
    }

    @Composable
    fun CustomImageCard(
        imageLink: String, painter: Painter = painterResource(id = R.drawable.ic_launcher_background), title: String, description: String, modifier: Modifier = Modifier
    ) {
        val context = LocalContext.current
        var enabled by remember { mutableStateOf(true) }
        LaunchedEffect(enabled) {
            if (enabled) return@LaunchedEffect
            else delay(1000L)
            enabled = true
        }
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clickable(enabled = enabled) {
                    pay(context)
                    enabled = false
                }, shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Box(modifier = Modifier.height(200.dp)) {
                if (imageLink.isEmpty()) {
                    Image(
                        painter = painter,
                        contentDescription = description,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else{
                    GlideImage(
                        model = imageLink,
                        contentDescription = description,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black), 300f
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        text = title, textAlign = TextAlign.Center, style = TextStyle(
                            fontSize = 10.sp, color = Color.White, fontStyle = FontStyle.Italic
                        ), maxLines = 2, overflow = TextOverflow.Ellipsis
                    )
                }
            }

        }
    }

}

var name = "Ashif Ali"

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Divider(Modifier.height(10.dp), color = Color.Transparent)
        Text(
            text = "Hello $name!",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )


    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FirstComposeAppTheme {
        Greeting("Android")
//        MainUI(Modifier.fillMaxSize())
    }
}