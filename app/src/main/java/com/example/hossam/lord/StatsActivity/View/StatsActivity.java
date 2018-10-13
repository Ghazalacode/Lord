package com.example.hossam.lord.StatsActivity.View;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hossam.lord.StatsActivity.Adapters.ActivitiesRecyclerAdapter;
import com.example.hossam.lord.R;
import com.example.hossam.lord.StatsActivity.Adapters.RecyclerItemClickListener;
import com.example.hossam.lord.StatsActivity.MyXAxisValueFormatter;
import com.example.hossam.lord.StatsActivity.ViewModel.StatsViewModel;
import com.example.hossam.lord.Utils.LocaleUtils;
import com.example.hossam.lord.databinding.ActivityCompanyStatsBinding;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by hossam on 10/8/2018.
 */

public class StatsActivity extends AppCompatActivity implements LifecycleOwner {

    private LifecycleRegistry mLifecycleRegistry ;
    private StatsViewModel viewModel;
    ActivitiesRecyclerAdapter adapter;
    DatePickerDialog startDatePickerDialog ,endDatePickerDialog ;

    ArrayList< ArrayList<String>> currentData = new ArrayList<>();

    protected ArrayList<String> mDays = new ArrayList<>();

    Random r ;

    private CombinedChart mChart;

    public StatsActivity() {
        LocaleUtils.updateConfig(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("JF-Flat-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)

                .build()
        );

        final ActivityCompanyStatsBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_company_stats);
        viewModel = ViewModelProviders.of(this).get(StatsViewModel.class);
        binding.setViewModel(viewModel);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);

         r= new Random();


        // set up the RecyclerView
        binding.recyler.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new ActivitiesRecyclerAdapter(this,    viewModel.getRecyclerData());
        binding.recyler.setAdapter(adapter);
        binding.recyler.addOnItemTouchListener(
                new RecyclerItemClickListener(StatsActivity.this , binding.recyler , new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO GET Arraylist with INDEX position and set Y

                        if (currentData.size()==0)
                        { Toast.makeText(StatsActivity.this, "لا توجد بيانات لعرضها حاليا", Toast.LENGTH_SHORT).show();}
                        ArrayList<Entry> entries = new ArrayList<>();
                        for (int index = 0; index < currentData.get(position).size(); index++)
                        {  entries.add(new Entry( index,Float.valueOf(currentData.get(position).get(index)) ));}
                        updateChart(binding , entries ,entries.size() );

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        viewModel.updateData("102" , "","", "","");

        int startYear = 2018;
         int starthMonth= 1;
        int startDay = 1;
        startDatePickerDialog = new DatePickerDialog(
                StatsActivity.this, new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
             String date = String.valueOf(dayOfMonth)+"/"+
               String.valueOf(month+1)+"/"+ String.valueOf(year);
               binding.tvStartDate.setText(date);
           }
       }, startYear, starthMonth, startDay);
        endDatePickerDialog = new DatePickerDialog(
                StatsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = String.valueOf(dayOfMonth)+"/"+
                        String.valueOf(month+1)+"/"+ String.valueOf(year);
                binding.tvEndDate.setText(date);
            }
        }, startYear, starthMonth, startDay);

      binding.tvStartDate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startDatePickerDialog.show();
          }
      });

        binding.tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               endDatePickerDialog.show();
            }
        });





        // setup the alert builder
        final   String[] citties = {"الرياض", "خميس مشيط" };
        AlertDialog.Builder builder = new AlertDialog.Builder(StatsActivity.this);
        builder.setTitle("اختر الفرع ");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(citties,-1,null);
builder.setPositiveButton("تم",new DialogInterface.OnClickListener()
{
    @Override
    public void onClick(DialogInterface dialog, int whichButton)
    {
        // SHOULD NOW WORK
        ListView lw = ((AlertDialog)dialog).getListView();
        Log.d("", "o" + lw.getCheckedItemPosition() + "], which" + whichButton + "]");

        // Object checkedItem = lw.getAdapter().getItem(1);
        binding.tvChooseBranch.setText(String.valueOf(citties[lw.getCheckedItemPosition()]));
        viewModel.updateData("102" , "", String.valueOf(lw.getCheckedItemPosition()+1) ,
                binding.tvStartDate.getText().toString(),binding.tvEndDate.getText().toString());

    }
});

