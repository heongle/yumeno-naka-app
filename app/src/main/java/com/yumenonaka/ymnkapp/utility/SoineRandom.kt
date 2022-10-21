package com.yumenonaka.ymnkapp.utility

import kotlin.random.Random

class SoineRandom {
    private var currentRandomIndex = -1
    private var sameIndexPickedCount = 0

    fun nextSoineIndex(): Int {
        var newRandomIndex = Random.nextInt(0, 2)
        for (i in 0..sameIndexPickedCount) {
            if(newRandomIndex != currentRandomIndex) {
                currentRandomIndex = newRandomIndex
                sameIndexPickedCount = 0
                return newRandomIndex
            }
            newRandomIndex = Random.nextInt(0, 2)
        }
        ++sameIndexPickedCount
        return currentRandomIndex
    }
}
