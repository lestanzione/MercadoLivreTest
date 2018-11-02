package br.com.stanzione.mercadolivretest.api;

import java.util.List;

import br.com.stanzione.mercadolivretest.data.CardIssuer;
import br.com.stanzione.mercadolivretest.data.InstallmentResponse;
import br.com.stanzione.mercadolivretest.data.Method;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MercadoLivreApi {

    public static final String KEY = "444a9ef5-8a6b-429f-abdf-587639155d88";

    @GET("payment_methods")
    Observable<List<Method>> getPaymentMethods();

    @GET("payment_methods/card_issuers")
    Observable<List<CardIssuer>> getCardIssuers(@Query("payment_method_id") String methodId);

    @GET("payment_methods/installments")
    Observable<List<InstallmentResponse>> getInstallments(
            @Query("amount") double amount,
            @Query("payment_method_id") String methodId,
            @Query("issuer.id") String issuerId);

}
