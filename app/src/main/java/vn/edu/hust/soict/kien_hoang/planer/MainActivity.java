package vn.edu.hust.soict.kien_hoang.planer;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Tạo các tab cho tab host
        tabConfiguration(savedInstanceState) ;
        //IntentRedirect();
    }

    // This function config the tab host: 4 tabs now, day, week, calendar
    public void tabConfiguration(Bundle savedInstanceState)
    {
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);
        TabHost.TabSpec tabSpec ;
        // Create tab now
        Intent nowIntent = new Intent(this, NowActivity.class);
        tabSpec = tabHost.newTabSpec("now");
        tabSpec.setIndicator("Now");
        tabSpec.setContent(nowIntent);
        tabHost.addTab(tabSpec);


        //Create tab day
        Intent dayIntent = new Intent(this, DayActivity.class);
        tabSpec = tabHost.newTabSpec("day");
        tabSpec.setIndicator("Day");
        tabSpec.setContent(dayIntent);
        tabHost.addTab(tabSpec);



        //Create tab week
        Intent weekIntent = new Intent(this, WeekActivity.class);
        tabSpec = tabHost.newTabSpec("week");
        tabSpec.setIndicator("Week");
        tabSpec.setContent(weekIntent);
        tabHost.addTab(tabSpec);



        //Create tab calendar
        Intent calendarIntent = new Intent(this, CalendarActivity.class);
        tabSpec = tabHost.newTabSpec("calendar");
        tabSpec.setIndicator("Calendar");
        tabSpec.setContent(calendarIntent);
        tabHost.addTab(tabSpec);

        // Config default tab is the first one
        tabHost.setCurrentTab(0);

        // Process changed tab conditions
    }

    public static void setCurrentTabHost(int t)
    {
        tabHost.setCurrentTab(t);
    }
    public void IntentRedirect()
    {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                String routes = bundle.getString("routes");
                if(routes.equals("Day"))
                {
                    // Chuyen huong intent sang
//                    Intent redirectIntent = new Intent(this,DayActivity.class);
//                    Bundle redirectBundle = bundle;
//                    intent.putExtra("bundle",bundle);
//                    startActivity(redirectIntent);
                    TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
                    tabHost.setCurrentTab(0);
                }
            } else {
                Toast.makeText(this,intent.getStringExtra("date"),Toast.LENGTH_LONG).show();
            }
        }
        else Toast.makeText(this,"welcome to Main Activity", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}