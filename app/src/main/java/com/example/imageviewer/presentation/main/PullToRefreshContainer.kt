package com.example.imageviewer.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@NonSkippableComposable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PullToRefreshLazyColumn(
  items: List<T>,
  content: @Composable (T) -> Unit,
  isRefreshing: Boolean,
  onRefresh: () -> Unit,
  modifier: Modifier = Modifier,
  dividerContent: @Composable (Int) -> Unit = {},
  lazyListState: LazyListState = rememberLazyListState()
) {
  PullToRefreshBox(
    modifier = modifier,
    isRefreshing = isRefreshing,
    onRefresh = onRefresh
  ) {
    LazyColumn(
      state = lazyListState,
      contentPadding = PaddingValues(8.dp),
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      itemsIndexed(items, key = { index, item -> index }) { index, it ->
        content(it)
        dividerContent(index)
      }
    }
  }
}
