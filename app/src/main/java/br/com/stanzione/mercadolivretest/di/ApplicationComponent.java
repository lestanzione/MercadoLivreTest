package br.com.stanzione.mercadolivretest.di;

import javax.inject.Singleton;

import br.com.stanzione.mercadolivretest.main.MainActivity;
import br.com.stanzione.mercadolivretest.main.MainModule;
import dagger.Component;

@Singleton
@Component(
        modules = {
                MainModule.class
        }
)
public interface ApplicationComponent {
    void inject(MainActivity activity);
}
