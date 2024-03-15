package com.example.firstcomposeapp.ui.activity

import android.graphics.BitmapFactory
import android.graphics.Shader
import android.graphics.Typeface
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.ui.activity.ui.theme.FirstComposeAppTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class SlotMachineActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )
        setContent {
            FirstComposeAppTheme {
                MainUI()
            }
        }
    }

}

@Composable
fun MainUI() {
    val context = LocalContext.current

    var cash by remember {
        mutableStateOf(120)
    }  // cash
    var bet by remember {
        mutableStateOf(5)
    }
    val font = Typeface.createFromAsset(context.assets, "CooperBlack.otf")
    var isScaled by remember { mutableStateOf(false) }

    val scaleSize by animateFloatAsState(
        targetValue = if (isScaled) 1.1f else 1f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500), repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(Unit) {
        isScaled = true
    }

    var isScaledButton by remember { mutableStateOf(false) }

    val scaleButtonSize by animateFloatAsState(
        targetValue = if (isScaledButton) .95f else 1f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500), repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    //=======================
    var count by remember {
        mutableStateOf(3)
    }

    val slowSpeed = 300L
    val fastSpeed = 150L
    var spinSpeed by remember {
        mutableStateOf(slowSpeed)
    }
    val spinnerItems by remember {
        mutableStateOf(mutableListOf(1, 2, 3, 4, 5))
    }
    LaunchedEffect(Unit) {
        spinnerItems.clear()
        spinnerItems.addAll(generateRandomNumbers())
    }

    var buttonVisibility by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(buttonVisibility) {
        if (buttonVisibility) return@LaunchedEffect
        else delay(2000L)
        buttonVisibility = true
    }

    LaunchedEffect(Unit) {
        isScaledButton = true
    }

    var isSpinEnable by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        isSpinEnable = false
    }

    //========================


    val scaleSlotList by remember {
        mutableStateOf(
            mutableListOf(
                false, false, false, false, false
            )
        )
    }
    val scaleSlotSizeList = List(5) {
        animateFloatAsState(
            targetValue = if (scaleSlotList[it]) 1.2f else 1f, animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 500), repeatMode = RepeatMode.Reverse
            ), label = ""
        )

    }
    LaunchedEffect(scaleSlotList) {
        scaleSlotList.forEachIndexed { index, value ->
            if (value) {
                delay(1000L)
                scaleSlotList[index] = false
            }
        }
    }
    //=====================

    Box(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .scale(scaleSize)
                .animateContentSize(),
            model = "file:///android_asset/bg.jpg",
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
//        Image(
//            modifier = Modifier
//                .fillMaxSize()
//                .scale(scaleSize)
//                .animateContentSize(),
//            bitmap = getAssetResource(fileName = "bg.jpg"),
//            contentDescription = null,
//            contentScale = ContentScale.Crop
//        )
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                bitmap = getAssetResource(fileName = "logo.png"),
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
                    .weight(4f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            shape = RoundedCornerShape(5.dp), brush = Brush.verticalGradient(
                                colors = listOf(Color.LightGray, Color.Black), 100f
                            )
                        )
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Black)
                    ) {
                        val largeRadialGradient = object : ShaderBrush() {
                            override fun createShader(size: Size): Shader {
                                val biggerDimension = maxOf(size.height, size.width)
                                return RadialGradientShader(
                                    colors = listOf(Color.Transparent, Color(0xFF000000)),
                                    center = size.center,
                                    radius = biggerDimension / 2f,
                                    colorStops = listOf(0f, 0.95f)
                                )
                            }
                        }
                        for (slotSection in 0 until 5) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(2.dp)
                                    .background(color = Color.White)
                                    .weight(1f)
                            ) {
                                VintageEffectContent {
                                    SlotAnimation(
                                        count = count,
                                        spinnerItems[slotSection],
                                        scaleSlotSizeList[slotSection].value, isSpinEnable
                                    )
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        isSpinEnable = !isSpinEnable.also { spinSpeed = slowSpeed }
                        if (!isSpinEnable) {
                            spinnerItems.clear()
                            spinnerItems.addAll(generateRandomNumbers())
                            getResult(scaleSlotList, spinnerItems)
                            spinSpeed = slowSpeed
                            GlobalScope.launch {
                                delay(3000)
                                scaleSlotList.forEachIndexed { index, value ->
                                    scaleSlotList[index] = false
                                }
                            }
                        } else {
                            buttonVisibility = false
                        }
                        GlobalScope.launch {
                            while (isSpinEnable) {
                                delay(spinSpeed)
                                spinSpeed = if (spinSpeed == slowSpeed) fastSpeed else spinSpeed
                                if (count == 6) {
                                    count = 0
                                } else {
                                    count++
                                }
                            }

                        }
                    },
                    enabled = buttonVisibility,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .wrapContentSize()
                        .scale(scaleButtonSize)
                        .alpha(if (buttonVisibility) 1f else 0f),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(3.dp, Color.White),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )

                ) {
                    Text(
                        modifier = Modifier.padding(15.dp),
                        text = if (isSpinEnable) stringResource(id = R.string.btn_stop) else stringResource(
                            id = R.string.btn_play
                        ),
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.txt_cash) + cash,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black, offset = Offset(1f, 2f), blurRadius = 1f
                            )
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.txt_bet) + bet,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Yellow,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black, offset = Offset(1f, 2f), blurRadius = 1f
                            )
                        )
                    )
                }
            }
        }
    }
}

