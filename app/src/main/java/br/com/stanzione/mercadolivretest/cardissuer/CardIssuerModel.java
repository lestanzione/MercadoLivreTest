package br.com.stanzione.mercadolivretest.cardissuer;

import java.util.List;

import br.com.stanzione.mercadolivretest.api.MercadoLivreApi;
import br.com.stanzione.mercadolivretest.data.CardIssuer;
import io.reactivex.Observable;

public class CardIssuerModel implements CardIssuerContract.Model {

    private MercadoLivreApi mercadoLivreApi;

    public CardIssuerModel(MercadoLivreApi mercadoLivreApi){
        this.mercadoLivreApi = mercadoLivreApi;
    }

    @Override
    public Observable<List<CardIssuer>> fetchCardIssuers(String methodId) {
        return mercadoLivreApi.getCardIssuers(methodId);
    }
}
