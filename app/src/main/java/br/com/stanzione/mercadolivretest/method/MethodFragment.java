package br.com.stanzione.mercadolivretest.method;


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
import br.com.stanzione.mercadolivretest.data.Method;
import br.com.stanzione.mercadolivretest.method.adapter.MethodsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MethodFragment extends Fragment implements MethodContract.View {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.methodsRecyclerView)
    RecyclerView methodsRecyclerView;

    @Inject
    MethodContract.Presenter presenter;

    private MethodsAdapter.OnMethodSelectedListener listener;
    private MethodsAdapter methodsAdapter;
    private List<Method> methodList;

    private double amount;

    private Boolean isStarted = false;
    private Boolean isVisible = false;


    public MethodFragment() {}

    public void setAmount(double amount){
        this.amount = amount;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_method, container, false);
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
            presenter.getPaymentMethods(amount);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isStarted) {
            presenter.getPaymentMethods(amount);
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

        methodsAdapter = new MethodsAdapter(getContext(), listener);
        methodsRecyclerView.setAdapter(methodsAdapter);
    }

    @Override
    public void showNetworkMessage() {
        Snackbar.make(methodsRecyclerView , getString(R.string.message_network_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showGeneralMessage() {
        Snackbar.make(methodsRecyclerView, getString(R.string.message_general_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showMethods(List<Method> methodList) {
        this.methodList = methodList;
        methodsAdapter.setItems(methodList);
    }

    @Override
    public void setProgressBarVisible(boolean visible) {
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void setListener(MethodsAdapter.OnMethodSelectedListener listener){
        this.listener = listener;
    }

}
