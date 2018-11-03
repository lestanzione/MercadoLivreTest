package br.com.stanzione.mercadolivretest.installment;

import java.io.IOException;
import java.util.List;

import br.com.stanzione.mercadolivretest.data.InstallmentResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
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
                                this::onInstallmentsReceived,
                                this::onFetchInstallmentsError
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

    private void onInstallmentsReceived(List<InstallmentResponse> installmentResponseList){
        view.setProgressBarVisible(false);
        view.showInstallments(installmentResponseList.get(0).getInstallmentList());
    }

    private void onFetchInstallmentsError(Throwable throwable) {
        view.setProgressBarVisible(false);
        if(throwable instanceof IOException){
            view.showNetworkMessage();
        }
        else{
            view.showGeneralMessage();
        }
    }
}
