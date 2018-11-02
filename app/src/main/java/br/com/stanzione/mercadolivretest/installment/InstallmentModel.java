package br.com.stanzione.mercadolivretest.installment;

import br.com.stanzione.mercadolivretest.api.MercadoLivreApi;
import br.com.stanzione.mercadolivretest.data.InstallmentResponse;
import io.reactivex.Observable;

public class InstallmentModel implements InstallmentContract.Model {

    private MercadoLivreApi mercadoLivreApi;

    public InstallmentModel(MercadoLivreApi mercadoLivreApi){
        this.mercadoLivreApi = mercadoLivreApi;
    }

    @Override
    public Observable<InstallmentResponse> fetchInstallments() {
        return null;
    }
}
