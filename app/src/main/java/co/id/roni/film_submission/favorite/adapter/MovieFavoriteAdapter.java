package co.id.roni.film_submission.favorite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.film_submission.R;
import co.id.roni.film_submission.favorite.movie.MovieFavModel;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavoriteHolder> {
    private List<MovieFavModel> movieFavModels;
    private MovieFavoriteAdapter.OnItemClickCallback onItemClickCallback;

    public MovieFavoriteAdapter(List<MovieFavModel> movieFavModels) {
        this.movieFavModels = movieFavModels;
    }

    public void setOnItemClickCallback(MovieFavoriteAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MovieFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieFavoriteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_fav_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFavoriteHolder holder, int position) {
        holder.bind(movieFavModels.get(position));
    }

    @Override
    public int getItemCount() {
        return movieFavModels.size();
    }

    public void setMovieFavModels(List<MovieFavModel> movieFavs) {
        this.movieFavModels.clear();
        this.movieFavModels.addAll(movieFavs);
        notifyDataSetChanged();
    }

    public interface OnItemClickCallback {
        void onItemClicked(MovieFavModel movieData);
    }

    class MovieFavoriteHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title_movie_item)
        TextView tvMovieTitle;
        @BindView(R.id.tv_overview_item)
        TextView tvOverview;
        @BindView(R.id.tv_rate_average)
        TextView tvMovieRate;
        @BindView(R.id.img_poster_item)
        ImageView imgMoviePoster;

        MovieFavoriteHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final MovieFavModel movies) {
            tvMovieTitle.setText(movies.getTitle());
            tvOverview.setText(movies.getOverview());
            tvMovieRate.setText(String.valueOf(movies.getVote_average()));
            Glide.with(itemView.getContext()).load(movies.getPoster_path()).into(imgMoviePoster);
            itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(movieFavModels.get(getAdapterPosition())));
        }

    }
}
