package com.checkout.example.frames.ui.component

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.checkout.example.frames.R
import com.checkout.example.frames.ui.screen.ThreedSecureActivity
import com.checkout.example.frames.ui.theme.ButtonBorder
import com.checkout.example.frames.ui.theme.DarkBlue
import com.checkout.example.frames.ui.theme.GrayColor
import com.checkout.example.frames.ui.utils.CORNER_RADIUS_PERCENT
import com.checkout.example.frames.ui.utils.PromptUtils
import com.checkout.example.frames.ui.utils.PromptUtils.neutralButton
import com.checkout.example.frames.ui.utils.URL_IDENTIFIER

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("MagicNumber")
fun ThreedComponent(context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var textValue by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = textValue,
            modifier = Modifier
                .fillMaxWidth()
                .weight(3F),
            shape = RoundedCornerShape(CORNER_RADIUS_PERCENT),
            placeholder = { Text(context.getString(R.string.enter_3ds_redirection_url)) },
            colors = outlinedTextFieldColors(
                unfocusedBorderColor = ButtonBorder,
                focusedPlaceholderColor = GrayColor,
                unfocusedPlaceholderColor = GrayColor
            ),
            onValueChange = { textValue = it }
        )

        Card(
            modifier = Modifier
                .size(width = Dp.Unspecified, height = 55.dp)
                .padding(start = 12.dp)
                .align(Alignment.CenterVertically)
                .weight(1F),
            border = BorderStroke(width = 1.dp, color = ButtonBorder),
            shape = RoundedCornerShape(12),
            onClick = {
                if (textValue.text.isNotEmpty()) {
                    val intent = Intent(context, ThreedSecureActivity::class.java)
                    intent.putExtra(URL_IDENTIFIER, textValue.text)
                    context.startActivity(intent)
                } else {
                    showAlertDialog(
                        context, context.getString(R.string.empty_url), context.getString(R.string.paste_valid_link)
                    )
                }
            },
            colors = CardDefaults.cardColors(containerColor = Color.Transparent, contentColor = DarkBlue)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.cloud),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp)
                    .align(CenterHorizontally),
                alignment = Alignment.Center,
                contentDescription = null
            )
        }
    }
}

fun showAlertDialog(context: Context, title: String, message: String) {
    PromptUtils.alertDialog(context) {
        setTitle(title)
        setMessage(message)
        neutralButton {
            it.dismiss()
        }
    }.show()
}
