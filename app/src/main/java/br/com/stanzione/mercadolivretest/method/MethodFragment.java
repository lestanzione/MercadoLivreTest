package br.com.stanzione.mercadolivretest.method;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.stanzione.mercadolivretest.R;

public class MethodFragment extends Fragment implements MethodContract.View{


    public MethodFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_method, container, false);
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
