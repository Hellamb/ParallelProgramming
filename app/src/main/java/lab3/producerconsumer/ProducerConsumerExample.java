package lab3.producerconsumer;

public class ProducerConsumerExample {
    public static void main(String[] args) {
        Drop<Integer> drop = new Drop<>();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer<>(drop))).start();
    }
}
