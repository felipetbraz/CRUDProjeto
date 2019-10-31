package com.ddm.crudprojeto.services;

import com.ddm.crudprojeto.dto.DtoLogin;
import com.ddm.crudprojeto.dto.DtoUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InterfaceDeServicos {

    @POST("/users")
    Call<DtoUser> cadastraUsuario(@Body DtoUser dtoUser);

    @POST("/auth/login")
    Call<DtoLogin> login(@Body DtoLogin dtoLogin);

    @GET("/users")
    Call<List<DtoUser>> todosUsuarios(@Header("Authorization") String authorization);

    @PUT("/user/{id}")
    Call<DtoUser> alteraUsuario(@Body DtoUser user, @Path("id") int id, @Header("Authorization") String authorization);

    @DELETE("/user/{id}")
    Call<Void> excluirUsuario(@Path("id") int id, @Header("Authorization") String token);
}