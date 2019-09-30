package uz.mahmudxon.picsumapplication.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PicsumClient {
    private var retrofit: Retrofit? = null
//
//    private fun getClient(): OkHttpClient {
//        return OkHttpClient().newBuilder().addInterceptor { chain ->
//            val originalRequest = chain.request()
//            val builder = originalRequest.newBuilder()
//            builder.addHeader(
//                "Authorization",
//                "563492ad6f917000010000015db6a349492e46988fce8722fdc4ba5f"
//            )
//            val newRequest = builder.build()
//            chain.proceed(newRequest)
//        }.build()
//    }

    fun getRetrofitData(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://picsum.photos")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun inalidate() {
        retrofit = null
    }
}