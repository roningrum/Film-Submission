package co.id.roni.film_submission;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {
    private List<Genre> genreList = new ArrayList<>();

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GenreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre_movies, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        holder.bind(genreList.get(position));

    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {
        private TextView genreMovieItem;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            genreMovieItem = itemView.findViewById(R.id.genre_text);
        }

        void bind(Genre genre) {
            genreMovieItem.setText(genre.getName());
        }
    }
}
