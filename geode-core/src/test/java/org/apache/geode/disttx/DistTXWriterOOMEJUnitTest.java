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
package org.apache.geode.disttx;

import static org.apache.geode.distributed.ConfigurationProperties.*;
import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.experimental.categories.Category;

import org.apache.geode.TXWriterOOMEJUnitTest;
import org.apache.geode.cache.AttributesFactory;
import org.apache.geode.cache.CacheException;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Scope;
import org.apache.geode.distributed.ConfigurationProperties;
import org.apache.geode.distributed.DistributedSystem;
import org.apache.geode.internal.cache.GemFireCacheImpl;
import org.apache.geode.test.junit.categories.DistributedTransactionsTest;
import org.apache.geode.test.junit.categories.IntegrationTest;

/**
 * Same tests as that of {@link TXWriterOOMEJUnitTest} after setting "distributed-transactions"
 * property to true
 */
@Category({IntegrationTest.class, DistributedTransactionsTest.class})
public class DistTXWriterOOMEJUnitTest extends TXWriterOOMEJUnitTest {

  @Override
  protected void createCache() throws CacheException {
    Properties properties = new Properties();
    properties.setProperty(MCAST_PORT, "0"); // loner
    properties.setProperty(ConfigurationProperties.DISTRIBUTED_TRANSACTIONS, "true");

    this.cache = (GemFireCacheImpl) CacheFactory.create(DistributedSystem.connect(properties));

    AttributesFactory<String, String> attributesFactory = new AttributesFactory<>();
    attributesFactory.setScope(Scope.DISTRIBUTED_NO_ACK);
    attributesFactory.setIndexMaintenanceSynchronous(true);

    this.region = this.cache.createRegion("TXTest", attributesFactory.create());
    this.txMgr = this.cache.getCacheTransactionManager();

    assertTrue(this.txMgr.isDistributed());
  }

}
