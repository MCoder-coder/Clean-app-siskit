package com.example.app_base_siskit.feature_geo_contact.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.example.app_base_siskit.feature_geo_contact.data.local.Dao.GeoPointDao
import com.example.app_base_siskit.feature_geo_contact.data.local.Dao.GeoUserDao
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoUserEntity
import com.google.common.truth.Truth
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
class GeoUserDaoTest {

    @get:Rule
    var instantiationException = InstantTaskExecutorRule()


    lateinit var database: GeoDatabase
    private lateinit var  userDao: GeoUserDao



    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GeoDatabase::class.java
        ).allowMainThreadQueries().build()
        userDao = database.getUserDao()
    }

    val user = GeoUserEntity(1 , "sadkjk" , 0 , "sadasd" , 1 , 2 , "asdasd")

    @Test
    fun insertUser() = runBlockingTest {
        userDao.insertGeoUser(user)
        val deleteUser = userDao.getAllGeoUserOrderByDesc().getOrAwaitValue()
        Truth.assertThat(deleteUser).contains(user)

    }

    @Test
    fun deleteUser() = runBlockingTest {
        val getAllUser = userDao.getAllGeoUserOrderByDesc().getOrAwaitValue()
        userDao.deleteGeoUser(user)
        Truth.assertThat(getAllUser).doesNotContain(user)
    }

    @Test
    fun updateUser() = runBlockingTest {
        val getAllUser = userDao.getAllGeoUserOrderByDesc().getOrAwaitValue()
        var name = user.NAME
        name = "zzzzz"
        userDao.updateGeoUser(user)
        Truth.assertThat(getAllUser).isNotEqualTo(user.NAME != name)
    }

    @Test
    fun getUserID() = runBlockingTest {
        userDao.insertGeoUser(user)
        val getAllUser = userDao.getAllGeoUserOrderByDesc().getOrAwaitValue()
        val id = userDao.getGeoUserID(1)
        Truth.assertThat(getAllUser).contains(id)
    }

    @Test
    fun getAllUser() = runBlockingTest {
        userDao.insertGeoUser(user)
        val getAllPoint = userDao.getAllUser()
        Truth.assertThat(getAllPoint.contains(user)).isTrue()

    }



    @After
    fun teardown(){
        database.close()
    }
}