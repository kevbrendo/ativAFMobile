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
import br.com.agoracomecouaviagem.controleioverland.Edits.EditPedagioView;
import br.com.agoracomecouaviagem.controleioverland.Entities.Pedagio;
import br.com.agoracomecouaviagem.controleioverland.Insert.InsertPedagioView;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterAlimentacao;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterPedagio;
import br.com.agoracomecouaviagem.controleioverland.Utils.ClickListener;

public class PedagioReadFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btnAdicionar;
    private TextView total;
    private List<Pedagio> lstPedagio = new ArrayList<>();
    private ReadDAO read ;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PedagioReadFragment() {

    }

    public static PedagioReadFragment newInstance(String param1, String param2) {
        PedagioReadFragment fragment = new PedagioReadFragment();
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
        lstPedagio = read.readPedagioByIdViagem(idViagemAtiva);




        // Configurar adapter
        AdapterPedagio adaptador = new AdapterPedagio(lstPedagio);
        recyclerView.setAdapter(adaptador);

        // Exibir o total de despesa
        Double valorTotal = 0.0;
        for(Pedagio p : lstPedagio){
            valorTotal += p.getValor();
        }

        total.setText("R$: " + String.format("%.2f", valorTotal));

        btnAdicionar = view.findViewById(R.id.btnAdicionarFragment);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InsertPedagioView.class);
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

              Pedagio pedagioSelecionado =   lstPedagio.get(position);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Intent intent = new Intent(getContext(), EditPedagioView.class);
                    intent.putExtra("idPedagioEditar", pedagioSelecionado.getId());            /// AQUI EDITAR
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    intent.putExtra("nomeViagemAtiva", nomeViagemAtiva);
                    startActivity(intent);
                }


            }

            @Override
            public void onLongItemClick(View view, int position) {
                Pedagio pedagio = lstPedagio.get(position);

                // Construa a mensagem para exibir na caixa de diálogo
                StringBuilder message = new StringBuilder();
                message.append("Data: ").append(AdapterAlimentacao.formatDate(pedagio.getData())).append("\n");
                message.append("Valor R$: ").append(String.format("%.2f", pedagio.getValor())).append("\n");
                message.append("Local: ").append(pedagio.getLocal()).append("\n");
                message.append("Metodo de Pagamento: ").append(pedagio.getMetodoPagamento()).append("\n");
                message.append("Descrição: ").append(pedagio.getDescricao()).append("\n");
                message.append("Qualidade da via: ").append(pedagio.getQualidadeDaVia());

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
