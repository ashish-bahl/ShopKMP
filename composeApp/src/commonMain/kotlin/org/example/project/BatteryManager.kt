@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.example.project

expect class BatteryManager {
    fun getBatteryLevel(): Int
}