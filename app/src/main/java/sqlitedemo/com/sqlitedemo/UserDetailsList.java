package sqlitedemo.com.sqlitedemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by JAWED on 04-07-2017.
 */

public class UserDetailsList extends AppCompatActivity {
    private Context mContext;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private UserDetailsAdapter adapter;
    private ArrayList<UserDetails> arrayList=new ArrayList<>();
    private DBAdapterClass dbAdapterClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_list);
        mContext = UserDetailsList.this;
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        dbAdapterClass=new DBAdapterClass(this);

        arrayList= dbAdapterClass.getAllData();

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new UserDetailsAdapter(mContext ,arrayList );
        recyclerView.setAdapter(adapter);
    }
}
