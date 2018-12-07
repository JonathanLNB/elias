package com.topicosweb.elias.practica3.Retrofit;

import com.topicosweb.elias.practica3.Modelos.Cliente;
import com.topicosweb.elias.practica3.Modelos.Orden;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Practica {
    @GET("customers")
    Call<ArrayList<Cliente>> getClientes();

    @GET("orders")
    Call<ArrayList<Orden>> getOrdenes(@Query("customer") Integer id);
}
