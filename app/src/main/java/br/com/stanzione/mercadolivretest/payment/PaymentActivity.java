package br.com.stanzione.mercadolivretest.payment;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import br.com.stanzione.mercadolivretest.Configs;
import br.com.stanzione.mercadolivretest.R;
import br.com.stanzione.mercadolivretest.cardissuer.CardIssuerFragment;
import br.com.stanzione.mercadolivretest.cardissuer.adapter.CardIssuersAdapter;
import br.com.stanzione.mercadolivretest.installment.InstallmentFragment;
import br.com.stanzione.mercadolivretest.installment.adapter.InstallmentsAdapter;
import br.com.stanzione.mercadolivretest.method.MethodFragment;
import br.com.stanzione.mercadolivretest.method.adapter.MethodsAdapter;
import br.com.stanzione.mercadolivretest.payment.adapter.ViewPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity implements MethodsAdapter.OnMethodSelectedListener, CardIssuersAdapter.OnCardIssuerSelectedListener, InstallmentsAdapter.OnInstallmentSelectedListener {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private MethodFragment methodFragment;
    private CardIssuerFragment cardIssuerFragment;
    private InstallmentFragment installmentFragment;

    private double amount;
    private String methodId;
    private String methodName;
    private String cardIssuerId;
    private String cardIssuerName;
    private String installment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        amount = getIntent().getDoubleExtra(Configs.ARG_AMOUNT, 0.00);

        setupUi();
    }

    private void setupUi(){
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        methodFragment = new MethodFragment();
        methodFragment.setListener(this);
        methodFragment.setAmount(amount);

        cardIssuerFragment = new CardIssuerFragment();
        cardIssuerFragment.setListener(this);

        installmentFragment = new InstallmentFragment();
        installmentFragment.setListener(this);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addOption(methodFragment, getString(R.string.tab_title_method));
        adapter.addOption(cardIssuerFragment, getString(R.string.tab_title_issuer));
        adapter.addOption(installmentFragment, getString(R.string.tab_title_installment));
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager, true);

        disableTabLayoutAndViewPager();
    }

    private void disableTabLayoutAndViewPager() {
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

        viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED);
                finish();
                return true;
            default:
                return false;
        }

    }

    @Override
    public void onMethodSelected(String methodId, String methodName) {
        this.methodId = methodId;
        this.methodName = methodName;
        cardIssuerFragment.setMethodId(methodId);
        tabLayout.setScrollPosition(1,0f,true);
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onCardIssuerSelected(String cardIssuerId, String cardIssuerName) {
        this.cardIssuerId = cardIssuerId;
        this.cardIssuerName = cardIssuerName;
        installmentFragment.setAmount(amount);
        installmentFragment.setMethodId(methodId);
        installmentFragment.setCardIssuerId(cardIssuerId);
        tabLayout.setScrollPosition(2,0f,true);
        viewPager.setCurrentItem(2);
    }

    @Override
    public void onInstallmentSelected(String installment) {
        this.installment = installment;

        Intent returnIntent = new Intent();
        returnIntent.putExtra(Configs.ARG_METHOD, methodName);
        returnIntent.putExtra(Configs.ARG_CARD_ISSUER, cardIssuerName);
        returnIntent.putExtra(Configs.ARG_INSTALLMENT, installment);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1,true);
        }else{
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
    }

}
