package com.hallel.domain.update

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@FlowPreview
class UpdateUseCaseImpl: UpdateUseCase {

    override fun onSearchForAppVersion(): Int {
        return 0
    }

    override fun onSearchForContentUpdates(dispatcher: CoroutineDispatcher): Flow<Pair<Int, Int>> {
        return flow {
            var count = 0
            val total = 10
            while (count < total) {
                delay(500)
                count += 2
                emit(Pair(count, total))
            }
        }.flowOn(dispatcher)
    }
}