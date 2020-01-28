package com.hallel.domain.update

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

interface UpdateUseCase {

    fun onSearchForAppVersion(): Int

    @FlowPreview
    fun onSearchForContentUpdates(): Flow<Pair<Int, Int>>
}