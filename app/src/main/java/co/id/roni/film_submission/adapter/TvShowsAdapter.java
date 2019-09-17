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

import co.id.roni.film_submission.R;
import co.id.roni.film_submission.model.TvShowModel;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowViewHolder> {
    private ArrayList<TvShowModel> tvSData = new ArrayList<>();
//    private TvShowsAdapter.OnItemClickCallback onItemClickCallback;

//    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback;
//    }

    public void setMovieData(ArrayList<TvShowModel> tvs) {
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

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitleTvShow;
        private TextView tvOverviewTvShow;
        private TextView tvRateTvShow;
        private ImageView imgPosterTvShow;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleTvShow = itemView.findViewById(R.id.tv_title_tv_item);
            tvOverviewTvShow = itemView.findViewById(R.id.tv_overview_tv_item);
            tvRateTvShow = itemView.findViewById(R.id.tv_rate_average);
            imgPosterTvShow = itemView.findViewById(R.id.img_poster_tv_item);
        }

        void bind(final TvShowModel tvs) {
            tvTitleTvShow.setText(tvs.getName());
            tvOverviewTvShow.setText(tvs.getOverview());
            tvRateTvShow.setText(String.valueOf(tvs.getVote_average()));
            Glide.with(itemView.getContext()).load(tvs.getPoster_path()).into(imgPosterTvShow);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickCallback.onItemClicked(movieData.get(getAdapterPosition()));
//                }
//            });
        }
    }
//
//    public interface OnItemClickCallback {
//        void onItemClicked(MovieModel movieData);
//    }
}
