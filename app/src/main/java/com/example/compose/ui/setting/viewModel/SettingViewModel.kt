package com.example.compose.ui.setting.viewModel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingViewModel: ViewModel() {

    sealed class SettingScreenModel {
        data class Title(val title: String) : SettingScreenModel()
        data class Item(val title: String, val screen: String, val textColor: Color, val isPro: Boolean = false) : SettingScreenModel()
    }
    private val _list: MutableStateFlow<List<SettingScreenModel>> = MutableStateFlow(emptyList())
    val list: StateFlow<List<SettingScreenModel>> = _list.asStateFlow()

    fun addSettingList(list: List<SettingScreenModel>) {
        _list.value = emptyList()
        _list.value = list
    }

}