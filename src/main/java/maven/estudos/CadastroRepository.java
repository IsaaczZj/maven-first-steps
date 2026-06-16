package maven.estudos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CadastroRepository {

    private Connection conexao;

    public CadastroRepository() {
        conexao = FabricaConexao.getConexao();
    }

    public void save(Cadastro dados) {
        try {
            String SQL =
                "INSERT INTO public.cadastro(nome, idade) VALUES (?,?)";
            PreparedStatement pst = conexao.prepareStatement(SQL);
            pst.setString(1, dados.getNome());
            pst.setInt(2, dados.getIdade());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void patch(Cadastro dados) {
        try {
            String SQL =
                "UPDATE public.cadastro SET nome=?, idade=? WHERE id=?";
            PreparedStatement pst = conexao.prepareStatement(SQL);
            pst.setString(1, dados.getNome());
            pst.setInt(2, dados.getIdade());
            pst.setInt(3, dados.getId());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer id) {
        try {
            String SQL = "DELETE FROM public.cadastro WHERE id=?";
            PreparedStatement pst = conexao.prepareStatement(SQL);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Cadastro> listar() {
        List<Cadastro> lista = new ArrayList<>();
        try {
            String SQL = "SELECT id, nome, idade FROM public.cadastro";
            PreparedStatement pst = conexao.prepareStatement(SQL);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Cadastro c = new Cadastro();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setIdade(rs.getInt("idade"));
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Cadastro find(Integer id) {
        try {
            String SQL =
                "SELECT id, nome, idade FROM public.cadastro WHERE id=?";
            PreparedStatement pst = conexao.prepareStatement(SQL);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Cadastro c = new Cadastro();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setIdade(rs.getInt("idade"));
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
