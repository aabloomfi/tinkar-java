package org.hl7.tinkar.coordinate.language.calculator;

import org.eclipse.collections.api.list.ImmutableList;
import org.hl7.tinkar.common.id.IntIdList;
import org.hl7.tinkar.common.id.IntIds;
import org.hl7.tinkar.coordinate.language.LanguageCoordinate;
import org.hl7.tinkar.coordinate.language.LanguageCoordinateRecord;
import org.hl7.tinkar.coordinate.stamp.StampFilter;
import org.hl7.tinkar.entity.SemanticEntity;
import org.hl7.tinkar.entity.SemanticEntityVersion;
import org.hl7.tinkar.coordinate.stamp.calculator.Latest;
import org.hl7.tinkar.terms.ConceptFacade;
import org.hl7.tinkar.terms.EntityFacade;
import org.hl7.tinkar.terms.TinkarTerm;

import java.util.Optional;
import java.util.OptionalInt;

public interface LanguageCalculator {

    ImmutableList<LanguageCoordinateRecord> languageCoordinateList();

    default ImmutableList<SemanticEntity> getDescriptionsForComponent(EntityFacade entityFacade) {
        return getDescriptionsForComponent(entityFacade.nid());
    }

    /**
     * Gets all descriptions from the first pattern in the the language coordinate pattern priority list
     * that contains any descriptions.
     * @param componentNid
     * @return descriptions from the first pattern in the the language coordinate pattern priority list
     * that contains any descriptions.
     */
    ImmutableList<SemanticEntity> getDescriptionsForComponent(int componentNid);


    default ImmutableList<SemanticEntityVersion> getDescriptionsForComponentOfType(EntityFacade entityFacade,
                                                                                   ConceptFacade descriptionTypeFacade) {
        return getDescriptionsForComponentOfType(entityFacade.nid(), descriptionTypeFacade.nid());
    }

    ImmutableList<SemanticEntityVersion> getDescriptionsForComponentOfType(int componentNid,
                                                                           int descriptionTypeNid);

    Optional<String> getDescriptionTextForComponentOfType(int entityNid, int descriptionTypeNid);

    Optional<String> getAnyName(int componentNid);

    /*
    Allow the pattern to also define a pattern for user text.
     */
    Optional<String> getUserText();


    /**
     * Return the latestDescription according to the pattern, type and dialect preferences of this {@code LanguageCoordinate}.
     *
     * @param descriptionList descriptions to consider
     * @return an optional latestDescription best matching the {@code LanguageCoordinate} constraints.
     */
    default Latest<SemanticEntityVersion> getDescription(ImmutableList<SemanticEntity> descriptionList) {
        return getSpecifiedDescription(descriptionList);
    }

    /**
     * @param entity
     * @return Return the latestDescription according to the type and dialect preferences of this {@code LanguageCoordinate}.
     * @see #getDescriptionText(int)
     */
    default Optional<String> getDescriptionText(EntityFacade entity) {
        return this.getDescriptionText(entity.nid());
    }

    /**
     * @param componentNid
     * @return Return the latestDescription according to the type and dialect preferences of this {@code LanguageCoordinate}.
     */
    Optional<String> getDescriptionText(int componentNid);

    default String getDescriptionTextOrNid(int componentNid) {
        Optional<String> text = getDescriptionText(componentNid);
        if (text.isPresent()) {
            return text.get();
        }
        return Integer.toString(componentNid);
    }

    /**
     * Return the latestDescription according to the type and dialect preferences of this {@code LanguageCoordinate}.
     * or a nested {@code LanguageCoordinate}
     *
     * @param entityNid   the concept nid.
     * @return an optional latestDescription best matching the {@code LanguageCoordinate} constraints.
     */
    default Latest<SemanticEntityVersion> getDescription(int entityNid) {
        return getDescription(getDescriptionsForComponent(entityNid));
    }

    Optional<String> getTextFromSemanticVersion(SemanticEntityVersion semanticEntityVersion);


    /**
     * Gets the latestDescription of type {@link TinkarTerm#REGULAR_NAME_DESCRIPTION_TYPE}, according to dialect preferences.
     * Will return empty, if no matching description type is found in this or any nested language coordinates
     *
     * @param descriptionList the latestDescription list
     * @return the regular name latestDescription, if available
     */
    default Latest<SemanticEntityVersion> getRegularDescription(ImmutableList<SemanticEntity> descriptionList) {
        return getSpecifiedDescription(descriptionList, IntIds.list.of(TinkarTerm.REGULAR_NAME_DESCRIPTION_TYPE.nid()));
    }

    default Optional<String> getRegularDescriptionText(EntityFacade entity) {
        return getRegularDescriptionText(entity.nid());
    }

