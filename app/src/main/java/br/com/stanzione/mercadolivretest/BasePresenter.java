package br.com.stanzione.mercadolivretest;

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void dispose();
}
