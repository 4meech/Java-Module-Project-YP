import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



class SplitDatBill{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Item> items = new ArrayList<>();

        int numberOfGuests;
        while (true) {
            System.out.print("Введите число гостей, на которых нужно разделить сумму счёта: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Ошибка ввода: введите целое положительное число:");
                scanner.next();
            }
            numberOfGuests = scanner.nextInt();
            scanner.nextLine();

            if (numberOfGuests == 1) {
                System.out.println("Нет смысла делить сумму на одного — введите число больше 1:");
            } else if (numberOfGuests < 1) {
                System.out.println("Число гостей не может быть отрицательным или равным нулю. Введите положительное число больше 1: ");
            } else {
                break;
            }
        }

        boolean addMoreItems = true;
        while (addMoreItems) {
            System.out.print("Введите наименование позиции меню (блюда или напитка). Для завершения работы, введите 'Завершить' ");
            String itemName = scanner.nextLine();
            if (itemName.equalsIgnoreCase("Завершить")) {
                break;
            }

            System.out.print("Введите стоимость в формате руб.коп (например, 40,65): ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Стоимость введена неверно. Введите стоимость в формате 'руб,коп' (например 40,65): ");
                scanner.next();
            }
            double itemCost = scanner.nextDouble();
            scanner.nextLine();

            Item item = new Item(itemName, itemCost);
            items.add(item);

            System.out.println("Позиция '" + itemName + "' успешно добавлена.");

            boolean validDecision = false;
            while (!validDecision) {
                System.out.print("Хотите добавить ещё одну позицию? (Да/Нет): ");
                String decision = scanner.nextLine().toLowerCase();
                if (decision.equals("нет")) {
                    addMoreItems = false;
                    validDecision = true;
                } else if (decision.equals("да")) {
                    validDecision = true;
                } else {
                    System.out.println("Некорректный ввод. Пожалуйста, введите 'Да' или 'Нет'.");
                }
            }
        }


        System.out.println("Вы добавили: ");
        for (Item item : items) {
            System.out.println(item.name + " - " + item.cost + " руб.");
        }

        double totalCost = 0.0;
        for (Item item : items) {
            totalCost += item.cost;
        }

        System.out.println("Общая стоимость позиций меню: " + totalCost + " руб. Делим счёт на " + numberOfGuests + " чел.");
        System.out.println("Каждый должен заплатить: " + formatAmount(totalCost / numberOfGuests));

        scanner.close();
    }

    static String formatAmount(double amount) {

        double kopPart = amount - (int) amount;
        int rubPart = (int) amount;

        String rubles;
        if (rubPart % 10 == 1 && rubPart % 100 != 11) {
            rubles = " рубль";
        } else if (rubPart % 10 >= 2 && rubPart % 10 <= 4 && !(rubPart % 100 >= 12 && rubPart % 100 <= 14)) {
            rubles = " рубля";
        } else {
            rubles = " рублей";
        }

        String result = rubPart + rubles;
        int kopecksAmount = (int) (kopPart * 100);
        result += " " + kopecksAmount + " коп.";
        return result;
    }

    static class Item {
        String name;
        double cost;

        public Item(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }
    }
}