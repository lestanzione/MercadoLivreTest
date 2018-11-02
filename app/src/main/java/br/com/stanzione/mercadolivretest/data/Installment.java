package br.com.stanzione.mercadolivretest.data;

import com.google.gson.annotations.SerializedName;

public class Installment {

    @SerializedName("installments")
    private int installments;
    @SerializedName("recommended_message")
    private String message;

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
