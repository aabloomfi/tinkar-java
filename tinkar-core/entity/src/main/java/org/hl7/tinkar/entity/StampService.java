package org.hl7.tinkar.entity;

import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.primitive.ImmutableLongList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.hl7.tinkar.common.id.IntIdSet;
import org.hl7.tinkar.common.service.PrimitiveData;
import org.hl7.tinkar.entity.internal.StampServiceFinder;
import org.hl7.tinkar.entity.util.StampRealizer;
import org.hl7.tinkar.terms.ConceptFacade;
import org.hl7.tinkar.terms.EntityProxy;

public interface StampService {

    static StampService get() {
        return StampServiceFinder.INSTANCE.get();
    }

    /**
     * Very inefficient. Please override.
     *
     * @return IntIdSet of the stamp nids.
     */
    default IntIdSet getStampNids() {
        StampRealizer stampRealizer = new StampRealizer();
        PrimitiveData.get().forEach(stampRealizer);
        return stampRealizer.stampNids();
    }

    default ImmutableSet<ConceptFacade> getAuthorsInUse() {
        MutableSet<ConceptFacade> authors = Sets.mutable.empty();
        for (int authorNid : getAuthorNidsInUse().toArray()) {
            authors.add(EntityProxy.Concept.make(authorNid));
        }
        return authors.toImmutable();
    }

    IntIdSet getAuthorNidsInUse();

    default ImmutableSet<ConceptFacade> getModulesInUse() {
        MutableSet<ConceptFacade> modules = Sets.mutable.empty();
        for (int moduleNid : getModuleNidsInUse().toArray()) {
            modules.add(EntityProxy.Concept.make(moduleNid));
        }
        return modules.toImmutable();
    }

    IntIdSet getModuleNidsInUse();

    default ImmutableSet<ConceptFacade> getPathsInUse() {
        MutableSet<ConceptFacade> paths = Sets.mutable.empty();
        for (int pathNid : getPathNidsInUse().toArray()) {
            paths.add(EntityProxy.Concept.make(pathNid));
        }
        return paths.toImmutable();
    }

    IntIdSet getPathNidsInUse();

    ImmutableLongList getTimesInUse();

}
