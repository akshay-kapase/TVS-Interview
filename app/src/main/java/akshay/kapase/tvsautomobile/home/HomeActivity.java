package akshay.kapase.tvsautomobile.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.SearchView;
import java.util.ArrayList;
import akshay.kapase.tvsautomobile.R;
import akshay.kapase.tvsautomobile.models.Employee;

public class HomeActivity extends AppCompatActivity {

    HomeViewModel homeViewModel;
    RecyclerView recyclerView;
    Context context;
    SearchView searchView;
    EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        homeViewModel = createHomeViewModel();
        homeViewModel.getAllEmployees().observe(this,new EmployeeObserver());
    }

    private HomeViewModel createHomeViewModel() {
        return ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu,menu);
        MenuItem searchItem = menu.findItem( R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if(searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String s) {
                    homeViewModel.filterEmployees(s);
                    return true;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_bar){
            homeViewModel.showBarChart();
        }
        return true;
    }

    class EmployeeObserver implements Observer<ArrayList<Employee>>{
        @Override
        public void onChanged(@Nullable ArrayList<Employee> employees) {
            if(adapter == null){
                adapter = new EmployeeAdapter(employees);
                recyclerView.setAdapter(adapter);
            } else{
                adapter.employees = employees;
                adapter.notifyDataSetChanged();
            }
        }
    }

    class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder>{

        ArrayList<Employee> employees;

        public EmployeeAdapter(ArrayList<Employee> employees) {
            this.employees = employees;
        }

        @NonNull
        @Override
        public EmployeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,final int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_employee,null,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final EmployeeAdapter.MyViewHolder myViewHolder, int i) {
            myViewHolder.tvEmpName.setText(employees.get(i).getEmpName());
        }

        @Override
        public int getItemCount() {
            return employees.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvEmpName;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvEmpName = itemView.findViewById(R.id.tvEmployeeName);
                tvEmpName.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                homeViewModel.onRowItemClicked(employees.get(getAdapterPosition()));
            }
        }

    }
}
