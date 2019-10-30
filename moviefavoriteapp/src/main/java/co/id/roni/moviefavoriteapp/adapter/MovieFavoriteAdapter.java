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
import co.id.roni.moviefavoriteapp.model.MovieModel;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavoriteHolder> {
    private List<MovieModel> movieFavModels;
    private Context context;

    public MovieFavoriteAdapter(Context context) {
        movieFavModels = new ArrayList<>();
        this.context = context;
    }


    @NonNull
    @Override
    public MovieFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieFavoriteHolder(LayoutInflater.from(context).inflate(R.layout.item_movie_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFavoriteHolder holder, int position) {
        holder.bind(movieFavModels.get(position));
    }

    @Override
    public int getItemCount() {

        return movieFavModels.size();
    }

    public void setMovieFavModels(List<MovieModel> movieFavs) {
        this.movieFavModels.clear();
        this.movieFavModels.addAll(movieFavs);
        notifyDataSetChanged();
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
        void bind(final MovieModel movies) {
            tvMovieTitle.setText(movies.getTitle());
            tvOverview.setText(movies.getOverview());
            tvMovieRate.setText(String.valueOf(movies.getVote_average()));
            Glide.with(context).load(movies.getPoster_path()).into(imgMoviePoster);
        }

    }
}
