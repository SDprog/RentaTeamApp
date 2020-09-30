package ru.developersementsov.rentateamapplication.ui.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.developersementsov.rentateamapplication.R;


public class UserFragment extends Fragment {
    Bundle arguments;
    ImageView avatar;
    TextView firstName;
    TextView lastName;
    TextView email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        arguments = getActivity().getIntent().getExtras();
        avatar = view.findViewById(R.id.avatar_iv);
        firstName = view.findViewById(R.id.firstname_tv);
        lastName = view.findViewById(R.id.lastname_tv);
        email = view.findViewById(R.id.email_tv);


        if(arguments!=null){
            String av = arguments.getString("avatar");
            String f = arguments.getString("firstName");
            String l = arguments.getString("lastName");
            String e = arguments.getString("email");
            firstName.setText(f);
            lastName.setText(l);
            email.setText(e);
            Picasso.with(getActivity())
                    .load(av)
                    .placeholder(R.drawable.ic_baseline_person_outline_24)
                    .error(R.drawable.ic_baseline_person_outline_24)
                    .into(avatar);
        }
        // Inflate the layout for this fragment
        return view;
    }
}