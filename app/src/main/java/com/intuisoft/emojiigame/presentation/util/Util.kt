package com.intuisoft.emojiigame.presentation.util

import java.util.*

fun <T> ArrayList<T>.customShuffle(): ArrayList<T> {
    val rng = Random()

    if(size > 0) {
        for (index in 1..this.size - 1) {
            val randomIndex = rng.nextInt(index)

            // Swap with the random position
            val temp = this[index]
            this[index] = this[randomIndex]
            this[randomIndex] = temp
        }
    }

    return this
}

fun computeDistance(s1: String, s2: String): Int {
    var s1 = s1
    var s2 = s2
    s1 = s1.toLowerCase()
    s2 = s2.toLowerCase()
    val costs = IntArray(s2.length + 1)
    for (i in 0..s1.length) {
        var lastValue = i
        for (j in 0..s2.length) {
            if (i == 0) costs[j] = j else {
                if (j > 0) {
                    var newValue = costs[j - 1]
                    if (s1[i - 1] != s2[j - 1]) newValue = Math.min(
                        Math.min(newValue, lastValue),
                        costs[j]
                    ) + 1
                    costs[j - 1] = lastValue
                    lastValue = newValue
                }
            }
        }
        if (i > 0) costs[s2.length] = lastValue
    }
    return costs[s2.length]
}

fun getWordAccuracyPercent(str1: String, str2: String): Int {
    val longestWord = if (str1.length > str2.length) str1.length.toDouble() else str2.length.toDouble()
    val dst = computeDistance(str1, str2).toDouble()

    return (100 - ((dst / longestWord) * 100)).toInt()
}