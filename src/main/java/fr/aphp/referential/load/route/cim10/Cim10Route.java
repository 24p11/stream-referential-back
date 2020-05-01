package fr.aphp.referential.load.route.cim10;

import java.nio.charset.StandardCharsets;

import org.apache.camel.Predicate;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.UpdateReferentialBean;
import fr.aphp.referential.load.domain.type.Cim10Type;
import fr.aphp.referential.load.message.Cim10V202004Message;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.domain.type.Cim10Type.V202004;
import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_REFERENTIAL_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UPDATE_REFERENTIAL_BEAN;
import static java.lang.String.format;

@Component
public class Cim10Route extends BaseRoute {
    public Cim10Route() {
        setInput(direct(CIM10));
        setOutput(direct(TO_DB_REFERENTIAL_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CIM10_ROUTE_ID)

                // Disable old entries if it fail later all rows will stay disabled
                .setHeader(UPDATE_REFERENTIAL_BEAN, constant(UpdateReferentialBean.of(CIM10)))
                .to(mybatis("updateEndDateReferential", "UpdateList", "inputHeader=" + UPDATE_REFERENTIAL_BEAN))

                .convertBodyTo(String.class, StandardCharsets.ISO_8859_1.displayName())
                .split().tokenize("[\\r]?\\n", true).streaming()
                .filter(body().isNotNull())

                .choice()
                .when(isVersion(V202004)).unmarshal().bindy(BindyType.Csv, Cim10V202004Message.class)
                .to(getOutput());
    }

    private Predicate isVersion(Cim10Type cim10Type) {
        return simple(format("${file:name.ext.single} =~ '%s'", V202004));
    }
}
