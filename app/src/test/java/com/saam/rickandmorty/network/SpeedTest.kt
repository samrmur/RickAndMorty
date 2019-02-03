package com.saam.rickandmorty.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.saam.rickandmorty.BuildConfig
import com.saam.rickandmorty.retrofit.TestService
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * This test is a simple network test. It was created while being aware that there are a lot of
 * variables when it comes to network traffic which is why it should be performed multiple times
 * @author Samer Alabi
 */
class SpeedTest {
    private lateinit var retrofit: Retrofit
    private lateinit var service: TestService

    @Before
    fun setUp() {
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BaseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        service = retrofit.create(TestService::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun checkRxSpeed() {
        val startTime = System.nanoTime()

        try {
            val body: ResponseBody? = service.getCharactersWithRx(1).blockingFirst()
            val endTime = System.nanoTime()

            System.out.println(body?.string())
            System.out.println(endTime - startTime)

            assert(true)
        } catch(err: Exception) {
            val endTime = System.nanoTime()

            System.out.println(err.message)
            System.out.println(endTime - startTime)

            assert(false)
        }
    }

    @Test
    @Throws(Exception::class)
    fun checkCoroutineSpeed() = runBlocking {
        val startTime = System.nanoTime()

        GlobalScope.launch {
            try {
                val body: ResponseBody? = service.getCharactersAsync(1).await()
                val endTime = System.nanoTime()

                System.out.println(body?.string())
                System.out.println(endTime - startTime)

                assert(true)
            } catch(err: Exception) {
                val endTime = System.nanoTime()

                System.out.println(err.message)
                System.out.println(endTime - startTime)

                assert(false)
            }
        }.join()
    }
}