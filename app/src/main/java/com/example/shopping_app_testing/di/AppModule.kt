package com.example.shopping_app_testing.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shopping_app_testing.R
import com.example.shopping_app_testing.data.local.ShoppingDao
import com.example.shopping_app_testing.data.local.ShoppingItemDatabase
import com.example.shopping_app_testing.data.remote.PixabayAPI
import com.example.shopping_app_testing.repositories.DefaultShoppingRepository
import com.example.shopping_app_testing.repositories.ShoppingRepository
import com.example.shopping_app_testing.utils.Constants.BASE_URL
import com.example.shopping_app_testing.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context:Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao:ShoppingDao,
        api: PixabayAPI
    ):ShoppingRepository{
        return DefaultShoppingRepository(dao, api)
    }

    @Singleton
    @Provides
    fun providesGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
        )

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()


    @Singleton
    @Provides
    fun providePixabayAPI():PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
}