package com.checkout.example.frames.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.checkout.example.frames.ui.component.ClickableImage
import com.checkout.example.frames.ui.component.CustomButton
import com.checkout.example.frames.ui.component.TextComponent
import com.checkout.example.frames.ui.theme.DarkBlue
import com.checkout.example.frames.ui.theme.FramesTheme
import com.checkout.example.frames.ui.viewmodel.CVVTokenizationViewModel
import com.checkout.frames.R
import com.checkout.frames.cvvinputfield.api.CVVComponentMediator

@Composable
fun LoadCVVComponentsContents(
    navController: NavHostController,
    cvvTokenizationViewModel: CVVTokenizationViewModel,
    visaMediator: CVVComponentMediator,
    maestroMediator: CVVComponentMediator,
) {
    FramesTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
                    .padding(start = 10.dp, end = 24.dp, bottom = 14.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
                ) {

                    ClickableImage(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "Back Arrow",
                        onClick = { navController.popBackStack() },
                        paddingValues = PaddingValues(top = 20.dp, bottom = 14.dp),
                    )

                    TextComponent(
                        titleResourceId = R.string.cvv_component,
                        fontSize = 20,
                        fontWeight = FontWeight.SemiBold,
                        paddingValues = PaddingValues(top = 20.dp, start = 10.dp, bottom = 14.dp),
                        textColor = DarkBlue
                    )
                }

                Spacer(Modifier.height(15.dp))

                Text("Default CVV Component")

                Spacer(Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
                ) {
                    visaMediator.CVVComponent()

                    CustomButton(isCVVValid = cvvTokenizationViewModel.isEnteredVisaCVVValid)
                }

                Spacer(Modifier.height(15.dp))

                Text("Custom CVV Component")

                Spacer(Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
                ) {
                    maestroMediator.CVVComponent()

                    CustomButton(isCVVValid = cvvTokenizationViewModel.isEnteredMaestroCVVValid)
                }
            }
        }
    }
}
