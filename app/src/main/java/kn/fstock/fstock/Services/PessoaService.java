package kn.fstock.fstock.Services;

import java.util.List;

import kn.fstock.fstock.models.Pessoa;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PessoaService {
    @GET("pessoa/")
    Call<List<Pessoa>> listarPessoas();

    @GET("pessoa/{id}")
    Call<Pessoa> buscarPessoa(@Path("id") long id);

    @POST("pessoa/")
    Call<Void> criarPessoa(@Body Pessoa pessoa);

    @PUT("pessoa/{id}")
    Call<Void> alteraPessoa(@Path("id") long id, @Body Pessoa pessoa);

    @DELETE("pessoa/{id}")
    Call<Void> deletarPessoa(@Path("id") long id);
}
