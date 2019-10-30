package co.id.roni.moviefavoriteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.moviefavoriteapp.R;
import co.id.roni.moviefavoriteapp.model.TVShowFavModel;

public class TVFavoriteAdapter extends RecyclerView.Adapter<TVFavoriteAdapter.TVFavoritViewHolder> {
    private List<TVShowFavModel> tvShowFavModelList;
    private Context context;

    public TVFavoriteAdapter(Context context) {
        tvShowFavModelList = new ArrayList<>();
        this.context = context;
    }

    public void setTvShowFavModelList(List<TVShowFavModel> tvShowFavModelList) {
        this.tvShowFavModelList.clear();
        this.tvShowFavModelList.addAll(tvShowFavModelList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVFavoritViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TVFavoritViewHolder(LayoutInflater.from(context).inflate(R.layout.item_tv_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TVFavoritViewHolder holder, int position) {
        holder.bind(tvShowFavModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return tvShowFavModelList.size();
    }

    class TVFavoritViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title_tv_item)
        TextView tvTitleTvShow;
        @BindView(R.id.tv_overview_tv_item)
        TextView tvOverviewTvShow;
        @BindView(R.id.tv_rate_average)
        TextView tvRateTvShow;
        @BindView(R.id.img_poster_tv_item)
        ImageView imgPosterTvShow;

        TVFavoritViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final TVShowFavModel tvShowFavModel) {
            tvTitleTvShow.setText(tvShowFavModel.getName());
            tvOverviewTvShow.setText(tvShowFavModel.getOverview());
            tvRateTvShow.setText(String.valueOf(tvShowFavModel.getVote_average()));
            Glide.with(itemView.getContext()).load(tvShowFavModel.getPoster_path()).into(imgPosterTvShow);
        }
    }


}
