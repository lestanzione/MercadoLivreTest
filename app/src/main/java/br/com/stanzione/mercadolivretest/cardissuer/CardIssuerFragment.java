package br.com.stanzione.mercadolivretest.cardissuer;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import br.com.stanzione.mercadolivretest.App;
import br.com.stanzione.mercadolivretest.R;
import br.com.stanzione.mercadolivretest.cardissuer.adapter.CardIssuersAdapter;
import br.com.stanzione.mercadolivretest.data.CardIssuer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CardIssuerFragment extends Fragment implements CardIssuerContract.View{

    @BindView(R.id.cardIssuersRecyclerView)
    RecyclerView cardIssuersRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.cardIssuerEmptyStateTextView)
    TextView cardIssuerEmptyStateTextView;

    @Inject
    CardIssuerContract.Presenter presenter;

    private CardIssuersAdapter.OnCardIssuerSelectedListener listener;
    private CardIssuersAdapter cardIssuersAdapter;
    private List<CardIssuer> cardIssuerList;

    private String methodId;

    private Boolean isStarted = false;
    private Boolean isVisible = false;

    public CardIssuerFragment() {}

    public void setMethodId(String methodId){
        this.methodId = methodId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_issuer, container, false);
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
            presenter.getCardIssuers(methodId);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isStarted) {
            presenter.getCardIssuers(methodId);
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

        cardIssuersAdapter = new CardIssuersAdapter(getContext(), listener);
        cardIssuersRecyclerView.setAdapter(cardIssuersAdapter);
    }

    @Override
    public void showNetworkMessage() {
        Snackbar.make(cardIssuersRecyclerView , getString(R.string.message_network_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showGeneralMessage() {
        Snackbar.make(cardIssuersRecyclerView, getString(R.string.message_general_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showCardIssuers(List<CardIssuer> cardIssuerList) {
        this.cardIssuerList = cardIssuerList;
        cardIssuersAdapter.setItems(cardIssuerList);
    }

    @Override
    public void setEmptyStateVisible(boolean visible) {
        if (visible) {
            cardIssuerEmptyStateTextView.setVisibility(View.VISIBLE);
        } else {
            cardIssuerEmptyStateTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setProgressBarVisible(boolean visible) {
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void setListener(CardIssuersAdapter.OnCardIssuerSelectedListener listener){
        this.listener = listener;
        if(null != cardIssuersAdapter){
            cardIssuersAdapter.setListener(listener);
        }
    }
}
