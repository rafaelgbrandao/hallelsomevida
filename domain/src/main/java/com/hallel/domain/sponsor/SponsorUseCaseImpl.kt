package com.hallel.domain.sponsor

import com.hallel.data.sponsor.Sponsor
import com.hallel.data.sponsor.SponsorDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SponsorUseCaseImpl(private val sponsorDao: SponsorDao): SponsorUseCase {

    override fun getSponsorsByEventId(eventId: Int, dispatcher: CoroutineDispatcher): Flow<List<SponsorVO>?> {
        return flow {
            val sponsors = sponsorDao.getSponsorByEventId(eventId)
            emit(transformSponsorIntoSponsorVO(sponsors))
        }.flowOn(dispatcher)
    }

    internal fun transformSponsorIntoSponsorVO(sponsor: List<Sponsor>?): List<SponsorVO>? {
        return sponsor?.map {
            SponsorVO(
                id = it.id,
                name = it.name,
                logo = it.logo,
                link =  it.link
            )
        }
    }
}