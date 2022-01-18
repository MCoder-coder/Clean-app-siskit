package com.example.app_base_siskit.feature_geo_contact.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.example.app_base_siskit.common.GeoDatabase
import com.example.app_base_siskit.feature_geo_point.data.local.dao.GeoPointDao
import com.example.app_base_siskit.feature_geo_point.data.local.entity.GeoPointEntity
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
        val getAllPoint = pointDao.getAllGeoPointOrderByDesc().getOrAwaitValue()
        pointDao.insertGeoPoint(point)
        assertThat(getAllPoint).contains(point)

    }

    @Test
    fun deletePoint() = runBlockingTest {
        val getAllPoint = pointDao.getAllGeoPointOrderByDesc().getOrAwaitValue()
        pointDao.deleteGeoPoint(point)
        assertThat(getAllPoint).doesNotContain(point)
    }

    @Test
    fun updatePoint() = runBlockingTest {
        val getAllPoint = pointDao.getAllGeoPointOrderByDesc().getOrAwaitValue()
        var latitude = point.LATTITUDE
        latitude = 2.3
        pointDao.updateGeoPoint(point)
        assertThat(getAllPoint).isNotEqualTo(point.LATTITUDE != latitude)
    }

    @Test
    fun getPoinID() = runBlockingTest {
        pointDao.insertGeoPoint(point)
        val getAllPoint = pointDao.getAllGeoPointOrderByDesc().getOrAwaitValue()
        val id = pointDao.getGeoPointID(1)
        assertThat(getAllPoint).contains(id)
    }

    @Test
    fun getAllPoint() = runBlockingTest {
        pointDao.insertGeoPoint(point)
        val getAllPoint = pointDao.getAllPoint()
        assertThat(getAllPoint.contains(point)).isTrue()

    }



    @After
    fun teardown(){
        database.close()
    }
}