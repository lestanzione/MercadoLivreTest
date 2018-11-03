package br.com.stanzione.mercadolivretest.installment;

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

import br.com.stanzione.mercadolivretest.data.Installment;
import br.com.stanzione.mercadolivretest.data.InstallmentResponse;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InstallmentPresenterTest {

    @Mock
    private InstallmentContract.View mockView;
    @Mock
    private InstallmentContract.Model mockModel;

    private InstallmentPresenter presenter;

    private double amount = 0.01;
    private String methodId = "fake-method-id";
    private String cardIssuerId = "fake-card-issuer-id";

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

        presenter = new InstallmentPresenter(mockModel);
        presenter.attachView(mockView);
    }

    @Test
    public void withNetworkShouldShowMethodList() {

        List<InstallmentResponse> installmentResponseList = new ArrayList<>();
        List<Installment> installmentList = new ArrayList();
        InstallmentResponse installmentResponse = new InstallmentResponse();
        installmentResponse.setInstallmentList(installmentList);
        installmentResponseList.add(installmentResponse);

        when(mockModel.fetchInstallments(anyDouble(), anyString(), anyString())).thenReturn(Observable.just(installmentResponseList));

        presenter.getInstallments(amount, methodId, cardIssuerId);

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).showInstallments(installmentResponseList.get(0).getInstallmentList());
        verify(mockView, never()).showGeneralMessage();
        verify(mockView, never()).showNetworkMessage();
        verify(mockModel, times(1)).fetchInstallments(amount, methodId, cardIssuerId);

    }

    @Test
    public void withNoNetworkShouldShowNetworkMessage() {
        when(mockModel.fetchInstallments(anyDouble(), anyString(), anyString())).thenReturn(Observable.error(new IOException()));

        presenter.getInstallments(amount, methodId, cardIssuerId);

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, never()).showInstallments(anyList());
        verify(mockView, never()).showGeneralMessage();
        verify(mockView, times(1)).showNetworkMessage();
        verify(mockModel, times(1)).fetchInstallments(amount, methodId, cardIssuerId);

    }

    @Test
    public void withGeneralErrorShouldShowGeneralMessage() {
        when(mockModel.fetchInstallments(anyDouble(), anyString(), anyString())).thenReturn(Observable.error(new Throwable()));

        presenter.getInstallments(amount, methodId, cardIssuerId);

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, never()).showInstallments(anyList());
        verify(mockView, times(1)).showGeneralMessage();
        verify(mockView, never()).showNetworkMessage();
        verify(mockModel, times(1)).fetchInstallments(amount, methodId, cardIssuerId);

    }

}