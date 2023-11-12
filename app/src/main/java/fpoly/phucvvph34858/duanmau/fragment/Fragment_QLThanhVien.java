package fpoly.phucvvph34858.duanmau.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.phucvvph34858.duanmau.R;
import fpoly.phucvvph34858.duanmau.adpter.ThanhVienAdapter;
import fpoly.phucvvph34858.duanmau.dao.ThanhVienDAO;
import fpoly.phucvvph34858.duanmau.model.ThanhVien;


public class Fragment_QLThanhVien extends Fragment {

    ThanhVienDAO thanhVienDAO;
    RecyclerView recyclerViewQLTV;
    ArrayList<ThanhVien> list;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlthanhvien,container,false);
        recyclerViewQLTV = view.findViewById(R.id.recyclerQLTV);
        FloatingActionButton floatAdd = view.findViewById(R.id.floataddThanhVien);
        loadData();
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return view;
    }
    private void loadData() {
        thanhVienDAO = new ThanhVienDAO(getContext());
        list = thanhVienDAO.getDSThanhVien();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewQLTV.setLayoutManager(linearLayoutManager);
        ThanhVienAdapter adapter = new ThanhVienAdapter(list,getContext());
        recyclerViewQLTV.setAdapter(adapter);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_them_thanhvien,null);
        builder.setView(view);

        EditText edtHoTen=view.findViewById(R.id.edtHoTen);
        EditText edtNamSinh =view.findViewById(R.id.edtNamSinh);
        EditText editstk=view.findViewById (R.id.edtstk);
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hoten = edtHoTen.getText().toString();
                String namsinh= edtNamSinh.getText().toString();
                String stk=editstk.getText ().toString ();

                boolean check  = thanhVienDAO.themThanhVien(hoten,namsinh,stk);
                if(check) {
                    Toast.makeText(getContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(getContext(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
