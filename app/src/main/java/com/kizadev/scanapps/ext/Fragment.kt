package com.kizadev.scanapps.ext

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

inline fun <reified T> Fragment.isDestinationsTheSame(): Boolean {
    val controller = findNavController()
    val destinationName =
        (controller.currentDestination as? FragmentNavigator.Destination)?.className
    return destinationName == T::class.java.name
}
