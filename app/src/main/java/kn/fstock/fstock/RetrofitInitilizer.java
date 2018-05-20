package kn.fstock.fstock;

import java.util.List;

import kn.fstock.fstock.models.Pessoa;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RetrofitInitilizer {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://fstock.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public PessoaService descricaoGrupoService()  {
        return retrofit.create(PessoaService.class);
    }

    public interface PessoaService {
        @GET("pessoa/")
        Call<List<Pessoa>> listarPessoas();
        @GET("pessoa/{id}")
        Call<Pessoa> listarPessoas(@Path("id") int id);
    }

}
