package br.com.stanzione.mercadolivretest.installment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.stanzione.mercadolivretest.R;

public class InstallmentFragment extends Fragment {


    public InstallmentFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_installment, container, false);
    }

}
