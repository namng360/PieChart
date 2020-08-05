//package com.eup.piechart.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.eup.piechart.model.Model;
//import com.eup.piechart.R;
//
//import java.util.List;
//
//public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
//    private Context context;
//    private List<Model> models;
//
//    public Adapter(Context context, List<Model> models) {
//        this.context = context;
//        this.models = models;
//    }
//
//    @NonNull
//    @Override
//    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
//        return new Holder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull Holder holder, int position) {
//        holder.tv.setText(models.get(position).getTv());
//    }
//
//    @Override
//    public int getItemCount() {
//        return models.size();
//    }
//
//    class Holder extends RecyclerView.ViewHolder{
//        private TextView tv;
//
//
//        public Holder(@NonNull View itemView) {
//            super(itemView);
//            tv = (TextView) itemView.findViewById(R.id.tv);
//
//        }
//    }
//}
