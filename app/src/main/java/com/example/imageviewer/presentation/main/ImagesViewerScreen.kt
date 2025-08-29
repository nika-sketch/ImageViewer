package com.example.imageviewer.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.imageviewer.presentation.main.model.ImageViewerState

@Composable
fun ImagesViewerScreen(
  modifier: Modifier = Modifier,
  onImageClick: (String, String) -> Unit,
  onRefresh: () -> Unit,
  state: ImageViewerState,
) = when (state) {
  is ImageViewerState.Loading -> LoadingContent()
  is ImageViewerState.Error -> ErrorContent(message = state.message)
  is ImageViewerState.Success -> SuccessContent(
    isRefreshing = state.isRefreshing,
    modifier = modifier,
    images = state.images,
    onImageClick = onImageClick,
    onRefresh = onRefresh
  )
}

@Composable
private fun ErrorContent(modifier: Modifier = Modifier, message: String) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .wrapContentSize(Alignment.Center)
      .padding(16.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(text = message)
  }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator()
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessContent(
  modifier: Modifier = Modifier,
  isRefreshing: Boolean,
  onRefresh: () -> Unit,
  images: List<ImageViewerState.Success.ImageViewerUi>,
  onImageClick: (String, String) -> Unit,
) {
  PullToRefreshLazyColumn(
    items = images,
    content = { image ->
      ImageWithTitle(
        image = image,
        modifier = Modifier.fillMaxSize(),
        onImageClick = onImageClick
      )
    },
    dividerContent = { index ->
      if (index != images.lastIndex)
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(horizontal = 8.dp))
    },
    isRefreshing = isRefreshing,
    onRefresh = onRefresh,
    modifier = modifier
  )
}

@Composable
private fun ImageWithTitle(
  modifier: Modifier = Modifier,
  image: ImageViewerState.Success.ImageViewerUi,
  onImageClick: (String, String) -> Unit,
) {
  val model = ImageRequest.Builder(LocalContext.current)
    .data(image.url)
    .crossfade(true)
    .build()
  Column(
    modifier = modifier.clickable {
      onImageClick(image.title, image.url)
    },
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    AsyncImage(
      model = model,
      contentDescription = image.title,
      modifier = Modifier
        .width(200.dp)
        .height(200.dp),
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = image.title)
  }
}