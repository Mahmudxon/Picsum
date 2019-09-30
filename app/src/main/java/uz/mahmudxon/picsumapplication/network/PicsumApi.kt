package uz.mahmudxon.picsumapplication.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import uz.mahmudxon.picsumapplication.model.PicsumData

interface PicsumApi {
    @GET("/v2/list")
    fun getPhotos() : Observable<ArrayList<PicsumData>>

    @GET("/v2/list?")
    fun getPhotoByPage(@Query("page") page : Int, @Query("limit") limit: Int) : Observable<ArrayList<PicsumData>>
}