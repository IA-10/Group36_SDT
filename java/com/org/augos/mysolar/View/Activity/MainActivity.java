package com.org.augos.mysolar.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.org.augos.mysolar.Adapter.IconMenuAdapter;
import com.org.augos.mysolar.Base.BaseActivity;
import com.org.augos.mysolar.Model.IconPowerMenuItem;
import com.org.augos.mysolar.R;
import com.org.augos.mysolar.View.Fargment.FragmentActivity;
import com.org.augos.mysolar.View.Fargment.FragmentActivityGraph;
import com.org.augos.mysolar.View.Fargment.FragmentDashBoard;
import com.org.augos.mysolar.View.Fargment.FragmentPricing;
import com.org.augos.mysolar.View.Fargment.FragmentWeather;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends BaseActivity {
    TextView title_view;
    FrameLayout container;
    ImageView more;
    DrawerLayout drawer;
    ImageView menu_view;

    LinearLayout dashboard;
    LinearLayout activity_flow;
    LinearLayout activity_graph;
    LinearLayout weather_detail;
    LinearLayout pricing;

    //BottomNavigationView bottomNavigationView;
    CustomPowerMenu pm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
    }
    private void initView(){
        title_view = findViewById(R.id.title_view);
        title_view.setText("DASHBOARD");
        drawer = findViewById(R.id.drawer);
        menu_view = findViewById(R.id.menu_btn);
        container = findViewById(R.id.container);
        more = findViewById(R.id.more);

        dashboard = findViewById(R.id.dashboard);
        activity_flow = findViewById(R.id.activity_flow);
        activity_graph = findViewById(R.id.activity_graph);
        weather_detail = findViewById(R.id.weather);
        pricing = findViewById(R.id.pricing);

        more.setOnClickListener(this);
        menu_view.setOnClickListener(this);
        dashboard.setOnClickListener(this);
        activity_flow.setOnClickListener(this);
        activity_graph.setOnClickListener(this);
        weather_detail.setOnClickListener(this);
        pricing.setOnClickListener(this);

        loadFragment(new FragmentDashBoard());
    }

    @Override
    public void onClick(View view) {
        if(view == more){
            showPopupMenu(more);
        }else if(view == menu_view){
            openDrawerMenu();
        }else if(view == dashboard){
            loadFragment(new FragmentDashBoard());
            closeDrawerMenu();
        }else if(view == activity_flow){
            title_view.setText("ACTIVITY (FLOW)");
            loadFragment(new FragmentActivity());
            closeDrawerMenu();
        }else if(view == activity_graph){
            title_view.setText("ACTIVITY (GRAPH)");
            loadFragment(new FragmentActivityGraph());
            closeDrawerMenu();
        }else if(view == weather_detail){
            closeDrawerMenu();
            if(sessionManager.getLevel() == 0){
                showMessage("Please become premium user !");
                return;
            }
            title_view.setText("WEATHER");
            loadFragment(new FragmentWeather());
        }else if(view == pricing){
            closeDrawerMenu();
            if(sessionManager.getLevel() == 0){
                showMessage("Please become premium user !");
                return;
            }
            title_view.setText("PRICING");
            loadFragment(new FragmentPricing());
        }
    }
    private void openDrawerMenu() {
        if (!drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.openDrawer(Gravity.LEFT);
        }
    }
    private void closeDrawerMenu(){
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
    }
    private void showPopupMenu(ImageView view){
        OnMenuItemClickListener<IconPowerMenuItem> onIconMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
            @Override
            public void onItemClick(int position, IconPowerMenuItem item) {
                if(item.getTitle().equalsIgnoreCase("Profile")){
                    startIntentLiveMode(AccountDetailActivity.class);
                }else if(item.getTitle().equalsIgnoreCase("Setting")){
                    startIntentLiveMode(SettingActivity.class);
                }else if(item.getTitle().equalsIgnoreCase("Support")){
                    startIntentLiveMode(SupportActivity.class);
                }else if(item.getTitle().equalsIgnoreCase("Notifications")){
                    startIntentLiveMode(NotificationsActivity.class);
                }else if(item.getTitle().equalsIgnoreCase("About us")){
                    startIntentLiveMode(AboutUsActivity.class);
                }else if(item.getTitle().equalsIgnoreCase("logout")){
                    sessionManager.logout();
                }
                pm.dismiss();
            }
        };
        pm = new CustomPowerMenu.Builder<>(context, new IconMenuAdapter())
                .addItem(new IconPowerMenuItem(ContextCompat.getDrawable(context, R.drawable.ic_account), "Profile"))
                .addItem(new IconPowerMenuItem(ContextCompat.getDrawable(context, R.drawable.ic_setting_black), "Setting"))
                .addItem(new IconPowerMenuItem(ContextCompat.getDrawable(context, R.drawable.ic_support_black), "Support"))
                .addItem(new IconPowerMenuItem(ContextCompat.getDrawable(context, R.drawable.ic_about_black),  "About us"))
                .addItem(new IconPowerMenuItem(ContextCompat.getDrawable(context, R.drawable.ic_notification), "Notifications"))
                .addItem(new IconPowerMenuItem(ContextCompat.getDrawable(context, R.drawable.ic_close), "logout"))
                .setOnMenuItemClickListener(onIconMenuItemClickListener)
                .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .build();
        pm.showAsDropDown(view);
    }
    private void removeAllFragments(FragmentManager fragmentManager) {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }
    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
    private void checkLocationEnabled(){
        if(!isLocationEnabled(context)){
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            finish();
        }else{
            initView();
        }
    }
    public boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
    private void checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST_CODE);
            return;
        }else{
            checkLocationEnabled();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                checkLocationEnabled();
            }else{
                showMessage("Permission denied !");
                finishAffinity();
            }
        }
    }
}
