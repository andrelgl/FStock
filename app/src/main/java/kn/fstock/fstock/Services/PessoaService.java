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
    Call<List<Pessoa>> listarPessoasEstoque();

    @GET("pessoa/{pessoa_id}")
    Call<Pessoa> buscarPessoa(@Path("pessoa_id") int id);

    @POST("pessoa/")
    Call<Void> criarPessoa(@Body Pessoa pessoa);

    @POST("pessoa/autenticar")
    Call<Pessoa> autenticarPessoa(@Body Pessoa pessoa);

    @PUT("pessoa/{pessoa_id}")
    Call<Void> alteraPessoa(@Path("pessoa_id") int id, @Body Pessoa pessoa);

    @DELETE("pessoa/{pessoa_id}")
    Call<Void> deletarPessoa(@Path("pessoa_id") int id);
}
