package com.seif.howtodrawpersontestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.seif.howtodrawpersontestapp.data.model.ChildDataModel
import com.seif.howtodrawpersontestapp.presentation.ChildOrSpecialistScreen
import com.seif.howtodrawpersontestapp.presentation.NavigationRoutes
import com.seif.howtodrawpersontestapp.presentation.child.GetStateViewModel
import com.seif.howtodrawpersontestapp.presentation.child.baby_info_screen.BabyInfoScreen
import com.seif.howtodrawpersontestapp.presentation.child.baby_info_screen.BabyInfoViewModel
import com.seif.howtodrawpersontestapp.presentation.child.draw_screen.DrawScreen
import com.seif.howtodrawpersontestapp.presentation.child.draw_screen.DrawScreenViewModel
import com.seif.howtodrawpersontestapp.presentation.child.draw_screen.DrawScreenViewState
import com.seif.howtodrawpersontestapp.presentation.child.grade_screen.GradeScreen
import com.seif.howtodrawpersontestapp.presentation.child.grade_screen.GradeViewModel
import com.seif.howtodrawpersontestapp.presentation.child.start_test_screen.StartTestScreen
import com.seif.howtodrawpersontestapp.presentation.specialist.draw_details_screen.DrawnDetailsViewModel
import com.seif.howtodrawpersontestapp.presentation.specialist.draw_details_screen.SpecialistDrawnDetailsScreen
import com.seif.howtodrawpersontestapp.presentation.specialist.draw_list_screen.DrawnListViewModel
import com.seif.howtodrawpersontestapp.presentation.specialist.draw_list_screen.SpecialistDrawnListScreen
import com.seif.howtodrawpersontestapp.presentation.specialist.enter_specialist_password.SpecialistPasswordScreen
import com.seif.howtodrawpersontestapp.ui.theme.HowToDrawPersonTestAppTheme
import com.seif.howtodrawpersontestapp.util.toByteArray
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var startDestination: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HowToDrawPersonTestAppTheme {
                val navController = rememberNavController()

                val getStateViewModel = hiltViewModel<GetStateViewModel>()
                val finishedTest = getStateViewModel.getFinishingTestSate()
                startDestination = if (finishedTest){
                    NavigationRoutes.GradeScreen.route
                } else {
                    NavigationRoutes.ChildOrSpecialistScreen.route
                }

                // not finishing tutorial enter app then -> ChildOrSpecialistScreen
                // after finishing tutorial (click save button in draw screen) -> enter GradeScreen

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {

                    composable(route = NavigationRoutes.ChildOrSpecialistScreen.route){
                        ChildOrSpecialistScreen(
                            onChildClick = {
                                navController.navigate(NavigationRoutes.BabyInfoScreen.route){
                                    popUpTo(NavigationRoutes.ChildOrSpecialistScreen.route){
                                        inclusive = true
                                    }
                                }
                            },
                            onSpecialistClick = {
                                navController.navigate(NavigationRoutes.SpecialistPasswordScreen.route)
                            }
                        )
                    }

                    composable(route = NavigationRoutes.BabyInfoScreen.route) {
                        val babyInfoViewModel = hiltViewModel<BabyInfoViewModel>()
                        val babyInfoViewSate = babyInfoViewModel.babyInfoState.collectAsStateWithLifecycle().value
                        BabyInfoScreen(
                            onNextClick = { name, dayOfBirth, monthOfBirth, yearOfBirth ->
                                babyInfoViewModel.uploadChildData(
                                    ChildDataModel(
                                        name = name,
                                        dayOfBirth = dayOfBirth,
                                        monthOfBirth = monthOfBirth,
                                        yearOfBirth = yearOfBirth
                                    )
                                )
                            },
                            babyInfoViewState = babyInfoViewSate,
                            navigateToStartTestScreen = {
                                navController.navigate(
                                    route = NavigationRoutes.StartTestScreen.route,
                                    builder = {
                                        popUpTo(NavigationRoutes.BabyInfoScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                )
                            }

                        )
                    }

                    composable(route = NavigationRoutes.StartTestScreen.route) {
                        StartTestScreen {
                            navController.navigate(NavigationRoutes.DrawPersonScreen.route){
                                popUpTo(NavigationRoutes.StartTestScreen.route){
                                    inclusive = true
                                }
                            }
                        }
                    }

                    composable(route = NavigationRoutes.DrawPersonScreen.route) {
                        val drawScreenViewModel = hiltViewModel<DrawScreenViewModel>()
                        val drawScreenViewSate = drawScreenViewModel.drawScreenState.collectAsStateWithLifecycle().value
                        DrawScreen(
                            onSaveImageClick = {
                                drawScreenViewModel.uploadDraw(it.toByteArray())
                            },
                            drawScreenViewSate = drawScreenViewSate,
                            navigateToGradeScreen = {
                                drawScreenViewModel.saveFinishingTestSate(completed = true)
                                navController.navigate(
                                    route = NavigationRoutes.GradeScreen.route,
                                    builder = {
                                        popUpTo(NavigationRoutes.DrawPersonScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                )
                            }
                        )
                    }

                    composable(route = NavigationRoutes.GradeScreen.route) {
                        val gradeScreenViewModel = hiltViewModel<GradeViewModel>()
                        val gradeScreenViewState = gradeScreenViewModel.gradeScreenState.collectAsStateWithLifecycle().value
                        GradeScreen(
                            gradeScreenViewState = gradeScreenViewState
                        ) {
                            gradeScreenViewModel.getGrade()
                        }
                    }

                    composable(route = NavigationRoutes.SpecialistPasswordScreen.route) {
                        SpecialistPasswordScreen {
                            navController.navigate(NavigationRoutes.SpecialistDrawnListScreen.route) {
                                popUpTo(NavigationRoutes.SpecialistPasswordScreen.route){
                                    inclusive = true
                                }
                            }
                        }
                    }

                    composable(route = NavigationRoutes.SpecialistDrawnListScreen.route) {
                        val drawnListScreenViewModel = hiltViewModel<DrawnListViewModel>()
                        val drawnListViewState = drawnListScreenViewModel.drawnListScreenState.collectAsStateWithLifecycle().value
                        SpecialistDrawnListScreen(
                            drawnListViewState = drawnListViewState,
                            onItemClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = "child_model",
                                    value = it
                                )
                                navController.navigate(NavigationRoutes.SpecialistDrawnDetailsScreen.route)
                            }
                        ){

                        }
                    }

                    composable(route = NavigationRoutes.SpecialistDrawnDetailsScreen.route) {
                        val childDataModel = navController.previousBackStackEntry?.savedStateHandle?.get<ChildDataModel>(key = "child_model")
                        val drawnDetailsViewModel = hiltViewModel<DrawnDetailsViewModel>()
                        val drawnDetailsViewState = drawnDetailsViewModel.drawnDetailsViewState.collectAsStateWithLifecycle().value
                        childDataModel?.let { childDataModel ->
                            SpecialistDrawnDetailsScreen(
                                drawnListViewState = drawnDetailsViewState,
                                childDataModel = childDataModel
                            ) {

                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HowToDrawPersonTestAppTheme {
        DrawScreen(onSaveImageClick = {}, drawScreenViewSate = DrawScreenViewState(isLoading = true)) {

        }
    }
}