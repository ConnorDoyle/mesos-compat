# mesos-compat

Minimal compatibility smoke test for the Mesos 0.20.1 JNI bindings.

## Prerequisites

- A running mesos cluster
- A Java JDK (1.5 or better)
- Maven

## Usage

Locate the Mesos master statically

```bash
./run.sh some-mesos-master:5050
```

Look up the leading Mesos master in Zookeeper

```bash
./run.sh zk://some-zk-host:2181
```
