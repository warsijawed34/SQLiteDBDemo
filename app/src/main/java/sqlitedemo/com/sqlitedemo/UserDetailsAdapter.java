package sqlitedemo.com.sqlitedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class of BuyCoupon
 */

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.MyViewHolder> {
    List<UserDetails> userDetailsList;
    Context mContext;

    public UserDetailsAdapter(Context context, ArrayList<UserDetails> arrayList) {
        this.mContext = context;
        this.userDetailsList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.user_details_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
      final UserDetails model=userDetailsList.get(position);
        holder.tvName.setText(model.getName());
        holder.tvPhone.setText(model.getPhone());
        holder.tvAddress.setText(model.getAddress());
    }

    @Override
    public int getItemCount() {
        return userDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPhone, tvAddress;
        public ImageView ivPhoto;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPhone= (TextView) itemView.findViewById(R.id.tvPhone);
            tvAddress= (TextView) itemView.findViewById(R.id.tvAddress);
            ivPhoto= (ImageView) itemView.findViewById(R.id.ivPhoto);
        }
    }
}

