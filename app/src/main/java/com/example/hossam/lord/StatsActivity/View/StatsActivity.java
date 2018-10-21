package com.example.hossam.lord.StatsActivity.View;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hossam.lord.CompanyActivitiesActivity.View.CompanyActivitiesActivity;
import com.example.hossam.lord.LoginActivity.View.LoginActivity;
import com.example.hossam.lord.R;
import com.example.hossam.lord.StatsActivity.Adapters.ActivitiesRecyclerAdapter;

import com.example.hossam.lord.StatsActivity.Adapters.CustomListAdapterDialog;
import com.example.hossam.lord.StatsActivity.Adapters.RecyclerItemClickListener;
import com.example.hossam.lord.StatsActivity.Data.Model.Employee;
import com.example.hossam.lord.StatsActivity.MyMarkerView;
import com.example.hossam.lord.StatsActivity.ViewModel.StatsViewModel;
import com.example.hossam.lord.Utils.LocaleUtils;
import com.example.hossam.lord.databinding.ActivityCompanyStatsBinding;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by hossam on 10/8/2018.
 */


//TODO  show progress bar when data is loading
    // TODO if there is no data with choosen filters make chart empty
public class StatsActivity extends AppCompatActivity implements LifecycleOwner {

    private LifecycleRegistry mLifecycleRegistry ;
    private StatsViewModel viewModel;
    ActivitiesRecyclerAdapter adapter;
    DatePickerDialog startDatePickerDialog ,endDatePickerDialog ;

    ArrayList< ArrayList<String>> currentData = new ArrayList<>();
    ArrayList< ArrayList<String>> firstdata = new ArrayList<>();

    protected ArrayList<String> mDays = new ArrayList<>();

    private CombinedChart mChart;

    Integer clickedPosition=0 , branchState =0 ;

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

        final String comActivity = getIntent().getStringExtra("activity");
        final String comID = getIntent().getStringExtra("comID");
        final String comName = getIntent().getStringExtra("comName");
        final ArrayList<String> activities  = getIntent().getStringArrayListExtra("activities");

        binding.tvCompanyActivites.setText(comActivity);
        // set up the RecyclerView
        binding.recyler.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new ActivitiesRecyclerAdapter(this,    viewModel.getRecyclerData());
        binding.recyler.setAdapter(adapter);
        binding.recyler.addOnItemTouchListener(
                new RecyclerItemClickListener(StatsActivity.this , binding.recyler , new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if (currentData.size()==0)
                        { Toast.makeText(StatsActivity.this, "لا توجد بيانات لعرضها حاليا", Toast.LENGTH_SHORT).show();

                        }
                       if (position>6){
                           ArrayList<Employee> employeeArrayList = new ArrayList<>() ;

                           for (int index = 0; index < currentData.get(position).size(); index++)
                           {
                                   employeeArrayList.add(index , new Employee(currentData.get(position).get(index), currentData.get(12).get(index)));

                           }
                           Log.e( "date: ",currentData.get(12).toString());
                           Log.e( ": ",String.valueOf(employeeArrayList.size()) );
                       /*    entries.add(new Entry( index,Float.valueOf(employeeArrayList.get(index).getdate()) ));
                           updateChart(binding , entries ,entries.size() );*/

                           final Dialog employeeDialog = new Dialog(StatsActivity.this);
                           employeeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


                           View dialogView = getLayoutInflater().inflate(R.layout.dialog_employee, null);
                          TextView title  = (TextView) dialogView.findViewById(R.id.cardTitle);
                           ListView lv = (ListView) dialogView.findViewById(R.id.custom_list);

                           // Change MyActivity.this and myListOfItems to your own values
                           CustomListAdapterDialog clad = new CustomListAdapterDialog(StatsActivity.this, employeeArrayList);

                           lv.setAdapter(clad);

                           employeeDialog.setContentView(dialogView);
                           employeeDialog.setCanceledOnTouchOutside(true);
                          // employeeDialog.setTitle(viewModel.getData().get(position).getTitle());
                                title.setText("                "+viewModel.getData().get(position).getTitle()+ "                ");
                           employeeDialog.show();



                       }else{
                           View previouslyClickedView =   binding.recyler.getLayoutManager().findViewByPosition(clickedPosition);
                           TextView previouslyClicked  = previouslyClickedView.findViewById(R.id.tv_title);
                           previouslyClickedView.setBackground(getResources().getDrawable(R.drawable.white_background_square));
                           previouslyClicked.setTextColor(getResources().getColor(R.color.darkblue));

                           clickedPosition = position;

                           View viewItem =   binding.recyler.getLayoutManager().findViewByPosition(position);
                           TextView choosenTV  = viewItem.findViewById(R.id.tv_title);
                            viewItem.setBackgroundColor(getResources().getColor(R.color.darkblue));
                            choosenTV.setTextColor(getResources().getColor(R.color.white));
                           ArrayList<Entry> entries = new ArrayList<>();
                           for (int index = 0; index < currentData.get(position).size(); index++)
                           {  entries.add(new Entry( index,Float.valueOf(currentData.get(position).get(index)) ));}
                           updateChart(binding , entries ,entries.size() );
                       }


                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        viewModel.updateData(comID , comActivity,"", "","");
        final ProgressDialog progressdialog = new ProgressDialog(StatsActivity.this  ,R.style.AppCompatAlertDialogStyle);
        progressdialog.setMessage("يتم الان تحميل البيانات..");
        progressdialog.show();
        Log.e( "updatedata: ",comID + "//"+ comActivity +"///////");

//Initialize your Date however you like it.
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int startYear = calendar.get(Calendar.YEAR);
         int starthMonth= calendar.get(Calendar.MONTH) + 1;
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
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




binding.textView4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (branchState ==0 ){
            view.setBackgroundColor(getResources().getColor(R.color.darkblue));
            ((TextView) view).setTextColor(getResources().getColor(R.color.white));//ay haga white set drawable white
            }else if (branchState ==2 ){
            view.setBackgroundColor(getResources().getColor(R.color.darkblue));
            ((TextView) view).setTextColor(getResources().getColor(R.color.white));
            binding.tvChooseBranch.setText("اختر الفرع");
            binding.tvChooseBranch.setTextColor(getResources().getColor(R.color.darkblue));
            binding.tvChooseBranch.setBackground(getResources().getDrawable(R.drawable.white_background_square_padding_right));
        }
        progressdialog.show();
        viewModel.updateData(comID , comActivity,"0" ,
                binding.tvStartDate.getText().toString(),binding.tvEndDate.getText().toString());

        branchState = 1;

        Log.e( "updatedata: ",comID + "//"+ comActivity +"//"+"0"+
                binding.tvStartDate.getText().toString()+ "//"+binding.tvEndDate.getText().toString() );
    }
});

