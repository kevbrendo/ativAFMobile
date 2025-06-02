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
import br.com.agoracomecouaviagem.controleioverland.Entities.Hospedagem;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;

public class AdapterHospedagem extends RecyclerView.Adapter<AdapterHospedagem.MyViewHolder> {

    private List<Hospedagem> lstHospedagem;
    private DataTextWatcher dtw = new DataTextWatcher();

    public AdapterHospedagem(List<Hospedagem> hospedagem) {
        this.lstHospedagem = hospedagem;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.despesas_gerais_layout,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       Hospedagem hospedagem = lstHospedagem.get(position);

       holder.data.setText("Data: " + formatDate(hospedagem.getData()));
       holder.valor.setText("Valor R$:" + String.format("%.2f", hospedagem.getValor()));
       holder.local.setText("Local: " + hospedagem.getLocal());
       holder.categoria.setText(hospedagem.getCategoria());
       holder.descricao.setText(hospedagem.getDescricao());
    }

    @Override
    public int getItemCount() {
        return this.lstHospedagem.size();
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
