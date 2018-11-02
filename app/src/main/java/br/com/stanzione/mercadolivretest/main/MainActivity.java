package br.com.stanzione.mercadolivretest.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import br.com.stanzione.mercadolivretest.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.amountEditText)
    EditText amountEditText;

    @BindView(R.id.payButton)
    Button payButton;

    private NumberFormat nf;
    private DecimalFormat df;

    private boolean mEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUi();
    }

    private void setupUi(){
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mercado Livre");

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

    @OnClick(R.id.payButton)
    public void payButtonClicked(){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
    }

}
