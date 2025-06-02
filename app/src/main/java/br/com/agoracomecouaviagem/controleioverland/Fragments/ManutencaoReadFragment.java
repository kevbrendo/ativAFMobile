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
import br.com.agoracomecouaviagem.controleioverland.Edits.EditManutencaoView;
import br.com.agoracomecouaviagem.controleioverland.Entities.Manutencao;
import br.com.agoracomecouaviagem.controleioverland.Insert.InsertManutencaoView;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterAlimentacao;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterManutencao;
import br.com.agoracomecouaviagem.controleioverland.Utils.ClickListener;

public class ManutencaoReadFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btnAdicionar;
    private TextView total;
    private List<Manutencao> lstManutencao = new ArrayList<>();
    private ReadDAO read ;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ManutencaoReadFragment() {

    }

    public static ManutencaoReadFragment newInstance(String param1, String param2) {
        ManutencaoReadFragment fragment = new ManutencaoReadFragment();
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
        String nomeViagemAtiva = getArguments().getString("nomeViagemAtiva");
        int idViagemAtiva = getArguments().getInt("idViagemAtiva");
        lstManutencao = read.readManutencaoByIdViagem(idViagemAtiva);


        // Configurar adapter
        AdapterManutencao adaptador = new AdapterManutencao(lstManutencao);
        recyclerView.setAdapter(adaptador);

        // Exibir o total de despesa
        Double valorTotal = 0.0;
        for(Manutencao m : lstManutencao){
            valorTotal += m.getValor();
        }

        total.setText("R$: " + String.format("%.2f", valorTotal));

        btnAdicionar = view.findViewById(R.id.btnAdicionarFragment);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InsertManutencaoView.class);
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

              Manutencao manutencaoSelecionado =   lstManutencao.get(position);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Intent intent = new Intent(getContext(), EditManutencaoView.class);
                    intent.putExtra("idManutencaoEditar", manutencaoSelecionado.getId());            /// AQUI EDITAR
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    intent.putExtra("nomeViagemAtiva", nomeViagemAtiva);
                    startActivity(intent);
                }


            }

            @Override
            public void onLongItemClick(View view, int position) {
                Manutencao manutencao = lstManutencao.get(position);

                // Construa a mensagem para exibir na caixa de diálogo
                StringBuilder message = new StringBuilder();
                message.append("Data: ").append(AdapterAlimentacao.formatDate(manutencao.getData())).append("\n");
                message.append("Valor R$: ").append(String.format("%.2f", manutencao.getValor())).append("\n");
                message.append("Local: ").append(manutencao.getLocal()).append("\n");
                message.append("Metodo de Pagamento: ").append(manutencao.getMetodoPagamento()).append("\n");
                message.append("Descrição: ").append(manutencao.getDescricao()).append("\n");
                message.append("Tipo de Manutencao: ").append(manutencao.getTipoDeManutencao());

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
