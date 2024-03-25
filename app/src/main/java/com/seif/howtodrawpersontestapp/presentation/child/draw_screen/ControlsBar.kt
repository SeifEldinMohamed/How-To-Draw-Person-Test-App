package com.seif.howtodrawpersontestapp.presentation.child.draw_screen

import androidx.compose.ui.graphics.Color
import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seif.howtodrawpersontestapp.R
import com.seif.howtodrawpersontestapp.ui.theme.white
import io.ak1.drawbox.DrawController

@Composable
fun ControlsBar(
    drawController: DrawController,
    onSaveClick: () -> Unit,
    undoVisibility: MutableState<Boolean>,
    redoVisibility: MutableState<Boolean>,
) {
    Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceAround) {
        MenuItems(
            R.drawable.ic_undo,
            "undo",
            if (undoVisibility.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        ) {
            if (undoVisibility.value) drawController.unDo()
        }
        MenuItems(
            R.drawable.ic_redo,
            "redo",
            if (redoVisibility.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        ) {
            if (redoVisibility.value) drawController.reDo()
        }
        Button(onClick = { onSaveClick()}) {
            Text(text = "حفظ", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun RowScope.MenuItems(
    @DrawableRes resId: Int,
    desc: String,
    colorTint: Color,
    border: Boolean = false,
    onClick: () -> Unit
) {
    val modifier = Modifier.size(24.dp)
    IconButton(
        onClick = onClick, modifier = Modifier.weight(1f, true)
    ) {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = desc,
            tint = colorTint,
            modifier = if (border) modifier.border(
                0.5.dp,
                white,
                shape = CircleShape
            ) else modifier
        )
    }
}