// create and show the alert dialog
       final AlertDialog dialog = builder.create();

        binding.tvChooseBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


        viewModel.getResponse().observe(StatsActivity.this, new Observer<ArrayList<ArrayList<String>>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ArrayList<String>> arrayLists) {
                currentData = arrayLists ;

                mDays = arrayLists.get(12);
                Log.e( ": ", String.valueOf( mDays.size())  );
                Log.e( ": ", arrayLists.get(0).toString()  );
                Log.e( ": ",arrayLists.get(12).toString() );
                ArrayList<Entry> entries = new ArrayList<>();
                for (int index = 0; index < arrayLists.get(0).size(); index++)
                {  entries.add(new Entry( index,Float.valueOf(arrayLists.get(0).get(index)) ));}
                updateChart(binding , entries ,entries.size() );
            }
        });




    }
   void updateChart(ActivityCompanyStatsBinding binding ,ArrayList<Entry> entries ,int itemcount){

       ////////  Setting Up Chart ////////////
       mChart = binding.chart;

       mChart.getDescription().setEnabled(false);
       mChart.setBackgroundColor(Color.WHITE);
       mChart.setDrawGridBackground(false);
       mChart.setDrawBarShadow(false);
       mChart.setHighlightFullBarEnabled(false);


       // draw bars behind lines
       mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
               /*  CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE,CombinedChart.DrawOrder.CANDLE,*/
               CombinedChart.DrawOrder.LINE,/* CombinedChart.DrawOrder.SCATTER*/
       });

       Legend l = mChart.getLegend();
       l.setWordWrapEnabled(true);
       l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
       l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
       l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
       l.setDrawInside(false);

       YAxis rightAxis = mChart.getAxisRight();
       rightAxis.setDrawGridLines(false);
       rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

       YAxis leftAxis = mChart.getAxisLeft();
       leftAxis.setDrawGridLines(false);
       leftAxis.setAxisMinimum(0f);// this replaces setStartAtZero(true)

       final XAxis xAxis = mChart.getXAxis();
      xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
      xAxis.setAxisMinimum(0f);
       xAxis.setGranularity(1f);

       xAxis.setValueFormatter(
               new IAxisValueFormatter() {
                   @Override
                   public String getFormattedValue(float value, AxisBase axis) {

                       return currentData.get(12).get((int) value % mDays.size()); ///TODO bug value 0  when zooming with x axis

                   }     });
           //    new MyXAxisValueFormatter(mDaysArr)





       CombinedData data = new CombinedData();

       data.setData(generateLineData(entries ,itemcount)); //TODO  POPULATE  HERE WHEN RECYCLER ITEM SELECTED
      /*  data.setData(generateBarData());
        data.setData(generateBubbleData());*/
       /* data.setData(generateScatterData());*/
       /* data.setData(generateCandleData());*/


       xAxis.setAxisMaximum(data.getXMax() + 0.25f);

       mChart.setData(data);
       mChart.invalidate();

   }

    private LineData generateLineData(ArrayList<Entry> entries ,int itemcount) {

        LineData d = new LineData();



      /*  for (int index = 0; index < itemcount; index++)
        {if (entries.get(index).getX() == 0.0) {
            entries.get(index).setX(entries.get(index).getX()+0.1f);

        }
            if (entries.get(index).getY() == 0.0) {
                entries.get(index).setY(entries.get(index).getY()+0.1f);

            }

         }*/ //TODO UPDATE Y FROM HERE

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(89, 98, 178));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(30, 52, 125));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(214,237,235));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(0,125,89));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

 /*   private BarData generateBarData() {

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();

        for (int index = 0; index < itemcount; index++) {
            entries1.add(new BarEntry(0, getRandom(35, 25)));

            // stacked
            entries2.add(new BarEntry(0, new float[]{getRandom(13, 5), getRandom(13, 12)}));
        }

        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "");
        set2.setStackLabels(new String[]{"Stack 1", "Stack 2"});
        set2.setColors(new int[]{Color.rgb(61, 165, 255), Color.rgb(23, 197, 255)});
        set2.setValueTextColor(Color.rgb(61, 165, 255));
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1, set2);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }
    protected BubbleData generateBubbleData() {

        BubbleData bd = new BubbleData();

        ArrayList<BubbleEntry> entries = new ArrayList<BubbleEntry>();

        for (int index = 0; index < itemcount; index++) {
            float y = getRandom(100, 50);
            float size = getRandom(100, 50);
            entries.add(new BubbleEntry(index + 0.5f, y, size));
        }

        BubbleDataSet set = new BubbleDataSet(entries, "Bubble DataSet");
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.WHITE);
        set.setHighlightCircleWidth(1.5f);
        set.setDrawValues(true);
        bd.addDataSet(set);

        return bd;
    }

    protected ScatterData generateScatterData() {

        ScatterData d = new ScatterData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (float index = 0; index < itemcount; index += 0.5f)
            entries.add(new Entry(index + 0.25f, getRandom(100, 55)));

        ScatterDataSet set = new ScatterDataSet(entries, "Scatter DataSet");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setScatterShapeSize(7.5f);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        d.addDataSet(set);

        return d;
    }

    protected CandleData generateCandleData() {

        CandleData d = new CandleData();

        ArrayList<CandleEntry> entries = new ArrayList<CandleEntry>();

        for (int index = 0; index < itemcount; index += 2)
            entries.add(new CandleEntry(index + 1f, 90, 70, 85, 75f));

        CandleDataSet set = new CandleDataSet(entries, "Candle DataSet");
        set.setDecreasingColor(Color.rgb(142, 150, 175));
        set.setShadowColor(Color.DKGRAY);
        set.setBarSpace(0.3f);
        set.setValueTextSize(10f);
        set.setDrawValues(false);
        d.addDataSet(set);

        return d;
    }*/



    private int getRandom(int High, int Low) {
      return   r.nextInt(High-Low) + Low ;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase ));
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }




}
