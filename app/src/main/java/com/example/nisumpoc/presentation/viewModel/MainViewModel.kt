package com.example.nisumpoc.presentation.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nisumpoc.data.model.User
import com.example.nisumpoc.domain.repository.UserRepository
import com.example.nisumpoc.domain.utils.ApiState
import com.example.nisumpoc.domain.utils.Converter
import com.example.nisumpoc.domain.utils.FilterType
import com.example.nisumpoc.domain.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(private val mainRepository: UserRepository) : ViewModel() {

    val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)
    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()
    private var _originalList = listOf<User>()

    fun getRandomUserApi() = viewModelScope.launch() {
        mainRepository.getUserList()
            .onStart {
                response.value = ApiState.Loading
            }.catch {
                response.value = ApiState.Failure(it)
            }.collect { success ->
                response.value = ApiState.Success(success)

                success.body()?.let { list ->
                    _viewState.update {
                        _originalList = list.results
                        it.copy(
                            filterList = list.results
                        )
                    }
                }
            }
    }

    fun getFilterListByName(list: List<User>, searchItem: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {

                val filteredItems = Converter.filterUsersItemsByNamePrefix(list, searchItem)

                _viewState.update {
                    it.copy(
                        filterList = filteredItems
                    )
                }
            }
        }
    }

    fun getFilterListByOption(selectedFilters: List<FilterType>) {

        viewModelScope.launch {
            withContext(Dispatchers.Default) {

                val filteredItems = Converter.getFilterListByOption(_originalList, selectedFilters)

                _viewState.update {
                    it.copy(
                        filterList = filteredItems
                    )
                }
            }
        }
    }
}

