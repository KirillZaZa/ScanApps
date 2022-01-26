package com.kizadev.domain.repository

import com.kizadev.domain.model.Apps
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getApps(): Flow<Apps>
}
