/*
 *  Copyright (c) 2012 Malhar, Inc.
 *  All Rights Reserved.
 */
package com.malhartech.lib.util;

import com.malhartech.api.Context.OperatorContext;
import com.malhartech.api.DefaultOutputPort;
import com.malhartech.api.Operator.Unifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Combiner for an output port that emits object with Map<K,ArrayList<V>(2)> interface and has the processing done
 * with round robin partition. The first element in the ArrayList is high, the next is low
 *
 * @author amol<br>
 *
 */
public class UnifierHashMapRange<K, V extends Number> implements Unifier<HashMap<K, HighLow<V>>>
{
  public HashMap<K, HighLow<V>> mergedTuple = new HashMap<K, HighLow<V>>();
  public final transient DefaultOutputPort<HashMap<K, HighLow<V>>> mergedport = new DefaultOutputPort<HashMap<K, HighLow<V>>>(this);

  /**
   * combines the tuple into a single final tuple which is emitted in endWindow
   * @param tuple incoming tuple from a partition
   */
  @Override
  public void merge(HashMap<K, HighLow<V>> tuple)
  {
    for (Map.Entry<K, HighLow<V>> e: tuple.entrySet()) {
      HighLow<V> val = mergedTuple.get(e.getKey());
      if (val == null) {
        val = new HighLow<V>(e.getValue().getHigh(), e.getValue().getLow());
        mergedTuple.put(e.getKey(), val);
      }
      else {
        if (val.getHigh().doubleValue() < e.getValue().getHigh().doubleValue()) {
          val.setHigh(e.getValue().getHigh());
        }
        if (val.getLow().doubleValue() > e.getValue().getLow().doubleValue()) {
          val.setLow(e.getValue().getLow());
        }
      }
    }
  }

  /**
   * a no op
   * @param windowId
   */
  @Override
  public void beginWindow(long windowId)
  {
  }


  /**
   * emits mergedTuple on mergedport if it is not empty
   */
  @Override
  public void endWindow()
  {
    if (!mergedTuple.isEmpty())  {
      mergedport.emit(mergedTuple);
      mergedTuple = new HashMap<K, HighLow<V>>();
    }
  }

  /**
   * a no-op
   * @param context
   */
  @Override
  public void setup(OperatorContext context)
  {
  }

  /**
   * a noop
   */
  @Override
  public void teardown()
  {
  }
}