// create and show the alert dialog




        binding.tvChooseBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String[] branches = new String[firstdata.get(11).size()];
                branches = firstdata.get(11).toArray(branches);
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(StatsActivity.this);
                builder.setTitle("اختر الفرع ");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(branches,-1,null);
                builder.setPositiveButton("تم",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton)
                    {  ListView lw = ((AlertDialog)dialog).getListView();
                        if (lw.getCheckedItemPosition()==-1){
                            Log.e( "return: ","return:return:return:" );
                            return;
                        }else {


                            Log.d("", "o" + lw.getCheckedItemPosition() + "], which" + whichButton + "]");

                            // Object checkedItem = lw.getAdapter().getItem(1);
Log.e( "updatedata: ",comID + "//"+ String.valueOf(firstdata.get(11).get(lw.getCheckedItemPosition())) + "//" +
        binding.tvStartDate.getText().toString()+ "//"+binding.tvEndDate.getText().toString() );
                            binding.tvChooseBranch.setText(firstdata.get(11).get(lw.getCheckedItemPosition()));
                            progressdialog.show();
                            viewModel.updateData(comID , comActivity, String.valueOf(firstdata.get(11).get(lw.getCheckedItemPosition())) ,
                                    binding.tvStartDate.getText().toString(),binding.tvEndDate.getText().toString());


                        }


                    }
                });

                final AlertDialog dialog = builder.create();

                dialog.show();


                if (branchState ==0 ){
                    v.setBackgroundColor(getResources().getColor(R.color.darkblue));
                    ((TextView) v).setTextColor(getResources().getColor(R.color.white));
                }else if (branchState ==1){
                    v.setBackgroundColor(getResources().getColor(R.color.darkblue));
                    ((TextView) v).setTextColor(getResources().getColor(R.color.white));
                    binding.textView4.setTextColor(getResources().getColor(R.color.darkblue));
                    binding.textView4.setBackground(getResources().getDrawable(R.drawable.white_background_square_padding_right));
                }
                branchState =2;

            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                         Log.e( " SDK",String.valueOf(Build.VERSION.SDK_INT) );
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        Log.e( "permission: ","granted" );
                        Boolean state =   mChart.saveToGallery(Calendar.getInstance().getTime().toString(),
                                "Charts",
                                "dedc", Bitmap.CompressFormat.PNG,90);
                        if (state) { Toast.makeText(StatsActivity.this, "Chart Saved",
                                Toast.LENGTH_SHORT).show(); }else { Log.e( "cal: ",Calendar.getInstance().getTime().toString() );   }
                    } else {
                        ActivityCompat.requestPermissions(StatsActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1234);
                    }
                }else{
                    Log.e( " SDK",String.valueOf(Build.VERSION.SDK_INT) );
                    Boolean state =   mChart.saveToGallery(Calendar.getInstance().getTime().toString(),"Charts",
                            "dedc", Bitmap.CompressFormat.PNG,90);
                    if (state)  Toast.makeText(StatsActivity.this, "Chart Saved", Toast.LENGTH_SHORT).show();

                    Log.e( "state: ",String.valueOf(Calendar.getInstance().getTime().toString()) );
                }

            }
        });
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatsActivity.this, CompanyActivitiesActivity.class);
                intent.putStringArrayListExtra("activities", activities);
                intent.putExtra("comID", comID);
                intent.putExtra("comName", comName);
                startActivity(intent);
                }
        });

        binding.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                      // Clearing back stack task >> hittin back won't get him back to application  LOGOUT
        Intent intent = new Intent(StatsActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
            }
        });

