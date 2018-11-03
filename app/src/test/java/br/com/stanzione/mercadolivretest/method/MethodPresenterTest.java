package br.com.stanzione.mercadolivretest.method;

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

import br.com.stanzione.mercadolivretest.data.Method;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MethodPresenterTest {

    @Mock
    private MethodContract.View mockView;
    @Mock
    private MethodContract.Model mockModel;

    private MethodPresenter presenter;

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

        presenter = new MethodPresenter(mockModel);
        presenter.attachView(mockView);
    }

    @Test
    public void withNetworkShouldShowMethodList() {
        List<Method> methodList = new ArrayList();

        when(mockModel.fetchPaymentMethods()).thenReturn(Observable.just(methodList));

        presenter.getPaymentMethods();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).showMethods(methodList);
        verify(mockView, never()).showGeneralMessage();
        verify(mockView, never()).showNetworkMessage();
        verify(mockModel, times(1)).fetchPaymentMethods();

    }

    @Test
    public void withNoNetworkShouldShowNetworkMessage() {
        when(mockModel.fetchPaymentMethods()).thenReturn(Observable.error(new IOException()));

        presenter.getPaymentMethods();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, never()).showMethods(anyList());
        verify(mockView, never()).showGeneralMessage();
        verify(mockView, times(1)).showNetworkMessage();
        verify(mockModel, times(1)).fetchPaymentMethods();

    }

    @Test
    public void withGeneralErrorShouldShowGeneralMessage() {
        when(mockModel.fetchPaymentMethods()).thenReturn(Observable.error(new Throwable()));

        presenter.getPaymentMethods();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, never()).showMethods(anyList());
        verify(mockView, times(1)).showGeneralMessage();
        verify(mockView, never()).showNetworkMessage();
        verify(mockModel, times(1)).fetchPaymentMethods();

    }

}