package br.com.agoracomecouaviagem.controleioverland.Utils;



public class Relatorio {

    public String criarCabecalho(String string) {

        String cabecalho =
                "---------------------------------------------------------------------" + "\n" +
                        ".                            " + string + "                           ." + "\n" +
                        "---------------------------------------------------------------------" + "\n" + "\n\n";

        return cabecalho;
    }




}
