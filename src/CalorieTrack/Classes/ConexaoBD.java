package CalorieTrack.Classes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/CalorieTrack2";
    private static final String USUARIO = "root";
    private static final String SENHA = "Vinii(9)";

    public static Connection conectar() {
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("✅ Conectado ao MySQL com sucesso!");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao conectar ao MySQL:");
            e.printStackTrace();
        }
        return conexao;
    }
    public static void fecharConexao(Connection conexao) {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("🔒 Conexão encerrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        conectar(); // apenas para testar
        fecharConexao( conectar());
        
    }
}
