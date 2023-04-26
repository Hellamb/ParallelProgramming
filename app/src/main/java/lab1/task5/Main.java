package lab1.task5;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Unordered: ");
        unordered();
        System.out.println("Ordered: ");
        ordered();
    }

    private static void unordered() throws InterruptedException {

        Thread verticalThread = new Thread(() -> System.out.print('|'));
        Thread horizontalThread = new Thread(() -> System.out.print('-'));

        for (int i = 0; i < 100; i++) {

            verticalThread.start();
            horizontalThread.start();

            verticalThread.join();
            horizontalThread.join();

            System.out.println("");
        }
    }

    private static void ordered() throws InterruptedException {

        Thread horizontalThread = new Thread(() -> System.out.print('-'));

        Thread verticalThread = new Thread(() -> {
            try {
                horizontalThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print('|');
        });

        for (int i = 0; i < 100; i++) {

            horizontalThread.start();
            verticalThread.start();

            horizontalThread.join();
            verticalThread.join();

            System.out.println("");
        }
    }
}
