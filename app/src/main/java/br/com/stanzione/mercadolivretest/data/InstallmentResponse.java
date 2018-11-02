package br.com.stanzione.mercadolivretest.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstallmentResponse {

    @SerializedName("payer_costs")
    private
    List<Installment> installmentList;


    public List<Installment> getInstallmentList() {
        return installmentList;
    }

    public void setInstallmentList(List<Installment> installmentList) {
        this.installmentList = installmentList;
    }
}
