package br.com.agoracomecouaviagem.controleioverland.DAO;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import br.com.agoracomecouaviagem.controleioverland.Utils.SQLConnection;

public class DeleteDAO {

    private SQLConnection conn;
    private SQLiteDatabase db;

    public DeleteDAO(Context context) {
        conn = new SQLConnection(context);
        db = conn.getWritableDatabase();
    }

    public int deleteViagem(Context context, int id) {
        try {
            int rowsDeleted = db.delete("viagem", "id=?", new String[]{String.valueOf(id)});
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Viagem deletada com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                // Nenhuma linha foi excluída
                Toast.makeText(null, "Nenhuma viagem encontrada com o ID: " + id, Toast.LENGTH_LONG).show();
            }
            return rowsDeleted;
        } catch (SQLException e) {
            Toast.makeText(null, "Erro ao deletar viagem.", Toast.LENGTH_LONG).show();
            Log.e("Delete Error:", e.getMessage());
            return -1;
        }
    }

    public int deleteAbastecimento(Context context,int id) {
        try {
            int rowsDeleted = db.delete("abastecimento", "id=?", new String[]{String.valueOf(id)});
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Abastecimento deletado com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Nenhum abastecimento encontrado com o ID: " + id, Toast.LENGTH_LONG).show();
            }
            return rowsDeleted;
        } catch (SQLException e) {
            Toast.makeText(context, "Erro ao deletar abastecimento.", Toast.LENGTH_LONG).show();
            Log.e("Delete Error:", e.getMessage());
            return -1;
        }
    }

    public int deleteAlimentacao(Context context,int id) {
        try {
            int rowsDeleted = db.delete("alimentacao", "id=?", new String[]{String.valueOf(id)});
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Alimentação deletada com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Nenhuma alimentação encontrada com o ID: " + id, Toast.LENGTH_LONG).show();
            }
            return rowsDeleted;
        } catch (SQLException e) {
            Toast.makeText(context, "Erro ao deletar alimentação.", Toast.LENGTH_LONG).show();
            Log.e("Delete Error:", e.getMessage());
            return -1;
        }
    }

    public int deleteDespesa(Context context,int id) {
        try {
            int rowsDeleted = db.delete("despesa", "id=?", new String[]{String.valueOf(id)});
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Despesa deletada com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Nenhuma despesa encontrada com o ID: " + id, Toast.LENGTH_LONG).show();
            }
            return rowsDeleted;
        } catch (SQLException e) {
            Toast.makeText(context, "Erro ao deletar despesa.", Toast.LENGTH_LONG).show();
            Log.e("Delete Error:", e.getMessage());
            return -1;
        }
    }

    public int deleteEstacionamento(Context context,int id) {
        try {
            int rowsDeleted = db.delete("estacionamento", "id=?", new String[]{String.valueOf(id)});
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Estacionamento deletado com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Nenhum estacionamento encontrado com o ID: " + id, Toast.LENGTH_LONG).show();
            }
            return rowsDeleted;
        } catch (SQLException e) {
            Toast.makeText(context, "Erro ao deletar estacionamento.", Toast.LENGTH_LONG).show();
            Log.e("Delete Error:", e.getMessage());
            return -1;
        }
    }

    public int deleteDiario(Context context,int id) {
        try {
            int rowsDeleted = db.delete("diario", "id=?", new String[]{String.valueOf(id)});
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Diário deletado com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Nenhum diário encontrado com o ID: " + id, Toast.LENGTH_LONG).show();
            }
            return rowsDeleted;
        } catch (SQLException e) {
            Toast.makeText(context, "Erro ao deletar diário.", Toast.LENGTH_LONG).show();
            Log.e("Delete Error:", e.getMessage());
            return -1;
        }
    }

    public int deleteHospedagem(Context context,int id) {
        try {
            int rowsDeleted = db.delete("hospedagem", "id=?", new String[]{String.valueOf(id)});
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Hospedagem deletada com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Nenhuma hospedagem encontrada com o ID: " + id, Toast.LENGTH_LONG).show();
            }
            return rowsDeleted;
        } catch (SQLException e) {
            Toast.makeText(context, "Erro ao deletar hospedagem.", Toast.LENGTH_LONG).show();
            Log.e("Delete Error:", e.getMessage());
            return -1;
        }
    }

    public int deleteManutencao(Context context,int id) {
        try {
            int rowsDeleted = db.delete("manutencao", "id=?", new String[]{String.valueOf(id)});
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Manutenção deletada com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Nenhuma manutenção encontrada com o ID: " + id, Toast.LENGTH_LONG).show();
            }
            return rowsDeleted;
        } catch (SQLException e) {
            Toast.makeText(context, "Erro ao deletar manutenção.", Toast.LENGTH_LONG).show();
            Log.e("Delete Error:", e.getMessage());
            return -1;
        }
    }

    public int deletePasseio(Context context,int id) {
        try {
            int rowsDeleted = db.delete("passeio", "id=?", new String[]{String.valueOf(id)});
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Passeio deletado com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Nenhum passeio encontrado com o ID: " + id, Toast.LENGTH_LONG).show();
            }
            return rowsDeleted;
        } catch (SQLException e) {
            Toast.makeText(context, "Erro ao deletar passeio.", Toast.LENGTH_LONG).show();
            Log.e("Delete Error:", e.getMessage());
            return -1;
        }
    }

    public int deletePedagio(Context context,int id) {
        try {
            int rowsDeleted = db.delete("pedagio", "id=?", new String[]{String.valueOf(id)});
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Pedágio deletado com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Nenhum pedágio encontrado com o ID: " + id, Toast.LENGTH_LONG).show();
            }
            return rowsDeleted;
        } catch (SQLException e) {
            Toast.makeText(context, "Erro ao deletar pedágio.", Toast.LENGTH_LONG).show();
            Log.e("Delete Error:", e.getMessage());
            return -1;
        }
    }

}

