package kn.fstock.fstock.Services;

import java.util.List;

import kn.fstock.fstock.models.Tipo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TipoService {
    @GET("tipo/")
    Call<List<Tipo>> listarTipo();

    @GET("tipo/{id}")
    Call<Tipo> buscarTipo(@Path("id") long id);

    @POST("tipo/")
    Call<Void> criarTipo(@Body Tipo tipo);

    @PUT("tipo/{id}")
    Call<Void> alteraTipo(@Path("id") long id, @Body Tipo tipo);

    @DELETE("tipo/{id}")
    Call<Void> deletarTipo(@Path("id") long id);
}
