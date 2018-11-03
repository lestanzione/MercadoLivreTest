package br.com.stanzione.mercadolivretest;

import android.support.design.widget.TabLayout;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.view.View;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TabLayoutSelectedTabAssertion implements ViewAssertion {
    private final int expectedTabPosition;

    public TabLayoutSelectedTabAssertion(int expectedTabPosition) {
        this.expectedTabPosition = expectedTabPosition;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        TabLayout tabLayout = (TabLayout) view;
        assertThat(tabLayout.getSelectedTabPosition(), is(expectedTabPosition));
    }
}
