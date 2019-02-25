package com.example.crudmahasiswa.network;

import com.example.crudmahasiswa.model.ResponseInsert;
import com.example.crudmahasiswa.model.ResponseMahasiswa;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("getMahasiswa")
    Call<ResponseMahasiswa> getDataMahasiswa();

    @FormUrlEncoded
    @POST("insertMahasiswa")
    Call<ResponseInsert> create(
            @Field("nim") String nim,
            @Field("name") String name,
            @Field("majors") String jurusan);

    @FormUrlEncoded
    @POST("updateMahasiswa")
    Call<ResponseInsert> update(
            @Field("id") String id,
            @Field("nim") String nim,
            @Field("name") String name,
            @Field("majors") String jurusan);

    @FormUrlEncoded
    @POST("deleteMahasiswa")
    Call<ResponseInsert> delete(
            @Field("id") String id);

}
