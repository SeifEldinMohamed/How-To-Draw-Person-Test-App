package com.seif.howtodrawpersontestapp.presentation.specialist.enter_specialist_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.seif.howtodrawpersontestapp.R
import com.seif.howtodrawpersontestapp.ui.theme.HowToDrawPersonTestAppTheme
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@Composable
fun SpecialistPasswordScreen(
    onNextClick: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var specialistPasswordState by remember {
        mutableStateOf("")
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_specialist),
                    contentDescription = "",
                    modifier = Modifier
                        .width(300.dp)
                        .height(400.dp)
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(20))
                )

                OutlinedTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    value = specialistPasswordState,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Black, unfocusedTextColor = Color.Black, unfocusedLabelColor = Color.Black, unfocusedLeadingIconColor = Color.Black

                    ),
                    enabled = true,
                    singleLine = true,
                    maxLines = 1,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    onValueChange = {
                        specialistPasswordState = it
                    },
                    label = {
                        Text(
                            "الرقم السري",
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                )


                Button(enabled = specialistPasswordState.isNotEmpty(), modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(top = 16.dp),
                    onClick = {
                        if (specialistPasswordState == "1020"){
                            onNextClick()
                        }
                        else {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "الرقم السري غير صحيح!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                }, shape = RoundedCornerShape(8.dp), content = {
                    Text(text = "المتابعة", style = MaterialTheme.typography.bodyLarge)
                })

            }
        }
    }
}

