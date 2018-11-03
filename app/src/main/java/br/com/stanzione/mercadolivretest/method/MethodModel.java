package br.com.stanzione.mercadolivretest.method;

import java.util.List;

import br.com.stanzione.mercadolivretest.api.MercadoLivreApi;
import br.com.stanzione.mercadolivretest.data.Method;
import io.reactivex.Observable;

public class MethodModel implements MethodContract.Model {

    private MercadoLivreApi mercadoLivreApi;

    public MethodModel(MercadoLivreApi mercadoLivreApi){
        this.mercadoLivreApi = mercadoLivreApi;
    }

    @Override
    public Observable<List<Method>> fetchPaymentMethods() {
        return mercadoLivreApi.getPaymentMethods();
    }
}
