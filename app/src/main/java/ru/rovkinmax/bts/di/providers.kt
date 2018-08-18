package ru.rovkinmax.bts.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.rovkinmax.bts.BuildConfig
import ru.rovkinmax.bts.feature.photos.data.Api
import ru.rovkinmax.bts.model.system.flow.FlowRouter
import ru.rovkinmax.bts.model.system.flow.GlobalRouter
import ru.rovkinmax.bts.model.system.network.CurlLoggingInterceptor
import ru.rovkinmax.bts.model.system.network.RxErrorHandlingCallAdapterFactory
import ru.rovkinmax.bts.model.system.rx.SchedulersProvider
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class LocalRouterProvider @Inject constructor(private val router: GlobalRouter) : Provider<FlowRouter> {
    override fun get(): FlowRouter = FlowRouter(router)
}

class LocalCiceroneProvider @Inject constructor(private val router: FlowRouter) : Provider<Cicerone<FlowRouter>> {
    override fun get(): Cicerone<FlowRouter> = Cicerone.create(router)
}

class LocalNavigatorProvider @Inject constructor(private val cicerone: Cicerone<FlowRouter>) : Provider<NavigatorHolder> {
    override fun get(): NavigatorHolder = cicerone.navigatorHolder
}

class OkHttpProvider @Inject constructor() : Provider<OkHttpClient> {

    companion object {
        private const val TIMEOUT_CONNECTION = 30L
        private const val TIMEOUT_READ = 30L
        private const val TIMEOUT_WRITE = 30L
    }

    override fun get(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
                .addInterceptor(CurlLoggingInterceptor())
                .build()
    }
}

class RetrofitProvider @Inject constructor(private val okHttpClient: OkHttpClient,
                                           private val gson: Gson,
                                           private val schedulersProvider: SchedulersProvider) : Provider<Retrofit> {
    override fun get(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.ENDPOINT)
                .client(okHttpClient)
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create(schedulersProvider.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
}

class ApiProvider @Inject constructor(private val retrofit: Retrofit) : Provider<Api> {
    override fun get(): Api {
        return retrofit.create(Api::class.java)
    }
}

