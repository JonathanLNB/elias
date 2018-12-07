package com.topicosweb.elias.practica3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.topicosweb.elias.practica3.Adaptadores.ClienteAdapter;
import com.topicosweb.elias.practica3.Modelos.Cliente;
import com.topicosweb.elias.practica3.Modelos.Orden;
import com.topicosweb.elias.practica3.Retrofit.Config;
import com.topicosweb.elias.practica3.Retrofit.Practica;
import com.topicosweb.elias.practica3.Retrofit.UnsafeOkHttpClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rClientes;
    private Gson gson;
    private ArrayList<Cliente> clientes;
    private Retrofit retrofit;
    private Practica restClient;
    private ClienteAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rClientes = (RecyclerView) findViewById(R.id.rClientes);
        rClientes.setHasFixedSize(true);
        rClientes.setLayoutManager(new LinearLayoutManager(this));
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        gson = new GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restClient = retrofit.create(Practica.class);
        loadClientes();
    }

    private void loadClientes() {
        Call<ArrayList<Cliente>> call = restClient.getClientes();
        call.enqueue(new Callback<ArrayList<Cliente>>() {
            @Override
            public void onResponse(Call<ArrayList<Cliente>> call, Response<ArrayList<Cliente>> response) {
                if (response.isSuccessful()) {
                    clientes = response.body();
                    for (Cliente c : clientes)
                        loadOrdenes(c);
                    adaptador = new ClienteAdapter(clientes, MainActivity.this);
                    rClientes.setAdapter(adaptador);
                } else
                    Toast.makeText(MainActivity.this, "Error, en la conexión", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Cliente>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error, en la conexión", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadOrdenes(final Cliente c) {
        Call<ArrayList<Orden>> call = restClient.getOrdenes(c.getId());
        call.enqueue(new Callback<ArrayList<Orden>>() {
            @Override
            public void onResponse(Call<ArrayList<Orden>> call, Response<ArrayList<Orden>> response) {
                if (response.isSuccessful()) {
                    c.setOrdenes(response.body().size());
                    adaptador.notifyDataSetChanged();
                } else
                    Toast.makeText(MainActivity.this, "Error, en la conexión", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Orden>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error, en la conexión", Toast.LENGTH_LONG).show();
            }
        });
    }
}
