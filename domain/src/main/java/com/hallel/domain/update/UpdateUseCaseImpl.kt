package com.hallel.domain.update

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@FlowPreview
class UpdateUseCaseImpl: UpdateUseCase {

    override fun onSearchForAppVersion(): Int {
        return 0
    }

    override fun onSearchForContentUpdates(): Flow<Pair<Int, Int>> {
        return flow {
            emit(Pair(0,0))
        }.flowOn(Dispatchers.IO)
    }
}