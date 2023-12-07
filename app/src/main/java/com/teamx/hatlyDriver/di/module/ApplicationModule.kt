package com.teamx.hatlyDriver.di.module

import android.content.Context
import com.teamx.hatlyDriver.constants.AppConstants
import com.teamx.hatlyDriver.data.local.datastore.DataStoreProvider
import com.teamx.hatlyDriver.data.local.db.AppDatabase
import com.teamx.hatlyDriver.data.remote.ApiService
import com.teamx.hatlyDriver.data.remote.reporitory.MainRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.teamx.hatlyDriver.data.local.db.AppDao
import com.teamx.hatlyDriver.data.local.dbModel.CartDao
import com.teamx.hatlyDriver.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .callTimeout(10, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.ApiConfiguration.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext appContext: Context) = DataStoreProvider(appContext)

    @Singleton
    @Provides
    fun provideDao(db: AppDatabase) = db.appDao()

    @Singleton
    @Provides
    fun provideCartDao(db: AppDatabase) = db.cartDao()

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService,
                          localDataSource: AppDao,
                          localDataSource2: CartDao,
    ) = MainRepository(
        apiService,
        localDataSource,
        localDataSource2)

}