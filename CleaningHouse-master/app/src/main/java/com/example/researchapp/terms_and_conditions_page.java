package com.example.researchapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class terms_and_conditions_page extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navigationView;
    FirebaseUser fbuser;
    FirebaseDatabase db;
    DatabaseReference ref;
    TextView username;
    TextView status;
    View header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Terms of Service");
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);
        username = header.findViewById(R.id.username);
        status = header.findViewById(R.id.status);
        db = FirebaseDatabase.getInstance();
        fbuser = FirebaseAuth.getInstance().getCurrentUser();
        String uName = fbuser.getDisplayName();
        if(uName == "") {
            username.setText("No name provided");
        }
        else {
            username.setText(uName);
        }
        ref= db.getReference("Users").child(fbuser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status.setText(dataSnapshot.child("Role").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TextView mMessageWindow = (TextView) findViewById(R.id.messageWindow);
        StringBuilder stringBuilder = new StringBuilder();
        String [] someMessages = {"These terms and conditions (\"Terms\", \"Agreement\") are an agreement between Mobile Application Developer (\"Mobile Application Developer\", \"us\", \"we\" or \"our\") and you (\"User\", \"you\" or \"your\"). " +
                "This Agreement sets forth the general terms and conditions of your use of the Puro mobile application and any of its products or services (collectively, \"Mobile Application\" or \"Services\").",
                "If you create an account in the Mobile Application, you are responsible for maintaining the security of your account and you are fully responsible for all activities that occur under the account and any other actions taken in connection with it. We may, but have no obligation to, monitor and review new accounts before you may sign in and use our Services.",
        "We do not own any data, information or material (\"Content\") that you submit in the Mobile Application in the course of using the Service. You shall have sole responsibility for the accuracy, quality, integrity, legality, reliability, appropriateness, and intellectual property ownership or right to use of all submitted Content. ",
        "We reserve the right to modify this Agreement or its policies relating to the Mobile Application or Services at any time, effective upon posting of an updated version of this Agreement in the Mobile Application. ", "You acknowledge that you have read this Agreement and agree to all its terms and conditions. By using the Mobile Application or its Services you agree to be bound by this Agreement. "};
        for(int i = 0; i < 5; ++i)
        {
            stringBuilder.append(someMessages[i]);
        }
        mMessageWindow.setText(stringBuilder.toString());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setupDrawerContent(navigationView);
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
                    Intent g = new Intent(this,HomeScreen.class);
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
            /*case R.id.nav_message:
                Intent l = new Intent(this, MessageScreen.class);
                startActivity(l);
                break;*/
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
    }
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

}
