package org.apache.mesos.compat;

import org.apache.mesos.*;
import org.apache.mesos.Protos.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class Scheduler implements org.apache.mesos.Scheduler {

  public void registered(SchedulerDriver driver,
                  FrameworkID frameworkId,
                  MasterInfo masterInfo) {
    println("Registered with the Mesos master:\n" + masterInfo);
    println("Assigned framework ID:\n" + frameworkId);
  }

  public void reregistered(SchedulerDriver driver, MasterInfo masterInfo) {}

  public void resourceOffers(SchedulerDriver driver, List<Offer> offers) {
    println("Received resource offers:");

    for (Offer offer : offers) {
      println(offer.toString());
      println("Building task list...");

      List<OfferID> offerIds = Arrays.asList(offer.getId());
      List<TaskInfo> tasks = Arrays.asList(taskInfo(offer));

      println("Calling SchedulerDriver.launchTasks...");
      driver.launchTasks(offerIds, tasks);
    }
  }

  public void offerRescinded(SchedulerDriver driver, OfferID offerId) {}

  public void statusUpdate(SchedulerDriver driver, TaskStatus status) {
    println("Received a status update:");
    println(status.toString());
  }

  public void frameworkMessage(SchedulerDriver driver,
                        ExecutorID executorId,
                        SlaveID slaveId,
                        byte[] data) {}

  public void disconnected(SchedulerDriver driver) {}

  public void slaveLost(SchedulerDriver driver, SlaveID slaveId) {}

  public void executorLost(SchedulerDriver driver,
                    ExecutorID executorId,
                    SlaveID slaveId,
                    int status) {}

  public void error(SchedulerDriver driver, String message) {}

  // auxilliary stuff

  void println(String s) { System.out.println(s); }

  Iterator<TaskID> taskIds = new Iterator<TaskID>() {
    private final String prefix = "mesos-compat-";
    private long n = 0;
    public boolean hasNext() { return true; }
    public TaskID next() {
      return TaskID.newBuilder().setValue(prefix + n++).build();
    }
    public void remove() { throw new UnsupportedOperationException(); }
  };

  TaskInfo taskInfo(Offer offer) {
    return TaskInfo.newBuilder()
      .setTaskId(taskIds.next())
      .setName("mesos-compat-task")
      .setSlaveId(offer.getSlaveId())
      .setCommand(CommandInfo.newBuilder().setValue("echo mesos-compat"))
      .addResources(scalarResource("cpus", 0.01))
      .addResources(scalarResource("mem", 8))
      .addResources(scalarResource("disk", 0))
      .build();
  }

  Resource scalarResource(String name, double value) {
    return Resource.newBuilder()
      .setType(Value.Type.SCALAR)
      .setName(name)
      .setScalar(Value.Scalar.newBuilder().setValue(value))
      .build();
  }

}
