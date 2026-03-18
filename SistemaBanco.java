package desafio.testeReal;

import java.util.Scanner;

public class SistemaBanco {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		menuBanco(sc);
		
		sc.close();
		
	}
	
	static int criarConta(Scanner sc, String[] usuarios, String[] senhas, double[] saldo, int totalUsuarios) {
		
		String novoUsuario;
		String novaSenha;
		
		while(true) {			
			System.out.print("Digite seu nome de usuário: ");
			novoUsuario = sc.nextLine();
			
			System.out.print("Digite sua senha: ");
			novaSenha = sc.nextLine();
			
			// Verificar se os nomes digitados não estão vazios
			if(!novoUsuario.equals("") && !novaSenha.equals("")) {
				break;
			} else {
				System.out.println("Não pode conter nome ou senha vazios, tente novamente!");
			}
		}
		
		// Recebe o valor digitado pelo usuário e atribui a posição atual de usuarios, senha e saldo
		usuarios[totalUsuarios] = novoUsuario;
		senhas[totalUsuarios] = novaSenha;
		saldo[totalUsuarios] = 0;
		
		System.out.println("Sua conta foi criada com sucesso!");
		
		return totalUsuarios + 1;
	}
	
	static int fazerLogin(Scanner sc, String[] usuarios, String[] senhas, int totalUsuarios) {
		
		System.out.println("==== REALIZAR LOGIN ====");
		while(true) {
		System.out.print("Digite seu nome de usuário: ");
		String tentativaLogin = sc.nextLine().toLowerCase();
		
		System.out.print("Digite sua senha: ");
		String tentativaSenha = sc.nextLine();
				
				// looping para verificar se o login existe
				for(int i = 0; i < totalUsuarios; i++) {
					if(tentativaLogin.equals(usuarios[i].toLowerCase()) && tentativaSenha.equals(senhas[i])) {
						
						// Se existir, retorna o número do índice
						return i;
				}
			}
			
			System.out.println("Usuário ou senha incorreto, tente novamente!");
		}
	}
	
	static void verificarSaldo(double[] saldo, int idUser) {
		System.out.printf("Seu saldo atual é: %.2f\n\n", saldo[idUser]);
	}
	
	static void depositar(Scanner sc, double[] saldo, int idUser, String[][] extratos, int[] totalOperacoes) {
		System.out.print("Digite quanto deseja depositar: ");
		double depositar = sc.nextDouble();
		sc.nextLine();
		
		// Verifica se o valor digitado é igual ou menor a 0
		while(depositar <= 0) {
			System.out.println("Valor inválido!");
			depositar = sc.nextDouble();
			sc.nextLine();
		}
		
		// Caso seja válido, vai acrescentar o valor no saldo
		saldo[idUser] += depositar;
		System.out.println("Depósito realizado com sucesso!");
		
		// Adiciona o histórico do depósito no extrato
		extratos[idUser][totalOperacoes[idUser]] = "Depósito: +" + depositar;
		totalOperacoes[idUser]++;
	}
	
	static void sacar(Scanner sc, double[] saldo, int idUser, String[][] extratos, int[] totalOperacoes) {
		System.out.print("Digite quanto deseja sacar: ");
		double saque = sc.nextDouble();
		sc.nextLine();
		
		// Verifica se o valor de saque não é maior que o saldo atual
		while(saque > saldo[idUser] || saque <= 0) {
			System.out.println("Saldo insuficiente!");
			System.out.printf("Seu saldo atual é: %.2f\n", saldo[idUser]);
			System.out.print("Digite quanto deseja sacar: ");
			saque = sc.nextDouble();
			sc.nextLine();
		}
		
		// Se válido, ele descrementa no valor atual do saldo
		saldo[idUser] -= saque;
		System.out.println("Saque realizado com sucesso!");
		
		// Adiciona o histórico de saque no extrato
		extratos[idUser][totalOperacoes[idUser]] = "Saque: -" + saque;
		totalOperacoes[idUser]++;
	}
	
	static void extrato(int idUser, String[][] extratos, int[] totalOperacoes) {
		System.out.println("==== EXTRATO ====");
		
		// Imprime todo o histórico de depósitos e saques realizados pelo usuário
		for(int i = 0; i < totalOperacoes[idUser]; i++) {
			System.out.println(extratos[idUser][i]);
		}
	}
	
	static void menuBanco(Scanner sc) {
		// Variaveis para conter usuários, senhas, saldos e a quantidade de usuários atual
		String[] usuarios = new String[100];
		String[] senhas = new String[100];
		int totalUsuarios = 0;
		double[] saldo = new double[100];
		
		// Arrays para armazenar o extrato do usuário
		String[][] extratos = new String[100][100];
		int[] totalOperacoes = new int[100];
		int opcaoMenu = 0;
		
		System.out.println("==== BANCO JAVA ====");
		System.out.println("Bem-vindo ao banco Java, digite abaixo o que deseja fazer");
		while(opcaoMenu != 3) {
			System.out.println("\n1 - Criar conta");
			System.out.println("2 - Login");
			System.out.println("3 - Sair");
			System.out.print("Escolha: ");
			opcaoMenu = sc.nextInt();
			sc.nextLine();
			switch (opcaoMenu) {
			case 1:
				totalUsuarios = criarConta(sc, usuarios, senhas, saldo, totalUsuarios);
				break;
			case 2:
				int opcaoBanco = 0;
				if(totalUsuarios == 0) {
					System.out.println("Nenhuma conta criada ainda!");
					break;
				}
				int idUser = fazerLogin(sc, usuarios, senhas, totalUsuarios);
				System.out.println("Login efetuado com sucesso!");
				System.out.println("Seja bem-vindo " + usuarios[idUser]);
				System.out.println("==== MENU DA CONTA ====");

				while(opcaoBanco != 5) {
					System.out.println("O que deseja fazer?");
					System.out.println("1 - Ver saldo");
					System.out.println("2 - Depositar");
					System.out.println("3 - Sacar");
					System.out.println("4 - Extrato");
					System.out.println("5 - Logout");
					System.out.print("Escolha: ");
					opcaoBanco = sc.nextInt();
					sc.nextLine();
					
					switch (opcaoBanco) {
					
					case 1: 
						verificarSaldo(saldo, idUser);
						break;
					case 2:
						depositar(sc, saldo, idUser, extratos, totalOperacoes);
						break;
					case 3:
						sacar(sc, saldo, idUser, extratos, totalOperacoes);
						break;
					case 4:
						extrato(idUser, extratos, totalOperacoes);
						break;
					default: 
						System.out.println("Opção inválida, tente novamente!\n");	
					}
				}
				break;
			default: 
				System.out.println("Opção inválida, tente novamente!\n");
			}
		}
		System.out.println("Obrigado e volte sempre!");
	}

}
