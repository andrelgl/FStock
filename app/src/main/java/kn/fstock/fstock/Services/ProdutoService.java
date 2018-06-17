package kn.fstock.fstock.Services;

import java.util.List;

import kn.fstock.fstock.models.Item;
import kn.fstock.fstock.models.Produto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProdutoService {
    @GET("pessoa/{pessoa_id}/estoque/{estoque_id}/produto")
    Call<List<Produto>> listarProdutos(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id);

    @GET("pessoa/{pessoa_id}/estoque/{estoque_id}/produto/{produto_id}")
    Call<Produto> buscarProduto(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id, @Path("produto_id") int produto_id);

    @POST("pessoa/{pessoa_id}/estoque/{estoque_id}/produto")
    Call<Void> criarProduto(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id, @Body Produto produto);

    @PUT("pessoa/{pessoa_id}/estoque/{estoque_id}/produto/{produto_id}")
    Call<Void> alteraProduto(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id, @Path("produto_id") int produto_id);

    @DELETE("pessoa/{pessoa_id}/estoque/{estoque_id}/produto/{produto_id}")
    Call<Void> deletarProduto(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id, @Path("produto_id") int produto_id);

    @GET("pessoa/{pessoa_id}/estoque/{estoque_id}/produto/{produto_id}/item")
    Call<List<Item>> itemListar(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id, @Path("produto_id") int produto_id);

    @POST("pessoa/{pessoa_id}/estoque/{estoque_id}/produto/{produto_id}/item")
    Call<Void> criarItem(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id, @Path("produto_id") int produto_id, @Body Item item);

    @DELETE("pessoa/{pessoa_id}/estoque/{estoque_id}/produto/{produto_id}")
    Call<Void> deletarItem(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id, @Path("produto_id") int produto_id);
}