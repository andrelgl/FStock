package kn.fstock.fstock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import kn.fstock.fstock.Services.EstoqueService;
import kn.fstock.fstock.Services.PessoaService;
import kn.fstock.fstock.Services.ProdutoService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFstock {

    private Retrofit retrofit;
    private static ApiFstock INSTANSE;

    private ApiFstock(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
        Gson gson = gsonBuilder.create();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://fstock.herokuapp.com/api/")
                //   .baseUrl("http://192.168.51.200:8000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

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

    class DateDeserializer implements JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
            String date = element.getAsString();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
