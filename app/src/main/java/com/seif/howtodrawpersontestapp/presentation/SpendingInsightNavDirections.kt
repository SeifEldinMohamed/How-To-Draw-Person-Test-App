package com.seif.howtodrawpersontestapp.presentation

sealed class NavigationRoutes(val route: String) {
    object ChildOrSpecialistScreen:NavigationRoutes("NAV_CHILD_OR_SPECIALIST_SCREEN")
    object BabyInfoScreen:NavigationRoutes("NAV_BABY_INFO_SCREEN")
    object StartTestScreen:NavigationRoutes("NAV_START_TEST_SCREEN")
    object DrawPersonScreen: NavigationRoutes("NAV_DRAW_PERSON_SCREEN")
    object GradeScreen: NavigationRoutes("NAV_GRADE_SCREEN")
    object SpecialistPasswordScreen: NavigationRoutes("NAV_SPECIALIST_PASSWORD_SCREEN")
    object SpecialistDrawnListScreen: NavigationRoutes("NAV_SPECIALIST_DRAWN_LIST_SCREEN")
    object SpecialistDrawnDetailsScreen: NavigationRoutes("NAV_SPECIALIST_DRAWN_DETAILS_SCREEN")
}
//   fun passTransactionKey(transactionKey: String): String {
//            return "NAV_TRANSACTION_DETAILS_SCREEN/$transactionKey"
//        }