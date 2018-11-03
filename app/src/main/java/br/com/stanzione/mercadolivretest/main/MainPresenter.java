package br.com.stanzione.mercadolivretest.main;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    @Override
    public void validateAmount(double amount) {
        if(amount <= 0){
            view.showInvalidAmountMessage();
        }
        else{
            view.navigateToPayment(amount);
        }
    }

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void dispose() {

    }
}
