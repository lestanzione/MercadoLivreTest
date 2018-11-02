package br.com.stanzione.mercadolivretest.api;

import java.util.List;

import br.com.stanzione.mercadolivretest.data.Method;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MercadoLivreApi {

    public static final String KEY = "444a9ef5-8a6b-429f-abdf-587639155d88";

    @GET("payment_methods")
    Observable<List<Method>> getPaymentMethods();

}
