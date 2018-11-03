package br.com.stanzione.mercadolivretest.method.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.stanzione.mercadolivretest.R;
import br.com.stanzione.mercadolivretest.data.Method;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MethodsAdapter extends RecyclerView.Adapter<MethodsAdapter.ViewHolder> {

    public interface OnMethodSelectedListener {
        void onMethodSelected(String methodId, String methodName);
    }

    private Context context;
    private List<Method> methodList = new ArrayList<>();
    private OnMethodSelectedListener listener;

    public MethodsAdapter(Context context, OnMethodSelectedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_method, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Method currentMethod = methodList.get(position);

        holder.methodNameTextView.setText(currentMethod.getName());

        Picasso.with(context)
                .load(currentMethod.getSecureThumbnail())
                .fit()
                .centerInside()
                .into(holder.methodLogoImageView);

        holder.constraintLayout.setOnClickListener(view -> listener.onMethodSelected(currentMethod.getId(), currentMethod.getName()));

    }

    @Override
    public int getItemCount() {
        return (null != methodList ? methodList.size() : 0);
    }

    public void setItems(List<Method> methodList) {
        this.methodList = methodList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraintLayout)
        ConstraintLayout constraintLayout;

        @BindView(R.id.methodLogoImageView)
        ImageView methodLogoImageView;

        @BindView(R.id.methodNameTextView)
        TextView methodNameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