fun getResult(
    scaleSlotList: MutableList<Boolean>, spinnerItems: MutableList<Int>
) {
    for (i in 0 until spinnerItems.size - 1) {
        if (spinnerItems[i] == spinnerItems[i + 1]) {
            scaleSlotList[i] = true
            scaleSlotList[i + 1] = true
        }
    }
}

@Composable
fun getAssetResource(fileName: String): ImageBitmap {
    val context = LocalContext.current
    val assetInputStream = context.assets.open(fileName)
    val bitmapBackgroundImage = BitmapFactory.decodeStream(assetInputStream)
    return bitmapBackgroundImage.asImageBitmap()
}

@Composable
fun SlotAnimation(count: Int, slotItem: Int, scale: Float, isSpinEnable: Boolean) {
//    var items = count
    var items = if (isSpinEnable) count else slotItem
    AnimatedContent(
        targetState = items, transitionSpec = {
            // Compare the incoming number with the previous number.
            (slideInVertically { height -> height } + fadeIn()).togetherWith(slideOutVertically { height -> -height } + fadeOut())
                .using(
                    // Disable clipping since the faded slide-in/out should
                    // be displayed out of bounds.
                    SizeTransform(clip = true)
                )
        }, label = ""
    ) { targetCount ->
        Column {
            var firstIndex = slotItem
            var secondIndex = if (firstIndex == 6) 0 else firstIndex + 1
            var thirdIndex = if (secondIndex == 6) 0 else secondIndex + 1
            AsyncImage(
                model = "file:///android_asset/img${(firstIndex)}.png", contentDescription = null
            )
            AsyncImage(
                modifier = Modifier
                    .scale(scale)
                    .animateContentSize(),
                model = "file:///android_asset/img${(secondIndex)}.png",
                contentDescription = null
            )
            AsyncImage(
                model = "file:///android_asset/img${(thirdIndex)}.png", contentDescription = null
            )
        }
    }
}

@Composable
fun VintageEffectContent(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        content()
        Box(modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxSize()
            .drawBehind {
                drawVintageGradient(
                    color = Color.Black, startY = 0f, endY = size.height * 0.2f
                )
            })
        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxSize()
            .drawBehind {
                drawVintageGradient(
                    color = Color.Black, startY = size.height, endY = size.height * 0.8f
                )
            })
    }
}

private fun DrawScope.drawVintageGradient(color: Color, startY: Float, endY: Float) {
    val gradient = Brush.verticalGradient(
        0f to color, 1f to Color.Transparent, startY = startY, endY = endY
    )
    drawRect(brush = gradient)
}

fun generateRandomNumbers(): MutableList<Int> {
    val randomNumbers = mutableListOf<Int>()
    repeat(5) {
        val randomNumber = Random.nextInt(0, 6)
        randomNumbers.add(randomNumber)
    }
    return randomNumbers
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {

    MainUI()

}