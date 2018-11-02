package br.com.stanzione.mercadolivretest.payment;

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

import br.com.stanzione.mercadolivretest.R;
import br.com.stanzione.mercadolivretest.cardissuer.CardIssuerFragment;
import br.com.stanzione.mercadolivretest.installment.InstallmentFragment;
import br.com.stanzione.mercadolivretest.method.MethodFragment;
import br.com.stanzione.mercadolivretest.method.adapter.MethodsAdapter;
import br.com.stanzione.mercadolivretest.payment.adapter.ViewPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity implements MethodsAdapter.OnMethodSelectedListener {

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

    private String methodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        setupUi();
    }

    private void setupUi(){
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        methodFragment = new MethodFragment();
        methodFragment.setListener(this);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addOption(methodFragment, getString(R.string.tab_title_method));
        adapter.addOption(new CardIssuerFragment(), getString(R.string.tab_title_issuer));
        adapter.addOption(new InstallmentFragment(), getString(R.string.tab_title_installment));
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
                finish();
                return true;
            default:
                return false;
        }

    }

    @Override
    public void onMethodSelected(String methodId) {
        this.methodId = methodId;
        tabLayout.setScrollPosition(1,0f,true);
        viewPager.setCurrentItem(1);
    }
}
