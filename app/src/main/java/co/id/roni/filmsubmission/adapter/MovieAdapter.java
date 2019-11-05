package co.id.roni.filmsubmission.adapter;

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
import co.id.roni.filmsubmission.R;
import co.id.roni.filmsubmission.model.MovieModel;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<MovieModel> movieData = new ArrayList<>();
    private MovieAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setMovieData(ArrayList<MovieModel> movies) {
        movieData.clear();
        movieData.addAll(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movieData.get(position));
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(MovieModel movieData);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title_movie_item)
        TextView tvMovieTitle;
        @BindView(R.id.tv_overview_item)
        TextView tvOverview;
        @BindView(R.id.tv_rate_average)
        TextView tvMovieRate;
        @BindView(R.id.img_poster_item)
        ImageView imgMoviePoster;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final MovieModel movies) {
            if (movies != null) {
                tvMovieTitle.setText(movies.getTitle());
                tvOverview.setText(movies.getOverview());
                tvMovieRate.setText(String.valueOf(movies.getVote_average()));
                Glide.with(itemView.getContext()).load(movies.getPoster_path()).into(imgMoviePoster);
            } else {
                Glide.with(itemView.getContext()).load(android.R.color.darker_gray).into(imgMoviePoster);
                tvMovieTitle.setText("");
                tvOverview.setText(R.string.not_available);
                tvMovieRate.setText(0);
            }

            itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(movieData.get(getAdapterPosition())));
        }
    }
}
