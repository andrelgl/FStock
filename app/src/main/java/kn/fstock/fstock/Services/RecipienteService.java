package kn.fstock.fstock.Services;

import java.util.List;

import kn.fstock.fstock.models.Produto;
import kn.fstock.fstock.models.Recipiente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RecipienteService {
    @GET("recipiente/")
    Call<List<Produto>> listarRecipientes();

    @GET("recipiente/{id}")
    Call<Recipiente> buscarRecipiente(@Path("id") long id);

    @POST("recipiente/")
    Call<Void> criarRecipiente(@Body Recipiente recipiente);

    @PUT("recipiente/{id}")
    Call<Void> alteraRecipiente(@Path("id") long id, @Body Recipiente recipiente);

    @DELETE("recipiente/{id}")
    Call<Void> deletarRecipiente(@Path("id") long id);
}