package br.com.stanzione.mercadolivretest.cardissuer.adapter;

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
import br.com.stanzione.mercadolivretest.data.CardIssuer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CardIssuersAdapter extends RecyclerView.Adapter<CardIssuersAdapter.ViewHolder> {

    public interface OnCardIssuerSelectedListener {
        void onCardIssuerSelected(String cardIssuerId, String cardIssuerName);
    }

    private Context context;
    private List<CardIssuer> cardIssuerList = new ArrayList<>();
    private CardIssuersAdapter.OnCardIssuerSelectedListener listener;

    public CardIssuersAdapter(Context context, CardIssuersAdapter.OnCardIssuerSelectedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public CardIssuersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_card_issuer, parent, false);
        return new CardIssuersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardIssuersAdapter.ViewHolder holder, int position) {
        CardIssuer currentCardIssuer = cardIssuerList.get(position);

        holder.cardIssuerNameTextView.setText(currentCardIssuer.getName());

        Picasso.with(context)
                .load(currentCardIssuer.getSecureThumbnail())
                .fit()
                .centerInside()
                .into(holder.cardIssuerLogoImageView);

        holder.constraintLayout.setOnClickListener(view -> listener.onCardIssuerSelected(currentCardIssuer.getId(), currentCardIssuer.getName()));

    }

    @Override
    public int getItemCount() {
        return (null != cardIssuerList ? cardIssuerList.size() : 0);
    }

    public void setItems(List<CardIssuer> methodList) {
        this.cardIssuerList = methodList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraintLayout)
        ConstraintLayout constraintLayout;

        @BindView(R.id.cardIssuerLogoImageView)
        ImageView cardIssuerLogoImageView;

        @BindView(R.id.cardIssuerNameTextView)
        TextView cardIssuerNameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
