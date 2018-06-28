/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.siddhi.core.query.input.stream.state.receiver;

import org.wso2.siddhi.core.config.SiddhiAppContext;
import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.input.StateMultiProcessStreamReceiver;
import org.wso2.siddhi.core.query.input.stream.state.PreStateProcessor;
import org.wso2.siddhi.core.util.statistics.LatencyTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link org.wso2.siddhi.core.stream.StreamJunction.Receiver} implementation to receive events into pattern queries
 * with multiple streams.
 */
public class PatternMultiProcessStreamReceiver extends StateMultiProcessStreamReceiver {

    public PatternMultiProcessStreamReceiver(String streamId, int processCount, LatencyTracker latencyTracker,
                                             String queryName, SiddhiAppContext siddhiAppContext) {
        super(streamId, processCount, latencyTracker, queryName, siddhiAppContext);
        eventSequence = new int[processCount];
        int count = 0;
        for (int i = eventSequence.length - 1; i >= 0; i--) {
            eventSequence[count] = i;
            count++;
        }
    }

    public PatternMultiProcessStreamReceiver clone(String key) {
        return new PatternMultiProcessStreamReceiver(streamId + key, processCount, latencyTracker, queryName,
                siddhiAppContext);
    }

    protected void stabilizeStates() {
        if (stateProcessorsSize != 0) {
            for (PreStateProcessor preStateProcessor : stateProcessors) {
                preStateProcessor.updateState();
            }
        }
    }

    @Override
    public void receive(ComplexEvent complexEvent) {
        super.receive(complexEvent);
    }

    @Override
    public void receive(Event event) {
        super.receive(event);
    }

    @Override
    public void receive(Event[] events) {
        super.receive(events);
    }

    @Override
    public void receive(Event event, boolean endOfBatch) {
        super.receive(event, endOfBatch);
    }

    @Override
    public void receive(long timestamp, Object[] data) {
        super.receive(timestamp, data);
    }

    @Override
    public String toString() {
        List<Integer> stateProcessorIds = new ArrayList<>(stateProcessorsSize);
        for (PreStateProcessor stateProcessor : stateProcessors) {
            stateProcessorIds.add(stateProcessor.getStateId());
        }
        return "{\"PatternMultiProcessStreamReceiver\":{" +
                "\"streamId\":\"" + streamId + '\"' +
                ", \"preStateProcessors\":" + stateProcessorIds +
                ", \"nextProcessors\":" + Arrays.toString(nextProcessors) +
                "}}";
    }

}
