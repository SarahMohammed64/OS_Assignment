package network;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class SemaphoreWrapper {
    private int permits;

    public SemaphoreWrapper(int permits) {
        this.permits = permits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permits == 0) {
            wait();
        }
        permits--;
    }

    public synchronized void release() {
        permits++;
        notify();
    }
}

class Router {
    private final int maxConnections;
    private SemaphoreWrapper semaphore;
    private Queue<Device> waitingQueue;

    public Router(int maxConnections) {
        this.maxConnections = maxConnections;
        this.semaphore = new SemaphoreWrapper(maxConnections);
        this.waitingQueue = new LinkedList<>();
    }

    public void occupyConnection(Device device) throws InterruptedException {
        synchronized (semaphore) {
            semaphore.acquire();
        }
        System.out.println("- " + device.getName() + " (" + device.getType() + ") arrived");
        System.out.println("- Connection " + device.getName() + " Occupied");
    }

    public void releaseConnection(Device device) {
        synchronized (semaphore) {
            semaphore.release();
        }
        if (!waitingQueue.isEmpty()) {
            Device nextDevice = waitingQueue.poll();
            synchronized (nextDevice) {
                nextDevice.notify();
            }
            System.out.println("- Connection " + device.getName() + " Logged out");
            System.out.println("- " + nextDevice.getName() + " (" + nextDevice.getType() + ") connected");
        } else {
            System.out.println("- Connection " + device.getName() + " Logged out");
        }
    }

    public synchronized void addToWaitingQueue(Device device) {
        waitingQueue.offer(device);
        System.out.println("- " + device.getName() + " (" + device.getType() + ") is waiting");
        synchronized (device) {
            try {
                device.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Device implements Runnable {
    private String name;
    private String type;
    private Router router;

    public Device(String name, String type, Router router) {
        this.name = name;
        this.type = type;
        this.router = router;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void run() {
        try {
            router.occupyConnection(this);
            Thread.sleep(1000); // Simulate online activity
            router.releaseConnection(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Network {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("What is the number of WI-FI Connections?");
        Scanner scanner = new Scanner(System.in);
        int maxRouterConnections = scanner.nextInt();

        System.out.println("What is the number of devices Clients want to connect?");
        int totalDevices = scanner.nextInt();

        String[] deviceNames = new String[totalDevices];
        String[] deviceTypes = new String[totalDevices];

        Scanner deviceScanner = new Scanner(System.in);
        for (int i = 0; i < totalDevices; i++) {
            String deviceLine = deviceScanner.nextLine();
            String[] deviceParts = deviceLine.split(" ");
            deviceNames[i] = deviceParts[0];
            deviceTypes[i] = deviceParts[1];
        }

        Router router = new Router(maxRouterConnections);

        for (int i = 0; i < totalDevices; i++) {
            Device device = new Device(deviceNames[i], deviceTypes[i], router);
            Thread thread = new Thread(device);
            thread.start();
        }

        // Creating a File object that represents the disk file
        File file = new File("C:\\Users\\Hala\\Desktop\\20201087_20200618_20200517\\test.txt");

        // Redirecting console output to the file
        PrintStream fileStream = new PrintStream(file);
        System.setOut(fileStream);
    }
}