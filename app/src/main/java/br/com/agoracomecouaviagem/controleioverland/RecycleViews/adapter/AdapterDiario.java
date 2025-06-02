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
import br.com.agoracomecouaviagem.controleioverland.Entities.Diario;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;

public class AdapterDiario extends RecyclerView.Adapter<AdapterDiario.MyViewHolder> {

    private List<Diario> lstDiario;
    private DataTextWatcher dtw = new DataTextWatcher();

    public AdapterDiario(List<Diario> diario) {
        this.lstDiario = diario;
    }

    

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_diario,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       Diario diario = lstDiario.get(position);

       holder.data.setText("Data: " + formatDate(diario .getData()));
       holder.comentario.setText(diario.getComentario());
       holder.local.setText(diario.getLocal());

    }

    @Override
    public int getItemCount() {
        return this.lstDiario.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView data;
        TextView comentario;
        TextView local;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            data = itemView.findViewById(R.id.cardViewNome);
            local = itemView.findViewById(R.id.cardViewLocal);
            comentario = itemView.findViewById(R.id.cardViewTexto);

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

    public String getPreview(String texto) {
        // Divida a string em palavras usando o espaço como delimitador
        String[] palavras = texto.split("\\s+");

        // Verifique se o número de palavras na string é menor ou igual ao número desejado
        if (palavras.length <= 5) {
            // Se o número de palavras for menor ou igual ao desejado, retorne a string original
            return texto;
        } else {
            // Caso contrário, concatene apenas o número desejado de palavras e adicione reticências
            StringBuilder preview = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                preview.append(palavras[i]).append(" ");
            }
            preview.append("..."); // Adiciona reticências para indicar que há mais texto
            return preview.toString().trim(); // Remove espaço extra no final e retorna a prévia
        }
    }
}
