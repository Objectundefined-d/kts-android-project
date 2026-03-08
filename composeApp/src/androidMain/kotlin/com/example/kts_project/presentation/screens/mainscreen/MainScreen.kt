package com.example.kts_project.presentation.screens.mainscreen

import KtsProjectTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kts_project.domain.model.Course
import com.example.kts_project.presentation.viewmodel.mainviewmodel.MainViewModel
import coil3.compose.AsyncImage
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.example.kts_project.R
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.RectangleShape
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onBack: () -> Unit,
    onCourseClick: (Int) -> Unit,
    viewModel: MainViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var search by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .systemBarsPadding()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
            }
            Text("Stepik courses search (Ktor)", style = MaterialTheme.typography.titleLarge)
        }

        OutlinedTextField(
            value = search,
            onValueChange = {
                search = it
                viewModel.onSearchQueryChanged(it)
            },
            label = { Text("Enter course name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(Modifier.width(16.dp))


        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.errorType != null -> {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Smth wrong")
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { viewModel.onSearchQueryChanged(search = search) }) {
                            Text("Repeat")
                        }
                    }
                }
            }

            state.courses.isEmpty() -> {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(R.drawable.outline_hourglass_empty_24),
                            contentDescription = null,
                            modifier = Modifier.size(120.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text("There is no courses", style = MaterialTheme.typography.bodyLarge)
                        Spacer(Modifier.height(8.dp))
                        Text("Try another query", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            else -> {
                val listState = rememberLazyListState()

                LaunchedEffect(listState) {
                    snapshotFlow {
                        val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                        val total = listState.layoutInfo.totalItemsCount
                        lastVisible >= total - 3
                    }
                        .distinctUntilChanged()
                        .filter { it }
                        .collect { viewModel.loadMore() }
                }

                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f).padding(top = 16.dp)
                ) {
                    items(state.courses) { course ->
                        CourseItem(
                            course = course,
                            onClick = { onCourseClick(course.id) }
                        )
                    }
                    if (state.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize().padding(16.dp)
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CourseItem(
    course: Course,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = course.cover,
                contentDescription = course.title,
                modifier = Modifier.size(64.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(course.title, style = MaterialTheme.typography.titleSmall, maxLines = 2)
                Spacer(Modifier.height(4.dp))
                Text(
                    "${course.learnersCount} учеников",
                    style = MaterialTheme.typography.bodySmall
                )
                if (course.isPaid) {
                    Text(
                        course.displayPrice ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Text("Бесплатно", style = MaterialTheme.typography.bodySmall, color = Color.Green)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    val sampleCourses = List(5) { index ->
        Course(
            id = index,
            title = "Курс по программированию ${index + 1}",
            summary = "Описание курса номер ${index + 1}",
            cover = null,
            learnersCount = 1000 + index * 100,
            isPaid = index % 2 == 0,
            displayPrice = if (index % 2 == 0) "990 ₽" else null,
            withCertificate = index % 3 == 0,
            language = "ru"
        )
    }

    KtsProjectTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(sampleCourses) { course ->
                CourseItem(
                    course = course,
                onClick = {})
            }
        }
    }
}