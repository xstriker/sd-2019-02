package mainPackage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws RemoteException {
		Scanner s = new Scanner(System.in);
		Integer option;
		ClientePessoa cli = null;
		ClientePessoaInterface cliInterface = null;
		try {
			Registry referenciaServicoNomes = LocateRegistry.getRegistry(9999);
			cli = new ClientePessoa(referenciaServicoNomes);
			cliInterface = new ClientePessoa(referenciaServicoNomes);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		while (true) {
			System.out.println("O que deseja? (1-inserir curriculo / 2-consultar vaga / 3-registrar interesse)");
			option = s.nextInt();
			switch (option) {
			case 1:
				Curriculo curriculo = new Curriculo();
				System.out.println("Nome:");
				curriculo.setNome(s.next());
				System.out.println("Contato:");
				curriculo.setContato(s.next());
				System.out.println("Area:");
				curriculo.setArea(s.next());
				System.out.println("Tempo:");
				curriculo.setTempo(s.nextDouble());
				System.out.println("Salario:");
				curriculo.setSalario(s.nextDouble());
				cli.getServer().cadastrarCurriculo(curriculo);
				break;

			case 2:
				System.out.println("Digite a area: ");
				String area = s.next();
				System.out.println("Digite o salario: ");
				List<Vaga> lst =  cli.getServer().buscarVagas(area, s.nextDouble());
				for (Vaga vaga : lst) {
					System.out.println("Vaga de: " + vaga.getNomeEmpresa());
				}
				break;

			case 3:
				System.out.println("Selecione a área: ");
				cli.getServer().cadastrarInteresseVaga(s.next(), cliInterface);
				break;

			default:
				break;
			}
		}
	}

}
