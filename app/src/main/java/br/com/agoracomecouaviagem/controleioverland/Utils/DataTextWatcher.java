package br.com.agoracomecouaviagem.controleioverland.Utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class DataTextWatcher implements TextWatcher {
    private EditText data;
    private boolean changingText;

    // Construtor para aceitar uma referência ao campo de texto e à flag
    public DataTextWatcher(EditText data, boolean changingText) {
        this.data = data;
        this.changingText = changingText;
    }

    public DataTextWatcher(){

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (changingText) {
            return; // Evite mudanças recursivas
        }

        String currentText = s.toString();
        int length = currentText.length();

        // Verifique se o usuário está apagando caracteres
        boolean deletandoCaracteres = before > 0 && count == 0;

        if (deletandoCaracteres) {
            // O usuário está apagando caracteres, não insira barras automaticamente
            return;
        }

        // Verifique se o comprimento do texto é 2 ou 5 (após o dia ou o mês)
        if (length == 2 || length == 5) {
            changingText = true; // Define a flag para evitar loop
            manipularEntradaData(currentText, length);
        }

        if (!Pattern.matches("[0-9/]+", currentText)) {
            changingText = true;
            data.setText(currentText.replaceAll("[^0-9/]", ""));
            changingText = false;
            return;
        }

        // Limita o ano a no máximo 4 dígitos
        if (length > 6) {
            // Encontre a posição da segunda barra (`/`)
            int posicaoSegundaBarra = currentText.indexOf("/", 3);

            if (posicaoSegundaBarra != -1) {
                // Verifique se há mais de 4 caracteres após a segunda barra
                if (length - posicaoSegundaBarra - 1 > 4) {
                    // Impede mais inserções se o ano tiver mais de 4 dígitos
                    changingText = true; // Define a flag para evitar loop
                    // Trunca o texto para garantir apenas 4 dígitos no ano
                    data.setText(currentText.substring(0, posicaoSegundaBarra + 5));
                    // Posiciona o cursor ao final do texto
                    data.setSelection(posicaoSegundaBarra + 5);
                    changingText = false; // Redefine a flag para evitar loop
                }
            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Não usado, mas deve ser implementado
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Não usado, mas deve ser implementado
    }

    private void manipularEntradaData(String currentText, int length) {
        // Insere uma barra (`/`) após o dia ou o mês
        data.setText(currentText + "/");
        // Move o cursor após a barra inserida
        data.setSelection(length + 1);
        // Redefine a flag para evitar loop
        changingText = false;
    }

    public static String converterData(String dataString) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            try {
                LocalDate data = LocalDate.parse(dataString, formatoEntrada);
                return data.format(formatoSaida);
            } catch (Exception e) {
                // Log a mensagem de erro ou informe o usuário sobre a entrada inválida

                return e.getMessage(); // Retorne null ou uma resposta apropriada para indicar um erro
            }
        }
        return null;
    }




}




