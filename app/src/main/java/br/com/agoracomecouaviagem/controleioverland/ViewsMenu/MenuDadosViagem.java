package br.com.agoracomecouaviagem.controleioverland.ViewsMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.Edits.EditViagemView;
import br.com.agoracomecouaviagem.controleioverland.Entities.Viagem;
import br.com.agoracomecouaviagem.controleioverland.Fragments.AbastecimentoReadFragment;
import br.com.agoracomecouaviagem.controleioverland.Fragments.AlimentacaoReadFragment;
import br.com.agoracomecouaviagem.controleioverland.Fragments.DespesaReadFragment;
import br.com.agoracomecouaviagem.controleioverland.Fragments.DiarioReadFragment;
import br.com.agoracomecouaviagem.controleioverland.Fragments.EstacionamentoReadFragment;
import br.com.agoracomecouaviagem.controleioverland.Fragments.HospedagemReadFragment;
import br.com.agoracomecouaviagem.controleioverland.Fragments.ManutencaoReadFragment;
import br.com.agoracomecouaviagem.controleioverland.Fragments.PasseioReadFragment;
import br.com.agoracomecouaviagem.controleioverland.Fragments.PedagioReadFragment;
import br.com.agoracomecouaviagem.controleioverland.R;


public class MenuDadosViagem extends AppCompatActivity {

    private TextView txtNomeViagem;
    private Button btnEditarViagem;
    private TextView btnAbastecimento;
    private TextView btnAlimentacao;
    private TextView btnDespesa;
    private TextView btnDiario;
    private TextView btnEstacionamento;
    private TextView btnHospedagem;
    private TextView btnManutencao;
    private TextView btnPasseio;
    private TextView btnPedagio;

    private ReadDAO read;
    private AbastecimentoReadFragment abastecimentoReadFragment;
    private AlimentacaoReadFragment alimentacaoReadFragment;
    private DespesaReadFragment despesaReadFragment;
    private DiarioReadFragment diarioReadFragment;
    private EstacionamentoReadFragment estacionamentoReadFragment;
    private HospedagemReadFragment hospedagemReadFragment;
    private ManutencaoReadFragment manutencaoReadFragment;
    private PasseioReadFragment passeioReadFragment;
    private PedagioReadFragment pedagioReadFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_dados_viagem);

        read = new ReadDAO(this);

        txtNomeViagem = findViewById(R.id.textNomeViagemDados);
        btnEditarViagem = findViewById(R.id.btnEditarViagem);
        btnAbastecimento = findViewById(R.id.abastecimento);
        btnAlimentacao = findViewById(R.id.alimentacao);
        btnDespesa = findViewById(R.id.despesas);
        btnDiario = findViewById(R.id.diario);
        btnEstacionamento = findViewById(R.id.estacionamento);
        btnHospedagem = findViewById(R.id.hospedagem);
        btnManutencao = findViewById(R.id.manutencao);
        btnPasseio = findViewById(R.id.passeio);
        btnPedagio = findViewById(R.id.pedagio);


        Bundle bundle = getIntent().getExtras();
      String nomeViagemAtiva =  bundle.getString("nomeViagemAtiva");

      txtNomeViagem.setText("Viagem: " + nomeViagemAtiva);
      Integer idViagemAtiva = bundle.getInt("idViagemAtiva");
      Integer fragmentId = bundle.getInt("fragment_id");


      Viagem viagemSelecionada = read.readViagemById(idViagemAtiva);

        bundle = new Bundle();
        bundle.putInt("idViagemAtiva", idViagemAtiva);
        bundle.putString("nomeViagemAtiva", nomeViagemAtiva);



        abastecimentoReadFragment = new AbastecimentoReadFragment();
        abastecimentoReadFragment.setArguments(bundle);

        alimentacaoReadFragment = new AlimentacaoReadFragment();
        alimentacaoReadFragment.setArguments(bundle);

        despesaReadFragment = new DespesaReadFragment();
        despesaReadFragment.setArguments(bundle);

        diarioReadFragment = new DiarioReadFragment();
        diarioReadFragment.setArguments(bundle);

        estacionamentoReadFragment = new EstacionamentoReadFragment();
        estacionamentoReadFragment.setArguments(bundle);

        hospedagemReadFragment = new HospedagemReadFragment();
        hospedagemReadFragment.setArguments(bundle);

        manutencaoReadFragment = new ManutencaoReadFragment();
        manutencaoReadFragment.setArguments(bundle);

        passeioReadFragment = new PasseioReadFragment();
        passeioReadFragment.setArguments(bundle);

        pedagioReadFragment = new PedagioReadFragment();
        pedagioReadFragment.setArguments(bundle);

        Fragment fragment;
        switch (fragmentId) {
            case 1:
                fragment = abastecimentoReadFragment;
                break;
            case 2:
                fragment = alimentacaoReadFragment;
                break;
            case 3:
                fragment = despesaReadFragment;
                break;
            case 4:
                fragment = diarioReadFragment;
                break;
            case 5:
                fragment = estacionamentoReadFragment;
                break;
            case 6:
                fragment = hospedagemReadFragment;
                break;
            case 7:
                fragment = manutencaoReadFragment;
                break;
            case 8:
                fragment = passeioReadFragment;
                break;
            case 9:
                fragment = pedagioReadFragment;
                break;
            default:
                fragment = abastecimentoReadFragment;
                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentConteudo,fragment);
        transaction.commit();
        getSupportFragmentManager().popBackStack();


        btnEditarViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Intent intent = new Intent(getApplicationContext(), EditViagemView.class);
                    intent.putExtra("idViagem", viagemSelecionada.getId());
                    intent.putExtra("nomeViagemAtiva", viagemSelecionada.getNome());
                    startActivity(intent);
                    getSupportFragmentManager().popBackStack();
                }
            }
        });

        btnAbastecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteudo,abastecimentoReadFragment);
                transaction.commit();
                getSupportFragmentManager().popBackStack();
            }
        });

        btnAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteudo, alimentacaoReadFragment);
                transaction.commit();
                getSupportFragmentManager().popBackStack();
            }
        });

        btnDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteudo, despesaReadFragment);
                transaction.commit();
                getSupportFragmentManager().popBackStack();
            }
        });

        btnDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteudo, diarioReadFragment);
                transaction.commit();
                getSupportFragmentManager().popBackStack();
            }
        });

        btnEstacionamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteudo, estacionamentoReadFragment);
                transaction.commit();
                getSupportFragmentManager().popBackStack();
            }
        });

        btnHospedagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteudo, hospedagemReadFragment);
                transaction.commit();
                getSupportFragmentManager().popBackStack();
            }
        });

        btnManutencao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteudo, manutencaoReadFragment);
                transaction.commit();
                getSupportFragmentManager().popBackStack();
            }
        });

        btnPasseio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteudo, passeioReadFragment);
                transaction.commit();
                getSupportFragmentManager().popBackStack();
            }
        });

        btnPedagio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentConteudo, pedagioReadFragment);
                transaction.commit();
                getSupportFragmentManager().popBackStack();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




    }


}