package database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {

    /*
     * Atributos da classe connection
     */

    private static final String DB_NAME = "symbian";
    private static final int DB_VERSION = 1;
    private static SQLHelper INSTANCE;

    /*
     * Método de verificação da conexão
     */

    public SQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /*
     * Método construtor - Recebe os valores iniciais de abertura da conexão
     */

    public static SQLHelper getINSTANCE(Context context) {

        if (INSTANCE == null) {
            INSTANCE = new SQLHelper(context);
        }

        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE tblUsuario" +
                "(idUsuario INTEGER PRIMARY KEY," +
                "nome TEXT," +
                "sobrenome TEXT," +
                "login TEXT," +
                "senha TEXT)");

        Log.d("SQLite - ", "banco de dados criado! - " + DB_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("CREATE TABLE tblEndereco" +
                "(idEndereco INTEGER PRIMARY KEY," +
                "idUsuario INTEGER," +
                "cep TEXT," +
                "numero INTEGER," +
                "complemento TEXT," +
                "FOREIGN KEY (idUsuario) REFERENCES tblUsuario (idUsuario))");

        Log.d("SQLite - ", "banco de dados criado! - " + DB_VERSION);

    }

    /*
     * Inserção de usuário
     */

    public int addUser(String nome, String sobrenome, String login, String senha) {

        /*
         * Configura o SQLITE para a escrita:
         */

        SQLiteDatabase SQLiteDatabase = getWritableDatabase();

        try {
            SQLiteDatabase.beginTransaction();
            ContentValues values = new ContentValues();

            values.put("nome", nome);
            values.put("sobrenome", sobrenome);
            values.put("login", login);
            values.put("senha", senha);

            int idUsuario = (int) SQLiteDatabase.insertOrThrow("tblUsuario", null, values);

            SQLiteDatabase.setTransactionSuccessful();

            return idUsuario;

        } catch (Exception e) {

            Log.d("SQLite - ", e.getMessage());

            return 0;

        } finally {

            if (SQLiteDatabase.isOpen()) {

                SQLiteDatabase.endTransaction();

            }
        }

    }


    @SuppressLint("Range")
    public int logarUser(String login, String senha) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tblUsuario WHERE login = ? AND senha = ?", new String[]{login, senha});

        try {

            if (cursor.moveToFirst()) {

                return cursor.getInt(cursor.getColumnIndex("idUsuario"));

            }

            return 0;

        } catch (Exception e) {

            Log.d("SQLITE-", e.getMessage());

        } finally {

            if (cursor != null && !cursor.isClosed()) {

                cursor.close();

            }

        }

        return 0;

        /*
         *  Fim do método de login
         */

    }


    /*
     * Inserção de endereços
     */

    public boolean addAddress(int idUsuario, String cep, int numero, String complemento) {


        /*
         * Configura o SQLite para a escrita:
         */

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        try {

            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();

            values.put("idUsuario", idUsuario);
            values.put("cep", cep);
            values.put("numero", numero);
            values.put("complemento", complemento);

            sqLiteDatabase.insertOrThrow("tblEndereco", null, values);
            sqLiteDatabase.setTransactionSuccessful();

            return true;

        } catch (Exception e) {

            Log.d("SQLite - ", e.getMessage());
            return false;

        } finally {

            if (sqLiteDatabase.isOpen()) {

                sqLiteDatabase.endTransaction();

            }
        }
    }

}
