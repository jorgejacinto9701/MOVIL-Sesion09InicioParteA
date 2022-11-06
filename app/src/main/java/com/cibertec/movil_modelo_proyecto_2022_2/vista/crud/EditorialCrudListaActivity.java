package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.EditorialAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.EditorialCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorialCrudListaActivity extends NewAppCompatActivity {

    ListView lstEditorial;
    ArrayList<Editorial> data = new ArrayList<Editorial>();
    EditorialCrudAdapter adaptador;
    Button btnFiltrar;
    EditText txtFiltro;

    ServiceEditorial serviceEditorial;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_lista);

        txtFiltro = findViewById(R.id.idCrudEditorialTxtFiltrar);
        btnFiltrar = findViewById(R.id.idCrudEditorialBtnFiltrar);

        lstEditorial = findViewById(R.id.idCrudEditorialListView);
        adaptador = new EditorialCrudAdapter(this, R.layout.activity_editorial_crud_item, data);
        lstEditorial.setAdapter(adaptador);

        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filtro = txtFiltro.getText().toString().trim();
                listaEditoriaPorNombre(filtro);
            }
        });
    }

    public void listaEditoriaPorNombre(String filtro){
         Call<List<Editorial>> call = serviceEditorial.listaEditorialPorNombre(filtro);
         call.enqueue(new Callback<List<Editorial>>() {
             @Override
             public void onResponse(Call<List<Editorial>> call, Response<List<Editorial>> response) {
                 if (response.isSuccessful()){
                     List<Editorial> lstSalida = response.body();
                     data.clear();
                     data.addAll(lstSalida);
                     adaptador.notifyDataSetChanged();
                 }
             }

             @Override
             public void onFailure(Call<List<Editorial>> call, Throwable t) {
                 mensajeAlert(t.getMessage());
                 t.fillInStackTrace();
             }
         });
    }




}