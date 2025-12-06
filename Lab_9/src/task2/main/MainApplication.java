package task2.main;


public class MainApplication {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Запуск системи кільцевих буферів\n");

        ThreadSafeRingBuffer<String> firstBuffer = new ThreadSafeRingBuffer<>(10);
        ThreadSafeRingBuffer<String> secondBuffer = new ThreadSafeRingBuffer<>(10);

        System.out.println("Створено перший буфер (ємність: 10)");
        System.out.println("Створено другий буфер (ємність: 10)");

        System.out.println("\nЗапуск 5 потоків-генераторів:");
        for (int i = 1; i <= 5; i++) {
            new StringGenerator(firstBuffer, i).start();
            System.out.println("Генератор " + i + " запущено");
        }

        System.out.println("\nЗапуск 2 потоків-перекладачів:");
        for (int i = 1; i <= 2; i++) {
            new MessageTransfer(firstBuffer, secondBuffer, i).start();
            System.out.println("Перекладач " + i + " запущено");
        }
        Thread.sleep(100);

        System.out.println("\nОсновний потік читає повідомлення\n");

        for (int i = 1; i <= 100; i++) {
            String message = secondBuffer.take();
            System.out.printf("[%3d] %s%n", i, message);

            if (i % 20 == 0) {
                System.out.println("Прогрес: " + i + "/100");
            }
        }
        System.out.println("\nРобота завершена. Всі потоки-демони автоматично зупиняються.");
    }
}