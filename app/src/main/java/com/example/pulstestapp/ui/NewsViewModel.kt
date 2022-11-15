package com.example.pulstestapp.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pulstestapp.NewsApplication
import com.example.pulstestapp.data.NewsItemRepository
import com.example.pulstestapp.model.ArticleModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private const val FIRST_PAGE = 1
private const val LAST_PAGE = 5

sealed interface NewsUiState{
    data class Success(val articleList: List<ArticleModel>) : NewsUiState
    object Error: NewsUiState
    object Loading: NewsUiState
}

class NewsViewModel(private val newsItemRepository: NewsItemRepository) : ViewModel() {
    var newsUiState: NewsUiState by mutableStateOf(NewsUiState.Loading)
        private set

    private val _articleItem = MutableStateFlow(ArticleModel("","","","",""))
    val articleItem: StateFlow<ArticleModel> = _articleItem.asStateFlow()

    private var currentPage by mutableStateOf(1)

    init {
        getNewsList()
    }

    fun updateArticleItem(articleItem: ArticleModel) {
        _articleItem.update { currentState ->
            currentState.copy(
                title = articleItem.title,
                imageUrl = articleItem.imageUrl,
                description = articleItem.description,
                url = articleItem.url,
                author = articleItem.author
            )
        }
    }

    fun changePage(moveForward: Boolean, context: Context) {
        if (isPageExists(moveForward)){
            if (moveForward) {
                currentPage += 1
                getNewsList()
            } else {
                currentPage -= 1
                getNewsList()
            }
        } else {
            if (moveForward) {
                Toast.makeText(context,"Вы на последней странице", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Вы на первой странице", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getNewsList() {
        viewModelScope.launch {
            newsUiState = NewsUiState.Loading
            newsUiState = try {
                NewsUiState.Success(newsItemRepository.getNewsItemList(currentPage))
            } catch (e: IOException) {
                NewsUiState.Error
            } catch (e: HttpException) {
                NewsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NewsApplication)
                val newsItemRepository = application.container.newsItemRepository
                NewsViewModel(newsItemRepository = newsItemRepository)}
        }
    }

    private fun isPageExists(moveForward: Boolean): Boolean {
        return if (moveForward) {
            (currentPage + 1 in FIRST_PAGE..LAST_PAGE)
        } else {
            (currentPage - 1 in FIRST_PAGE..LAST_PAGE)
        }
    }
}
