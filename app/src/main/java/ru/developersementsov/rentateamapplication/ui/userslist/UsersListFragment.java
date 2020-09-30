package ru.developersementsov.rentateamapplication.ui.userslist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ru.developersementsov.rentateamapplication.R;
import ru.developersementsov.rentateamapplication.db.UserDB;
import ru.developersementsov.rentateamapplication.recycler.UsersAdapter;
import ru.developersementsov.rentateamapplication.model.AnswersResponse;
import ru.developersementsov.rentateamapplication.model.User;
import ru.developersementsov.rentateamapplication.retrofit.ApiUtils;
import ru.developersementsov.rentateamapplication.retrofit.UsersApi;
import ru.developersementsov.rentateamapplication.ui.about.AboutFragment;
import ru.developersementsov.rentateamapplication.ui.user.UserActivity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class UsersListFragment extends Fragment {
    private UsersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private UsersApi usersApi;
    UserDB userDB;

    public UsersListFragment() {
    }

    public static UsersListFragment newInstance() {
        return new UsersListFragment();
    }
    // private UsersListViewModel usersListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_users_list, container, false);
        usersApi = ApiUtils.getUsersApi();
        mRecyclerView = root.findViewById(R.id.users_recycle_view);
        mAdapter = new UsersAdapter(getActivity(), new ArrayList<User>(0),
                new UsersAdapter.UserClickListener() {

                    @Override
                    public void onUserClick(long id, String firstName, String lastName, String email, String avatar) {
                        Toast.makeText(getActivity(), "User id is " + id, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), UserActivity.class);

                        intent.putExtra("firstName", firstName);
                        intent.putExtra("lastName", lastName);
                        intent.putExtra("email", email);
                        intent.putExtra("avatar", avatar);

                        startActivity(intent);
                    }


                });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        loadUsers();

        return root;
    }

    public void loadUsers() {
        usersApi.getAnswers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AnswersResponse>() {
                    @Override
                    public void onCompleted() {
                        showComplitedMessage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("MainActivity", "Error loading from API");
                        showErrorMessage();
                    }

                    @Override
                    public void onNext(AnswersResponse answersResponse) {
                        mAdapter.updateAnswers(answersResponse.getUsers());
                    }
                });

    }

    public void showErrorMessage() {
        Toast.makeText(getActivity(), "Error loading from API", Toast.LENGTH_LONG).show();
    }

    public void showComplitedMessage() {
        Toast.makeText(getActivity(), "Completed loading from API", Toast.LENGTH_SHORT).show();
    }
}