package com.example.kts_project.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import kts_project.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.example.kts_project.utils.ImageUrl
import kts_project.composeapp.generated.resources.ic_launcher

@Composable
fun MainScreen(
    receivedData: String = "",
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var imageState by remember { mutableStateOf<AsyncImagePainter.State?>(null) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageUrl.CAT_IMAGE,
                contentDescription = "Cat image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                onState = { state ->
                    imageState = state
                }
            )

            if (imageState is AsyncImagePainter.State.Error) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .background(Color.LightGray.copy(alpha = 0.3f))
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_launcher),
                        contentDescription = "App icon",
                        modifier = Modifier.size(120.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Не удалось загрузить фото",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }

            if (imageState is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "TRAVEL",
            textAlign = TextAlign.Center,
            color = Color.Blue,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "──",
            textAlign = TextAlign.Center,
            color = Color.Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Duis aute dolor in reprehenderit in\n" +
                    "voluptate velit esse cilum dolore eu fugiat\n" +
                    "nulla pariatur.",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.Default
        )
        Spacer(modifier = Modifier.height(54.dp))
        Button(
            modifier = Modifier
                .width(200.dp)
                .height(40.dp),
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Blue
            ),
            elevation = null
        ) {
            Text(
                text = "LEARN MORE",
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.height(74.dp))
        Button(
            modifier = Modifier
                .width(170.dp)
                .height(40.dp),
            onClick = onNavigateToLogin,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                text = "Get Started")
        }

        if (receivedData.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Получено:")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = receivedData)
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MainScreenPreview() {
    MainScreen(
        receivedData = "",
        onNavigateToLogin = {}
    )
}