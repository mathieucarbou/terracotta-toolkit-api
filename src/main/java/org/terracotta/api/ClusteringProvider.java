package org.terracotta.api;

import java.util.concurrent.Callable;

import org.terracotta.cluster.TerracottaCluster;
import org.terracotta.locking.LockType;
import org.terracotta.locking.TerracottaLock;
import org.terracotta.logging.TerracottaLogger;
import org.terracotta.properties.TerracottaProperties;

public interface ClusteringProvider {

  TerracottaLock createLock(Object monitor, LockType type);

  TerracottaCluster getCluster();
  
  TerracottaLogger getLogger(String name);
  
  TerracottaProperties getProperties();
  
  /**
   * Disable eviction on the provided object in case it implements a Terracotta interface that supports this.
   * 
   * @param object the object on which eviction should be disabled
   * @return {@code true} when eviction could be disabled; or {@code false} otherwise
   */
  boolean disableEviction(Object object);
  
  /**
   * Enable eviction on the provided object in case it implements a Terracotta interface that supports this.
   * 
   * @param object the object on which eviction should be enabled
   * @return {@code true} when eviction could be enabled; or {@code false} otherwise
   */
  boolean enableEviction(Object object);
  
  void waitForAllCurrentTransactionsToComplete();
  
  void registerBeforeShutdownHook(Runnable beforeShutdownHook);
  
  <T> T lookupOrCreateRoot(String rootName, Callable<T> creator);
  
  String getClientID();
  
  String getUUID();
}