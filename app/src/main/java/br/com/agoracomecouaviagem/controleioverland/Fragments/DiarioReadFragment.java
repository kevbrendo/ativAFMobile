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
import br.com.agoracomecouaviagem.controleioverland.Edits.EditDiarioView;
import br.com.agoracomecouaviagem.controleioverland.Entities.Diario;
import br.com.agoracomecouaviagem.controleioverland.Insert.InsertDiarioView;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterAbastecimento;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterDiario;
import br.com.agoracomecouaviagem.controleioverland.Utils.ClickListener;

public class DiarioReadFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btnAdicionar;
    private TextView total;
    private List<Diario> lstDiario = new ArrayList<>();
    private ReadDAO read ;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DiarioReadFragment() {

    }

    public static DiarioReadFragment newInstance(String param1, String param2) {
        DiarioReadFragment fragment = new DiarioReadFragment();
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
        total.setVisibility(View.GONE);

        // Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Inicializar o DAO
        read = new ReadDAO(requireContext());

        // Ler os dados do abastecimento
        int idViagemAtiva = getArguments().getInt("idViagemAtiva");
        String nomeViagemAtiva = getArguments().getString("nomeViagemAtiva");
        lstDiario = read.readDiarioByIdViagem(idViagemAtiva);


        // Configurar adapter
        AdapterDiario adaptador = new AdapterDiario(lstDiario);
        recyclerView.setAdapter(adaptador);


        btnAdicionar = view.findViewById(R.id.btnAdicionarFragment);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InsertDiarioView.class);
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

              Diario diarioSelecionado =   lstDiario.get(position);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Intent intent = new Intent(getContext(), EditDiarioView.class);
                    intent.putExtra("idDiarioEditar", diarioSelecionado.getId());
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    intent.putExtra("nomeViagemAtiva", nomeViagemAtiva);
                    startActivity(intent);
                }


            }

            @Override
            public void onLongItemClick(View view, int position) {
                Diario diario = lstDiario.get(position);

                // Construa a mensagem para exibir na caixa de diálogo
                StringBuilder message = new StringBuilder();
                message.append("Data: ").append(AdapterAbastecimento.formatDate(diario.getData())).append("\n");
                message.append("Local: ").append(diario.getLocal()).append("\n");
                message.append("Comentário: ").append(diario.getComentario()).append("\n");

                // Mostrar uma caixa de diálogo com as informações do item
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Exibição do Diário");
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
