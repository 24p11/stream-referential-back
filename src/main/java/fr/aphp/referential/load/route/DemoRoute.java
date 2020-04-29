package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

@Component
public class DemoRoute extends BaseRoute {
    @Override
    public void configure() throws Exception {
        super.configure();

        from(timer("demo").repeatCount(10))
                .log("Demo");
    }
}
