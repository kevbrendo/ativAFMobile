package br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.Entities.Pedagio;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;

public class AdapterPedagio extends RecyclerView.Adapter<AdapterPedagio.MyViewHolder> {

    private List<Pedagio> lstPedagio;
    private DataTextWatcher dtw = new DataTextWatcher();

    public AdapterPedagio(List<Pedagio> pedagio) {
        this.lstPedagio = pedagio;
    }

    

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.despesas_gerais_layout,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       Pedagio pedagio = lstPedagio.get(position);

       holder.data.setText("Data: " + formatDate(pedagio.getData()));
       holder.valor.setText("Valor R$:" + String.format("%.2f", pedagio.getValor()));
       holder.local.setText("Local: " + pedagio.getLocal());
       holder.categoria.setText(pedagio.getCategoria());
       holder.descricao.setText(pedagio.getDescricao());
    }

    @Override
    public int getItemCount() {
        return this.lstPedagio.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView data;
        TextView valor;
        TextView local;
        TextView categoria;
        TextView descricao;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            data = itemView.findViewById(R.id.cardViewNome);
            valor = itemView.findViewById(R.id.cardViewValor);
            local = itemView.findViewById(R.id.cardViewLocal);
            categoria = itemView.findViewById(R.id.cardViewInicio);
            descricao = itemView.findViewById(R.id.cardViewDescricao);
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
