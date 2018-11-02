package br.com.stanzione.mercadolivretest.cardissuer;

import javax.inject.Singleton;

import br.com.stanzione.mercadolivretest.api.MercadoLivreApi;
import dagger.Module;
import dagger.Provides;

@Module
public class CardIssuerModule {

    @Singleton
    @Provides
    CardIssuerContract.Presenter providesPresenter(CardIssuerContract.Model model){
        CardIssuerPresenter presenter = new CardIssuerPresenter(model);
        return presenter;
    }

    @Singleton
    @Provides
    CardIssuerContract.Model providesModel(MercadoLivreApi mercadoLivreApi){
        CardIssuerModel model = new CardIssuerModel(mercadoLivreApi);
        return model;
    }

}
