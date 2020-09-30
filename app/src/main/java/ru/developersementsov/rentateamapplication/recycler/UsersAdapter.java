package ru.developersementsov.rentateamapplication.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.developersementsov.rentateamapplication.model.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private List<User> mUsers;
    private Context mContext;
    private UserClickListener mUserClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView titleTv;
        UserClickListener mItemListener;

        public ViewHolder(View itemView, UserClickListener userClickListener) {
            super(itemView);
            titleTv = itemView.findViewById(android.R.id.text1);

            this.mItemListener = userClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            User user = getItem(getAdapterPosition());
            this.mItemListener.onUserClick(user.getId());

            //notifyDataSetChanged();
        }
    }

    public UsersAdapter(Context context, List<User> users, UserClickListener itemListener) {
        mUsers = users;
        mContext = context;
        mUserClickListener = itemListener;
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mUserClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder holder, int position) {

        User user = mUsers.get(position);
        TextView textView = holder.titleTv;
        textView.append(user.getFirstName() + " " + user.getLastName());
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void updateAnswers(List<User> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    private User getItem(int adapterPosition) {
        return mUsers.get(adapterPosition);
    }

    public interface UserClickListener {
        void onUserClick(long id);
    }
}