package top.d7c.springboot.client.config;

import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.flowable.ui.modeler.service.FlowableDecisionTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Title: ModelerBootstrapper
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2021年4月29日 下午2:44:29
 * @Description: Responsible for executing all action required after booting up the Spring container.
 */
@Component
public class ModelerBootstrapper implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private FlowableDecisionTableService decisionTableService;
    @Autowired
    private FlowableModelerAppProperties modelerAppProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) { // Using Spring MVC, there are multiple child contexts. We only care about the root
            if (modelerAppProperties == null || modelerAppProperties.isDecisionTableMigrationEnabled()) {
                decisionTableService.migrateDecisionTables();
            }
        }
    }

}
