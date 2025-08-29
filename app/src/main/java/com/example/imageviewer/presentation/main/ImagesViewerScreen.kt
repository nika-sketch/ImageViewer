package com.example.imageviewer.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.imageviewer.presentation.main.model.ImageViewerState

@Composable
fun ImagesViewerScreen(
  modifier: Modifier = Modifier,
  images: List<ImageViewerState.Success.ImageViewerUi>,
  onImageClick: (Int) -> Unit,
) {
  Text(text = images.first().toString(), modifier = modifier.fillMaxSize(), textAlign = TextAlign.Center)
}