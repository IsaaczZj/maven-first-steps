package maven.estudos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class FabricaConexao {

    private static Connection conexao;

    public static void conectar() {
        try {
            if (conexao == null) {
                String url = "jdbc:postgresql://localhost:5432/maven_postgres";
                Properties props = new Properties();
                props.setProperty("user", "maven_postgres_user");
                props.setProperty("password", "maven_postgres_pass");
                props.setProperty("ssl", "false");
                conexao = DriverManager.getConnection(url, props);
                System.out.println("Conexao realizada com sucesso");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConexao() {
        if (conexao == null) {
            conectar();
        }
        return conexao;
    }
}
