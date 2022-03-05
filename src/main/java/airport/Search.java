package airport;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Search {
	public static void main(String[] args) {
		
		// ������ ��������� �������
		int number = 1; // �������� �� ���������
		
		if(args.length > 0) {
			Integer.parseInt(args[0]);
		} else { // ����� ������ ��������� �� ����� ��������
			try {
				PropertiesConfiguration config = new PropertiesConfiguration("resources/application.yml");
				number = config.getInt("column");
			} catch(ConfigurationException e) {
				e.printStackTrace();
			}
		}

		// ������ ������, �������� �������������
		Scanner scanner = new Scanner(System.in);
		System.out.print("������� ������: ");
		String str = scanner.next();
		scanner.close();
		

		
		// ��������� ������
		long time = System.currentTimeMillis();
		
		// ����������
		List<String> list = new ArrayList<>();
		
		try {
			InputStream is = Search.class.getResourceAsStream("/resources/airports.dat");
			
			BufferedReader breader = new BufferedReader(new InputStreamReader(is));
			
			Stream<String> stream = breader.lines();
			
			final int num = number;
			list = stream
						//.parallel() ��� ������ � �������� ������ 100�� �������� ������������ ������������ ������ 
						.filter(line -> line.split(",")[num].startsWith("\"" + str)) // ���������� �� ��������� ��������� � ������ ������ ��������������� �������
						.sorted((line1, line2) -> line1.split(",")[num].compareTo(line2.split(",")[num])) // ���������� � ������������������ �������
						.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// ������� ������������ �������
		time = System.currentTimeMillis() - time; 
		
		// ��������� ��������� ������, ��� O(n). 
		// �������� ���������� �� 3 ������ �� 1, 10 � 100�� �������������. 
		// ������ ����� ������������ � 10 ���, ������ ����������� ����� � ~4.
		
		// ����� � �������
		list.forEach(System.out::println);
		System.out.println("���������� ��������� �����: " + list.size());
		System.out.println("����� ����������� �� �����: " + (time) + " ��");
	}
}
