package com.d2fault.mybrary.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.d2fault.mybrary.R;
import com.d2fault.mybrary.utils.BackPressCloseHandler;
import com.d2fault.mybrary.utils.BookInfoUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "[Mybrary] MainActivity - ";
    private AlertDialog.Builder addBookInfoDialogBuilder;
    private AlertDialog addBookInfoDialog;
    private BackPressCloseHandler backPressCloseHandler;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Fragment fragment;
    private FragmentTransaction ft;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initModel();
        initView();
        initController();
        initListener();

        ft.replace(R.id.content, fragment);
        ft.commit();
    }

    private void initModel() {
        fragment = new AddBookInfoFragment();
        title = getString(R.string.app_name);
        ft = getSupportFragmentManager().beginTransaction();

        addBookInfoDialogBuilder = new AlertDialog.Builder(this);
        addBookInfoDialogBuilder.setTitle("책 정보 등록");
        addBookInfoDialogBuilder
                .setMessage("책 정보를 등록하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("등록",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        addBookInfoDialog = addBookInfoDialogBuilder.create();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);


        setSupportActionBar(toolbar);
    }

    private void initController() {
        backPressCloseHandler = new BackPressCloseHandler(this);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    private void initListener() {
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d(TAG, "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                String isbnStr = result.getContents();
                if (result.getFormatName().startsWith("EAN_13")) {
                    Toast.makeText(this, isbnStr, Toast.LENGTH_SHORT).show();
                    BookInfoUtils.getBookInfoFromISBN(this, isbnStr);
                    addBookInfoDialog.show();
                } else {
                    Log.e(TAG, "ISBN ERROR: EAN_13 형식이 아닙니다.\n인식 바코드: " + isbnStr);
                    Toast.makeText(this, "ISBN을 인식하지 못했습니다", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            backPressCloseHandler.onBackPressed();
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();
        ft = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