    default Optional<String> getRegularDescriptionText(int entityNid) {
        Latest<SemanticEntityVersion> latestRegularDescription =  getSpecifiedDescription(getDescriptionsForComponent(entityNid),
                IntIds.list.of(TinkarTerm.REGULAR_NAME_DESCRIPTION_TYPE.nid()));
        if (latestRegularDescription.isPresent()) {
            return getTextFromSemanticVersion(latestRegularDescription.get());
        }
        return Optional.empty();
    }

    /**
     * Return a description of type {@link TinkarTerm#DEFINITION_DESCRIPTION_TYPE}, or an empty latest version, if none are of type definition in this or any
     * nested language coordinates
     *
     * @param descriptionList
     * @return
     */
    default Latest<SemanticEntityVersion> getDefinitionDescription(ImmutableList<SemanticEntity> descriptionList) {
        return getSpecifiedDescription(descriptionList, IntIds.list.of(TinkarTerm.DEFINITION_DESCRIPTION_TYPE.nid()));
    }

    default Optional<String> getDefinitionDescriptionText(EntityFacade entityFacade) {
        return getDefinitionDescriptionText(entityFacade.nid());
    }
    default Optional<String> getDefinitionDescriptionText(int entityNid) {
        return getDescriptionTextForComponentOfType(entityNid, TinkarTerm.DEFINITION_DESCRIPTION_TYPE.nid());
    }


    /**
     * Gets the latestDescription of type {@link TinkarTerm#REGULAR_NAME_DESCRIPTION_TYPE}.  Will return empty, if
     * no matching description type is found in this or any nested language coordinates
     *
     * @param entityNid  the conceptId to get the fully specified latestDescription for
     * @return the regular name latestDescription
     */
    default Latest<SemanticEntityVersion> getRegularDescription(int entityNid) {
        return getSpecifiedDescription(getDescriptionsForComponent(entityNid), IntIds.list.of(TinkarTerm.REGULAR_NAME_DESCRIPTION_TYPE.nid()));
    }

    /**
     * Gets the text of type {@link TinkarTerm#FULLY_QUALIFIED_NAME_DESCRIPTION_TYPE}.  Will return empty, if
     * no matching description type is found in this or any nested language coordinates
     *
     * @param componentNid    the componentNid to get a regular name for.
     * @return the regular name text
     */


    default Optional<String> getFullyQualifiedNameText(int componentNid) {
        return getDescriptionTextForComponentOfType(componentNid, TinkarTerm.FULLY_QUALIFIED_NAME_DESCRIPTION_TYPE.nid());
    }

    /**
     * Gets the latestDescription of type {@link TinkarTerm#FULLY_QUALIFIED_NAME_DESCRIPTION_TYPE}.  Will return empty, if
     * no matching description type is found in this or any nested language coordinates
     *
     * @param descriptionList the latestDescription list
     * @return the regular name latestDescription, if available
     */
    default Latest<SemanticEntityVersion> getFullyQualifiedDescription(ImmutableList<SemanticEntity> descriptionList) {
        return getSpecifiedDescription(descriptionList, IntIds.list.of(TinkarTerm.FULLY_QUALIFIED_NAME_DESCRIPTION_TYPE.nid()));
    }

    /**
     * Gets the latestDescription of type {@link TinkarTerm#FULLY_QUALIFIED_NAME_DESCRIPTION_TYPE}.  Will return empty, if
     * no matching description type is found in this or any nested language coordinates
     *
     * @param conceptId   the conceptId to get the fully specified latestDescription for
     * @return the fully specified latestDescription
     */
    default Latest<SemanticEntityVersion> getFullyQualifiedDescription(int conceptId) {
        throw new UnsupportedOperationException();
        //return getFullyQualifiedDescription(Get.conceptService().getConceptDescriptions(conceptId), stampFilter);
    }

