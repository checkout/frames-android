package com.checkout.frames.screen.countrypicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.di.base.Injector
import com.checkout.frames.style.screen.CountryPickerStyle
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import com.checkout.frames.view.InputField
import com.checkout.frames.view.InputFieldState
import com.checkout.frames.view.TextLabel
import com.checkout.frames.view.TextLabelState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun CountryPickerScreen(
    style: CountryPickerStyle,
    injector: Injector,
    onClose: () -> Unit
) {
    val viewModel: CountryPickerViewModel = viewModel(
        factory = CountryPickerViewModel.Factory(injector, style)
    )

    if (viewModel.goBack.value) {
        onClose()
    }

    if (!viewModel.isSearchActive.value) {
        LocalSoftwareKeyboardController.current?.hide()
        LocalFocusManager.current.clearFocus(true)
    }

    Column(
        modifier = viewModel.screenModifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        HeaderComponent(
            screenTitleStyle = viewModel.screenTitleStyle,
            screenTitleState = viewModel.screenTitleState,
            searchFieldStyle = viewModel.searchFieldStyle,
            searchFieldState = viewModel.searchFieldState,
            onSearchFocusChange = viewModel::onFocusChanged,
            onSearchValueChange = viewModel::onSearchChange
        )
        LazyColumn {
            items(viewModel.searchCountries.value) { itemData ->
                CountryItemComponent(viewModel.searchItemStyle, itemData, viewModel::onCountryChosen)
            }
        }
    }
}

@Composable
private fun HeaderComponent(
    screenTitleStyle: TextLabelViewStyle,
    screenTitleState: TextLabelState,
    searchFieldStyle: InputFieldViewStyle,
    searchFieldState: InputFieldState,
    onSearchFocusChange: (Boolean) -> Unit,
    onSearchValueChange: (String) -> Unit
) {
    Column {
        TextLabel(style = screenTitleStyle, state = screenTitleState)
        InputField(searchFieldStyle, searchFieldState, onSearchValueChange, onSearchFocusChange)
    }
}

@Composable
private fun CountryItemComponent(
    labelStyle: TextLabelViewStyle,
    data: CountryItem,
    onClick: (String) -> Unit
) {
    val state = TextLabelState().apply {
        isVisible.value = true
        text.value = "${data.emojiFlag}    ${data.name}"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(data.iso2) }
    ) {
        TextLabel(style = labelStyle, state = state)
    }
}
