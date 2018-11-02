package br.com.stanzione.mercadolivretest.installment;

import javax.inject.Singleton;

import br.com.stanzione.mercadolivretest.api.MercadoLivreApi;
import dagger.Module;
import dagger.Provides;

@Module
public class InstallmentModule {

    @Singleton
    @Provides
    InstallmentContract.Presenter providesPresenter(InstallmentContract.Model model){
        InstallmentPresenter presenter = new InstallmentPresenter(model);
        return presenter;
    }

    @Singleton
    @Provides
    InstallmentContract.Model providesModel(MercadoLivreApi mercadoLivreApi){
        InstallmentModel model = new InstallmentModel(mercadoLivreApi);
        return model;
    }

}
