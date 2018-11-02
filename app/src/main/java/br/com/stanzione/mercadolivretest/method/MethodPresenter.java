package br.com.stanzione.mercadolivretest.method;

import java.util.List;

import br.com.stanzione.mercadolivretest.data.Method;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MethodPresenter implements MethodContract.Presenter {

    private MethodContract.View view;
    private MethodContract.Model model;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MethodPresenter(MethodContract.Model model){
        this.model = model;
    }

    @Override
    public void getPaymentMethods() {
        compositeDisposable.add(
                model.fetchPaymentMethods()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Method>>() {
                               @Override
                               public void accept(List<Method> methods) throws Exception {
                                    System.out.println(methods);
                                    System.out.println(methods.size());
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                            }
                        })
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
}
