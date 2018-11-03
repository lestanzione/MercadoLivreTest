package br.com.stanzione.mercadolivretest.main.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import br.com.stanzione.mercadolivretest.Configs;
import br.com.stanzione.mercadolivretest.R;

public class SummaryDialog extends DialogFragment {

    public static SummaryDialog newInstance(String method, String cardIssuer, String installment) {

        Bundle bundle = new Bundle();
        bundle.putString(Configs.ARG_METHOD, method);
        bundle.putString(Configs.ARG_CARD_ISSUER, cardIssuer);
        bundle.putString(Configs.ARG_INSTALLMENT, installment);

        SummaryDialog summaryDialog = new SummaryDialog();
        summaryDialog.setArguments(bundle);

        return summaryDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String method = getArguments().getString(Configs.ARG_METHOD);
        String cardIssuer = getArguments().getString(Configs.ARG_CARD_ISSUER);
        String installment = getArguments().getString(Configs.ARG_INSTALLMENT);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_summary, null, false);

        TextView methodTextView = view.findViewById(R.id.summaryMethodTextView);
        TextView cardIssuerTextView = view.findViewById(R.id.summaryCardIssuerTextView);
        TextView installmentTextView = view.findViewById(R.id.summaryInstallmentTextView);

        methodTextView.setText(method);
        cardIssuerTextView.setText(cardIssuer);
        installmentTextView.setText(installment);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }

}
