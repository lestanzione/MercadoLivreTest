package br.com.stanzione.mercadolivretest.installment;

public class InstallmentPresenter implements InstallmentContract.Presenter {

    private InstallmentContract.View view;
    private InstallmentContract.Model model;

    public InstallmentPresenter(InstallmentContract.Model model){
        this.model = model;
    }

    @Override
    public void getInstallments() {

    }

    @Override
    public void attachView(InstallmentContract.View view) {

    }

    @Override
    public void dispose() {

    }
}
