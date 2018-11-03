package br.com.stanzione.mercadolivretest.method;

import javax.inject.Singleton;

import br.com.stanzione.mercadolivretest.api.MercadoLivreApi;
import dagger.Module;
import dagger.Provides;

@Module
public class MethodModule {

    @Singleton
    @Provides
    MethodContract.Presenter providesPresenter(MethodContract.Model model){
        MethodPresenter presenter = new MethodPresenter(model);
        return presenter;
    }

    @Singleton
    @Provides
    MethodContract.Model providesModel(MercadoLivreApi mercadoLivreApi){
        MethodModel model = new MethodModel(mercadoLivreApi);
        return model;
    }

}
