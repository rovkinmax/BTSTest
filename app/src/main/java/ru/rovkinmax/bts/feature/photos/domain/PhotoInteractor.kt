package ru.rovkinmax.bts.feature.photos.domain

import io.reactivex.Single
import ru.rovkinmax.bts.feature.photos.data.PhotoRepository
import ru.rovkinmax.bts.model.system.rx.SchedulersProvider
import javax.inject.Inject

class PhotoInteractor @Inject constructor(private val repository: PhotoRepository, private val schedulersProvider: SchedulersProvider) {


    fun search(query: String, count: Int, offset: Int): Single<List<PhotoEntity>> {
        val page = if (offset == 0) 0 else Math.ceil(offset.toDouble() / count.toDouble()).toInt()
        return repository.search(query, count, page)
                .flatMap { photoList -> repository.addSearchQueryToSuggestion(query).andThen(Single.just(photoList)) }
    }

    fun getSuggestionsForQuery(query: String): Single<List<String>> {
        return if (query.isEmpty()) Single.just(emptyList())
        else repository.getSuggestions()
                .map { set -> filterSuggestions(set, query) }
                .map { list -> addQueryIfNotContains(list, query) }
                .subscribeOn(schedulersProvider.computation())
    }

    private fun filterSuggestions(set: MutableSet<String>, query: String): List<String> {
        return set.reversed().filter { it.startsWith(query) }
                .plus(set.filter { it.contains(query) && it.startsWith(query).not() })
    }

    private fun addQueryIfNotContains(list: List<String>, query: String): List<String> {
        val mutableList = list.toMutableList()
        if (mutableList.contains(query).not()) mutableList.add(0, query)
        return mutableList
    }
}