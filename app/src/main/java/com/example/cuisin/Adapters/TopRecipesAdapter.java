package com.example.cuisin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuisin.Api.Result;
import com.example.cuisin.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopRecipesAdapter extends RecyclerView.Adapter<TopRecipesAdapter.ViewHolder> {

    Context ctx;
    List<Result> list; //lijst van alle eigenschappen van het recept
    LayoutInflater inflater; // om recepten in een layout te steken

    //constructor
    public TopRecipesAdapter(Context ctx, List<Result> list, ItemClickListener itemClickListener) {
        this.ctx = ctx;
        this.list = list;
        this.inflater = LayoutInflater.from(ctx);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public TopRecipesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.top_recipes, parent, false); // voor elk recept die wordt opgehaald uit de API wordt er een layout geinflate.

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRecipesAdapter.ViewHolder holder, int position) {

        Picasso.get().load(list.get(position).image).into(holder.recipeImage); //afbeelding van het recept binden
        holder.top_recipe.setText(list.get(position).title); //titel van het recept binden
        holder.recipe_prep.setText("Ready in " + list.get(position).readyInMinutes + " minutes!"); //preparatie tijd binden

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //we geven de id mee van het recept
    public interface ItemClickListener
    {
        void onItemClick(String recipeId);
    }
    public ItemClickListener itemClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        FrameLayout recipeLayout;
        ShapeableImageView recipeImage;
        TextView top_recipe, recipe_prep;

        public ViewHolder(@NonNull View itemView) {
//          declaratie van de id's in de top_recipes.xml bestand
            super(itemView);
            recipeLayout = itemView.findViewById(R.id.recipeLayout);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            top_recipe = itemView.findViewById((R.id.top_recipe));
            recipe_prep = itemView.findViewById(R.id.recipe_prep);
        }
    }
}
