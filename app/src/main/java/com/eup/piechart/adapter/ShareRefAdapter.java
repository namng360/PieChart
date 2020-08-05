package com.eup.piechart.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eup.piechart.R;
import com.eup.piechart.model.ShareRefModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ShareRefAdapter extends RecyclerView.Adapter<ShareRefAdapter.ViewHolder> {
    private ArrayList<ShareRefModel> shareRefModels;
    private Context context;
    public Delete delete;

    public ShareRefAdapter(ArrayList<ShareRefModel> shareRefModels, Context context) {
        this.shareRefModels = shareRefModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share_ref, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ShareRefModel shareRefModel = shareRefModels.get(position);

        holder.mTextViewLine1.setText(shareRefModel.getLine1());
        holder.mTextViewLine2.setText(shareRefModel.getLine2());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareRefModels.remove(position);
                SharedPreferences settings = context.getSharedPreferences("sharedpreferences", Context.MODE_PRIVATE);
                settings.edit().putString("task list", new Gson().toJson(shareRefModels)).commit();
                delete.onDelete();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return shareRefModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewLine1;
        public TextView mTextViewLine2;
        private ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewLine1 = itemView.findViewById(R.id.textview_line1);
            mTextViewLine2 = itemView.findViewById(R.id.textview_line_2);
            img = (ImageView) itemView.findViewById(R.id.img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int pos = getAdapterPosition();
                    String m1 = mTextViewLine1.getText().toString().trim();
                    Toast.makeText(context, m1, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public interface Delete {
        void onDelete();
    }
}
