package ru.developersementsov.rentateamapplication.ui.userslist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.developersementsov.rentateamapplication.R;
import ru.developersementsov.rentateamapplication.db.UserViewModel;
import ru.developersementsov.rentateamapplication.model.AnswersResponse;
import ru.developersementsov.rentateamapplication.model.User;
import ru.developersementsov.rentateamapplication.recycler.UsersAdapter;
import ru.developersementsov.rentateamapplication.retrofit.ApiUtils;
import ru.developersementsov.rentateamapplication.retrofit.UsersApi;
import ru.developersementsov.rentateamapplication.ui.user.UserActivity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class UsersListFragment extends Fragment {
    private UsersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private UsersApi usersApi;
    private UserViewModel userViewModel;


    public UsersListFragment() {
    }

    public static UsersListFragment newInstance() {
        return new UsersListFragment();
    }
    // private UsersListViewModel usersListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_users_list, container, false);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        usersApi = ApiUtils.getUsersApi();

        mRecyclerView = root.findViewById(R.id.users_recycle_view);

        mAdapter = new UsersAdapter(getActivity(), new ArrayList<User>(0),
                new UsersAdapter.UserClickListener() {

                    @Override
                    public void onUserClick(long id, String firstName, String lastName, String email, String avatar) {
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
                       // showComplitedMessage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("MainActivity", "Error loading from API");
                        showErrorMessage();
                        LiveData<List<User>> usersLiveData = userViewModel.getAllUsers();
                        usersLiveData.observe(getActivity(), new Observer<List<User>>() {
                            @Override
                            public void onChanged(@Nullable List<User> users) {
                                mAdapter.updateAnswers(users);
                            }
                        });

                    }

                    @Override
                    public void onNext(AnswersResponse answersResponse) {
                        for (User user : answersResponse.getUsers()
                        ) {
                            userViewModel.insert(user);
                            Log.d("MainActivity", "add user " + user.getFirstName());
                        }
                        mAdapter.updateAnswers(answersResponse.getUsers());
                    }
                });

    }

    public void showErrorMessage() {
        Toast.makeText(getActivity(), "Error loading from API, Load from DataBase", Toast.LENGTH_LONG).show();
    }

    public void showComplitedMessage() {
        Toast.makeText(getActivity(), "Completed loading from API", Toast.LENGTH_SHORT).show();
    }
}