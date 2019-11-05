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
		ClienteEmpresa cli = null;
		try {
			// Busca pelo servico de nomes 
			Registry referenciaServicoNomes = LocateRegistry.getRegistry(9999);
			cli = new ClienteEmpresa(referenciaServicoNomes);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// Interface grafica
		while (true) {
			System.out.println("O que deseja? (1-inserir vaga / 2-consultar curriculo / 3-registrar interesse)");
			option = s.nextInt();
			switch (option) {
			case 1:
				Vaga vaga = new Vaga();
				System.out.println("Empresa:");
				vaga.setNomeEmpresa(s.next());
				System.out.println("Area:");
				vaga.setArea(s.next());
				System.out.println("Tempo:");
				vaga.setTempo(s.nextDouble());
				System.out.println("Salario:");
				vaga.setSalario(s.nextDouble());
				cli.getServer().cadastrarVaga(vaga);
				break;

			case 2:
				System.out.println("Digite a area:");
				List<Curriculo> lst =  cli.getServer().buscarCurriculos(s.next());
				for (Curriculo curriculo : lst) {
					System.out.println("Curriculo de:" + curriculo.getNome());
				}
				break;

			case 3:
				System.out.println("Selecione a área: ");
				cli.getServer().cadastrarInteresseCurriculo(s.next(), cli);
				break;

			default:
				break;
			}
		}
	}

}
