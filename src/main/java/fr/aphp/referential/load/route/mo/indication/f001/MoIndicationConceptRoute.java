package fr.aphp.referential.load.route.mo.indication.f001;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.bean.ConceptRelationshipBean;
import fr.aphp.referential.load.processor.ghmghs.f001.GhmGhsConceptProcessor;
import fr.aphp.referential.load.processor.mo.indication.f001.MoIndicationConceptProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.MO_INDICATION_F001_CONCEPT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_RELATIONSHIP_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_ROUTE_ID;

@Component(MO_INDICATION_F001_CONCEPT_ROUTE_ID)
public class MoIndicationConceptRoute extends BaseRoute {
    public MoIndicationConceptRoute() {
        setInput(direct(MO_INDICATION_F001_CONCEPT_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(MO_INDICATION_F001_CONCEPT_ROUTE_ID)

                .transform().message(MoIndicationConceptProcessor::streamBean)

                .split(body())
                .parallelProcessing()

                .choice()
                .when(body().isInstanceOf(ConceptBean.class)).process().message(GhmGhsConceptProcessor::setSourceTypeHeader).to(direct(TO_DB_CONCEPT_ROUTE_ID))
                .when(body().isInstanceOf(ConceptRelationshipBean.class)).to(direct(TO_DB_CONCEPT_RELATIONSHIP_ROUTE_ID));
    }
}
