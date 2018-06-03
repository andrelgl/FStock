package kn.fstock.fstock.Services;

import java.util.List;

import kn.fstock.fstock.models.Estoque;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EstoqueService {
    @GET("estoque/")
    Call<List<Estoque>> listarEstoque();

    @GET("estoque/{id}")
    Call<Estoque> buscarEstoque(@Path("id") long id);

    @POST("estoque/")
    Call<Void> criarEstoque(@Body Estoque estoque);

    @PUT("estoque/{id}")
    Call<Void> alteraEstoque(@Path("id") long id, @Body Estoque estoque);

    @DELETE("estoque/{id}")
    Call<Void> deletarEstoque(@Path("id") long id);
}