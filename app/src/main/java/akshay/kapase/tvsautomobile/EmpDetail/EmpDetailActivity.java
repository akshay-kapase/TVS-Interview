package akshay.kapase.tvsautomobile.EmpDetail;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import akshay.kapase.tvsautomobile.R;
import akshay.kapase.tvsautomobile.models.Employee;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmpDetailActivity extends AppCompatActivity {

    EmpDetailViewModel empDetailViewModel;
    TextView tvEmpName, tvEmpRole, tvEmpAddress, tvEmpSalary, tvJoinDate, tvTimestamp;
    ActionBar actionBar;
    ImageView ivCamera;
    ImageView empPic;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_detail);
        ButterKnife.bind(this);
        initViews();
        Bundle bundle = getIntent().getExtras();
        String empId = "";
        if(bundle != null){
            empId = bundle.getString("empId");
        }
        empDetailViewModel = createViewModel(empId);
        empDetailViewModel.getEmployeeDetail().observe(this,new EmpDetailObserver());
    }

    private void initViews() {
        actionBar = getSupportActionBar();
        actionBar.setTitle("Employee Details");
        tvEmpName = findViewById(R.id.tvName);
        tvEmpRole = findViewById(R.id.tvRole);
        tvEmpAddress = findViewById(R.id.tvAddress);
        tvEmpSalary = findViewById(R.id.tvSalary);
        tvJoinDate = findViewById(R.id.tvJoinDate);
        ivCamera = findViewById(R.id.ivCamera);
        empPic = findViewById(R.id.empPic);
        tvTimestamp = findViewById(R.id.tvTimestamp);
    }

    @OnClick(R.id.ivCamera)
    public void getEmpPic(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            }
            else {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        } else{
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            empPic.setImageBitmap(photo);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            tvTimestamp.setText(formatter.format(date));
        }
    }

    private EmpDetailViewModel createViewModel(String empId) {
        return ViewModelProviders.of(this, new EmpDetailViewModelFactory(this.getApplication(), empId)).get(EmpDetailViewModel.class);
    }

    class EmpDetailObserver implements Observer<Employee> {
        @Override
        public void onChanged(@Nullable Employee employee) {
            if(employee != null){
                tvEmpName.setText(employee.getEmpName());
                tvEmpRole.setText(employee.getEmpRole());
                tvEmpAddress.setText(employee.getEmpAddress());
                tvEmpSalary.setText(employee.getEmpSalary());
                tvJoinDate.setText(employee.getEmpJoinDate());
            }
        }
    }
}
