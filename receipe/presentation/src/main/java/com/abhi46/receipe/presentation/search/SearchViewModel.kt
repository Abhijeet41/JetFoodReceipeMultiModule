package com.abhi46.receipe.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi46.receipe.domain.models.RecipeResult
import com.abhi46.receipe.domain.useCases.SearchResultUseCase
import com.abhi46.receipe.domain.utils.Resource
import com.abhi46.receipe.presentation.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchResultUseCase: SearchResultUseCase
) : ViewModel(){

    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getSearchRecipes(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchResultUseCase(queries = applySearchQuery(searchQuery = searchQuery)).
                onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _searchState.value = _searchState.value.copy(
                                result = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _searchState.value = _searchState.value.copy(
                                result = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _searchState.value = _searchState.value.copy(
                                result = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }

    }
    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[Constants.QUERY_SEARCH] = searchQuery
        queries[Constants.QUERY_NUMBER] = Constants.QUERY_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_ADD_RECIPE_INFO] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
    fun cleareSearchList() { //clear data when clicked on search trailing close button
        _searchState.value = _searchState.value.copy(
            result = emptyList()
        )
    }
}


data class SearchState(
    val result: List<RecipeResult> = emptyList(),
    val isLoading: Boolean = false
)
