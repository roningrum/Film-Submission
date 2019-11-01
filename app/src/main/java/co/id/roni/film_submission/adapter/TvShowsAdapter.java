package co.id.roni.film_submission.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.model.TVShowModel;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowViewHolder> {
    private ArrayList<TVShowModel> tvSData = new ArrayList<>();
    private TvShowsAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setTvData(ArrayList<TVShowModel> tvs) {
        tvSData.clear();
        tvSData.addAll(tvs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TvShowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        holder.bind(tvSData.get(position));
    }

    @Override
    public int getItemCount() {
        return tvSData.size();
    }


    public interface OnItemClickCallback {
        void onItemClicked(TVShowModel tvShowModel);
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title_tv_item)
        TextView tvTitleTvShow;
        @BindView(R.id.tv_overview_tv_item)
        TextView tvOverviewTvShow;
        @BindView(R.id.tv_rate_average)
        TextView tvRateTvShow;
        @BindView(R.id.img_poster_tv_item)
        ImageView imgPosterTvShow;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final TVShowModel tvs) {
            tvTitleTvShow.setText(tvs.getName());
            tvOverviewTvShow.setText(tvs.getOverview());
            tvRateTvShow.setText(String.valueOf(tvs.getVote_average()));
            Glide.with(itemView.getContext()).load(tvs.getPoster_path()).into(imgPosterTvShow);
            itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(tvSData.get(getAdapterPosition())));
        }
    }
}
