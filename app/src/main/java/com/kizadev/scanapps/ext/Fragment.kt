package com.kizadev.scanapps.ext

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

fun <T> Fragment.isDestinationsTheSame(fragmentObj: Class<T>): Boolean {
    val controller = findNavController()
    return (controller.currentDestination as? FragmentNavigator.Destination)?.className == fragmentObj.name
}
