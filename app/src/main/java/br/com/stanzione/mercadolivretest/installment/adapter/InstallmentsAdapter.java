package br.com.stanzione.mercadolivretest.installment.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.stanzione.mercadolivretest.R;
import br.com.stanzione.mercadolivretest.data.Installment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InstallmentsAdapter extends RecyclerView.Adapter<InstallmentsAdapter.ViewHolder> {

    public interface OnInstallmentSelectedListener {
        void onInstallmentSelected(String installment);
    }

    private Context context;
    private List<Installment> installmentList = new ArrayList<>();
    private InstallmentsAdapter.OnInstallmentSelectedListener listener;

    public InstallmentsAdapter(Context context, InstallmentsAdapter.OnInstallmentSelectedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public InstallmentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_installment, parent, false);
        return new InstallmentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InstallmentsAdapter.ViewHolder holder, int position) {
        Installment currentInstallment = installmentList.get(position);

        holder.installmentTextView.setText(currentInstallment.getMessage());

        holder.constraintLayout.setOnClickListener(view -> listener.onInstallmentSelected(currentInstallment.getMessage()));

    }

    @Override
    public int getItemCount() {
        return (null != installmentList ? installmentList.size() : 0);
    }

    public void setItems(List<Installment> installmentList) {
        this.installmentList = installmentList;
        notifyDataSetChanged();
    }

    public void setListener(OnInstallmentSelectedListener listener){
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraintLayout)
        ConstraintLayout constraintLayout;

        @BindView(R.id.installmentTextView)
        TextView installmentTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
