package com.example.compose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    focusedIndicatorColor: Color = Color(0xFF4A4A4A),
    unfocusedIndicatorColor: Color = Color(0xFFDDDDDD),
    disabledIndicatorColor: Color = Color(0xFFDDDDDD),
    labelModifier: Modifier = Modifier,
    spacing: Dp = 8.dp
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            modifier = labelModifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.size(spacing))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = singleLine,
            maxLines = maxLines,
            enabled = enabled,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = focusedIndicatorColor,
                unfocusedIndicatorColor = unfocusedIndicatorColor,
                disabledIndicatorColor = disabledIndicatorColor
            )
        )
    }
}
