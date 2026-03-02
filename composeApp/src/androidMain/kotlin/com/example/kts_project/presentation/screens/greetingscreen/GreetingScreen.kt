package com.example.kts_project.presentation.screens.greetingscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kts_project.R
import com.example.kts_project.presentation.components.LoadableImage

private const val CAT_IMAGE_URL = "https://img.freepik.com/premium-photo/close-up-portrait-cat_1048944-18104761.jpg?semt=ais_hybrid&w=740"

@Composable
fun GreetingScreen(
    onNavigateToLogin: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .systemBarsPadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadableImage(
                imageUrl = CAT_IMAGE_URL,
                contentDescription = stringResource(R.string.cat_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.greeting_title),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.greeting_divider),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                text = stringResource(R.string.greeting_description),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Default,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(54.dp))

            Button(
                modifier = Modifier
                    .width(200.dp)
                    .height(40.dp),
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                elevation = null
            ) {
                Text(
                    text = stringResource(R.string.greeting_learn_more),
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
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    text = stringResource(R.string.greeting_get_started)
                )
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingScreenPreview() {
    MaterialTheme {
        GreetingScreen(
            onNavigateToLogin = {}
        )
    }
}