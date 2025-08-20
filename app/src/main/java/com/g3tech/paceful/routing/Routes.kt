package com.g3tech.paceful.routing

import kotlinx.serialization.Serializable

sealed interface Routes {
    data object PopBack : Routes

    @Serializable
    data object HomeScreenRoute : Routes
}