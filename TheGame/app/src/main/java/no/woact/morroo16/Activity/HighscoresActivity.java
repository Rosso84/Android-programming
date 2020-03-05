package no.woact.morroo16.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import no.woact.morroo16.Dbhandler.DBsoure;
import no.woact.morroo16.Dbhandler.WinnerData;
import no.woact.morroo16.Messager;
import no.woact.morroo16.tictactoe.R;

/*Created By Roozbeh Moradi*/


public class HighscoresActivity extends AppCompatActivity {

    private Button btnClear, btnBack;
    private TextView tv;

    private DBsoure mDBsoure;
    private ListView lv;

    private List<WinnerData> nameList;
    private ArrayAdapter<WinnerData> adapter;
    private Messager msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        nameList = new ArrayList<>();
        findView();
        onClick();
        mDBsoure = new DBsoure(this);
        mDBsoure.open();
        makeListView(nameList);
        updateListView(nameList);

    }

    private void findView() {
        lv = (ListView) findViewById(R.id.lvId);
        tv = (TextView) findViewById(R.id.tvHigh);
        btnClear = (Button) findViewById(R.id.btnClearList);
        btnBack = (Button) findViewById(R.id.btnBack);

    }

    private void onClick(){
        btnClear.setOnClickListener((View v)->{
            msg = new Messager();
            mDBsoure.clearTableHigscoreList();
            msg.showMessageDialog(this, "List deleted", "Back to main menu to refresh list");
            btnBack.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        });
        btnBack.setOnClickListener((View v)->{
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
        });
    }

    private void makeListView(List<WinnerData> dataList) {
        adapter = new ArrayAdapter<WinnerData>(
                this,
                R.layout.list_items,
                dataList);
        lv.setAdapter(adapter);
    }

    private void updateListView(List<WinnerData> dataList) {
        List<WinnerData> temporaryList = mDBsoure.getAllWinnerData(); //samler data i midlertidig liste
        for (int i = 0; i < temporaryList.size(); i++) {
            nameList.add(temporaryList.get(i));
        }
        //sorterer med antall wins(ascending) og time(desending)
        nameList.sort(Comparator.comparing(WinnerData::getCountWin)//order ascending wins
                .reversed()
                .thenComparing(Comparator.comparing(WinnerData::getTime) ) );//order descending  time
        adapter.notifyDataSetChanged();
        temporaryList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDBsoure.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDBsoure.close();
    }

    @Override
    protected void onDestroy() {
        mDBsoure.close();
        super.onDestroy();
    }


}
