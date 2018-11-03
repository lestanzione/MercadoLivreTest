package br.com.stanzione.mercadolivretest.payment;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;

import br.com.stanzione.mercadolivretest.App;
import br.com.stanzione.mercadolivretest.Configs;
import br.com.stanzione.mercadolivretest.R;
import br.com.stanzione.mercadolivretest.RecyclerViewItemCountAssertion;
import br.com.stanzione.mercadolivretest.TabLayoutSelectedTabAssertion;
import br.com.stanzione.mercadolivretest.di.ApplicationComponent;
import br.com.stanzione.mercadolivretest.di.DaggerApplicationComponent;
import br.com.stanzione.mercadolivretest.di.MockNetworkModule;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class PaymentActivityTest {

    @Rule
    public ActivityTestRule<PaymentActivity> activityRule = new ActivityTestRule<>(PaymentActivity.class, true, false);

    private MockWebServer server = new MockWebServer();

    @Before
    public void setUp() throws Exception {
        setUpServer();
        server.start();

        Intent intent = new Intent();
        intent.putExtra(Configs.ARG_AMOUNT, 130.80);

        activityRule.launchActivity(intent);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    public void setUpServer() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new MockNetworkModule(server))
                .build();

        ((App) getTargetContext().getApplicationContext())
                .setApplicationComponent(applicationComponent);
    }

    @Test
    public void withAmountShouldShowMethodList() throws IOException, InterruptedException {

        server.enqueue(new MockResponse()
                .setBody(readFile("methods_response.json")));

        methodTabVerification();
        methodRecyclerViewVerification();
    }

    @Test
    public void withSelectedMethodShouldShowCardIssuerList() throws IOException, InterruptedException {

        server.enqueue(new MockResponse()
                .setBody(readFile("methods_response.json")));

        server.enqueue(new MockResponse()
                .setBody(readFile("card_issuers_response.json")));

        methodTabVerification();
        methodRecyclerViewVerification();
        methodTabItemSelection();
        cardIssuerTabVerification();
        cardIssuerRecyclerViewVerification();
    }

    @Test
    public void withSelectedMethodAndEmptyCardIssuerShouldShowCardIssuerEmptyState() throws IOException, InterruptedException {

        server.enqueue(new MockResponse()
                .setBody(readFile("methods_response.json")));

        server.enqueue(new MockResponse()
                .setBody(readFile("empty_card_issuers_response.json")));

        methodTabVerification();
        methodRecyclerViewVerification();
        methodTabItemSelection();
        cardIssuerTabVerification();
        cardIssuerEmptyStateVerification();
    }

    @Test
    public void withSelectedCardIssuerShouldShowInstallmentList() throws IOException, InterruptedException {

        server.enqueue(new MockResponse()
                .setBody(readFile("methods_response.json")));

        server.enqueue(new MockResponse()
                .setBody(readFile("card_issuers_response.json")));

        server.enqueue(new MockResponse()
                .setBody(readFile("installment_response.json")));

        methodTabVerification();
        methodRecyclerViewVerification();
        methodTabItemSelection();
        cardIssuerTabVerification();
        cardIssuerRecyclerViewVerification();
        cardIssuerTabItemSelection();
        installmentsTabVerification();
        installmentRecyclerViewVerification();
    }

    @Test
    public void withBackPressedInInstallmentShouldShowPreviousTab() throws IOException, InterruptedException {

        server.enqueue(new MockResponse()
                .setBody(readFile("methods_response.json")));

        server.enqueue(new MockResponse()
                .setBody(readFile("card_issuers_response.json")));

        server.enqueue(new MockResponse()
                .setBody(readFile("installment_response.json")));

        methodTabVerification();
        methodRecyclerViewVerification();
        methodTabItemSelection();
        cardIssuerTabVerification();
        cardIssuerRecyclerViewVerification();
        cardIssuerTabItemSelection();
        installmentsTabVerification();
        installmentRecyclerViewVerification();

        pressBack();

        cardIssuerTabVerification();
    }

    @Test
    public void withBackPressedInCardIssuerShouldShowPreviousTab() throws IOException, InterruptedException {

        server.enqueue(new MockResponse()
                .setBody(readFile("methods_response.json")));

        server.enqueue(new MockResponse()
                .setBody(readFile("card_issuers_response.json")));

        methodTabVerification();
        methodRecyclerViewVerification();
        methodTabItemSelection();
        cardIssuerTabVerification();
        cardIssuerRecyclerViewVerification();

        pressBack();

        methodTabVerification();
    }

    @Test
    public void withNoConnectionInMethodShouldShowNetworkError() throws IOException, InterruptedException {

        server.shutdown();

        methodTabVerification();
        networkMessageVerification();

    }

    @Test
    public void withNoConnectionInCardIssuerShouldShowNetworkError() throws IOException, InterruptedException {

        server.enqueue(new MockResponse()
                .setBody(readFile("methods_response.json")));

        methodTabVerification();
        methodRecyclerViewVerification();
        methodTabItemSelection();

        server.shutdown();

        cardIssuerTabVerification();
        networkMessageVerification();

    }

    @Test
    public void withNoConnectionInInstallmentShouldShowNetworkError() throws IOException, InterruptedException {

        server.enqueue(new MockResponse()
                .setBody(readFile("methods_response.json")));

        server.enqueue(new MockResponse()
                .setBody(readFile("card_issuers_response.json")));

        methodTabVerification();
        methodRecyclerViewVerification();
        methodTabItemSelection();
        cardIssuerTabVerification();
        cardIssuerRecyclerViewVerification();
        cardIssuerTabItemSelection();

        server.shutdown();

        installmentsTabVerification();
        networkMessageVerification();

    }

    @Test
    public void withGeneralErrorInMethodShouldShowGeneralError() throws InterruptedException {

        server.enqueue(new MockResponse()
                .setResponseCode(500));

        methodTabVerification();
        generalMessageVerification();

    }

    @Test
    public void withGeneralErrorInCardIssuerShouldShowGeneralError() throws IOException, InterruptedException {

        server.enqueue(new MockResponse()
                .setBody(readFile("methods_response.json")));

        server.enqueue(new MockResponse()
                .setResponseCode(500));

        methodTabVerification();
        methodRecyclerViewVerification();
        methodTabItemSelection();
        cardIssuerTabVerification();
        generalMessageVerification();

    }

    @Test
    public void withGeneralErrorInInstallmentShouldShowGeneralError() throws IOException, InterruptedException {

        server.enqueue(new MockResponse()
                .setBody(readFile("methods_response.json")));

        server.enqueue(new MockResponse()
                .setBody(readFile("card_issuers_response.json")));

        server.enqueue(new MockResponse()
                .setResponseCode(500));

        methodTabVerification();
        methodRecyclerViewVerification();
        methodTabItemSelection();
        cardIssuerTabVerification();
        cardIssuerRecyclerViewVerification();
        cardIssuerTabItemSelection();
        installmentsTabVerification();
        generalMessageVerification();

    }

    private void methodTabVerification() throws InterruptedException {
        onView(withId(R.id.tabLayout))
                .check(new TabLayoutSelectedTabAssertion(0));

        Thread.sleep(1000);
    }

    private void methodRecyclerViewVerification(){
        onView(withId(R.id.methodsRecyclerView))
                .check(new RecyclerViewItemCountAssertion(21));
    }

    private void methodTabItemSelection() throws InterruptedException {
        onView(withId(R.id.methodsRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Thread.sleep(1000);
    }

    private void cardIssuerTabVerification() throws InterruptedException {
        onView(withId(R.id.tabLayout))
                .check(new TabLayoutSelectedTabAssertion(1));

        Thread.sleep(1000);
    }

    private void cardIssuerRecyclerViewVerification(){
        onView(withId(R.id.cardIssuersRecyclerView))
                .check(new RecyclerViewItemCountAssertion(32));
        onView(withId(R.id.cardIssuerEmptyStateTextView))
                .check(matches(not(isDisplayed())));
    }

    private void cardIssuerEmptyStateVerification(){
        onView(withId(R.id.cardIssuersRecyclerView))
                .check(new RecyclerViewItemCountAssertion(0));
        onView(withId(R.id.cardIssuerEmptyStateTextView))
                .check(matches(isDisplayed()));
    }

    private void cardIssuerTabItemSelection() throws InterruptedException {
        onView(withId(R.id.cardIssuersRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Thread.sleep(1000);
    }

    private void installmentsTabVerification() throws InterruptedException {
        onView(withId(R.id.tabLayout))
                .check(new TabLayoutSelectedTabAssertion(2));

        Thread.sleep(1000);
    }

    private void installmentRecyclerViewVerification(){
        onView(withId(R.id.installmentsRecyclerView))
                .check(new RecyclerViewItemCountAssertion(5));
    }

    private void networkMessageVerification(){
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.message_network_error)))
                .check(matches(isDisplayed()));
    }

    private void generalMessageVerification(){
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.message_general_error)))
                .check(matches(isDisplayed()));
    }

    private String readFile(String fileName) throws IOException {
        InputStream stream = InstrumentationRegistry.getContext()
                .getAssets()
                .open(fileName);

        Source source = Okio.source(stream);
        BufferedSource buffer = Okio.buffer(source);

        return buffer.readUtf8();
    }

}