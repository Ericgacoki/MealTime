/*
 * Copyright 2022 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kanyideveloper.core_database.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.kanyideveloper.core.util.Constants
import com.kanyideveloper.core_database.DatabaseMigrations.migration_1_3
import com.kanyideveloper.core_database.MealTimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTypeConverters(gson: Gson) =
        com.kanyideveloper.core_database.converters.Converters(gson)

    @Provides
    @Singleton
    fun provideMealTimeDatabase(
        @ApplicationContext context: Context,
        converters: com.kanyideveloper.core_database.converters.Converters
    ): MealTimeDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MealTimeDatabase::class.java,
            Constants.MEALTIME_DATABASE
        )
            .addMigrations(migration_1_3)
            .fallbackToDestructiveMigration()
            .addTypeConverter(converters)
            .build()
    }

    @Provides
    @Singleton
    fun provideMealDao(database: MealTimeDatabase) = database.mealDao

    @Provides
    @Singleton
    fun providesFavoritesDao(database: MealTimeDatabase) = database.favoritesDao

    @Provides
    @Singleton
    fun providesMealPlanDao(database: MealTimeDatabase) = database.mealPlanDao
}
