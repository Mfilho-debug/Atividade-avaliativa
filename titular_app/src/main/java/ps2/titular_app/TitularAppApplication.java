package ps2.titular_app;

import static ps2.titular_app.ES.*;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TitularAppApplication implements CommandLineRunner {

    @Autowired
    private TitularDao titularDao;

    public static void main(String[] args) {
        SpringApplication.run(TitularAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        print("# GERENCIADOR DE TITULARES!");
        boolean sair = false;
        
        String menu = "\n(1) Listar todos os titulares";
        menu += "\n(2) Buscar titular por ID";
        menu += "\n(3) Criar novo titular";
        menu += "\n(4) Alterar dados do titular";
        menu += "\n(5) Apagar titular";
        menu += "\n(0) Sair \n";
        menu += "Escolha uma opção: ";

        while (!sair) {
            String op = input(menu);
            switch (op) {
                case "1":
                    listar();
                    break;
                case "2":
                    buscar();
                    break;
                case "3":
                    criar();
                    break;
                case "4":
                    alterar();
                    break;
                case "5":
                    apagar();
                    break;
                case "0":
                    sair = true;
                    break;
                default:
                    print("Opção inválida!");
            }
        }
        print("\nPrograma finalizado.");
    }
    
    
    private void listar() {
        print("\n# LISTA DE TITULARES");
        Iterable<Titular> titulares = titularDao.findAll();
        
        if (!titulares.iterator().hasNext()) {
            print("Nenhum titular cadastrado.");
            return;
        }
        
        
        for (Titular titular : titulares) {
            print(titular.toString());
        }
    }

    
    private void buscar() {
        print("\n# BUSCAR TITULAR POR ID");
        try {
            long id = Long.parseLong(input("Digite o ID do titular: "));
            Optional<Titular> titularOpt = titularDao.findById(id);
            
            if (titularOpt.isPresent()) {
                print("Titular encontrado:");
                print(titularOpt.get().toString());
            } else {
                print("Titular com o ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException e) {
            print("ERRO: O ID deve ser um número válido.");
        }
    }

    private void criar() {
        print("\n# CRIAR NOVO TITULAR");
        String nome = input("Digite o nome: ");
        String cpf = input("Digite o CPF: ");
        
        Titular novoTitular = new Titular();
        novoTitular.setNome(nome);
        novoTitular.setCpf(cpf);
        
        Titular titularSalvo = titularDao.save(novoTitular);
        print("Titular criado com sucesso!");
        print(titularSalvo.toString());
    }

    private void alterar() {
        print("\n# ALTERAR DADOS DO TITULAR");
        try {
            long id = Long.parseLong(input("Digite o ID do titular para alterar: "));
            Optional<Titular> titularOpt = titularDao.findById(id);

            if (titularOpt.isPresent()) {
                Titular titular = titularOpt.get();
                print("Titular atual: " + titular);
                
                String novoNome = input("Digite o novo nome (deixe em branco para não alterar): ");
                String novoCpf = input("Digite o novo CPF (deixe em branco para não alterar): ");
                
                if (!novoNome.isBlank()) {
                    titular.setNome(novoNome);
                }
                if (!novoCpf.isBlank()) {
                    titular.setCpf(novoCpf);
                }
                
                Titular titularAtualizado = titularDao.save(titular);
                print("Titular atualizado com sucesso!");
                print(titularAtualizado.toString());
            } else {
                print("Titular com o ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException e) {
            print("ERRO: O ID deve ser um número válido.");
        }
    }

    private void apagar() {
        print("\n# APAGAR TITULAR");
        try {
            long id = Long.parseLong(input("Digite o ID do titular para apagar: "));
            
            if (titularDao.findById(id).isPresent()) {
                titularDao.deleteById(id);
                print("Titular com ID " + id + " apagado com sucesso.");
            } else {
                print("Titular com o ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException e) {
            print("ERRO: O ID deve ser um número válido.");
        }
    }
}