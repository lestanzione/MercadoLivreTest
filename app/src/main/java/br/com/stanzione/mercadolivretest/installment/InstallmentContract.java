package br.com.stanzione.mercadolivretest.installment;

import java.util.List;

import br.com.stanzione.mercadolivretest.BasePresenter;
import br.com.stanzione.mercadolivretest.BaseView;
import br.com.stanzione.mercadolivretest.data.Installment;
import br.com.stanzione.mercadolivretest.data.InstallmentResponse;
import io.reactivex.Observable;

public interface InstallmentContract {

    interface View extends BaseView{
        void showNetworkMessage();
        void showGeneralMessage();
        void showInstallments(List<Installment> installmentList);
    }

    interface Presenter extends BasePresenter<View> {
        void getInstallments();
    }

    interface Model{
        Observable<InstallmentResponse> fetchInstallments();
    }

}
