package br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ViewGroup;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.Entities.Abastecimento;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;

public class AdapterAbastecimento extends RecyclerView.Adapter<AdapterAbastecimento.MyViewHolder> {

    private List<Abastecimento> lstAbastecimento;
    private DataTextWatcher dtw = new DataTextWatcher();

    public AdapterAbastecimento(List<Abastecimento> abastecimento) {
        this.lstAbastecimento = abastecimento;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.despesas_gerais_layout,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       Abastecimento abastecimento = lstAbastecimento.get(position);

       holder.data.setText("Data: " + formatDate(abastecimento.getData()));
       holder.valor.setText("Valor R$:" + String.format("%.2f", abastecimento.getValor()));
       holder.local.setText("Local: " + abastecimento.getLocal());
       holder.categoria.setText(abastecimento.getCategoria());
       holder.descricao.setText(abastecimento.getDescricao());

    }

    @Override
    public int getItemCount() {
        return this.lstAbastecimento.size();
    }


    // NOVIDADES PARA IMPLEMENTAR NOS RESTANTES


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

    public interface OnEditarClickListener {
        void onEditarClick(int position);
    }

    public interface OnExcluirClickListener {
        void onExcluirClick(int position);
    }

    private OnEditarClickListener editarClickListener;
    private OnExcluirClickListener excluirClickListener;

    public void setOnEditarClickListener(OnEditarClickListener listener) {
        this.editarClickListener = listener;
    }

    public void setOnExcluirClickListener(OnExcluirClickListener listener) {
        this.excluirClickListener = listener;
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
