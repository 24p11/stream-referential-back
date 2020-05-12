package fr.aphp.referential.load.bean;

import fr.aphp.referential.load.annotation.Tupled;

@Tupled
public interface ConceptRelationshipBean {
    static ConceptRelationshipBean of(String conceptId, String conceptRelationId) {
        return ImmutableConceptRelationshipBean.of(conceptId, conceptRelationId);
    }

    String conceptId();

    String conceptRelationId();
}
