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
		
		// чтение параметра запуска
		int number = 1; // значение по умолчанию
		
		if(args.length > 0) {
			Integer.parseInt(args[0]);
		} else { // иначе взятие параметра из файла настроек
			try {
				PropertiesConfiguration config = new PropertiesConfiguration("resources/application.yml");
				number = config.getInt("column");
			} catch(ConfigurationException e) {
				e.printStackTrace();
			}
		}

		// чтение строки, введённой пользователем
		Scanner scanner = new Scanner(System.in);
		System.out.print("Введите строку: ");
		String str = scanner.next();
		scanner.close();
		

		
		// начальный отсчет
		long time = System.currentTimeMillis();
		
		// фильтрация
		List<String> list = new ArrayList<>();
		
		try {
			InputStream is = Search.class.getResourceAsStream("/resources/airports.dat");
			
			BufferedReader breader = new BufferedReader(new InputStreamReader(is));
			
			Stream<String> stream = breader.lines();
			
			final int num = number;
			list = stream
						//.parallel() при файлах с размером больше 100мб выгоднее использовать параллельные потоки 
						.filter(line -> line.split(",")[num].startsWith("\"" + str)) // фильтрация по вхождению подстроки в начале строки соответствующей колонки
						.sorted((line1, line2) -> line1.split(",")[num].compareTo(line2.split(",")[num])) // сортировка в лексикографическом порядке
						.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// подсчёт затраченного времени
		time = System.currentTimeMillis() - time; 
		
		// Сложность алгоритма меньше, чем O(n). 
		// Алгоритм запускался на 3 файлах по 1, 10 и 100мб соответсвенно. 
		// Размер файла увеличивался в 10 раз, однако затраченное время в ~4.
		
		// вывод в консоль
		list.forEach(System.out::println);
		System.out.println("Количество найденных строк: " + list.size());
		System.out.println("Время затраченное на поиск: " + (time) + " мс");
	}
}
