package com.seif.howtodrawpersontestapp.util

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.ByteArrayOutputStream

fun ImageBitmap.toByteArray(): ByteArray {
    val bitmap = this.asAndroidBitmap().copy(Bitmap.Config.ARGB_8888, false)
    val output = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
    return output.toByteArray()
}

fun getGradeEquivalencyMap(): Map<Int, Pair<Int, Int>> {
    return mapOf(
        1 to Pair(3, 3),
        2 to Pair(3, 6),
        3 to Pair(3, 9),
        4 to Pair(4, 0),
        5 to Pair(4, 3),
        6 to Pair(4, 6),
        7 to Pair(4, 9),
        8 to Pair(5, 0),
        9 to Pair(5, 3),
        10 to Pair(5, 6),
        11 to Pair(5, 9),
        12 to Pair(6, 0),
        13 to Pair(6, 3),
        14 to Pair(6, 6),
        15 to Pair(6, 9),
        16 to Pair(7, 0),
        17 to Pair(7, 3),
        18 to Pair(7, 6),
        19 to Pair(7, 9),
        20 to Pair(8, 0),
        21 to Pair(8, 3),
        22 to Pair(8, 6),
        23 to Pair(8, 9),
        24 to Pair(9, 0),
        25 to Pair(9, 3),
        26 to Pair(9, 6),
        27 to Pair(9, 9),
        28 to Pair(10, 0),
        29 to Pair(10, 3),
        30 to Pair(10, 6),
        31 to Pair(10, 9),
        32 to Pair(11, 0),
        33 to Pair(11, 3),
        34 to Pair(11, 6),
        35 to Pair(11, 9),
        36 to Pair(12, 0),
        37 to Pair(12, 3),
        38 to Pair(12, 6),
        39 to Pair(12, 9),
        40 to Pair(13, 0),
        41 to Pair(13, 0),
        43 to Pair(13, 0),
        44 to Pair(13, 0),
        45 to Pair(13, 0),
        46 to Pair(13, 0),
        47 to Pair(13, 0),
        48 to Pair(13, 0),
        49 to Pair(13, 0),
        50 to Pair(13, 0),
    )
}