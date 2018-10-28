package com.yeoga.realmmemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import io.realm.Realm;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    List<dbModel> realmResults;

    public Adapter(List<dbModel> _realmResults) {
        realmResults = _realmResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);

        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(realmResults.get(position).text);
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        Button button_modify, button_remove;

        ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.textView);
            button_modify = (Button) itemView.findViewById(R.id.button_modify);
            button_remove = (Button) itemView.findViewById(R.id.button_remove);
            button_modify.setOnClickListener(this);
            button_remove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_modify:
                    showModifyDialog();
//                        Model model =MainActivity.models.get(getAdapterPosition());
//
//                        MainActivity.realm.insertOrUpdate(model);
                    break;
                case R.id.button_remove:
                    MainActivity.realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            dbModel dbmodel =MainActivity.realmResults.get(getAdapterPosition());
                            dbmodel.deleteFromRealm();
                            MainActivity.adapter.notifyDataSetChanged();
                        }
                    });
                    break;
            }
        }

        public void showModifyDialog() {
            LayoutInflater inflater = getLayoutInflater();
            final View modifyView = inflater.inflate(R.layout.modify_dialog,null);
            AlertDialog.Builder builder = new AlertDialog.Builder();

        }
    }



}
