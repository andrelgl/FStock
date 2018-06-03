package kn.fstock.fstock.Services;

import java.util.List;

import kn.fstock.fstock.models.Produto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProdutoService {
    @GET("produto/")
    Call<List<Produto>> listarProdutos();

    @GET("produto/{id}")
    Call<Produto> buscarProduto(@Path("id") long id);

    @POST("produto/")
    Call<Void> criarProduto(@Body Produto produto);

    @PUT("produto/{id}")
    Call<Void> alteraProduto(@Path("id") long id, @Body Produto produto);

    @DELETE("produto/{id}")
    Call<Void> deletarProduto(@Path("id") long id);
}