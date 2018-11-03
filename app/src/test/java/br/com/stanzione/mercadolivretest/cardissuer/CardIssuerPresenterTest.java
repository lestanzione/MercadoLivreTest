package br.com.stanzione.mercadolivretest.cardissuer;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.stanzione.mercadolivretest.data.CardIssuer;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CardIssuerPresenterTest {

    @Mock
    private CardIssuerContract.View mockView;
    @Mock
    private CardIssuerContract.Model mockModel;

    private CardIssuerPresenter presenter;

    private String methodId = "fake-method-id";

    @BeforeClass
    public static void setupRxSchedulers() {

        Scheduler immediate = new Scheduler() {

            @Override
            public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);

    }

    @AfterClass
    public static void tearDownRxSchedulers(){
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        presenter = new CardIssuerPresenter(mockModel);
        presenter.attachView(mockView);
    }

    @Test
    public void withNoEmptyListShouldShowMethodList() {
        List<CardIssuer> cardIssuerList = new ArrayList();
        cardIssuerList.add(new CardIssuer());

        when(mockModel.fetchCardIssuers(anyString())).thenReturn(Observable.just(cardIssuerList));

        presenter.getCardIssuers(methodId);

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setEmptyStateVisible(false);
        verify(mockView, never()).setEmptyStateVisible(true);
        verify(mockView, times(1)).showCardIssuers(cardIssuerList);
        verify(mockView, never()).showGeneralMessage();
        verify(mockView, never()).showNetworkMessage();
        verify(mockModel, times(1)).fetchCardIssuers(methodId);

    }

    @Test
    public void withEmptyListShouldShowEmptyState() {
        List<CardIssuer> cardIssuerList = new ArrayList();

        when(mockModel.fetchCardIssuers(anyString())).thenReturn(Observable.just(cardIssuerList));

        presenter.getCardIssuers(methodId);

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setEmptyStateVisible(false);
        verify(mockView, times(1)).setEmptyStateVisible(true);
        verify(mockView, never()).showCardIssuers(anyList());
        verify(mockView, never()).showGeneralMessage();
        verify(mockView, never()).showNetworkMessage();
        verify(mockModel, times(1)).fetchCardIssuers(methodId);

    }

    @Test
    public void withNoNetworkShouldShowNetworkMessage() {
        when(mockModel.fetchCardIssuers(anyString())).thenReturn(Observable.error(new IOException()));

        presenter.getCardIssuers(methodId);

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setEmptyStateVisible(false);
        verify(mockView, never()).setEmptyStateVisible(true);
        verify(mockView, never()).showCardIssuers(anyList());
        verify(mockView, never()).showGeneralMessage();
        verify(mockView, times(1)).showNetworkMessage();
        verify(mockModel, times(1)).fetchCardIssuers(methodId);

    }

    @Test
    public void withGeneralErrorShouldShowGeneralMessage() {
        when(mockModel.fetchCardIssuers(anyString())).thenReturn(Observable.error(new Throwable()));

        presenter.getCardIssuers(methodId);

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).setEmptyStateVisible(false);
        verify(mockView, never()).setEmptyStateVisible(true);
        verify(mockView, never()).showCardIssuers(anyList());
        verify(mockView, times(1)).showGeneralMessage();
        verify(mockView, never()).showNetworkMessage();
        verify(mockModel, times(1)).fetchCardIssuers(methodId);

    }

}