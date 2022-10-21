package com.checkout.frames.component.billingaddressfields

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.checkout.frames.component.base.InputComponent
import com.checkout.frames.component.country.CountryComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.screen.navigation.Screen
import com.checkout.frames.style.component.CountryComponentStyle
import com.checkout.frames.style.view.BillingAddressInputComponentViewStyle

@Composable
internal fun BillingAddressDynamicInputComponent(
    billingAddressInputComponentViewStyle: List<BillingAddressInputComponentViewStyle>,
    billingAddressInputComponentState: List<BillingAddressInputComponentState>,
    countryComponentStyle: CountryComponentStyle,
    onFocusChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    injector: Injector,
    navController: NavHostController
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        items(billingAddressInputComponentViewStyle.size) { index ->

            if (billingAddressInputComponentViewStyle[index].addressFieldName == BillingFormFields.Country.name) {
                CountryComponent(countryComponentStyle, injector) {
                    navController.navigate(Screen.CountryPicker.route)
                }
            } else {
                InputComponent(
                    style = billingAddressInputComponentViewStyle[index].inputComponentViewStyle,
                    state = billingAddressInputComponentState[index].inputComponentState,
                    onFocusChanged = onFocusChange,
                    onValueChange = onValueChange
                )
            }
        }
    }
}
