package dev.best.covidkotlin.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.best.covidkotlin.data.constants.Constants
import dev.best.covidkotlin.data.local.dao.CovidDao
import dev.best.covidkotlin.data.local.db.AppDatabase
import dev.best.covidkotlin.data.local.pref.AppPrefs
import dev.best.covidkotlin.data.local.pref.PrefHelper
import dev.best.covidkotlin.data.remote.ApiService
import dev.best.covidkotlin.data.repository.UserRepository
import dev.best.covidkotlin.data.repository.impl.UserRepositoryImpl
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()


    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: ApiService,
        covidDao: CovidDao
    ): UserRepository = UserRepositoryImpl(apiService, covidDao)


    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideCountryDao(appDatabase: AppDatabase): CovidDao = appDatabase.covidDao()

    @Singleton
    @Provides
    fun providePrefHelper(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ) : PrefHelper = AppPrefs(sharedPreferences, gson)


    @Singleton
    @Provides
    fun provideSharedPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)



}