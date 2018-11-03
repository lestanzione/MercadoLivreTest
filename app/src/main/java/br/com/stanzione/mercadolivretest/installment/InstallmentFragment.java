package br.com.stanzione.mercadolivretest.installment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import br.com.stanzione.mercadolivretest.App;
import br.com.stanzione.mercadolivretest.R;
import br.com.stanzione.mercadolivretest.data.Installment;
import br.com.stanzione.mercadolivretest.installment.adapter.InstallmentsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InstallmentFragment extends Fragment implements InstallmentContract.View {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.installmentsRecyclerView)
    RecyclerView installmentsRecyclerView;

    @Inject
    InstallmentContract.Presenter presenter;

    private InstallmentsAdapter.OnInstallmentSelectedListener listener;
    private InstallmentsAdapter installmentsAdapter;
    private List<Installment> installmentList;

    private double amount;
    private String methodId;
    private String cardIssuerId;

    private Boolean isStarted = false;
    private Boolean isVisible = false;


    public InstallmentFragment() {}

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setMethodId(String methodId){
        this.methodId = methodId;
    }

    public void setCardIssuerId(String cardIssuerId){
        this.cardIssuerId = cardIssuerId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_installment, container, false);
        setupUi(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        setupInjector(context);
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible) {
            presenter.getInstallments(amount, methodId, cardIssuerId);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isStarted) {
            presenter.getInstallments(amount, methodId, cardIssuerId);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }

    private void setupInjector(Context context){
        ((App) (context.getApplicationContext()))
                .getApplicationComponent()
                .inject(this);

        presenter.attachView(this);
    }

    private void setupUi(View view) {
        ButterKnife.bind(this, view);

        installmentsAdapter = new InstallmentsAdapter(getContext(), listener);
        installmentsRecyclerView.setAdapter(installmentsAdapter);
    }

    @Override
    public void showNetworkMessage() {
        Snackbar.make(installmentsRecyclerView , getString(R.string.message_network_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showGeneralMessage() {
        Snackbar.make(installmentsRecyclerView, getString(R.string.message_general_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showInstallments(List<Installment> installmentList) {
        this.installmentList = installmentList;
        installmentsAdapter.setItems(installmentList);
    }

    @Override
    public void setProgressBarVisible(boolean visible) {
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void setListener(InstallmentsAdapter.OnInstallmentSelectedListener listener){
        this.listener = listener;
        if(null != installmentsAdapter){
            installmentsAdapter.setListener(listener);
        }
    }
}
