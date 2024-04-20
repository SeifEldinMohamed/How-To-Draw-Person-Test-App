package com.seif.howtodrawpersontestapp.presentation.specialist.draw_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.seif.howtodrawpersontestapp.R
import com.seif.howtodrawpersontestapp.data.model.ChildDataModel
import com.seif.howtodrawpersontestapp.ui.theme.primary
import com.seif.howtodrawpersontestapp.ui.theme.white
import com.seif.howtodrawpersontestapp.util.getGradeEquivalencyMap
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period

@Composable
fun SpecialistDrawnDetailsScreen(
    childDataModel: ChildDataModel,
    drawnListViewState: DrawnDetailsViewState?,
    onSaveGradeClick: (String, Int, Int, Int, String, List<String>) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val showLoadingState = remember { mutableStateOf(false) }

    var totalGrades by remember { mutableIntStateOf(
        if (childDataModel.totalGrade.isEmpty()) 0 else childDataModel.totalGrade.toInt()
    ) }
    var childMindAgeInMonths by remember { mutableIntStateOf(
        if (childDataModel.mindAgeInMonths.isEmpty()) 0 else childDataModel.mindAgeInMonths.toInt()
    ) }
    var childIntelligenceGrade by remember { mutableIntStateOf(
        if (childDataModel.intelligenceGrade.isEmpty()) 0 else childDataModel.intelligenceGrade.toInt()
    ) }
    var childIntelligenceValue by remember { mutableStateOf( childDataModel.intelligenceValue ) }

        drawnListViewState?.let {
            when {
                drawnListViewState.isLoading -> {
                    showLoadingState.value = true

                }

                drawnListViewState.errorMessage != null -> {
                    showLoadingState.value = false
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = drawnListViewState.errorMessage, duration = SnackbarDuration.Short
                        )
                    }
                }

                drawnListViewState.savedChildGrade != null -> {
                    showLoadingState.value = false
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "تم حفظ النتبجة", duration = SnackbarDuration.Short
                        )
                    }
                }

                else -> {}
            }

        }
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        Column(
            Modifier
                .padding(padding)
                .background(white)) {
            ChildDetailsScreen(
                childDataModel = childDataModel,
                showLoadingState = showLoadingState.value,
                totalGrades = totalGrades,
                onUpdateTotalGrades = { newTotalGrades ->
                    totalGrades = newTotalGrades
                },
                onUpdateMindAgeInMonths =  {
                    childMindAgeInMonths = it
                },
                mindAgeInMonths = childMindAgeInMonths,
                onUpdateChildIntelligenceGrade = {
                    childIntelligenceGrade = it
                },
                intelligenceGrad = childIntelligenceGrade,
                onUpdateChildIntelligenceValue = {
                    childIntelligenceValue = it
                },
                intelligenceValue = childIntelligenceValue,
                onSaveGradeClicked = { childId, totalGrades, mindAge, intellegenceGrade, intellegenceValue, gradeList ->
                    onSaveGradeClick(childId, totalGrades, mindAge, intellegenceGrade, intellegenceValue, gradeList)
                }
            )
        }
    }

}

