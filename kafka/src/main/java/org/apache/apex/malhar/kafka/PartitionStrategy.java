/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.apex.malhar.kafka;

import org.apache.hadoop.classification.InterfaceStability;

/**
 * @since 3.3.0
 */
@InterfaceStability.Evolving
public enum PartitionStrategy
{
  /**
   * Each operator partition connect to only one kafka partition
   */
  ONE_TO_ONE,
  /**
   * Each operator consumes from several kafka partitions with overall input rate under some certain hard limit in msgs/s or bytes/s
   * For now it <b>only</b> support <b>simple kafka consumer</b>
   */
  ONE_TO_MANY,
  /**
   * 1 to N partition based on the heuristic function
   * <b>NOT</b> implemented yet
   * TODO implement this later
   */
  ONE_TO_MANY_HEURISTIC
}
