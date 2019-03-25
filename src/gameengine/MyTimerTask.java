package gameengine;

import gameengine.gamedata.VendorData;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MyTimerTask extends TimerTask {
    private VendorData vendorData;


    public MyTimerTask (VendorData vendorData){
        this.vendorData = vendorData;
    }

    @Override
    public void run() {
        System.out.println("Restock Timer started at:"+new Date());
        completeTask();
        System.out.println("Time to restock:"+new Date());
    }

    private void completeTask() {
        /*try {
            //assuming it takes 20 secs to complete the task
            Thread.sleep(20000);
            GameEngine.vendor.restockItems();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        vendorData.restockItems();
    }

    public static void main(String args[], VendorData vendorData){
        TimerTask timerTask = new MyTimerTask(vendorData);
        //running timer task as daemon thread
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 10*1000);
        System.out.println("TimerTask started");
        //cancel after sometime
        /*try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("TimerTask cancelled");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
