package com.kizadev.domain.usecase

import com.kizadev.domain.model.PhoneApps
import com.kizadev.domain.repository.AppRepository
import com.kizadev.domain.wrapper.AppScanResult
import com.kizadev.domain.wrapper.Error
import com.kizadev.domain.wrapper.Failure
import com.kizadev.domain.wrapper.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.lang.Exception
import java.lang.NullPointerException
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(
    private val repository: AppRepository
) {

    fun execute(): Flow<AppScanResult<PhoneApps, Exception>> {
        return repository.getApps()
            .map { phoneApps ->
                if (phoneApps.appsList.isEmpty()) {
                    Success(phoneApps)
                } else Failure(NullPointerException("Apps not found"))
            }
            .catch { exception ->
                emit(Error(exception.localizedMessage))
            }.flowOn(Dispatchers.IO)
    }
}
