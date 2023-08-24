package com.xplor.android.challenge.utils

fun Long.toImageUrlById(): String {

    // todo we need to concat  link with response with UI as we cant pass string value
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$this.png"
//    return ""
}

fun Long.toEntryNumber(): String {
    val number = this.toString()
    val zeroPrefix = when (number.length) {
        1 -> "00"
        2 -> "0"
        else -> ""
    }
    return "$zeroPrefix$number"
}
