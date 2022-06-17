package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		// Entrar com caminho da pasta
		System.out.print("Enter full file path: ");
		String path = sc.nextLine();
		// C:\Users\thiago.freitas\Desktop\Formação Dev Moderno\Módulo 4 - Java\Projetos\Apoio\Ex18_D\in.csv

		// Abrir o arquivo
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Sale> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}
			
			// Cinco primeiras vendas de 2016 de maior preço médio
			
			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			List<Sale> newList = list.stream()
					.filter(s -> s.getYear() == 2016)
                    .sorted((s1, s2) -> s2.averagePrice().compareTo(s1.averagePrice()))
                    .limit(5)
                    .collect(Collectors.toList());
			
			newList.forEach(System.out::println);
			
			// Valor total vendido pelo vendedor Logan nos meses 1 e 7
			
			// Versão 1 ------------------------------------------------
			/*double sum1 = list.stream()
                    .filter(a -> a.getMonth() == 1 )
                    .filter(b -> b.getSeller().equals("Logan"))
                    .map(c -> c.getTotal())
                    .reduce(0.0, (x, y) -> x + y);
			
			double sum2 = list.stream()
                    .filter(a -> a.getMonth() == 7 )
                    .filter(b -> b.getSeller().equals("Logan"))
                    .map(c -> c.getTotal())
                    .reduce(0.0, (x, y) -> x + y);
			
			double sum = sum1 + sum2;*/
			// ---------------------------------------------------------
			// Versão 2 - Compacta -------------------------------------
			
			double sum = list.stream()
            .filter(s -> s.getMonth() == 1 || s.getMonth() == 7)
            .filter(s -> s.getSeller().equals("Logan"))
            .map(s -> s.getTotal())
            .reduce(0.0, (x, y) -> x + y);
			
			System.out.println();
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + sum);
			
						
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		sc.close();
	}
}
