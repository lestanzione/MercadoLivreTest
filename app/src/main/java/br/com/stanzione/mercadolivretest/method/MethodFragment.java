package br.com.stanzione.mercadolivretest.method;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import br.com.stanzione.mercadolivretest.App;
import br.com.stanzione.mercadolivretest.R;
import butterknife.ButterKnife;

public class MethodFragment extends Fragment implements MethodContract.View{

    @Inject
    MethodContract.Presenter presenter;

    private Boolean isStarted = false;
    private Boolean isVisible = false;


    public MethodFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_method, container, false);
        //setupUi(view);
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
            presenter.getPaymentMethods();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isStarted) {
            presenter.getPaymentMethods();
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
    }

    @Override
    public void showNetworkMessage() {

    }

    @Override
    public void showGeneralMessage() {

    }

    @Override
    public void setProgressBarVisible(boolean visible) {

    }
}
