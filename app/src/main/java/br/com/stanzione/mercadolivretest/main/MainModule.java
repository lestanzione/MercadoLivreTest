package br.com.stanzione.mercadolivretest.main;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Singleton
    @Provides
    MainContract.Presenter providesPresenter(){
        MainPresenter presenter = new MainPresenter();
        return presenter;
    }

}
