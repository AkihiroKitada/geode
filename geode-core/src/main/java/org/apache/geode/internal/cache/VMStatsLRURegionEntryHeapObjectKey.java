/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode.internal.cache;

// DO NOT modify this class. It was generated from LeafRegionEntry.cpp
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import org.apache.geode.internal.cache.lru.EnableLRU;
import org.apache.geode.internal.InternalStatisticsDisabledException;
import org.apache.geode.internal.cache.lru.LRUClockNode;
import org.apache.geode.internal.cache.lru.NewLRUClockHand;
import org.apache.geode.internal.util.concurrent.CustomEntryConcurrentHashMap.HashEntry;

// macros whose definition changes this class:
// disk: DISK
// lru: LRU
// stats: STATS
// versioned: VERSIONED
// offheap: OFFHEAP
// One of the following key macros must be defined:
// key object: KEY_OBJECT
// key int: KEY_INT
// key long: KEY_LONG
// key uuid: KEY_UUID
// key string1: KEY_STRING1
// key string2: KEY_STRING2
/**
 * Do not modify this class. It was generated. Instead modify LeafRegionEntry.cpp and then run
 * ./dev-tools/generateRegionEntryClasses.sh (it must be run from the top level directory).
 */
public class VMStatsLRURegionEntryHeapObjectKey extends VMStatsLRURegionEntryHeap {
  public VMStatsLRURegionEntryHeapObjectKey(RegionEntryContext context, Object key, Object value) {
    super(context, value);
    // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
    this.key = key;
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  // common code
  protected int hash;
  private HashEntry<Object, Object> next;
  @SuppressWarnings("unused")
  private volatile long lastModified;
  private static final AtomicLongFieldUpdater<VMStatsLRURegionEntryHeapObjectKey> lastModifiedUpdater =
      AtomicLongFieldUpdater.newUpdater(VMStatsLRURegionEntryHeapObjectKey.class, "lastModified");
  private volatile Object value;

  @Override
  protected final Object getValueField() {
    return this.value;
  }

  @Override
  protected void setValueField(Object v) {
    this.value = v;
  }

  protected long getLastModifiedField() {
    return lastModifiedUpdater.get(this);
  }

  protected boolean compareAndSetLastModifiedField(long expectedValue, long newValue) {
    return lastModifiedUpdater.compareAndSet(this, expectedValue, newValue);
  }

  /**
   * @see HashEntry#getEntryHash()
   */
  public final int getEntryHash() {
    return this.hash;
  }

  protected void setEntryHash(int v) {
    this.hash = v;
  }

  /**
   * @see HashEntry#getNextEntry()
   */
  public final HashEntry<Object, Object> getNextEntry() {
    return this.next;
  }

  /**
   * @see HashEntry#setNextEntry
   */
  public final void setNextEntry(final HashEntry<Object, Object> n) {
    this.next = n;
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  // lru code
  @Override
  public void setDelayedDiskId(LocalRegion r) {
    // nothing needed for LRUs with no disk
  }

  public final synchronized int updateEntrySize(EnableLRU capacityController) {
    return updateEntrySize(capacityController, _getValue()); // OFHEAP: _getValue ok w/o incing
                                                             // refcount because we are synced and
                                                             // only getting the size
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  public final synchronized int updateEntrySize(EnableLRU capacityController, Object value) {
    int oldSize = getEntrySize();
    int newSize = capacityController.entrySize(getKeyForSizing(), value);
    setEntrySize(newSize);
    int delta = newSize - oldSize;
    // if ( debug ) log( "updateEntrySize key=" + getKey()
    // + (_getValue() == Token.INVALID ? " invalid" :
    // (_getValue() == Token.LOCAL_INVALID ? "local_invalid" :
    // (_getValue()==null ? " evicted" : " valid")))
    // + " oldSize=" + oldSize
    // + " newSize=" + this.size );
    return delta;
  }

  public final boolean testRecentlyUsed() {
    return areAnyBitsSet(RECENTLY_USED);
  }

  @Override
  public final void setRecentlyUsed() {
    setBits(RECENTLY_USED);
  }

  public final void unsetRecentlyUsed() {
    clearBits(~RECENTLY_USED);
  }

  public final boolean testEvicted() {
    return areAnyBitsSet(EVICTED);
  }

  public final void setEvicted() {
    setBits(EVICTED);
  }

  public final void unsetEvicted() {
    clearBits(~EVICTED);
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  private LRUClockNode nextLRU;
  private LRUClockNode prevLRU;
  private int size;

  public final void setNextLRUNode(LRUClockNode next) {
    this.nextLRU = next;
  }

  public final LRUClockNode nextLRUNode() {
    return this.nextLRU;
  }

  public final void setPrevLRUNode(LRUClockNode prev) {
    this.prevLRU = prev;
  }

  public final LRUClockNode prevLRUNode() {
    return this.prevLRU;
  }

  public final int getEntrySize() {
    return this.size;
  }

  protected final void setEntrySize(int size) {
    this.size = size;
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  // @Override
  // public StringBuilder appendFieldsToString(final StringBuilder sb) {
  // StringBuilder result = super.appendFieldsToString(sb);
  // result.append("; prev=").append(this.prevLRU==null?"null":"not null");
  // result.append("; next=").append(this.nextLRU==null?"null":"not null");
  // return result;
  // }
  @Override
  public Object getKeyForSizing() {
    // default implementation.
    return getKey();
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  // stats code
  @Override
  public final void updateStatsForGet(boolean hit, long time) {
    setLastAccessed(time);
    if (hit) {
      incrementHitCount();
    } else {
      incrementMissCount();
    }
  }

  @Override
  protected final void setLastModifiedAndAccessedTimes(long lastModified, long lastAccessed) {
    _setLastModified(lastModified);
    if (!DISABLE_ACCESS_TIME_UPDATE_ON_PUT) {
      setLastAccessed(lastAccessed);
    }
  }

  private volatile long lastAccessed;
  private volatile int hitCount;
  private volatile int missCount;
  private static final AtomicIntegerFieldUpdater<VMStatsLRURegionEntryHeapObjectKey> hitCountUpdater =
      AtomicIntegerFieldUpdater.newUpdater(VMStatsLRURegionEntryHeapObjectKey.class, "hitCount");
  private static final AtomicIntegerFieldUpdater<VMStatsLRURegionEntryHeapObjectKey> missCountUpdater =
      AtomicIntegerFieldUpdater.newUpdater(VMStatsLRURegionEntryHeapObjectKey.class, "missCount");

  @Override
  public final long getLastAccessed() throws InternalStatisticsDisabledException {
    return this.lastAccessed;
  }

  private void setLastAccessed(long lastAccessed) {
    this.lastAccessed = lastAccessed;
  }

  @Override
  public final long getHitCount() throws InternalStatisticsDisabledException {
    return this.hitCount & 0xFFFFFFFFL;
  }

  @Override
  public final long getMissCount() throws InternalStatisticsDisabledException {
    return this.missCount & 0xFFFFFFFFL;
  }

  private void incrementHitCount() {
    hitCountUpdater.incrementAndGet(this);
  }

  private void incrementMissCount() {
    missCountUpdater.incrementAndGet(this);
  }

  @Override
  public final void resetCounts() throws InternalStatisticsDisabledException {
    hitCountUpdater.set(this, 0);
    missCountUpdater.set(this, 0);
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  @Override
  public final void txDidDestroy(long currTime) {
    setLastModified(currTime);
    setLastAccessed(currTime);
    this.hitCount = 0;
    this.missCount = 0;
  }

  @Override
  public boolean hasStats() {
    return true;
  }

  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
  // key code
  private final Object key;

  @Override
  public final Object getKey() {
    return this.key;
  }
  // DO NOT modify this class. It was generated from LeafRegionEntry.cpp
}
