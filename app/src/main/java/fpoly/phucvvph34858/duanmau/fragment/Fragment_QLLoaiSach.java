package fpoly.phucvvph34858.duanmau.fragment;

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
import fpoly.phucvvph34858.duanmau.adpter.LoaiSachAdapter;
import fpoly.phucvvph34858.duanmau.dao.LoaiSachDAO;
import fpoly.phucvvph34858.duanmau.model.LoaiSach;


public class Fragment_QLLoaiSach extends Fragment {

    LoaiSachDAO loaiSachDAO;
    RecyclerView recyclerViewQLLS;
    ArrayList<LoaiSach> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlloaisach,container,false);
        recyclerViewQLLS = view.findViewById(R.id.recyclerQLLS);
        FloatingActionButton floatAdd = view.findViewById(R.id.floataddLoaiSach);
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
        loaiSachDAO = new LoaiSachDAO(getContext());
        list = loaiSachDAO.getDSLoaiSach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewQLLS.setLayoutManager(linearLayoutManager);
        LoaiSachAdapter adapter = new LoaiSachAdapter(list,getContext());
        recyclerViewQLLS.setAdapter(adapter);

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_loaisach, null);
        builder.setView(view);

        final EditText edtMaLoai = view.findViewById(R.id.edtMaLoai); // Thay thế bằng EditText tương ứng
        final EditText edtTenLoai = view.findViewById(R.id.edtTenLoai); // Thay thế bằng EditText tương ứng

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String maLoai = edtMaLoai.getText().toString().trim();
                String tenLoai = edtTenLoai.getText().toString().trim();

                if (!maLoai.isEmpty() && !tenLoai.isEmpty()) {
                    int maloai = Integer.parseInt(maLoai);
                    themLoaiSach(maloai, tenLoai);
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void themLoaiSach(int maloai,String tenloai) {
        LoaiSach loaiSach = new LoaiSach(maloai, tenloai);
        boolean kiemtra = loaiSachDAO.themLoaiSach(loaiSach);
        if (kiemtra) {
            Toast.makeText(getContext(), "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
            loadData();
        } else {
            Toast.makeText(getContext(), "Thêm loại sách thất bại", Toast.LENGTH_SHORT).show();
        }
    }

}
