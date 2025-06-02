package br.com.agoracomecouaviagem.controleioverland.DAO;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

import br.com.agoracomecouaviagem.controleioverland.Entities.Despesa;
import br.com.agoracomecouaviagem.controleioverland.Entities.Diario;
import br.com.agoracomecouaviagem.controleioverland.Entities.Estacionamento;
import br.com.agoracomecouaviagem.controleioverland.Entities.Hospedagem;
import br.com.agoracomecouaviagem.controleioverland.Entities.Manutencao;
import br.com.agoracomecouaviagem.controleioverland.Entities.Passeio;
import br.com.agoracomecouaviagem.controleioverland.Entities.Pedagio;
import br.com.agoracomecouaviagem.controleioverland.Entities.Viagem;
import br.com.agoracomecouaviagem.controleioverland.Utils.SQLConnection;
import br.com.agoracomecouaviagem.controleioverland.Entities.Abastecimento;
import br.com.agoracomecouaviagem.controleioverland.Entities.Alimentacao;

public class UpdateDAO {

    private SQLConnection conn;
    private SQLiteDatabase db;

    public UpdateDAO(Context context) {
        conn = new SQLConnection(context);
        db = conn.getWritableDatabase();
    }


    public long updateViagem(Viagem viagem) {
        if (viagem == null) {
            Log.e("Viagem no Update:", "null");
            return -1; // ou lance uma exceção IllegalArgumentException

        }

        ContentValues values = new ContentValues();
        values.put("nome", viagem.getNome());


        if (viagem.getDataDeInicio() != null) {
            values.put("dataDeInicio", viagem.getDataDeInicio().toString());
        } else {
            Log.e("Data de inicio:","Null");
        }

        // Verifica se a data de término não é nula antes de convertê-la em uma string
        if (viagem.getDataDeTermino() != null) {
            values.put("dataDeTermino", viagem.getDataDeTermino().toString());
        } else {
            Log.e("Data de Termino:","Null");
        }

        values.put("localDePartida", viagem.getLocalDePartida());
        values.put("destinoFinal", viagem.getDestinoFinal());
        values.put("kilometragemInicial", viagem.getKilometragemInicial());
        values.put("kilometragemParcial", viagem.getKilometragemParcial());
        values.put("kilometragemFinal", viagem.getKilometragemFinal());
        values.put("gastoParcial", viagem.getGastoParcial());
        values.put("gastoTotal", viagem.getGastoTotal());
        values.put("status", viagem.isStatus());

        try {

            return db.update("viagem", values, "id=?", new String[]{String.valueOf(viagem.getId())});
        } catch (SQLException e) {
            // Trate a exceção adequadamente (por exemplo, log, mensagem de erro, etc.)
            e.printStackTrace();
            return -1; // ou lance uma exceção personalizada
        }
    }

