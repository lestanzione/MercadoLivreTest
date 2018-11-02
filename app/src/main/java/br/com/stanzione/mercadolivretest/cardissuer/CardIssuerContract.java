package br.com.stanzione.mercadolivretest.cardissuer;

import java.util.List;

import br.com.stanzione.mercadolivretest.BasePresenter;
import br.com.stanzione.mercadolivretest.BaseView;
import br.com.stanzione.mercadolivretest.data.CardIssuer;
import io.reactivex.Observable;

public interface CardIssuerContract {

    interface View extends BaseView {
        void showNetworkMessage();
        void showGeneralMessage();
        void showCardIssuers(List<CardIssuer> cardIssuerList);
    }

    interface Presenter extends BasePresenter<View> {
        void getCardIssuers(String methodId);
    }

    interface Model{
        Observable<List<CardIssuer>> fetchCardIssuers(String methodId);
    }

}