    /**
     * TODO needs update. Add info on patterns, and we don't use getNextPriorityLanguageCoordinate anymore...
     * The developer can pass an ordered list of language coordinates to the language stampFilter.
     * <p>
     * Gets the specified description(s).
     * <p>
     * Iterates over the list of supplied descriptions, finding the descriptions that match the highest ranked
     * {@link LanguageCoordinate#descriptionTypePreferenceNidList()} (first item in the array) and the
     * {@link LanguageCoordinate#languageConceptNid()}.  If no descriptions match, the process is repeated
     * with each subsequent item in {@link LanguageCoordinate#descriptionTypePreferenceNidList()}, walking
     * through the array one by one.
     * <p>
     * For any given step, if multiple descriptions match the criteria, an ACTIVE description should have priority over
     * an inactive one.
     * <p>
     * To be returned, a description MUST match one of the description types, and the specified language.
     * <p>
     * If the specified language {@link LanguageCoordinate#languageConceptNid()} is {@link TinkarTerm#LANGUAGE},
     * then language will be considered to always match, ignoring the actual value of the language in the description.
     * This allows this method to be used with a fallback behavior - where it will match a description of any language,
     * but still rank by the requested type.
     * <p>
     * For any descriptions that matched the criteria, they are then compared with the requested
     * {@link LanguageCoordinate#dialectPatternPreferenceNidList()}
     * The dialect preferences are evaluated in array order.  Each description that has a dialect annotation that matches
     * the dialect preference, with a type of {@link TinkarTerm#PREFERRED}, it is advanced to the next ranking step (below)
     * <p>
     * If none of the descriptions has a dialect annotation of type {@link TinkarTerm#PREFERRED} that matches a dialect
     * in the {@link LanguageCoordinate#dialectPatternPreferenceNidList()}, then all matching language / type matching
     * descriptions are advanced to the next ranking step (below).
     * <p>
     * The final ranking step, is to evaluate {@link LanguageCoordinate#modulePreferenceNidListForLanguage()}
     * The module list is evaluated in order.  If a description matches the requested module, then it is placed
     * into the top position, so it is returned via {@link Latest#get()}.  All other descriptions are still
     * returned, but as part of the {@link Latest#contradictions()}.
     * <p>
     * If none of the description match a specified module ranking, then the descriptions are returned in an arbitrary order,
     * between {@link Latest#get()} and {@link Latest#contradictions()}.
     *
     * @param descriptionList    List of descriptions to consider.
     * @return the specified description
     */
    Latest<SemanticEntityVersion> getSpecifiedDescription(ImmutableList<SemanticEntity> descriptionList);


    /**
     * Same as getSpecifiedDescription(StampFilter stampFilter,
     * List<SemanticChronology> descriptionList,
     * LanguageCoordinate languageCoordinate);
     * but allows the descriptionTypePriority to be independent of the coordinate, without forcing a clone of
     * the coordinate.
     *
     * @param descriptionList
     * @param descriptionTypePriority
     * @return
     */

    Latest<SemanticEntityVersion> getSpecifiedDescription(ImmutableList<SemanticEntity> descriptionList,
                                                                  IntIdList descriptionTypePriority);

    /**
     * Call getRegularName or getFullyQualifiedName for better quality names before calling this method.
     *
     * @param componentNid
     * @param stampFilter
     * @return
     */
    default String getAnyName(int componentNid, StampFilter stampFilter) {
        throw new UnsupportedOperationException();
//       switch (Get.identifierService().getObjectTypeForComponent(componentNid)) {
//           case CONCEPT: {
//               List<SemanticEntity> descriptions = Get.conceptService().getConceptDescriptions(componentNid);
//               if (descriptions.isEmpty()) {
//                   return "No descriptions for: " + Get.identifierService().getUuidPrimordialForNid(componentNid);
//               }
//               SemanticEntityVersion descriptionVersion = (SemanticEntityVersion) descriptions.get(0).getVersionList().get(0);
//               return descriptionVersion.getText();
//           }
//           case SEMANTIC: {
//               SemanticEntity sc = Get.assemblageService().getSemanticEntity(componentNid);
//               if (sc.getVersionType() == VersionType.DESCRIPTION) {
//                   LatestVersion<SemanticEntityVersion> latestDescription = sc.getLatestVersion(stampFilter);
//                   if (latestDescription.isPresent()) {
//                       return latestDescription.get().getText();
//                   }
//                   return ((SemanticEntityVersion) sc.getVersionList().get(0)).getText();
//               }
//               return Get.assemblageService().getSemanticEntity(componentNid).toString();
//           }
//           case UNKNOWN:
//           default:
//               return "No name for: " + Get.identifierService().getUuidPrimordialForNid(componentNid);
//       }
    }

    default OptionalInt getAcceptabilityNid(int descriptionNid, int dialectAssemblageNid, StampFilter stampFilter) {
        throw new UnsupportedOperationException();
//       ImmutableIntSet acceptabilityChronologyNids = Get.assemblageService().getSemanticNidsForComponentFromAssemblage(descriptionNid, dialectAssemblageNid);
//
//       for (int acceptabilityChronologyNid: acceptabilityChronologyNids.toArray()) {
//           SemanticEntity acceptabilityChronology = Get.assemblageService().getSemanticEntity(acceptabilityChronologyNid);
//               LatestVersion<ComponentNidVersion> latestAcceptability = acceptabilityChronology.getLatestVersion(stampFilter);
//               if (latestAcceptability.isPresent()) {
//                   return OptionalInt.of(latestAcceptability.get().getComponentNid());
//               }
//       }
//       return OptionalInt.empty();
    }

}