    public long updateAbastecimento(Abastecimento abastecimento) {
        if (abastecimento == null) {
            Log.e("Abastecimento Update:", "null");
            return -1; // ou lance uma exceção IllegalArgumentException

        }

        ContentValues values = new ContentValues();
        values.put("id", abastecimento.getId());


        if (abastecimento.getData() != null) {
            values.put("data", abastecimento.getData().toString());
        } else {
            Log.e("Data :","Null");
        }

        values.put("descricao", abastecimento.getDescricao());
        values.put("valor", abastecimento.getValor());
        values.put("categoria", abastecimento.getCategoria());
        values.put("metodoPagamento", abastecimento.getMetodoPagamento());
        values.put("local", abastecimento.getLocal());
        values.put("quantidadeCombustivel", abastecimento.getQuantidadeCombustivel());
        values.put("valorPorLitro", abastecimento.getValorPorLitro());
        values.put("id_viagem", abastecimento.getId_viagem());

        try {
            return db.update("abastecimento", values, "id=?", new String[]{String.valueOf(abastecimento.getId())});
        } catch (SQLException e) {
            Log.e("ErroUpdateAbastecimento", e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public long updateAlimentacao(Alimentacao alimentacao) {
        if (alimentacao == null) {
            Log.e("Alimentacao Update:", "null");
            return -1; // ou lance uma exceção IllegalArgumentException

        }

        ContentValues values = new ContentValues();
        values.put("id", alimentacao.getId());


        if (alimentacao.getData() != null) {
            values.put("data", alimentacao.getData().toString());
        } else {
            Log.e("Data :","Null");
        }

        values.put("descricao", alimentacao.getDescricao());
        values.put("valor", alimentacao.getValor());
        values.put("categoria", alimentacao.getCategoria());
        values.put("metodoPagamento", alimentacao.getMetodoPagamento());
        values.put("local", alimentacao.getLocal());
        values.put("tipoAlimentacao", alimentacao.getTipoAlimentacao());
        values.put("id_viagem", alimentacao.getId_viagem());

        try {
            return db.update("alimentacao", values, "id=?", new String[]{String.valueOf(alimentacao.getId())});
        } catch (SQLException e) {
            Log.e("ErroUpdateAbastecimento", e.getMessage());
            e.printStackTrace();
            return -1; // ou lance uma exceção personalizada
        }
    }

    public long updateDespesa(Despesa despesa) {
        if (despesa == null) {
            Log.e("Despesa Update:", "null");
            return -1; // ou lance uma exceção IllegalArgumentException

        }

        ContentValues values = new ContentValues();
        values.put("id", despesa.getId());


        if (despesa.getData() != null) {
            values.put("data", despesa.getData().toString());
        } else {
            Log.e("Data :","Null");
        }

        values.put("descricao", despesa.getDescricao());
        values.put("valor", despesa.getValor());
        values.put("categoria", despesa.getCategoria());
        values.put("metodoPagamento", despesa.getMetodoPagamento());
        values.put("local", despesa.getLocal());
        values.put("id_viagem", despesa.getId_viagem());

        try {
            return db.update("despesa", values, "id=?", new String[]{String.valueOf(despesa.getId())});
        } catch (SQLException e) {
            Log.e("ErroUpdateDespesa", e.getMessage());
            e.printStackTrace();
            return -1; // ou lance uma exceção personalizada
        }
    }

    public long updateEstacionamento(Estacionamento estacionamento) {
        if (estacionamento == null) {
            Log.e("Estacionamento Update:", "null");
            return -1; // ou lance uma exceção IllegalArgumentException

        }

        ContentValues values = new ContentValues();
        values.put("id", estacionamento.getId());


        if (estacionamento.getData() != null) {
            values.put("data", estacionamento.getData().toString());
        } else {
            Log.e("Data :","Null");
        }

        values.put("descricao", estacionamento.getDescricao());
        values.put("valor", estacionamento.getValor());
        values.put("categoria", estacionamento.getCategoria());
        values.put("metodoPagamento", estacionamento.getMetodoPagamento());
        values.put("local", estacionamento.getLocal());
        values.put("quantidadeHoras", estacionamento.getQuantidadeHoras());
        values.put("id_viagem", estacionamento.getId_viagem());

        try {
            return db.update("estacionamento", values, "id=?", new String[]{String.valueOf(estacionamento.getId())});
        } catch (SQLException e) {
            Log.e("ErroUpdateDespesa", e.getMessage());
            e.printStackTrace();
            return -1; // ou lance uma exceção personalizada
        }
    }

    public long updateDiario(Diario diario) {
        if (diario == null) {
            Log.e("Diario Update:", "null");
            return -1; // ou lance uma exceção IllegalArgumentException

        }

        ContentValues values = new ContentValues();
        values.put("id", diario.getId());


        if (diario.getData() != null) {
            values.put("data", diario.getData().toString());
        } else {
            Log.e("Data :","Null");
        }


        values.put("comentario", diario.getComentario());
        values.put("local", diario.getLocal());
        values.put("id_viagem", diario.getId_viagem());
        values.put("favorito", diario.getFavorito());


        try {
            return db.update("diario", values, "id=?", new String[]{String.valueOf(diario.getId())});
        } catch (SQLException e) {
            Log.e("ErroUpdateDiario", e.getMessage());
            e.printStackTrace();
            return -1; // ou lance uma exceção personalizada
        }
    }

    public long updateHospedagem(Hospedagem hospedagem) {
        if (hospedagem == null) {
            Log.e("Hospedagem Update:", "null");
            return -1; // ou lance uma exceção IllegalArgumentException

        }

        ContentValues values = new ContentValues();

        values.put("id", hospedagem.getId());

        if (hospedagem.getData() != null) {
            values.put("data", hospedagem.getData().toString());
        } else {
            Log.e("Data :","Null");
        }

        values.put("descricao", hospedagem.getDescricao());
        values.put("valor", hospedagem.getValor());
        values.put("categoria", hospedagem.getCategoria());
        values.put("metodoPagamento", hospedagem.getMetodoPagamento());
        values.put("local", hospedagem.getLocal());
        values.put("tipoHospedagem", hospedagem.getTipoHospedagem());
        values.put("quantidadeDeDiarias", hospedagem.getQuantidadeDeDiarias());
        values.put("nomeHospedagem", hospedagem.getNomeHospedagem());
        values.put("id_viagem", hospedagem.getId_viagem());

        try {
            return db.update("hospedagem", values, "id=?", new String[]{String.valueOf(hospedagem.getId())});
        } catch (SQLException e) {
            Log.e("ErroUpdateHospedagem", e.getMessage());
            e.printStackTrace();
            return -1; // ou lance uma exceção personalizada
        }
    }

    public long updateManutencao(Manutencao manutencao) {
        if (manutencao == null) {
            Log.e("Manutencao Update:", "null");
            return -1; // ou lance uma exceção IllegalArgumentException

        }

        ContentValues values = new ContentValues();
        values.put("id", manutencao.getId());


        if (manutencao.getData() != null) {
            values.put("data", manutencao.getData().toString());
        } else {
            Log.e("Data :","Null");
        }

        values.put("descricao", manutencao.getDescricao());
        values.put("valor", manutencao.getValor());
        values.put("categoria", manutencao.getCategoria());
        values.put("metodoPagamento", manutencao.getMetodoPagamento());
        values.put("local", manutencao.getLocal());
        values.put("tipoDeManutencao", manutencao.getTipoDeManutencao());
        values.put("id_viagem", manutencao.getId_viagem());

        try {
            return db.update("manutencao", values, "id=?", new String[]{String.valueOf(manutencao.getId())});
        } catch (SQLException e) {
            Log.e("ErroUpdateManutencao", e.getMessage());
            e.printStackTrace();
            return -1; // ou lance uma exceção personalizada
        }
    }

    public long updatePasseio(Passeio passeio) {
        if (passeio == null) {
            Log.e("Passeio Update:", "null");
            return -1; // ou lance uma exceção IllegalArgumentException

        }

        ContentValues values = new ContentValues();
        values.put("id", passeio.getId());


        if (passeio.getData() != null) {
            values.put("data", passeio.getData().toString());
        } else {
            Log.e("Data :","Null");
        }

        values.put("descricao", passeio.getDescricao());
        values.put("valor", passeio.getValor());
        values.put("categoria", passeio.getCategoria());
        values.put("metodoPagamento", passeio.getMetodoPagamento());
        values.put("local", passeio.getLocal());
        values.put("nomePasseio", passeio.getNomePasseio());
        values.put("id_viagem", passeio.getId_viagem());

        try {
            return db.update("passeio", values, "id=?", new String[]{String.valueOf(passeio.getId())});
        } catch (SQLException e) {
            Log.e("ErroUpdatePasseio", e.getMessage());
            e.printStackTrace();
            return -1; // ou lance uma exceção personalizada
        }
    }

    public long updatePedagio(Pedagio pedagio) {
        if (pedagio == null) {
            Log.e("Passeio Update:", "null");
            return -1; // ou lance uma exceção IllegalArgumentException

        }

        ContentValues values = new ContentValues();
        values.put("id", pedagio.getId());


        if (pedagio.getData() != null) {
            values.put("data", pedagio.getData().toString());
        } else {
            Log.e("Data :","Null");
        }

        values.put("descricao", pedagio.getDescricao());
        values.put("valor", pedagio.getValor());
        values.put("categoria", pedagio.getCategoria());
        values.put("metodoPagamento", pedagio.getMetodoPagamento());
        values.put("local", pedagio.getLocal());
        values.put("qualidadeDaVia", pedagio.getQualidadeDaVia());
        values.put("id_viagem", pedagio.getId_viagem());

        try {
            return db.update("pedagio", values, "id=?", new String[]{String.valueOf(pedagio.getId())});
        } catch (SQLException e) {
            Log.e("ErroUpdatePedagio", e.getMessage());
            e.printStackTrace();
            return -1; // ou lance uma exceção personalizada
        }
    }

}
