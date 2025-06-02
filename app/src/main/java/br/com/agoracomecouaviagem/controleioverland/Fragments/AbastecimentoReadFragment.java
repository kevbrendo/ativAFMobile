package br.com.agoracomecouaviagem.controleioverland.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.Edits.EditAbastecimentoView;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterAbastecimento;
import br.com.agoracomecouaviagem.controleioverland.Entities.Abastecimento;
import br.com.agoracomecouaviagem.controleioverland.Utils.ClickListener;
import br.com.agoracomecouaviagem.controleioverland.Insert.InsertAbastecimentoView;

public class AbastecimentoReadFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btnAdicionar;
    private TextView total;
    private List<Abastecimento> lstAbastecimento = new ArrayList<>();
    private ReadDAO read ;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AbastecimentoReadFragment() {
        // Required empty public constructor
    }

    public static AbastecimentoReadFragment newInstance(String param1, String param2) {
        AbastecimentoReadFragment fragment = new AbastecimentoReadFragment();
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
        lstAbastecimento = read.readAbastecimentoByIdViagem(idViagemAtiva);

        // Configurar adapter
        AdapterAbastecimento adaptador = new AdapterAbastecimento(lstAbastecimento);
        recyclerView.setAdapter(adaptador);

        // Exibir o total de abastecimento
        Double valorTotal = 0.0;
        for(Abastecimento a : lstAbastecimento){
            valorTotal += a.getValor();
        }

        total.setText("R$: " + String.format("%.2f", valorTotal));

        btnAdicionar = view.findViewById(R.id.btnAdicionarFragment);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),InsertAbastecimentoView.class);
                intent.putExtra("idViagemAtiva", idViagemAtiva);
                intent.putExtra("nomeViagemAtiva", nomeViagemAtiva);
                startActivity(intent);
            }
        });

        recyclerView.addOnItemTouchListener(new ClickListener(requireContext(), recyclerView, new ClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lógica do clique do item, se necessário
            }

            @Override
            public void onItemClick(View view, int position) {

              Abastecimento abastecimentoSelecionado =   lstAbastecimento.get(position);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Intent intent = new Intent(getContext(), EditAbastecimentoView.class);
                    intent.putExtra("idAbastecimentoEditar", abastecimentoSelecionado.getId());
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    intent.putExtra("nomeViagemAtiva", nomeViagemAtiva);
                    startActivity(intent);
                }


            }

            @Override
            public void onLongItemClick(View view, int position) {
                Abastecimento abastecimento = lstAbastecimento.get(position);

                // Construa a mensagem para exibir na caixa de diálogo
                StringBuilder message = new StringBuilder();
                message.append("Data: ").append(AdapterAbastecimento.formatDate(abastecimento.getData())).append("\n");
                message.append("Valor R$: ").append(String.format("%.2f", abastecimento.getValor())).append("\n");
                message.append("Local: ").append(abastecimento.getLocal()).append("\n");
                message.append("Metodo de Pagamento: ").append(abastecimento.getMetodoPagamento()).append("\n");
                message.append("Quantidade de Combustivel: ").append(String.format("%.2f", abastecimento.getQuantidadeCombustivel())).append(" Litros \n");
                message.append("Valor por Litro R$: ").append(String.format("%.2f", abastecimento.getValorPorLitro())).append("\n");
                message.append("Descrição: ").append(abastecimento.getDescricao()).append("\n");

                // Mostrar uma caixa de diálogo com as informações do item
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Detalhes do Abastecimento");
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