viewModel.getErrorLD().observe(StatsActivity.this, new Observer<String>() {
    @Override
    public void onChanged(@Nullable String s) {
        progressdialog.dismiss();
        if (s.equals("لا توجد بيانات بالفلاتر المختارة")){
            Toast.makeText(StatsActivity.this, s, Toast.LENGTH_SHORT).show();
            mChart.notifyDataSetChanged();
            mChart.invalidate();
            mChart.clear();

        }
        Toast.makeText(StatsActivity.this, s, Toast.LENGTH_SHORT).show();
    }
});
        viewModel.getResponse().observe(StatsActivity.this, new Observer<ArrayList<ArrayList<String>>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ArrayList<String>> arrayLists) {
                progressdialog.dismiss();
                currentData = arrayLists ;
                if (firstdata.size()==0){  firstdata = arrayLists ; }

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

   void updateChart(final ActivityCompanyStatsBinding binding , ArrayList<Entry> entries , int itemcount){

       ////////  Setting Up Chart ////////////
       mChart = binding.chart;

       mChart.getDescription().setEnabled(false);
       mChart.setBackgroundColor(Color.WHITE);
       mChart.setDrawGridBackground(false);
       mChart.setDrawBarShadow(false);
       mChart.setHighlightFullBarEnabled(false);
       mChart.setNoDataText("لا توجد بيانات لعرضها حاليا");

     mChart.setPinchZoom(false);
     mChart.setMarker(new MyMarkerView(StatsActivity.this , R.layout.chart_marker_stats_activitiy , mDays));

       mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
           @Override
           public void onValueSelected(Entry e, Highlight h) {
               Log.e( "x: ", String.valueOf( e.getX()));
               Log.e( "y: ", String.valueOf( e.getY()));


           }

           @Override
           public void onNothingSelected() {

           }
       });
       // draw bars behind lines
       mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
               /*  CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE,CombinedChart.DrawOrder.CANDLE,*/
               CombinedChart.DrawOrder.LINE,/* CombinedChart.DrawOrder.SCATTER*/
       });

      /* Legend l = mChart.getLegend();
       l.setWordWrapEnabled(true);
       l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
       l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
       l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
       l.setDrawInside(false);*/

       mChart.getLegend().setEnabled(false);
       YAxis rightAxis = mChart.getAxisRight();
       rightAxis.setDrawGridLines(false);
       rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

       YAxis leftAxis = mChart.getAxisLeft();
       leftAxis.setDrawGridLines(false);
       leftAxis.setAxisMinimum(0f);// this replaces setStartAtZero(true)


       final XAxis xAxis = mChart.getXAxis();
      xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

       xAxis.setAxisMinimum(0f);
       xAxis.setAxisMaximum(9f);

       xAxis.setGranularity(1f);

       xAxis.setLabelCount(2);
       xAxis.setGranularity(1f);
     xAxis.setAxisMaxValue(mDays.size());
      xAxis.setAxisMinValue(0);
       xAxis.setLabelCount(2);


xAxis.setAvoidFirstLastClipping(true);


       xAxis.setValueFormatter(
               new IAxisValueFormatter() {
                   @Override
                   public String getFormattedValue(float value, AxisBase axis) {
                       // return currentData.get(12).get((int) value % mDays.size());
                       if (value==0) {
                           Log.e( ":00000 ", String.valueOf(value % mDays.size()));
                           return currentData.get(12).get((int) value % mDays.size()); }


                       else if (value==currentData.get(12).size()-1) {
                           Log.e( "://///// ", String.valueOf(value % mDays.size()) +"+++++"
                                   +String.valueOf(currentData.get(12).size()-1));
                           Log.e( "mdays size: ", String.valueOf(mDays.size()));
                           return currentData.get(12).get((int) value % mDays.size());}
                       else {  Log.e( "value:  ''''''' ",String.valueOf(value) );
                           return "";}

                   }}  );
            //   new MyXAxisValueFormatter(mDaysArr)





       CombinedData data = new CombinedData();

       data.setData(generateLineData(entries ,itemcount));
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

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(89, 98, 178));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(30, 52, 125));
        set.setCircleRadius(5f);
        set.setFillColor( getResources().getColor(android.R.color.holo_purple));
        set.setDrawFilled(true);
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.blue_fade);
            set.setFillDrawable(drawable);
        }
        else {
            set.setFillColor(Color.YELLOW);
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1234: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Boolean state =   mChart.saveToGallery(Calendar.getInstance().getTime().toString(),"Charts",
                            "dedc", Bitmap.CompressFormat.PNG,90);
                    if (state){  Toast.makeText(StatsActivity.this, "Chart Saved", Toast.LENGTH_SHORT).show();
                    }else {    Log.e( ":calcol ",Calendar.getInstance().getTime().toString() );}

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(StatsActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
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
