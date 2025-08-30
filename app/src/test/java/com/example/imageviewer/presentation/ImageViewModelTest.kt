package com.example.imageviewer.presentation

import com.example.imageviewer.domain.model.ImageViewerDomain
import com.example.imageviewer.presentation.main.ImagesViewerViewModel
import org.junit.Assert
import app.cash.turbine.test
import com.example.imageviewer.di.DispatcherProvider
import com.example.imageviewer.domain.repository.ImageViewerRepository
import com.example.imageviewer.presentation.main.model.ImageViewerState
import com.example.imageviewer.presentation.main.model.ImageViewerState.Success.ImageViewerUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ImagesViewerViewModelTest {

  private lateinit var viewModel: ImagesViewerViewModel
  private val testDispatcher = StandardTestDispatcher()

  private val testDispatchers = object : DispatcherProvider {
    override fun io(): CoroutineDispatcher = testDispatcher
    override fun main(): CoroutineDispatcher = testDispatcher
    override fun mainImmediate(): CoroutineDispatcher = testDispatcher
    override fun default(): CoroutineDispatcher = testDispatcher
  }

  private class Repository(
    private var imagesDomain: List<ImageViewerDomain>
  ) : ImageViewerRepository {
    override suspend fun getImages(): List<ImageViewerDomain> = imagesDomain

    fun updateImages(newImages: List<ImageViewerDomain>) {
      imagesDomain = newImages
    }
  }

  @Test
  fun `emits Success when repository returns images`() = runTest(testDispatcher) {
    val repository = Repository(
      imagesDomain = listOf(
        ImageViewerDomain(id = 1, title = "Title1", image = "url1"),
        ImageViewerDomain(id = 2, title = "Title2", image = "url2")
      )
    )
    viewModel = ImagesViewerViewModel(repository = repository, dispatchers = testDispatchers)

    viewModel.imagesState.test {
      val loading = awaitItem()
      Assert.assertTrue(loading is ImageViewerState.Loading)

      val success = awaitItem()
      Assert.assertTrue(success is ImageViewerState.Success)
      val successResult = success as ImageViewerState.Success
      Assert.assertEquals(2, successResult.images.size)
      Assert.assertEquals(
        listOf(
          ImageViewerUi(id = 1, title = "Title1", url = "url1"),
          ImageViewerUi(id = 2, title = "Title2", url = "url2")
        ),
        successResult.images
      )
      Assert.assertEquals(false, successResult.isRefreshing)
      cancelAndIgnoreRemainingEvents()
    }
  }

  @Test
  fun `emits Error when repository returns empty list`() = runTest(testDispatcher) {
    val repository = Repository(imagesDomain = emptyList())
    viewModel = ImagesViewerViewModel(repository = repository, dispatchers = testDispatchers)

    viewModel.imagesState.test {
      val loading = awaitItem()
      Assert.assertTrue(loading is ImageViewerState.Loading)

      val error = awaitItem()
      Assert.assertTrue(error is ImageViewerState.Error)
      val errorResult = error as ImageViewerState.Error
      Assert.assertEquals("No images found", errorResult.message)
      cancelAndIgnoreRemainingEvents()
    }
  }

  @Test
  fun `emits refreshed Success when refresh is called`() = runTest(testDispatcher) {
    val repository = Repository(
      imagesDomain = listOf(
        ImageViewerDomain(id = 1, title = "Title1", image = "url1")
      )
    )
    viewModel = ImagesViewerViewModel(repository = repository, dispatchers = testDispatchers)

    viewModel.imagesState.test {

      Assert.assertTrue(awaitItem() is ImageViewerState.Loading)
      val initialSuccess = awaitItem() as ImageViewerState.Success
      Assert.assertEquals(1, initialSuccess.images.size)
      Assert.assertFalse(initialSuccess.isRefreshing)

      repository.updateImages(
        listOf(
          ImageViewerDomain(id = 1, title = "Title1", image = "url1"),
          ImageViewerDomain(id = 2, title = "Title2", image = "url2")
        )
      )
      viewModel.refresh()

      val refreshingState = awaitItem() as ImageViewerState.Success
      Assert.assertTrue(refreshingState.isRefreshing)

      val refreshedSuccess = awaitItem() as ImageViewerState.Success
      Assert.assertEquals(2, refreshedSuccess.images.size)
      Assert.assertFalse(refreshedSuccess.isRefreshing)

      cancelAndIgnoreRemainingEvents()
    }
  }
}
