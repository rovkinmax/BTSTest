package ru.rovkinmax.bts.feature.photos.domain

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.rovkinmax.bts.feature.photos.data.PhotoRepository

@RunWith(MockitoJUnitRunner::class)
class PhotoInteractorTest {

    @Mock
    lateinit var repository: PhotoRepository

    private lateinit var interactor: PhotoInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        interactor = spy(PhotoInteractor(repository))
    }

    @Test
    fun testCorrectPageNumber() {
        whenever(repository.search(any(), any(), any())).doReturn(Single.just(emptyList()))


        //PAGE 0
        var observer = interactor.search("", 30, 0).test()
        verify(repository).search("", 30, 0)
        observer.awaitTerminalEvent()
        observer.assertNoErrors()
        observer.assertValueCount(1)


        //PAGE 1
        clearInvocations(repository)
        observer = interactor.search("", 30, 1).test()

        verify(repository).search("", 30, 1)
        observer.awaitTerminalEvent()
        observer.assertNoErrors()
        observer.assertValueCount(1)


        clearInvocations(repository)
        observer = interactor.search("", 30, 29).test()

        verify(repository).search("", 30, 1)
        observer.awaitTerminalEvent()
        observer.assertNoErrors()
        observer.assertValueCount(1)

        clearInvocations(repository)
        observer = interactor.search("", 30, 30).test()

        verify(repository).search("", 30, 1)
        observer.awaitTerminalEvent()
        observer.assertNoErrors()
        observer.assertValueCount(1)


        //PAGE 2
        clearInvocations(repository)
        observer = interactor.search("", 30, 31).test()

        verify(repository).search("", 30, 2)
        observer.awaitTerminalEvent()
        observer.assertNoErrors()
        observer.assertValueCount(1)


        clearInvocations(repository)
        observer = interactor.search("", 30, 59).test()

        verify(repository).search("", 30, 2)
        observer.awaitTerminalEvent()
        observer.assertNoErrors()
        observer.assertValueCount(1)


        clearInvocations(repository)
        observer = interactor.search("", 30, 60).test()

        verify(repository).search("", 30, 2)
        observer.awaitTerminalEvent()
        observer.assertNoErrors()
        observer.assertValueCount(1)


        //PAGE 3
        clearInvocations(repository)
        observer = interactor.search("", 30, 61).test()

        verify(repository).search("", 30, 3)
        observer.awaitTerminalEvent()
        observer.assertNoErrors()
        observer.assertValueCount(1)
    }
}