package com.seif.howtodrawpersontestapp.presentation.specialist.draw_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.seif.howtodrawpersontestapp.data.model.ChildDataModel
import com.seif.howtodrawpersontestapp.ui.theme.primary
import com.seif.howtodrawpersontestapp.ui.theme.white
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialistDrawnListScreen(
    drawnListViewState: DrawnListScreenViewState?, onItemClick: (childDataModel: ChildDataModel) -> Unit, onRefreshClick: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->

        drawnListViewState?.let {
            when {
                drawnListViewState.isLoading -> {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(padding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.padding(vertical = 12.dp))
                    }
                }

                drawnListViewState.errorMessage != null -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = drawnListViewState.errorMessage, duration = SnackbarDuration.Short
                        )
                    }

                }

                drawnListViewState.fetchDrawnList != null -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                    ) {
                        item {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "الإختبارات", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
                                    )

                                }, modifier = Modifier.fillMaxWidth()
                            )
                        }
                        items(drawnListViewState.fetchDrawnList) {
                            ChildTestItem(
                                childDataModel = it
                            ) {
                                onItemClick(it)
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                }

                else -> {}
            }

        }

    }

}

@Composable
fun ChildTestItem(
    modifier: Modifier = Modifier, childDataModel: ChildDataModel, onItemClick: (childDataModel: ChildDataModel) -> Unit
) {
    val dateOfBirth = childDataModel.dayOfBirth + " / " + childDataModel.monthOfBirth + " / " + childDataModel.yearOfBirth
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
                .border(width = 2.dp, color = primary), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                .fillMaxWidth()
                .background(white)
                .clickable {
                    onItemClick(childDataModel)
                }) {
                AsyncImage(
                    model = childDataModel.drawnImage,
                    contentDescription = "",
                    Modifier
                        .width(80.dp)
                        .height(120.dp)
                        .padding(vertical = 12.dp)
                        .padding(horizontal = 16.dp),
                    contentScale = ContentScale.Fit
                )
                Column {
                    Text(text = "اسم الطفل: ${childDataModel.name}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "تاريخ الميلاد: $dateOfBirth", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "تاريخ الاختبار: ${childDataModel.testDate}", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
