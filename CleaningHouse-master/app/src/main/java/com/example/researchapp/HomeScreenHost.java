package com.example.researchapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreenHost extends AppCompatActivity implements View.OnClickListener {
    TextView username;
    View header;
    CircleImageView profile_image;
    Button logOutBtn;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser fbuser;
    FirebaseDatabase db;
    DatabaseReference reference;
    Button currentProperties;
    Button viewMessages;
    Button upcomingCleanings;
    Button billsReceipts;
    DrawerLayout drawer;
    NavigationView navigationView;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_host);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);
        username = (TextView) header.findViewById(R.id.userName);
        profile_image = (CircleImageView) header.findViewById(R.id.profileImage);
        drawer = findViewById(R.id.drawer_layout);
        fbuser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();
        username.setText(fbuser.getUid());
        reference = db.getReference("Users").child(fbuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                username.setText(fbuser.getDisplayName());
                /*if(user.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(HomeScreenHost.this).load(user.getImageURL()).into(profile_image);
                }*/
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setupDrawerContent(navigationView);
        logOutBtn = findViewById(R.id.logOut);
        currentProperties = (Button) findViewById(R.id.currentProperties);
        viewMessages = (Button) findViewById(R.id.viewMessages);
        upcomingCleanings = (Button) findViewById(R.id.upcomingCleanings);
        billsReceipts = (Button) findViewById(R.id.billsReceipts);
        currentProperties.setOnClickListener(this);
        viewMessages.setOnClickListener(this);
        upcomingCleanings.setOnClickListener(this);
        billsReceipts.setOnClickListener(this);
      
        Toast.makeText(HomeScreenHost.this, "Welcome" , Toast.LENGTH_SHORT).show();

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.getInstance().signOut();
                Intent backToHome = new Intent(HomeScreenHost.this, OpeningScreen.class);
                startActivity(backToHome);
            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent g = new Intent(this,HomeScreenHost.class);
                startActivity(g);
                break;
            case R.id.nav_profile:
                Intent h = new Intent(this, Profile.class);
                startActivity(h);
                break;
            case R.id.nav_schedule:
                Intent i = new Intent(this, ScheduleScreen.class);
                startActivity(i);
                break;
            case R.id.property:
                Intent j = new Intent(this, PropertiesScreen.class);
                startActivity(j);
                break;
            case R.id.listings:
                Intent k = new Intent(this,Listings.class);
                startActivity(k);
                break;
            case R.id.nav_message:
                Intent l = new Intent(this, MessageScreen.class);
                startActivity(l);
                break;
            case R.id.transactions:
                Intent m = new Intent(this, BillingScreen.class);
                startActivity(m);
                break;
            case R.id.contacts:
                Intent n = new Intent(this,Contacts.class);
                startActivity(n);
                break;
            case R.id.termsOfService:
                Intent o = new Intent(this, terms_and_conditions_page.class);
                startActivity(o);
                break;
                default:

        }
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout container = (LinearLayout) findViewById(R.id.content_frame);
        inflater.inflate(R.layout.activity_home_screen_host, container);
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawer.closeDrawers();
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.currentProperties:
                propertiesPage();
                break;
            case R.id.viewMessages:
                messagePage();
                break;
            case R.id.upcomingCleanings:
                schedulePage();
                break;
            case R.id.billsReceipts:
                billingPage();
                break;
        }
    }
    public void propertiesPage(){
        Intent i = new Intent(this, PropertiesScreen.class);
        startActivity(i);
    }
    public void schedulePage() {
        Intent i = new Intent(this, ScheduleScreen.class);
        startActivity(i);
    }
    public void billingPage(){
        Intent i = new Intent(this, BillingScreen.class);
        startActivity(i);

    }
    public void messagePage(){
        Intent i = new Intent(this, MessageScreen.class);
        startActivity(i);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}