 package com.example.researchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

 public class Contacts extends AppCompatActivity {
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
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Contact Us");
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
                 Intent g = new Intent(this, HomeScreen.class);
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
