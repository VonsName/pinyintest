package com.example.pinyintest;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author Administrator
 */
public class ZookeeperTest {
    public static void main(String[] args) throws IOException {
        ZooKeeper zk = new ZooKeeper("192.168.1.1", 2000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {

            }
        });
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
