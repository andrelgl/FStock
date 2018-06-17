package kn.fstock.fstock.Services;

import java.util.List;

import kn.fstock.fstock.models.Estoque;
import kn.fstock.fstock.models.Pessoa;
import kn.fstock.fstock.models.PessoaEstoque;
import kn.fstock.fstock.models.Produto;
import kn.fstock.fstock.models.ProdutoVencer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EstoqueService {
    @GET("pessoa/{pessoa_id}/estoque")
    Call<List<Estoque>> listarEstoque(@Path("pessoa_id") int pessoa_id);

    @GET("pessoa/{pessoa_id}/estoque/{estoque_id}")
    Call<Estoque> buscarEstoque(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int id);

    @POST("pessoa/{pessoa_id}/estoque")
    Call<Estoque> criarEstoque(@Path("pessoa_id") int pessoa_id, @Body Estoque estoque);

    @PUT("pessoa/{pessoa_id}/estoque/{estoque_id}")
    Call<Void> alteraEstoque(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id, @Body Estoque estoque);

    @DELETE("pessoa/{pessoa_id}/estoque/{estoque_id}")
    Call<Void> deletarEstoque(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id);

    @GET("pessoa/{pessoa_id}/estoque/{estoque_id}/alerta-qtd")
    Call<List<Produto>> alertQuantidade(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id);

    @GET("pessoa/{pessoa_id}/estoque/{estoque_id}/alerta-vencer")
    Call<List<ProdutoVencer>> alertVencimento(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id);

    @POST("pessoa/{pessoa_id}/estoque/{estoque_id}/pessoa")
    Call<Void> adcionarPessoaEstoque(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id, @Body PessoaEstoque pessoa);

    @GET("pessoa/{pessoa_id}/estoque/{estoque_id}/pessoa")
    Call<List<Pessoa>> listarPessoaEstoque(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id);

    @DELETE("pessoa/{pessoa_id}/estoque/{estoque_id}/pessoa")
    Call<Void> deletarPessoaEstoque(@Path("pessoa_id") int pessoa_id, @Path("estoque_id") int estoque_id);

}
