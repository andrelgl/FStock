package kn.fstock.fstock;

import kn.fstock.fstock.Services.EstoqueService;
import kn.fstock.fstock.Services.PessoaService;
import kn.fstock.fstock.Services.ProdutoService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFstock {
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://fstock.herokuapp.com/api/")
         //   .baseUrl("http://192.168.51.200:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static ApiFstock INSTANSE;
    private ApiFstock(){}
    public static ApiFstock getInstance(){
        if(INSTANSE == null){
            INSTANSE = new ApiFstock();
        }
        return INSTANSE;
    }

    public PessoaService descricaoPessoaService()  {
        return retrofit.create(PessoaService.class);
    }
    public EstoqueService descricaoEstoqueService()  {
        return retrofit.create(EstoqueService.class);
    }
    public ProdutoService descricaoProdutoService()  {
        return retrofit.create(ProdutoService.class);
    }
}
