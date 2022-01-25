package com.kizadev.domain.repository

import com.kizadev.domain.model.PhoneApps
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getApps(): Flow<PhoneApps>
}
