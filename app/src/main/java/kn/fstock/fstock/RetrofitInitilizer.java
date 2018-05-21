package kn.fstock.fstock;

import kn.fstock.fstock.Services.EstoqueService;
import kn.fstock.fstock.Services.PessoaService;
import kn.fstock.fstock.Services.ProdutoService;
import kn.fstock.fstock.Services.RecipienteService;
import kn.fstock.fstock.Services.TipoService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInitilizer {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://fstock.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public PessoaService descricaoPessoaService()  {
        return retrofit.create(PessoaService.class);
    }
    public EstoqueService descricaoEstoqueService()  {
        return retrofit.create(EstoqueService.class);
    }
    public ProdutoService descricaoProdutoService()  {
        return retrofit.create(ProdutoService.class);
    }
    public RecipienteService descricaoRecipienteService()  {
        return retrofit.create(RecipienteService.class);
    }
    public TipoService descricaoTipoService()  {
        return retrofit.create(TipoService.class);
    }

}
