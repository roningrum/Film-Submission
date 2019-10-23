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
import co.id.roni.film_submission.model.Cast;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastMovieViewHolder> {
    private ArrayList<Cast> castMovieList = new ArrayList<>();

    public void setCastMovieList(ArrayList<Cast> castMovieList) {
        this.castMovieList.addAll(castMovieList);
    }

    @NonNull
    @Override
    public CastAdapter.CastMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastMovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit_cast, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.CastMovieViewHolder holder, int position) {
        holder.bind(castMovieList.get(position));
    }

    @Override
    public int getItemCount() {
        return castMovieList.size();
    }


    class CastMovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cast)
        TextView movieCastName;
        @BindView(R.id.img_cast)
        ImageView imgMovieCast;

        CastMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Cast cast) {
            movieCastName.setText(cast.getName());
            Glide.with(itemView.getContext()).load(cast.getProfile_path()).into(imgMovieCast);
        }
    }
}
