package br.com.agoracomecouaviagem.controleioverland.ViewsMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.Insert.insertViagemView;

public class MenuPrimeiroAcesso extends AppCompatActivity {

    private Button btnCadastrarPrimeiraViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_primeiro_acesso);

        btnCadastrarPrimeiraViagem = findViewById(R.id.btnCadastrarPrimeiraViagem);

        btnCadastrarPrimeiraViagem = findViewById(R.id.btnCadastrarPrimeiraViagem);
        btnCadastrarPrimeiraViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    intent = new Intent(MenuPrimeiroAcesso.this, insertViagemView.class);
                }
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}