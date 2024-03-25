package com.seif.howtodrawpersontestapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seif.howtodrawpersontestapp.R
import com.seif.howtodrawpersontestapp.ui.theme.darkGrey

@Composable
fun ChildOrSpecialistScreen(
    onChildClick:() -> Unit,
    onSpecialistClick:() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "مرحبا في اختبار رسم رجل",
                modifier = Modifier.padding(top = 32.dp),
                style = MaterialTheme.typography.headlineMedium
            )

            Image(
                painter = painterResource(id = R.drawable.ic_app_icon),
                contentDescription = "",
                Modifier.size(350.dp).padding(top = 8.dp)
            )

        }
        Column {
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.75f),
                onClick = {
                    onChildClick()
                },
                shape = RoundedCornerShape(8.dp),
                content = {
                    Text(text = "المتابعة كطفل", style = MaterialTheme.typography.bodyLarge)
                }
            )

            Button(
                modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(bottom = 32.dp, top = 16.dp),
                onClick = {
                    onSpecialistClick()
                },
                shape = RoundedCornerShape(8.dp),
                content = {
                    Text(text = "المتابعة كاخصائي", style = MaterialTheme.typography.bodyLarge)
                }
            )
        }
    }
}