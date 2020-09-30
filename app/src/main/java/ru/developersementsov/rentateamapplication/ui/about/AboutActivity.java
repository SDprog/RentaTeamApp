package ru.developersementsov.rentateamapplication.ui.about;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import ru.developersementsov.rentateamapplication.R;
import ru.developersementsov.rentateamapplication.ui.SingleFragmentActivity;

public class AboutActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new AboutFragment();
    }


}