package br.com.stanzione.mercadolivretest.main;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.inject.Inject;

import br.com.stanzione.mercadolivretest.App;
import br.com.stanzione.mercadolivretest.Configs;
import br.com.stanzione.mercadolivretest.R;
import br.com.stanzione.mercadolivretest.payment.PaymentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.amountEditText)
    EditText amountEditText;

    @BindView(R.id.payButton)
    Button payButton;

    @Inject
    MainContract.Presenter presenter;

    private NumberFormat nf;
    private DecimalFormat df;

    private boolean mEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUi();
        setupPresenter();
    }

    private void setupUi(){
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_main);

        nf = NumberFormat.getCurrencyInstance();
        df = (DecimalFormat) nf;
        df.applyPattern("#,##0.00");

        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                amountEditText.removeTextChangedListener(this);

                String digits = editable.toString().replaceAll("\\D", "");

                try{
                    String formatted = df.format(Double.parseDouble(digits)/100);
                    editable.replace(0, editable.length(), formatted);
                } catch (NumberFormatException nfe) {
                    editable.clear();
                }

                amountEditText.addTextChangedListener(this);
            }
        });
    }

    private void setupPresenter(){
        ((App) getApplicationContext())
                .getApplicationComponent()
                .inject(this);

        presenter.attachView(this);
    }

    @OnClick(R.id.payButton)
    public void payButtonClicked(){
        hideKeyboard();
        presenter.validateAmount(Double.parseDouble(amountEditText.getText().toString()));
    }

    @Override
    public void showInvalidAmountMessage() {
        Snackbar.make(coordinatorLayout, R.string.message_invalid_amount, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void navigateToPayment(double amount) {
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(Configs.ARG_AMOUNT, amount);
        startActivity(intent);
    }

    @Override
    public void setProgressBarVisible(boolean visible) {

    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
