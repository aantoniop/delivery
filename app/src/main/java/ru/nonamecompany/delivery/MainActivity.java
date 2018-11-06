package ru.nonamecompany.delivery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ru.nonamecompany.delivery.Extensions.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    private  BottomNavigationView menu;

    private FragmentManager fragmentManager;

    private Fragment featuredFragment;
    private Fragment categoriesFragment;
    private Fragment cartFragment;
    private Fragment profileFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu = findViewById(R.id.navigation);

        fragmentManager = getSupportFragmentManager();

        featuredFragment = new FeaturedFragment();
        categoriesFragment = new CategoriesFragment();
        cartFragment = new CartFragment();
        profileFragment = new ProfileFragment();

        setActiveFragment(featuredFragment);

        handleMenu(menu);

        Log.i("Custom", "Main Activity created");
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();

        } else {
            super.onBackPressed();
        }
    }


    private void handleMenu(BottomNavigationView menu) {
        BottomNavigationViewHelper.removeShiftMode(menu);

        menu.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_featured:
                    setActiveFragment(featuredFragment);
                    return true;
                case R.id.navigation_categories:
                    setActiveFragment(categoriesFragment);
                    return true;
                case R.id.navigation_cart:
                    setActiveFragment(cartFragment);
                    return true;
                case R.id.navigation_profile:
                    setActiveFragment(profileFragment);
                    return true;
            }
            return false;
        });
    }

    private void setActiveFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }
}