@Composable
fun ChildDetailsScreen(
    modifier: Modifier = Modifier,
    showLoadingState: Boolean,
    childDataModel: ChildDataModel,
    totalGrades: Int,
    mindAgeInMonths: Int,
    intelligenceGrad: Int,
    intelligenceValue: String,
    onUpdateTotalGrades: (Int) -> Unit ,
    onUpdateMindAgeInMonths: (Int) -> Unit ,
    onUpdateChildIntelligenceGrade: (Int) -> Unit ,
    onUpdateChildIntelligenceValue: (String) -> Unit,
    onSaveGradeClicked: (String, Int, Int, Int, String, List<String >) -> Unit
) {
    val dateOfBirth = childDataModel.dayOfBirth + " / " + childDataModel.monthOfBirth + " / " + childDataModel.yearOfBirth
    val criteriaList = remember { getCriteriaList() }

    // Store the grades for each criterion in a mutable list
    val grades = remember { mutableStateListOf<Int>().apply {
        addAll(
            if (childDataModel.gradeList.isNotEmpty())
                childDataModel.gradeList.map { it.toInt() }
            else
                criteriaList.map { it.grade }
        )
    } }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = modifier
                .fillMaxWidth()
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ZoomableImage(model = childDataModel.drawnImage)

            Text(text = "اسم الطفل: ${childDataModel.name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "تاريخ الميلاد: $dateOfBirth", style = MaterialTheme.typography.bodyLarge)
            Text(text = "تاريخ الاختبار: ${childDataModel.testDate}", style = MaterialTheme.typography.bodyLarge)

            LazyColumn(
                modifier = Modifier.padding(top = 12.dp)
            ){
                itemsIndexed(items = criteriaList){ index, item->
                    CriteriaItem(
                        criteria = item,
                        grade = grades[index], // Pass the grade from the list
                        onGradeChanged = { newGrade ->
                            // Update the grade in the list when changed
                            grades[index] = newGrade
                        }
                    )
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                val newTotalGrades = grades.sum()
                                onUpdateTotalGrades(newTotalGrades)
                                val childTimeAgeInMonths =  getAgeInMonths(childDataModel.yearOfBirth, childDataModel.monthOfBirth)
                                val childMindAgePair = getTimeAgeInMonths(newTotalGrades)
                                val childMindAgeInMonths = getAgeInMonthsGivenYearsAndMonths(childMindAgePair?.first, childMindAgePair?.second)
                                onUpdateMindAgeInMonths(childMindAgeInMonths)
                                val childIntelligenceGrade = (childMindAgeInMonths.toDouble() / childTimeAgeInMonths) * 100
                                onUpdateChildIntelligenceGrade(childIntelligenceGrade.toInt())
                                val childIntelligenceValue = getChildIntelligenceValue(childIntelligenceGrade)
                                onUpdateChildIntelligenceValue(childIntelligenceValue)

                            }, modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(text = "أحسب النتيجة")
                        }
                    }

                    Text(
                        text = "مجموع الدرجات الخام = $totalGrades",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .padding(vertical = 8.dp)
                    )
                    Text(
                        text = "العمر العقلي بالشهور = $mindAgeInMonths",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .padding(vertical = 8.dp)
                    )
                    Text(
                        text = "ذكاء الطفل بالدرجات = $intelligenceGrad",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .padding(vertical = 8.dp)
                    )
                    Text(
                        text = "ذكاء الطفل بالقيمة = $intelligenceValue",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .padding(top = 8.dp)
                    )

                    if (showLoadingState)
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularProgressIndicator(modifier = Modifier.padding(vertical = 12.dp))
                        }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                onSaveGradeClicked(
                                    childDataModel.id,
                                    totalGrades,
                                    mindAgeInMonths,
                                    intelligenceGrad,
                                    intelligenceValue,
                                    grades.map { it.toString() }
                                )
                            }, modifier = Modifier.padding(top = 32.dp, bottom = 64.dp),
                            enabled = if (
                                totalGrades.toString().isEmpty() ||
                                mindAgeInMonths.toString().isEmpty() ||
                                intelligenceGrad.toString().isEmpty() ||
                                intelligenceValue.isEmpty()
                                ) false else true
                        ) {
                            Text(text = "حفظ النتيجة")
                        }
                    }
                }
            }
        }
    }
}

fun getAgeInMonthsGivenYearsAndMonths(years: Int?, months: Int?): Int {
    return if (years != null && months != null)
        years * 12 + months
    else
        0
}

fun getChildIntelligenceValue(childIntelligenceGrade: Double): String {
    return when {
        (childIntelligenceGrade < 40.0) -> "إعاقة عقلية شديدة"
        (childIntelligenceGrade in 40.0..54.9) -> "إعاقة عقلية متوسطة"
        (childIntelligenceGrade in 55.0..70.9) -> "إعاقة عقلية بسيطة"
        (childIntelligenceGrade in 70.0..79.9) -> "علي حدود الضعف العقلي"
        (childIntelligenceGrade in 80.0..89.9) -> "أقل من المتوسط"
        (childIntelligenceGrade in 90.0..109.9) -> "متوسط"
        (childIntelligenceGrade in 110.0..119.9) -> "فوق المتوسط"
        (childIntelligenceGrade in 120.0..139.9) -> "ذكي جدآ"
        (childIntelligenceGrade >= 140.0) -> "عبقري"
        else -> {
            ""
        }
    }

}

fun getTimeAgeInMonths(totalGrade: Int): Pair<Int, Int>? {
    val ageMap = getGradeEquivalencyMap()
    return ageMap[totalGrade]
}

fun getAgeInMonths(yearOfBirth: String, monthOfBirth: String): Int {
    val currentDate = LocalDate.now()
    val birthDate = LocalDate.of(yearOfBirth.toInt(), monthOfBirth.toInt(), 1) // Assuming the day is the 1st of the month
    val period = Period.between(birthDate, currentDate)
    val years = period.years
    val months = period.months
    return years * 12 + months
}

@Composable
fun CriteriaItem(
    criteria: Criteria,
    grade: Int,
    onGradeChanged: (Int) -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp)
                .border(width = 1.dp, color = primary, shape = RoundedCornerShape(8.dp))

        ) {
            Text(
                text = criteria.criteriaName,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(0.7f)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    if (grade < 1) { // Modify the condition based on your criteria
                        onGradeChanged(grade + 1)
                    }
                }
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                }
                Text(
                    text = grade.toString(),
                    style = MaterialTheme.typography.titleMedium.copy(color = primary)
                )

                IconButton(onClick = {
                    if (grade > 0) {
                        onGradeChanged(grade - 1)
                    }
                },
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Icon(painterResource(id = R.drawable.ic_minus), contentDescription = "")
                }
            }
        }
    }
}


