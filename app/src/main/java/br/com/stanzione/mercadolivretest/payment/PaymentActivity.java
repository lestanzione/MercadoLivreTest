package br.com.stanzione.mercadolivretest.payment;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.stanzione.mercadolivretest.R;
import br.com.stanzione.mercadolivretest.cardissuer.CardIssuerFragment;
import br.com.stanzione.mercadolivretest.installment.InstallmentFragment;
import br.com.stanzione.mercadolivretest.method.MethodFragment;
import br.com.stanzione.mercadolivretest.payment.adapter.ViewPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

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

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addOption(new MethodFragment(), getString(R.string.tab_title_method));
        adapter.addOption(new CardIssuerFragment(), getString(R.string.tab_title_issuer));
        adapter.addOption(new InstallmentFragment(), getString(R.string.tab_title_installment));
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager, true);
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
}
