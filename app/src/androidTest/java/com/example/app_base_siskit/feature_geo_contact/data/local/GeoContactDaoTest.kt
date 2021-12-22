package com.example.app_base_siskit.feature_geo_contact.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.example.app_base_siskit.feature_geo_contact.data.local.Dao.GeoContactDao
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoContactEntity
import com.google.common.truth.Truth.assertThat

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class GeoContactDaoTest {



    @get:Rule
    var instantiationException = InstantTaskExecutorRule()


    lateinit var database: GeoDatabase
    private lateinit var  contactDao: GeoContactDao



    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GeoDatabase::class.java
        ).allowMainThreadQueries().build()
        contactDao = database.geoContactDao()
    }



    @Test
    fun insertContact() = runBlockingTest {
        val contact = GeoContactEntity(
            1,
            "",
            15,
            15,
            15,
            21,
            "", "",
            15,
            15,
            1,
            1,
            1,
            1.2,
            2.3,
            "",
            "",
            "",
            154,
            1,
            "654654",
            2,
            "asuidhasd",
            1.2,
            5
        )
        contactDao.insertGeoContact(contact)

        val allContact = contactDao.getAllGeoContactOrderByDesc().getOrAwaitValue()

        assertThat(allContact).contains(contact)

    }

    @Test
    fun deleteContact() = runBlockingTest {
        val contact = GeoContactEntity(
            1,
            "",
            15,
            15,
            15,
            21,
            "", "",
            15,
            15,
            1,
            1,
            1,
            1.2,
            2.3,
            "",
            "",
            "",
            154,
            1,
            "654654",
            2,
            "asuidhasd",
            1.2,
            5
        )
        contactDao.insertGeoContact(contact)
        contactDao.deleteGeoContact(contact)

        val allContactDao = contactDao.getAllGeoContactOrderByDesc().getOrAwaitValue()

        assertThat(allContactDao).doesNotContain(contact)
    }

    @Test
    fun updateContact() = runBlockingTest {
        val contact = GeoContactEntity(
            1,
            "",
            15,
            15,
            15,
            21,
            "", "",
            15,
            15,
            1,
            1,
            1,
            1.2,
            2.3,
            "",
            "",
            "",
            154,
            1,
            "654654",
            2,
            "asuidhasd",
            1.2,
            5
        )
        contactDao.insertGeoContact(contact)

        val contact2 = GeoContactEntity(
            1,
            "",
            15,
            15,
            15,
            21,
            "", "",
            15,
            15,
            1,
            1,
            1,
            1.2,
            2.3,
            "",
            "",
            "",
            154,
            1,
            "654654553333",
            2,
            "asuidhasd",
            1.2,
            5
        )
        contactDao.updateGeoContact(contact2)

        val allContactDao = contactDao.getAllGeoContactOrderByDesc().getOrAwaitValue()
        assertThat(allContactDao).contains(contact2).equals(contact.TELEFONO != contact2.TELEFONO)
    }

    @Test
    fun geoContactId() = runBlockingTest {
        val contact = GeoContactEntity(
            1,
            "",
            15,
            15,
            15,
            21,
            "", "",
            15,
            15,
            1,
            1,
            1,
            1.2,
            2.3,
            "",
            "",
            "",
            154,
            1,
            "654654",
            2,
            "asuidhasd",
            1.2,
            5
        )
        contactDao.insertGeoContact(contact)
        val id = contactDao.getGeoContactID(1)

        val allContactDao = contactDao.getAllGeoContactOrderByDesc().getOrAwaitValue()


        assertThat(allContactDao).contains(id)
    }
    @After
    fun teardown(){
        database.close()
    }


}