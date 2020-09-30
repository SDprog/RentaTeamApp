package ru.developersementsov.rentateamapplication.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import ru.developersementsov.rentateamapplication.R;
import ru.developersementsov.rentateamapplication.ui.SingleFragmentActivity;

public class UserActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new UserFragment();
    }
}