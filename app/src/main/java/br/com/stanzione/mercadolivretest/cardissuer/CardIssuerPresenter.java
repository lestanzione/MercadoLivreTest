package br.com.stanzione.mercadolivretest.cardissuer;

public class CardIssuerPresenter implements CardIssuerContract.Presenter {

    private CardIssuerContract.View view;
    private CardIssuerContract.Model model;

    public CardIssuerPresenter(CardIssuerContract.Model model){
        this.model = model;
    }

    @Override
    public void getCardIssuers(String methodId) {

    }

    @Override
    public void attachView(CardIssuerContract.View view) {

    }

    @Override
    public void dispose() {

    }
}
