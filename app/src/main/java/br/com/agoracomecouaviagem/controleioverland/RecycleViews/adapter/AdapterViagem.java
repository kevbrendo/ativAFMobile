package br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.Entities.Viagem;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;


public class AdapterViagem extends RecyclerView.Adapter<AdapterViagem.MyViewHolder> {


    private final Context context;
    private ReadDAO read;
    private List<Viagem> lstViagem;

    private DataTextWatcher dtw = new DataTextWatcher();



    public AdapterViagem(Context context, List<Viagem> lstViagem) {
        this.context = context;
        this.lstViagem = lstViagem;
        this.read = new ReadDAO(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle_viagem,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Viagem viagem = lstViagem.get(position);

        Double gastoParcial = read.calcularGastoTotal(viagem.getId());
        viagem.setGastoParcial(gastoParcial);

        holder.dataInicio.setText("Inicio: " + formatDate(viagem.getDataDeInicio()));
        holder.valorParcial.setText("Gasto Parcial R$:" + String.format("%.2f", viagem.getGastoParcial()));
        holder.localPartida.setText("Local: " + viagem.getLocalDePartida());
        holder.nomeViagem.setText(viagem.getNome());
        holder.kmParcial.setText("Quilometragem percorrida: "+viagem.getKilometragemParcial().toString());


    }


    @Override
    public int getItemCount() {
        return lstViagem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView dataInicio;
        TextView valorParcial;
        TextView localPartida;
        TextView nomeViagem;
        TextView kmParcial;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeViagem = itemView.findViewById(R.id.cardViewNome);
            dataInicio = itemView.findViewById(R.id.cardViewInicio);
            localPartida = itemView.findViewById(R.id.cardViewLocal);
            valorParcial = itemView.findViewById(R.id.cardViewValor);
            kmParcial = itemView.findViewById(R.id.cardViewtKilometragemParcial);

            }
    }

    public static String formatDate(LocalDate date) {
        String dataFormatada = "";
        DateTimeFormatter outputFormatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataFormatada = date.format(outputFormatter);
        }
        return dataFormatada;
    }
}
