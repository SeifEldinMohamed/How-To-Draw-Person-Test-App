package com.seif.howtodrawpersontestapp.presentation.child.grade_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.seif.howtodrawpersontestapp.R
import kotlinx.coroutines.launch

@Composable
fun GradeScreen(
    gradeScreenViewState: GradeScreenViewState?, onRefreshClick: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val showLoadingState = remember { mutableStateOf(false) }
    val animationFile = remember { mutableStateOf(R.raw.waiting_animation) }
    val showGrades = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    gradeScreenViewState?.let {
        when {
            gradeScreenViewState.isLoading -> {
                showLoadingState.value = true
            }

            gradeScreenViewState.errorMessage != null -> {
                showLoadingState.value = false
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = gradeScreenViewState.errorMessage, duration = SnackbarDuration.Short
                    )
                }

            }

            gradeScreenViewState.fetchChildData != null -> {
                showLoadingState.value = false
                if (gradeScreenViewState.fetchChildData.intelligenceGrade.isEmpty() || gradeScreenViewState.fetchChildData.totalGrade.isEmpty()) {
                    // wait for grade
                    animationFile.value = R.raw.waiting_animation
                } else {
                    // show grade
                    animationFile.value = R.raw.congratulations
                    showGrades.value = true
                }
            }

            else -> {}
        }
    }

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(animationFile.value))
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween
        ) {

            LottieAnimation(
                composition = composition, iterations = LottieConstants.IterateForever, modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth()
                    .height(350.dp)
            )
            if (!showGrades.value) Text(
                text = "برجاء إنتظار النتيجة", style = MaterialTheme.typography.headlineMedium
            )
            else {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Column {
                        Text(
                            text = "الدرجة الكلية: ${gradeScreenViewState?.fetchChildData?.totalGrade}", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = "ذكاء الطفل بالدرجات: ${gradeScreenViewState?.fetchChildData?.intelligenceGrade}", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = "ذكاء الطفل بالقيمة: ${gradeScreenViewState?.fetchChildData?.intelligenceValue}", style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }
            if (showLoadingState.value)
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 12.dp))

            Button(modifier = Modifier
                .padding(bottom = 32.dp, top = 16.dp)
                .fillMaxWidth(0.75f), onClick = {
                onRefreshClick()
            }, shape = RoundedCornerShape(8.dp), content = {
                Text(text = "تحديث", style = MaterialTheme.typography.bodyLarge)
            })
        }
    }
}