package br.com.stanzione.mercadolivretest.method;

import java.io.IOException;
import java.util.List;

import br.com.stanzione.mercadolivretest.data.Method;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MethodPresenter implements MethodContract.Presenter {

    private MethodContract.View view;
    private MethodContract.Model model;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private double amount;

    public MethodPresenter(MethodContract.Model model){
        this.model = model;
    }

    @Override
    public void getPaymentMethods(double amount) {
        this.amount = amount;

        view.setProgressBarVisible(true);

        compositeDisposable.add(
                model.fetchPaymentMethods()
                        .flatMap(Observable::fromIterable)
                        .filter(this::filterAllowedAmount)
                        .toList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::onMethodsReceived,
                                this::onFetchMethodsError
                        )
        );
    }

    @Override
    public void attachView(MethodContract.View view) {
        this.view = view;
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }

    private boolean filterAllowedAmount(Method method){
        if(amount >= method.getMinAmount() && amount <= method.getMaxAmount()){
            return true;
        }
        else{
            return false;
        }
    }

    private void onMethodsReceived(List<Method> methodList){
        view.setProgressBarVisible(false);
        view.showMethods(methodList);
    }

    private void onFetchMethodsError(Throwable throwable) {
        view.setProgressBarVisible(false);
        if(throwable instanceof IOException){
            view.showNetworkMessage();
        }
        else{
            view.showGeneralMessage();
        }
    }
}
