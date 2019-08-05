package akshay.kapase.tvsautomobile.barchart;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import akshay.kapase.tvsautomobile.R;

public class BarChartActivity extends AppCompatActivity {

    BarChartViewModel barChartViewModel;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        barChart = findViewById(R.id.bar_chart_view);
        barChartViewModel = createViewModel();
        showBarChart();
    }

    private void showBarChart() {
        barChart.setData(barChartViewModel.getTopTenEmployees());
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate();
    }

    private BarChartViewModel createViewModel() {
        return ViewModelProviders.of(this).get(BarChartViewModel.class);
    }

}
