package com.example.imageviewer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.imageviewer.ImageViewerApp
import com.example.imageviewer.di.DispatcherProvider
import com.example.imageviewer.presentation.main.ImagesViewerScreen
import com.example.imageviewer.presentation.main.ImagesViewerViewModel
import com.example.imageviewer.presentation.main.model.ImageViewerState
import com.example.imageviewer.presentation.main.model.ImageViewerState.Initial
import com.example.imageviewer.presentation.main.model.ImageViewerState.Loading
import com.example.imageviewer.presentation.main.model.ImageViewerState.Success
import com.example.imageviewer.presentation.navigation.model.MainScreen
import com.example.imageviewer.shared.viewModelFactory

@Composable
fun NavRoot(modifier: Modifier = Modifier) {
  val backStack = rememberNavBackStack(MainScreen)
  NavDisplay(
    backStack = backStack,
    entryDecorators = listOf(
      rememberSavedStateNavEntryDecorator(),
      rememberViewModelStoreNavEntryDecorator(),
      rememberSceneSetupNavEntryDecorator()
    ),
    modifier = modifier,
    entryProvider = entryProvider {
      entry<MainScreen> {
        val imagesViewerViewModel = viewModel<ImagesViewerViewModel>(
          factory = viewModelFactory {
            ImagesViewerViewModel(
              repository = ImageViewerApp.imageViewerRepository,
              dispatchers = DispatcherProvider.Default(),
            )
          }
        )
        val state = imagesViewerViewModel.imagesState.collectAsStateWithLifecycle()
        when (val state = state.value) {
          // TODO add error/loading screens
          is ImageViewerState.Error -> Unit
          is Loading -> Unit
          is Success -> ImagesViewerScreen(
            images = state.images,
            onImageClick = { imageId ->
              // TODO navigate to detail and get current image by id
            }
          )

          is Initial -> Unit
        }
      }
//      entry<ImageDetail> { imageDetail ->
//        ImageContent(
//          title = imageDetail.title,
//          imageUrl = imageDetail.imageUrl,
//        )
//      }
    }
  )
}
