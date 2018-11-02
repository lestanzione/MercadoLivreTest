package br.com.stanzione.mercadolivretest;

import android.app.Application;

import br.com.stanzione.mercadolivretest.di.ApplicationComponent;
import br.com.stanzione.mercadolivretest.di.DaggerApplicationComponent;
import br.com.stanzione.mercadolivretest.main.MainModule;
import br.com.stanzione.mercadolivretest.method.MethodModule;

public class App extends Application {

    private ApplicationComponent applicationComponent;

    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .mainModule(new MainModule())
                .methodModule(new MethodModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
