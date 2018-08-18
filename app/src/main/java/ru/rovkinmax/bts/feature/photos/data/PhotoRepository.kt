package ru.rovkinmax.bts.feature.photos.data

import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import ru.rovkinmax.bts.feature.photos.domain.PhotoEntity
import ru.rovkinmax.bts.model.system.network.gson.fromJson
import ru.rovkinmax.bts.model.system.prefs.Preferences
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val api: Api, private val pref: Preferences, private val gson: Gson) {

    fun search(query: String, count: Int, page: Int): Single<List<PhotoEntity>> {
        return api.search(query, count, page)
                .map { it.photos.photo }
                .map { it.map { it.toEntity() } }
    }

    fun addSearchQueryToSuggestion(query: String): Completable {
        return getSuggestions()
                .map { it.add(query);it }
                .flatMapCompletable(this::saveSuggestions)
    }

    private fun saveSuggestions(suggestionSet: MutableSet<String>): Completable {
        //хранение намеренно было сделано в преференсах,чтобы не тянуть базу данных для этого,
        // но в то же времяпоказать пример suggestions, с текущей рахитектурой мето хранения с легкостью меняется
        // на лубое более примелемое хранилище
        return Completable.create { emitter ->
            val json = gson.toJson(suggestionSet.toMutableList())
            pref.suggestions = json
            emitter.onComplete()
        }
    }

    fun getSuggestions(): Single<MutableSet<String>> {
        return Single.create { emitter ->
            val json = pref.suggestions
            val list = gson.fromJson<List<String>>(json)
            emitter.onSuccess(LinkedHashSet(list))
        }
    }
}