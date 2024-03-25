package com.seif.howtodrawpersontestapp.presentation.child.draw_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.rememberDrawController
import kotlinx.coroutines.launch


@Composable
fun DrawScreen(
    onSaveImageClick: (bitmap: ImageBitmap) -> Unit,
    drawScreenViewSate: DrawScreenViewState?,
    navigateToGradeScreen:() -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val showLoadingState = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
            drawScreenViewSate?.let {
                when{
                    drawScreenViewSate.isLoading -> {
                        showLoadingState.value = true
                    }
                    drawScreenViewSate.errorMessage != null -> {
                        showLoadingState.value = false
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = drawScreenViewSate.errorMessage,
                                duration = SnackbarDuration.Short
                            )
                        }

                    }
                    drawScreenViewSate.uploadDrawSuccessfully -> {
                        showLoadingState.value = false
                        LaunchedEffect(Unit){
                            navigateToGradeScreen()
                        }
                    }

                    else -> {}
                }
            }

    val undoVisibility = remember { mutableStateOf(false) }
    val redoVisibility = remember { mutableStateOf(false) }
    val drawController = rememberDrawController()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(Modifier.padding(it), horizontalAlignment = Alignment.CenterHorizontally) {
            DrawBox(drawController = drawController, modifier = Modifier.fillMaxSize().weight(1f, fill = false), bitmapCallback = { imageBitmap, error ->
                imageBitmap?.let {
                    onSaveImageClick(it)
                }
            }) { undoCount, redoCount ->
                undoVisibility.value = undoCount != 0
                redoVisibility.value = redoCount != 0
            }
            if (showLoadingState.value)
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 8.dp))

            ControlsBar(
                drawController = drawController, onSaveClick = {
                    drawController.saveBitmap()
                }, undoVisibility = undoVisibility, redoVisibility = redoVisibility
            )

            drawController.changeColor(Color.Black)

        }
    }
}
