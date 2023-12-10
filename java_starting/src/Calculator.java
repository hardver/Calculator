import java.io.IOException;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        // запрашиваем данные
        System.out.println("Введите выражение для вычисления:");
        Scanner inputData = new Scanner(System.in);
        String term = inputData.nextLine();
        // вызываем метод calc
        String result = calc(term);
        // выводим полученное значение
        System.out.println("Ответ: " + result);
    }
    private static String calc(String input) {
        // парсим полученные данные
        // разбиваем строку по знакам арифметических действий
        String[] words = input.split("[+/*-]");
        // проверяем длину массива, если не равно 2м, то выбрасываем исключение
        if (words.length != 2) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Было введено неправильное выражение!");
                System.exit(1);
            }
        }
        // получаем использованный разделитель
        char znak = input.charAt(words[0].length());
        //работаем с аргументами:
        String arg1 = words[0].strip();
        String arg2 = words[1].strip();
        // проверяем полученные данные на соответствие условиям задачи
        int a = tryParseInt(arg1);
        int b = tryParseInt(arg2);
        // если оба числа введены римскими цифрами
        boolean rum = false;
        if ( a == 0 && b == 0 ) {
            rum = true;
        }
        // если введены числа разных систем
        if ( (a == 0 && b != 0) | (a != 0 && b == 0) ) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Были введены римские и арабские числа одновременно!");
                System.exit(1);
            }
        }
        // переводим римские числа в арабские
        a = checkRumNumber(a, arg1);
        b = checkRumNumber(b, arg2);
        // проверяем на соответствие чисел заданному диапазону
        if ((11 < a) | (a < 1)) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Одно из введенных чисел выходит за заданный диапазон - 1...10 !");
                System.exit(1);
            }
        }
        if ((11 < b) | (b < 1)) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Одно из введенных чисел выходит за заданный диапазон - 1...10 !");
                System.exit(1);
            }
        }
        // вычисляем результат выражения
        int x = -100;
        switch (znak){
            case '+' :
                x = a + b;
                break;
            case '-' :
                x = a - b;
                break;
            case '*' :
                x = a * b;
                break;
            case '/' :
                x = a / b;
                break;
            default:
                System.out.println("Что-то пошло не так...");
        }
        // приводим результат выражения к требуемуму формату
        // если требуется вывести результат в римском формате
        if ( rum && ( x < 1 )) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Требуется вывод результата в римском формате, но результат меньше 1!");
                System.exit(1);
            }
        }
        String result;
        if ( rum ) {
            RumNumeric res = RumNumeric.getRumByArabian(x);
            result = String.valueOf(res);
        } else {
            result = String.valueOf(x);
        }
        return result;
    }

    private static int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    private static int checkRumNumber(int number, String rumNumber) {
        if (number == 0) {
            RumNumeric num = null;
            try {
                num = RumNumeric.valueOf(rumNumber);
            } catch (IllegalArgumentException e){
                System.out.println("Было введено неверное римское число!");
                System.exit(1);
            }
            number = num.getArabian();
        }
        return number;
    }
}
