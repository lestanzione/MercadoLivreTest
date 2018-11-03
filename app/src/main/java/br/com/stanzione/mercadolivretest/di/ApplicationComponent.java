package br.com.stanzione.mercadolivretest.di;

import javax.inject.Singleton;

import br.com.stanzione.mercadolivretest.cardissuer.CardIssuerFragment;
import br.com.stanzione.mercadolivretest.cardissuer.CardIssuerModel;
import br.com.stanzione.mercadolivretest.cardissuer.CardIssuerModule;
import br.com.stanzione.mercadolivretest.installment.InstallmentFragment;
import br.com.stanzione.mercadolivretest.installment.InstallmentModule;
import br.com.stanzione.mercadolivretest.main.MainActivity;
import br.com.stanzione.mercadolivretest.main.MainModule;
import br.com.stanzione.mercadolivretest.method.MethodFragment;
import br.com.stanzione.mercadolivretest.method.MethodModule;
import dagger.Component;

@Singleton
@Component(
        modules = {
                NetworkModule.class,
                MainModule.class,
                MethodModule.class,
                CardIssuerModule.class,
                InstallmentModule.class
        }
)
public interface ApplicationComponent {
    void inject(MainActivity activity);
    void inject(MethodFragment fragment);
    void inject(CardIssuerFragment fragment);
    void inject(InstallmentFragment fragment);
}
