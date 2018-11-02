package br.com.stanzione.mercadolivretest.installment;

import java.util.List;

import br.com.stanzione.mercadolivretest.data.InstallmentResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class InstallmentPresenter implements InstallmentContract.Presenter {

    private InstallmentContract.View view;
    private InstallmentContract.Model model;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public InstallmentPresenter(InstallmentContract.Model model){
        this.model = model;
    }

    @Override
    public void getInstallments(double amount, String methodId, String cardIssuerId) {

        view.setProgressBarVisible(true);

        compositeDisposable.add(
                model.fetchInstallments(amount, methodId, cardIssuerId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<List<InstallmentResponse>>() {
                                    @Override
                                    public void accept(List<InstallmentResponse> installmentResponses) throws Exception {
                                        view.setProgressBarVisible(false);
                                        view.showInstallments(installmentResponses.get(0).getInstallmentList());
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        throwable.printStackTrace();
                                    }
                                }
                        )
        );
    }

    @Override
    public void attachView(InstallmentContract.View view) {
        this.view = view;
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }
}
