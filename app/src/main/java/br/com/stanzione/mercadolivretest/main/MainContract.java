package br.com.stanzione.mercadolivretest.main;

import br.com.stanzione.mercadolivretest.BasePresenter;
import br.com.stanzione.mercadolivretest.BaseView;

public interface MainContract {

    interface View extends BaseView {
        void showInvalidAmountMessage();
        void navigateToPayment(double amount);
    }

    interface Presenter extends BasePresenter<View> {
        void validateAmount(double amount);
    }

}
