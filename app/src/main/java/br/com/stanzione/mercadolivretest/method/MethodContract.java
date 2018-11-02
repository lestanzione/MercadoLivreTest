package br.com.stanzione.mercadolivretest.method;

import java.util.List;

import br.com.stanzione.mercadolivretest.BasePresenter;
import br.com.stanzione.mercadolivretest.BaseView;
import br.com.stanzione.mercadolivretest.data.Method;
import io.reactivex.Observable;

public interface MethodContract {

    interface View extends BaseView{
        void showNetworkMessage();
        void showGeneralMessage();
        void showMethods(List<Method> methodList);
    }

    interface Presenter extends BasePresenter<View>{
        void getPaymentMethods();
    }

    interface Model{
        Observable<List<Method>> fetchPaymentMethods();
    }

}
