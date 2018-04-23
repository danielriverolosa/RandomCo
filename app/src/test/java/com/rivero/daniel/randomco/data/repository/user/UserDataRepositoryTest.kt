package com.rivero.daniel.randomco.data.repository.user

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.rivero.daniel.randomco.data.datasource.api.ApiClientGenerator
import com.rivero.daniel.randomco.data.datasource.api.user.UserApi
import com.rivero.daniel.randomco.data.datasource.api.user.model.LocationResponse
import com.rivero.daniel.randomco.data.datasource.api.user.model.LoginResponse
import com.rivero.daniel.randomco.data.datasource.api.user.model.NameResponse
import com.rivero.daniel.randomco.data.datasource.api.user.model.PictureResponse
import com.rivero.daniel.randomco.data.datasource.api.user.model.UserListResponse
import com.rivero.daniel.randomco.data.datasource.api.user.model.UserResponse
import com.rivero.daniel.randomco.data.datasource.local.user.dao.UserDao
import com.rivero.daniel.randomco.data.service.CacheService
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.test.help.CallFake
import com.rivero.daniel.randomco.test.help.mockUser
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasProperty
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class UserDataRepositoryTest {

    @InjectMocks
    private lateinit var repository: UserDataRepository

    @Mock
    private lateinit var clientGenerator: ApiClientGenerator
    @Mock
    private lateinit var userDao: UserDao
    @Mock
    private lateinit var cacheService: CacheService<User>
    @Mock
    private lateinit var userApi: UserApi

    @Before
    @Throws(Exception::class)
    fun setUp() {
        `when`(clientGenerator.generateApi(UserApi::class.java)).thenReturn(userApi)
    }

    @Test
    @Throws(Exception::class)
    fun getUserList_whenCacheAndDatabaseIsEmpty_shouldCallToApi() {
        `when`(cacheService.hasData()).thenReturn(false)
        `when`(userDao.getUsers()).thenReturn(emptyList())
        `when`(userApi.getUsers(any()))
                .thenReturn(CallFake.buildSuccess(buildUserListResponse()))

        val userList = repository.getUserList()

        verify(userApi).getUsers()
        verify(userDao).deleteUsers()
        verify(userDao).insertUser(any())
        verify(cacheService).clear()
        verify(cacheService).put(any(), any())

        assertThat(userList, notNullValue())
        assertThat(userList.size, `is`(1))
        assertThat(userList, hasItem(hasProperty("gender", `is`("gender"))))
        assertThat(userList, hasItem(hasProperty("email", `is`("email"))))
        assertThat(userList, hasItem(hasProperty("phone", `is`("phone"))))
    }

    @Test
    @Throws(Exception::class)
    fun getUserList_whenCacheHasData_shouldReturnImmediately() {
        `when`(cacheService.hasData()).thenReturn(true)
        `when`(cacheService.getAllAsList()).thenReturn(listOf(mockUser()))

        val userList = repository.getUserList()

        verify(userApi, never()).getUsers()
        verify(userDao, never()).deleteUsers()
        verify(userDao, never()).insertUser(any())
        verify(userDao, never()).getUsers()
        verify(cacheService, never()).clear()
        verify(cacheService, never()).put(any(), any())

        assertThat(userList, notNullValue())
        assertThat(userList.size, `is`(1))
        assertThat(userList, hasItem(hasProperty("gender", `is`("gender"))))
        assertThat(userList, hasItem(hasProperty("email", `is`("email"))))
        assertThat(userList, hasItem(hasProperty("phone", `is`("phone"))))
    }

    @Test
    @Throws(Exception::class)
    fun getUserList_whenDatabaseHasDataAndCacheIsEmpty_shouldUpdateCache() {
        `when`(cacheService.hasData()).thenReturn(false)
        `when`(userDao.getUsers()).thenReturn(listOf(mockUser().map()))

        val userList = repository.getUserList()

        verify(userApi, never()).getUsers()
        verify(userDao, never()).deleteUsers()
        verify(userDao, never()).insertUser(any())

        verify(userDao).getUsers()
        verify(cacheService).clear()
        verify(cacheService).put(any(), any())

        assertThat(userList, notNullValue())
        assertThat(userList.size, `is`(1))
        assertThat(userList, hasItem(hasProperty("gender", `is`("gender"))))
        assertThat(userList, hasItem(hasProperty("email", `is`("email"))))
        assertThat(userList, hasItem(hasProperty("phone", `is`("phone"))))
    }

    private fun buildUserListResponse(): UserListResponse {
        return UserListResponse(
                listOf(buildUserResponse())
        )
    }

    private fun buildUserResponse(): UserResponse {
        return UserResponse(
                "gender",
                buildNameResponse(),
                buildLocationResponse(),
                "email",
                buildLoginResponse(),
                "dob",
                Date(),
                "phone",
                "cell",
                buildPictureResponse(),
                "nat"
        )
    }

    private fun buildNameResponse() = NameResponse("title", "first", "last")

    private fun buildLocationResponse() = LocationResponse("street", "city", "state", "postalcode")

    private fun buildLoginResponse() = LoginResponse("username", "password", null, null, null, null)

    private fun buildPictureResponse() = PictureResponse("large", "mediuem", "thumbnail")

}