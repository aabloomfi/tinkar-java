package org.hl7.tinkar.provider.mvstore.internal;

import org.eclipse.collections.api.list.ImmutableList;
import org.hl7.tinkar.component.Stamp;
import org.hl7.tinkar.entity.ConceptEntity;
import org.hl7.tinkar.entity.EntityRecordFactory;
import org.hl7.tinkar.entity.StampEntity;
import org.hl7.tinkar.provider.mvstore.MVStoreProvider;

import java.util.UUID;

public class Get {
    public static MVStoreProvider singleton;

    public static ConceptEntity concept(int nid) {
        return EntityRecordFactory.make(singleton.getBytes(nid));
    }

    public static StampEntity stamp(int nid) {
        return EntityRecordFactory.make(singleton.getBytes(nid));
    }

    public static int nidForUuids(ImmutableList<UUID> uuidList) {
        return singleton.nidForUuids(uuidList);
    }

    public static int stampNid(Stamp stamp) {
        throw new UnsupportedOperationException();
    }
}
