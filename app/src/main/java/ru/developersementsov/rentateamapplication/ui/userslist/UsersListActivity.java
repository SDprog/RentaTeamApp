package ru.developersementsov.rentateamapplication.ui.userslist;

import androidx.fragment.app.Fragment;

import ru.developersementsov.rentateamapplication.ui.SingleFragmentActivity;

public class UsersListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new UsersListFragment();
    }
}
