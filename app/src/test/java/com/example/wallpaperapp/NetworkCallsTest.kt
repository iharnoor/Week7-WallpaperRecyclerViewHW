package com.example.wallpaperapp

import com.example.wallpaperapp.Utils.Resource
import com.example.wallpaperapp.data.api.PicSumApi
import com.example.wallpaperapp.data.api.WallpaperRepostiryImpl
import com.example.wallpaperapp.data.api.model.PicSumItem
import com.example.wallpaperapp.domain.entity.WallpaperLink
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class DataRepositoryTest {

    @Mock
    private lateinit var mockApiService: PicSumApi

    @Mock
    private lateinit var employeeMock: Employee

    private lateinit var dataRepository: WallpaperRepostiryImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        dataRepository = WallpaperRepostiryImpl(mockApiService)
    }


    @Test
    fun test1() { // PowerMock
        var employeeMock = mock(Employee::class.java)
        println("Employee name= "+ employeeMock.name)
        println("Employee id= "+ employeeMock.id)
    }

    @Test
    fun testGetImagesSuccess() = runBlocking {

        val mockWallpaperList = listOf(mock(WallpaperLink::class.java))/* initialize with test data */
        val mockListPicSum = listOf(mock(PicSumItem::class.java))
//
//
        `when`(mockApiService.getWallpaperImages()).thenReturn(mockListPicSum)
//
//        // Actual
        val result = dataRepository.getImages().collect()
//        // Assert
        assertEquals(Resource.Success(mockWallpaperList), result)
    }


    @Test
    fun testGetImagesFailed() = runBlocking {
        `when`(mockApiService.getWallpaperImages()).thenReturn(Exception("test"))
//        // Actual
        val result = dataRepository.getImages().collect()
//        // Assert
        assertEquals(Resource.Error(null, "Test message"), result)
    }
}

open class Employee(val name: String, val id:Int) {

}