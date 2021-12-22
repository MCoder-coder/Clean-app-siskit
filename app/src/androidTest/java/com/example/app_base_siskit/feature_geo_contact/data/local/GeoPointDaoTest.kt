package com.example.app_base_siskit.feature_geo_contact.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.example.app_base_siskit.feature_geo_contact.data.local.Dao.GeoContactDao
import com.example.app_base_siskit.feature_geo_contact.data.local.Dao.GeoPointDao
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoPointEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class GeoPointDaoTest {


    @get:Rule
    var instantiationException = InstantTaskExecutorRule()


    lateinit var database: GeoDatabase
    private lateinit var  pointDao: GeoPointDao



    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GeoDatabase::class.java
        ).allowMainThreadQueries().build()
        pointDao = database.geoPointDao()
    }

    val point = GeoPointEntity(1 , 1.5 , 15 , "sad" , 1 , 1 , 1 , 1 , 1.54 , 1.2 , "151" , 1 , 1.5 , 1)

    @Test
    fun insertPoint() = runBlockingTest {


        pointDao.insertGeoPoint(point)

        val getAllPoint = pointDao.getAllGeoPointOrderByDesc().getOrAwaitValue()

        assertThat(getAllPoint).contains(point)

    }

    @After
    fun teardown(){
        database.close()
    }
}