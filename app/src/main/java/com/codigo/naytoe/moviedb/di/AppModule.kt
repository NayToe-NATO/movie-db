package com.codigo.naytoe.moviedb.di

import android.content.Context
import com.codigo.naytoe.moviedb.data.local.AppDatabase
import com.codigo.naytoe.moviedb.data.local.MovieDao
import com.codigo.naytoe.moviedb.data.remote.MovieRemoteDataSource
import com.codigo.naytoe.moviedb.data.remote.MovieService
import com.codigo.naytoe.moviedb.data.repository.MovieRepository
import com.codigo.naytoe.moviedb.util.AppConst
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(AppConst.BASEURL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(movieService: MovieService) = MovieRemoteDataSource(movieService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: MovieRemoteDataSource,
                          localDataSource: MovieDao
    ) =
        MovieRepository(remoteDataSource, localDataSource)
}