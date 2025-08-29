package com.example.imageviewer.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun ImageDetailContentScreen(modifier: Modifier = Modifier, title: String, url: String) {
  val model = ImageRequest.Builder(LocalContext.current).data(url).crossfade(true).build()
  Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
    Text(text = title, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(8.dp))
    AsyncImage(
      model = model,
      contentDescription = title,
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
        .height(300.dp),
    )
  }
}