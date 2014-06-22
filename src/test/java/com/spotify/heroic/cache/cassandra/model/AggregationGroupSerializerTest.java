package com.spotify.heroic.cache.cassandra.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.spotify.heroic.aggregation.Aggregation;
import com.spotify.heroic.aggregation.AggregationGroup;
import com.spotify.heroic.aggregation.SumAggregation;
import com.spotify.heroic.model.Sampling;

public class AggregationGroupSerializerTest {
    private static final AggregationGroupSerializer serializer = AggregationGroupSerializer.get();
    private static final Sampling resolution = new Sampling(
            Sampling.DEFAULT_VALUE,
            Sampling.DEFAULT_VALUE);

    private AggregationGroup roundTrip(AggregationGroup aggregationGroup) {
        return serializer.fromByteBuffer(serializer.toByteBuffer(aggregationGroup));
    }

    @Test
    public void testEmpty() throws Exception {
        final AggregationGroup aggregationGroup = new AggregationGroup(new ArrayList<Aggregation>());
        Assert.assertEquals(aggregationGroup, roundTrip(aggregationGroup));
    }

    @Test
    public void testOne() throws Exception {
        final Aggregation aggregation = new SumAggregation(resolution);
        final List<Aggregation> aggregations = new ArrayList<Aggregation>();
        aggregations.add(aggregation);
        final AggregationGroup aggregationGroup = new AggregationGroup(aggregations);
        Assert.assertEquals(aggregationGroup, roundTrip(aggregationGroup));
    }
}
