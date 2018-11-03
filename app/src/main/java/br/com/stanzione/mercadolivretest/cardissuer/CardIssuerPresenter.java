package br.com.stanzione.mercadolivretest.cardissuer;

import java.io.IOException;
import java.util.List;

import br.com.stanzione.mercadolivretest.data.CardIssuer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CardIssuerPresenter implements CardIssuerContract.Presenter {

    private CardIssuerContract.View view;
    private CardIssuerContract.Model model;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CardIssuerPresenter(CardIssuerContract.Model model){
        this.model = model;
    }

    @Override
    public void getCardIssuers(String methodId) {

        view.setProgressBarVisible(true);
        view.setEmptyStateVisible(false);

        compositeDisposable.add(
                model.fetchCardIssuers(methodId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::onCardIssuersReceived,
                                this::onFetchCardIssuersError
                        )
        );
    }

    @Override
    public void attachView(CardIssuerContract.View view) {
        this.view = view;
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }

    private void onCardIssuersReceived(List<CardIssuer> cardIssuerList){
        view.setProgressBarVisible(false);
        if(cardIssuerList.isEmpty()){
            view.setEmptyStateVisible(true);
        }
        else {
            view.showCardIssuers(cardIssuerList);
        }
    }

    private void onFetchCardIssuersError(Throwable throwable) {
        view.setProgressBarVisible(false);
        if(throwable instanceof IOException){
            view.showNetworkMessage();
        }
        else{
            view.showGeneralMessage();
        }
    }
}
