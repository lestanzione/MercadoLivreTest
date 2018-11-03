package br.com.stanzione.mercadolivretest.main;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MainPresenterTest {

    @Mock
    private MainContract.View mockView;

    private MainPresenter presenter;

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

        presenter = new MainPresenter();
        presenter.attachView(mockView);
    }

    @Test
    public void withValidAmountShouldPass(){

        double validAmount = 0.01;

        presenter.validateAmount(validAmount);

        verify(mockView, times(1)).navigateToPayment(validAmount);
        verify(mockView, never()).showInvalidAmountMessage();

    }

    @Test
    public void withZeroAmountShouldShowMessage(){

        double zeroAmount = 0.00;

        presenter.validateAmount(zeroAmount);

        verify(mockView, never()).navigateToPayment(anyDouble());
        verify(mockView, times(1)).showInvalidAmountMessage();

    }

    @Test
    public void withNegativeAmountShouldShowMessage(){

        double negativeAmount = -0.01;

        presenter.validateAmount(negativeAmount);

        verify(mockView, never()).navigateToPayment(anyDouble());
        verify(mockView, times(1)).showInvalidAmountMessage();

    }

}