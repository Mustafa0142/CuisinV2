package com.example.cuisin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuisin.Api.Result;
import com.example.cuisin.R;
import java.util.List;

public class TopRecipesAdapter extends RecyclerView.Adapter<TopRecipesAdapter.ViewHolder> {

    Context ctx;
    List<Result> list;
    LayoutInflater inflater; // om recepten in een layout te steken

    public TopRecipesAdapter(Context ctx, List<Result> list) {
        this.ctx = ctx;
        this.list = list;
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public TopRecipesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.top_recipes, parent, false); // voor elk recept die wordt opgehaald uit de API wordt er een layout geinflate.

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRecipesAdapter.ViewHolder holder, int position) {
        holder.top_recipe.setText(list.get(position).title); // de geinflate layout wordt momenteel enkel gevuld met de titel van het recept.
        //holder.top_recipe.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView top_recipe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            top_recipe = itemView.findViewById((R.id.top_recipe));
        }
    }
}
