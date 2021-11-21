package com.example.tp6;

import static com.example.tp6.POJO.PojoNameComparator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PojoAdapter extends RecyclerView.Adapter<PojoAdapter.PojoViewHolder> {

    List<POJO> pojoList;
    Context context;

    public PojoAdapter(Context context, List<POJO> pojoList) {
        this.pojoList = pojoList;
        this.context = context;
    }

    @NonNull
    @Override
    public PojoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pojo_item, parent, false);

        return new PojoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PojoViewHolder holder, int position) {
        holder.display(pojoList.get(position));
    }

    @Override
    public int getItemCount() {
        return pojoList.size();
    }

    public void addItem(String pays){
        POJO p = new POJO(pays.trim());
        this.pojoList.add(p);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        this.pojoList.remove(position);
        notifyDataSetChanged();
    }

    public void sortByName(){
        Collections.sort(this.pojoList, PojoNameComparator);
        notifyDataSetChanged();
    }

    public void reverseByName(){
        this.sortByName();
        Collections.reverse(this.pojoList);
        notifyDataSetChanged();
    }


    public void openBrowser(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }


    //View holder
    public static class PojoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView tNom;
        CardView itemCardView;

        public PojoViewHolder(@NonNull View itemView) {
            super(itemView);
            tNom = (TextView) itemView.findViewById(R.id.tNom);
            itemCardView = (CardView) itemView.findViewById(R.id.itemCardView);
            itemCardView.setOnCreateContextMenuListener(this::onCreateContextMenu);
        }


        public void display(POJO pojo){
            tNom.setText(pojo.getNom());
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getBindingAdapterPosition(), R.id.delete, 0, "delete item");
            menu.add(getBindingAdapterPosition(), R.id.gotowiki, 1, "go to wiki");
            /*PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.getMenuInflater().inflate(R.menu.context_menu, popup.getMenu());
            popup.show();*/
        }

    }

}
