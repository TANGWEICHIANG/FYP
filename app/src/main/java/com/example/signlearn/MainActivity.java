package com.example.signlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.signlearn.activities.AboutSignLanguages;
import com.example.signlearn.activities.LoginActivity;
import com.example.signlearn.activities.SettingsActivity;
import com.example.signlearn.activities.TranslatorActivity;
import com.example.signlearn.fragment.QuizFragment;
import com.example.signlearn.fragment.RecognitionFragment;
import com.example.signlearn.fragment.TutorialFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.opencv.android.OpenCVLoader;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static {
        if(OpenCVLoader.initDebug()){
            Log.d("MainActivity: ","Opencv is loaded");
        }
        else {
            Log.d("MainActivity: ","Opencv failed to load");
        }
    }

    static final float END_SCALE = 0.7f;
    LinearLayout contentView;
    ImageView menuIcon;
    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content_view);

        // ini
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        //drawer hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        TextView userProfileName = findViewById(R.id.userName);

        //navigation drawer profile image
        View headerView = navigationView.getHeaderView(0);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);
        TextView userName = headerView.findViewById(R.id.user_name);
        TextView userEmailId = headerView.findViewById(R.id.user_emailID);

        if (currentUser.getPhotoUrl() != null)
        {
            userName.setText(currentUser.getDisplayName());
            userEmailId.setText(currentUser.getEmail());
            userProfileName.setText(currentUser.getDisplayName());
            Glide.with(MainActivity.this).load(currentUser.getPhotoUrl()).into(profileImageView);

        }
        else {
            userName.setText(currentUser.getDisplayName());
            userEmailId.setText(currentUser.getEmail());
            userProfileName.setText(currentUser.getDisplayName());
            Glide.with(MainActivity.this).load(R.drawable.profile_avatar).into(profileImageView);

        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RecognitionFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_recognition); // Highlight the menu item (optional)
        }

        navigationDrawer();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_recognition:
                    if (!(currentFragment instanceof RecognitionFragment))
                        selectedFragment = new RecognitionFragment();
                    break;
                case R.id.nav_quizzes:
                    if (!(currentFragment instanceof QuizFragment))
                        selectedFragment = new QuizFragment();
                    break;
                case R.id.nav_tutorial:
                    if (!(currentFragment instanceof TutorialFragment))
                        selectedFragment = new TutorialFragment();
                    break;
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });

    }

    //Navigation Drawer Function
    private void navigationDrawer() {

        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(v -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        // drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.nav_signLanguages:
                startActivity(new Intent(getApplicationContext(), AboutSignLanguages.class));
                break;
            case R.id.nav_translator:
                startActivity(new Intent(getApplicationContext(), TranslatorActivity.class));
                break;
//            case R.id.nav_how_to_use:
//                startActivity(new Intent(getApplicationContext(), HowToUse.class));
//                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.nav_profile:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
        }
        return true;
    }

}