package br.com.stanzione.mercadolivretest.method;

import io.reactivex.disposables.CompositeDisposable;

public class MethodPresenter implements MethodContract.Presenter {

    private MethodContract.View view;
    private MethodContract.Model model;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MethodPresenter(MethodContract.Model model){
        this.model = model;
    }

    @Override
    public void getPaymentMethods() {

    }

    @Override
    public void attachView(MethodContract.View view) {
        this.view = view;
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }
}
