package ru.nonamecompany.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import ru.nonamecompany.delivery.Model.Auth.AuthHandler;

public class ProfileFragment extends Fragment {

    private AuthHandler authHandler;

    private ImageView profilePicture;
    private TextView accountName;
    private Button orderHistory;
    private Button signOut;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authHandler = AuthHandler.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (!authHandler.isSigned()) authHandler.authenticate(this);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePicture = view.findViewById(R.id.profile_picture);
        accountName = view.findViewById(R.id.account_name);
        orderHistory = view.findViewById(R.id.profile_order_history);
        signOut = view.findViewById(R.id.sign_out_button);

        orderHistory.setOnClickListener(v -> orderHistoryClicked());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (authHandler.isSigned()) updateUserPage();
    }


    private void updateUserPage() {
        accountName.setText(authHandler.getUserName());

        if (authHandler.getProfilePic() != null) {
            Glide.with(this)
                    .load(authHandler.getProfilePic())
                    .apply(new RequestOptions().centerCrop())
                    .into(profilePicture);
        }


        signOut.setOnClickListener(v -> authHandler.signOut(this.getContext(), task -> authHandler.authenticate(this)));
    }

    private void orderHistoryClicked() {
        startActivity(new Intent(this.getContext(), OrderHistoryActivity.class));
    }
}
