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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
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
  onImageClick: (Int) -> Unit,
  state: ImageViewerState,
) {
  when (state) {
    is ImageViewerState.Error -> ErrorContent()
    is ImageViewerState.Loading -> LoadingContent()
    is ImageViewerState.Success -> SuccessContent(
      modifier = modifier,
      images = state.images,
      onImageClick = onImageClick
    )
  }
}

@Composable
private fun ErrorContent(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .wrapContentSize(Alignment.Center)
      .padding(16.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(text = "An error occurred. Please try again.")
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

@Composable
private fun SuccessContent(
  modifier: Modifier = Modifier,
  images: List<ImageViewerState.Success.ImageViewerUi>,
  onImageClick: (Int) -> Unit,
) {
  LazyColumn(modifier = modifier) {
    itemsIndexed(
      items = images,
      key = { index, image -> image.id }
    ) { index, image ->
      ImageWithTitle(image = image, modifier = Modifier.fillMaxSize(), onImageClick = onImageClick)
      if (index != images.lastIndex)
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(horizontal = 8.dp))
    }
  }
}

@Composable
private fun ImageWithTitle(
  modifier: Modifier = Modifier,
  image: ImageViewerState.Success.ImageViewerUi,
  onImageClick: (Int) -> Unit,
) {
  val model = ImageRequest.Builder(LocalContext.current)
    .data(image.url)
    .crossfade(true)
    .build()
  Column(
    modifier = modifier.clickable {
      onImageClick(image.id)
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