package co.id.roni.filmsubmission.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.roni.filmsubmission.R;
import co.id.roni.filmsubmission.model.Genre;

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

    class GenreViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.genre_text)
        TextView genreMovieItem;

        GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Genre genre) {
            genreMovieItem.setText(genre.getName());
        }
    }
}
