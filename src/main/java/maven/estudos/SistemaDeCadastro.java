package maven.estudos;

import java.util.List;

public class SistemaDeCadastro {

    public static void main(String[] args) {
        CadastroRepository repository = new CadastroRepository();

     
        Cadastro novo = new Cadastro();
        novo.setNome("João");
        novo.setIdade(25);
        repository.save(novo);
        System.out.println("Cadastro salvo: " + novo.getNome());

       
        List<Cadastro> lista = repository.listar();
        System.out.println("\nLista de cadastros:");
        for (Cadastro c : lista) {
            System.out.println(
                "  ID: " +
                    c.getId() +
                    " | Nome: " +
                    c.getNome() +
                    " | Idade: " +
                    c.getIdade()
            );
        }

   
        if (!lista.isEmpty()) {
            Integer primeiroId = lista.get(0).getId();

            Cadastro encontrado = repository.find(primeiroId);
            System.out.println(
                "\nBusca por ID " + primeiroId + ": " + encontrado.getNome()
            );

         
            encontrado.setNome("João Atualizado");
            encontrado.setIdade(30);
            repository.patch(encontrado);
            System.out.println("Cadastro atualizado: " + encontrado.getNome());


            repository.delete(primeiroId);
            System.out.println("Cadastro removido (ID: " + primeiroId + ")");
        }
    }
}