data class Criteria (
    val criteriaName:String,
    var grade:Int = 0
)
fun getCriteriaList(): List<Criteria> {
    return listOf(
        Criteria(criteriaName = "الراس"),
        Criteria(criteriaName = "الساقين"),
        Criteria(criteriaName = "الذرعاين"),
        Criteria(criteriaName = "وجود الجدع"),
        Criteria(criteriaName = "طول الجزع اطول من العرض"),
        Criteria(criteriaName = "الكتفين"),
        Criteria(criteriaName = "الذراعين و الساقين متصلين بالجزع"),
        Criteria(criteriaName = "الذراعين و الساقين متصلتين بالجزع و في مكانهما الصحيح"),
        Criteria(criteriaName = "الرقبة"),
        Criteria(criteriaName = "الرقبة متصلة بالراس"),
        Criteria(criteriaName = "العينان"),
        Criteria(criteriaName = "الانف"),
        Criteria(criteriaName = "الفم"),
        Criteria(criteriaName = "الانف و الفم من بعدين و الشفتان ظاهرتان"),
        Criteria(criteriaName = "وجود تجاويف الانف"),
        Criteria(criteriaName = "وجود الشعر"),
        Criteria(criteriaName = "الشعر بتفاصيل موجود على اكثر من جانب من جوانب الراس بطريقة منظمه"),
        Criteria(criteriaName = "الملابس"),
        Criteria(criteriaName = "قطعتان من الملابس غير شفافه"),
        Criteria(criteriaName = "عدم شفافية الملابس وجود اكمام او بنطلون"),
        Criteria(criteriaName = "اربع قطع من الملابس"),
        Criteria(criteriaName = "ملابس كاملة بدون تناقض الاصابع"),
        Criteria(criteriaName = "عدد الاصابع"),
        Criteria(criteriaName = "الاصابع من بعدين و طولهما اكبر من عرضها"),
        Criteria(criteriaName = "صحة رسم الابهام"),
        Criteria(criteriaName = "راحة اليد"),
        Criteria(criteriaName = "مفاصل الساقين - الركبة او الفخد او كلاهما"),
        Criteria(criteriaName = "تناسب الراس"),
        Criteria(criteriaName = "تناسب الذراعين"),
        Criteria(criteriaName = "تناسب الساقين"),
        Criteria(criteriaName = "تناسب القدمين"),
        Criteria(criteriaName = "الذراعين و الساقين من بعدين"),
        Criteria(criteriaName = "الكعب"),
        Criteria(criteriaName = "الخطوط واضحة و قوية"),
        Criteria(criteriaName = "الخطوط متصلة اتصالا صحيحا"),
        Criteria(criteriaName = "الراس بدون انتظام غير مقصود"),
        Criteria(criteriaName = "الجدع بدون انتظام غير مقصود"),
        Criteria(criteriaName = "الذراعين والساقين بدون انتظام غير مقصود"),
        Criteria(criteriaName = "تقاطيع الوجه متناسقة و من بعدين و الجانبان متشابهين"),
        Criteria(criteriaName = "الاذن"),
        Criteria(criteriaName = "تفاصيل الاذن و في مكانها الصحيح"),
        Criteria(criteriaName = "تفاصيل العين و الحاجب و الرموش"),
        Criteria(criteriaName = "انسان العين"),
        Criteria(criteriaName = "شكل العين و نسبتها و تناسقها"),
        Criteria(criteriaName = "في البروفيل العين تنظر الى الامام"),
        Criteria(criteriaName = "الذقن و الجبهة"),
        Criteria(criteriaName = "تفاصيل الذقن و الجبهة – الذقن بارزه"),
        Criteria(criteriaName = "بروفيل بخطا واحد"),
        Criteria(criteriaName = "بروفيل بدون اخطاء"),
    )
}

@Composable
fun ZoomableImage(
    model: String, // Replace with your DrawnImage model
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(vertical = 16.dp)
    ) {
        val state = rememberTransformableState { zoomChange, panChange, rotationChange ->
            scale = (scale * zoomChange).coerceIn(1f, 5f)

            val extraWidth = (scale - 1) * constraints.maxWidth
            val extraHeight = (scale - 1) * constraints.maxHeight

            val maxX = extraWidth / 2
            val maxY = extraHeight / 2

            offset = Offset(
                x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
            )
        }
        AsyncImage(
            model = model,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(state)
        )
    }
}
