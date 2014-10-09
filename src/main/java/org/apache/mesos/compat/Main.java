package org.apache.mesos.compat;

import org.apache.mesos.*;
import org.apache.mesos.Protos.*;

public class Main {

  public static void main(String[] args) { new Main(args[0]); }

  Main(String mesosMaster) {
    System.out.println("mesosMaster: [" + mesosMaster + "]");

    Scheduler scheduler = new Scheduler();

    FrameworkInfo frameworkInfo = FrameworkInfo.newBuilder()
      .setName("conpatibility-test")
      .setFailoverTimeout(60000) // one minute in milliseconds
      .setCheckpoint(false)
      .setUser("") // whatever Mesos thinks is best
      .build();

    SchedulerDriver driver =
      new MesosSchedulerDriver(scheduler, frameworkInfo, mesosMaster);

    DriverThread driverThread = new DriverThread(driver);
    driverThread.start();
  }

  private class DriverThread extends Thread {
    private SchedulerDriver driver;
    public DriverThread(SchedulerDriver driver) { this.driver = driver; }
    public void run() { driver.run(); }
  }

}
