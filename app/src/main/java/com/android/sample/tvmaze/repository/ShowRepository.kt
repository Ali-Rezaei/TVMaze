package com.android.sample.tvmaze.repository

import android.content.Context
import com.android.sample.tvmaze.base.BaseListRepository
import com.android.sample.tvmaze.database.ShowDao
import com.android.sample.tvmaze.database.asDomainModel
import com.android.sample.tvmaze.domain.Show
import com.android.sample.tvmaze.domain.asDatabaseModel
import com.android.sample.tvmaze.network.TVMazeService
import com.android.sample.tvmaze.util.contextProvider.CoroutineContextProvider

class ShowRepository(
        private val dao: ShowDao,
        private val api: TVMazeService,
        context: Context,
        contextProvider: CoroutineContextProvider
) : BaseListRepository<Show>(context, contextProvider) {

    override suspend fun query(): List<Show> = dao.getShows().asDomainModel()

    override suspend fun fetch(): List<Show> = api.fetchShowList()

    override suspend fun saveFetchResult(items: List<Show>) {
        dao.insertAll(*items.asDatabaseModel())
    }
}