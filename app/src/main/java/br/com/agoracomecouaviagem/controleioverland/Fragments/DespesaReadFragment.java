package br.com.agoracomecouaviagem.controleioverland.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.Edits.EditDespesaView;
import br.com.agoracomecouaviagem.controleioverland.Entities.Despesa;
import br.com.agoracomecouaviagem.controleioverland.Insert.InsertDespesaView;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterAlimentacao;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterDespesa;
import br.com.agoracomecouaviagem.controleioverland.Utils.ClickListener;

public class DespesaReadFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btnAdicionar;
    private TextView total;
    private List<Despesa> lstDespesa = new ArrayList<>();
    private ReadDAO read ;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DespesaReadFragment() {

    }

    public static DespesaReadFragment newInstance(String param1, String param2) {
        DespesaReadFragment fragment = new DespesaReadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_despesasviagem_read, container, false);

        recyclerView = view.findViewById(R.id.rcrView);
        total = view.findViewById(R.id.textTotal);

        // Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Inicializar o DAO
        read = new ReadDAO(requireContext());

        // Ler os dados do abastecimento
        int idViagemAtiva = getArguments().getInt("idViagemAtiva");
        String nomeViagemAtiva = getArguments().getString("nomeViagemAtiva");
        lstDespesa = read.readDespesaByIdViagem(idViagemAtiva);




        // Configurar adapter
        AdapterDespesa adaptador = new AdapterDespesa(lstDespesa);
        recyclerView.setAdapter(adaptador);

        // Exibir o total de despesa
        Double valorTotal = 0.0;
        for(Despesa d : lstDespesa){
            valorTotal += d.getValor();
        }

        total.setText("R$: " + String.format("%.2f", valorTotal));

        btnAdicionar = view.findViewById(R.id.btnAdicionarFragment);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InsertDespesaView.class);
                intent.putExtra("idViagemAtiva", idViagemAtiva);
                intent.putExtra("nomeViagemAtiva", nomeViagemAtiva);
                startActivity(intent);
            }
        });

        recyclerView.addOnItemTouchListener(new ClickListener(requireContext(), recyclerView, new ClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onItemClick(View view, int position) {

              Despesa despesaSelecionado =   lstDespesa.get(position);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Intent intent = new Intent(getContext(), EditDespesaView.class);
                    intent.putExtra("idDespesaEditar", despesaSelecionado.getId());            /// AQUI EDITAR
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    intent.putExtra("nomeViagemAtiva", nomeViagemAtiva);
                    startActivity(intent);
                }


            }

            @Override
            public void onLongItemClick(View view, int position) {
                Despesa despesa = lstDespesa.get(position);

                // Construa a mensagem para exibir na caixa de diálogo
                StringBuilder message = new StringBuilder();
                message.append("Data: ").append(AdapterAlimentacao.formatDate(despesa.getData())).append("\n");
                message.append("Valor R$: ").append(String.format("%.2f", despesa.getValor())).append("\n");
                message.append("Local: ").append(despesa.getLocal()).append("\n");
                message.append("Metodo de Pagamento: ").append(despesa.getMetodoPagamento()).append("\n");
                message.append("Descrição: ").append(despesa.getDescricao()).append("\n");

                // Mostrar uma caixa de diálogo com as informações do item
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Detalhes da Despesa");
                builder.setMessage(message.toString());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        }));

        return view;
    }


}
