package com.anggrayudi.hiddenapi.sample;

import android.content.Intent;
import android.net.EthernetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.anggrayudi.hiddenapi.InternalAccessor;
import com.anggrayudi.hiddenapi.r.Rc;

import java.util.ArrayList;

/**
 * Created by Anggrayudi on 11/03/2016.<p>
 * An example class for Hidden API.<p>
 * If you plan to use only Android internal resources rather than internal classes or methods,
 * just add <br><code>compile 'com.anggrayudi:android-hidden-api:0.0.6'</code><br> library
 * to your app's module without need to replace <code>android.jar</code>. This version does not use
 * Java reflection anymore, and certainly safe.
 * See the <a href="https://github.com/anggrayudi/android-hidden-api#usage">Usage</a>.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<Model> items = new ArrayList<>();

        items.add(new Model("Formatter.formatShortElapsedTime(this, 100000000)", Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? Formatter.formatShortElapsedTime(this, 100000000) : "",
                "Accessing hidden method.\nThis method only available for API 21+. If you run it on a"+
                        " device with API 20 and lower, you'll get java.lang.NoSuchMethodError exception."));

        items.add(new Model("com.android.internal.R.string.accept", "",
                "Accessing hidden String resource.\nWe cannot access internal resources directly. Sometimes, IDE " +
                        "says 'error: cannot find symbol variable accept' once you run the app, or "+
                        "your app picks wrong resource id. If you want to have the internal resources, " +
                        "copy them to your project or use InternalAccessor utility class. Below are the example."));

        items.add(new Model("InternalAccessor.getString(\"accept\")", InternalAccessor.getString(Rc.string.accept),
                "Accessing hidden String resource.\nBecause above method is not working, so we need to use "+
                        "InternalAccessor.getString() method."));

        items.add(new Model("InternalAccessor.getDimension(\"status_bar_height\")", InternalAccessor.getDimension(Rc.dimen.status_bar_height)+"",
                "Accessing hidden dimension resource."));

        items.add(new Model("InternalAccessor.getColor(\"config_defaultNotificationColor\")", InternalAccessor.getColor("config_defaultNotificationColor")+"",
                "Accessing hidden color resource."));

        items.add(new Model("Info", "", "For more information, download this app's source code on " +
                "https://github.com/anggrayudi/android-hidden-api"));

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(new Adapter(items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == items.size() - 1)
                    parent.getContext().startActivity(new Intent(Intent.ACTION_VIEW)
                            .setData(Uri.parse("https://github.com/anggrayudi/android-hidden-api")));
            }
        });

        if (Build.VERSION.SDK_INT >= 22){
        /*   Accessing EthernetManager that is a hidden class and only available for API 22+.
             If you want to find out which API was built to, just type method, resource or class name
             to search box on http://jcs.mobile-utopia.com/servlet/Source?type=s&q=android.net.EthernetManager%E2%80%8C%E2%80%8B
             And then, look at 'Category' column.
        */
            EthernetManager em;
        }

        // If you want to check whether EthernetManager exists without checking API level, you can call:
        boolean isClassExists = InternalAccessor.isClassExists("android.net.EthernetManager");
        Log.d("---", "isClassExists = " + isClassExists);

        // Check whether a method exists
        boolean isMethodExists = InternalAccessor.isMethodExists("android.content.Intent", "getExtra");
        Log.d("---", "isMethodExists = "+ isMethodExists);

        try {
            // This will retrieve resource id named accelerate_cubic in com.android.internal.R.interpolator class.
            Log.d("---", "interpolator.accelerate_cubic = "+ InternalAccessor.getResourceId(
                    InternalAccessor.INTERPOLATOR, "accelerate_cubic"));

            Log.d("---", "plurals.duration_hours = "+ InternalAccessor.getResourceId(InternalAccessor.PLURALS, Rc.plurals.duration_hours));
            Log.d("---", "transition.no_transition = "+ InternalAccessor.getResourceId(InternalAccessor.TRANSITION, "no_transition"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /* DEPRECATED EXAMPLE OF InternalAccessor.Builder
        // Using InternalAccessor with other code styling
        InternalAccessor.Builder builder = new InternalAccessor.Builder(true);
        boolean b = builder.getBoolean("config_sip_wifi_only");
        String accept = builder.getString("accept");
        // Because we set true to 'saveToResourcesHolder' in the Builder constructor, every value we got always
        // saved to ResourcesHolder automatically. We can retrieve the holder now:
        ResourcesHolder accessorHolder = builder.getResourcesHolder();
        b = accessorHolder.getAsBoolean("config_sip_wifi_only");
        accept = accessorHolder.getAsString("accept");
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}