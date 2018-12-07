package com.topicosweb.elias.practica3.Adaptadores;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.topicosweb.elias.practica3.Modelos.Cliente;
import com.topicosweb.elias.practica3.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {
    private ArrayList<Cliente> clientes;
    private Activity context;
    private Realm mRealm;

    public ClienteAdapter(ArrayList<Cliente> clientes, Activity context) {
        this.clientes = clientes;
        this.context = context;
        Realm.init(context);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(configuration);
        mRealm = Realm.getDefaultInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.listado, parent, false);
        ClienteAdapter.ViewHolder viewHolder = new ClienteAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cliente p = clientes.get(position);
        Picasso.get().load(p.getAvatarUrl()).into(holder.iFoto);
        holder.vNombre.setText(p.getNombre());
        holder.vCorreo.setText(p.getCorreo());
        holder.vOrdenes.setText(context.getString(R.string.ordenes_totales) + " " + p.getOrdenes());
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout lSeleccion;
        CircleImageView iFoto;
        TextView vNombre;
        TextView vCorreo;
        TextView vOrdenes;

        public ViewHolder(View itemView) {
            super(itemView);
            lSeleccion = (RelativeLayout) itemView.findViewById(R.id.lSeleccion);
            iFoto = (CircleImageView) itemView.findViewById(R.id.iFoto);
            vNombre = (TextView) itemView.findViewById(R.id.vNombre);
            vCorreo = (TextView) itemView.findViewById(R.id.vCorreo);
            vOrdenes = (TextView) itemView.findViewById(R.id.vOrdenes);

        }

    }
}