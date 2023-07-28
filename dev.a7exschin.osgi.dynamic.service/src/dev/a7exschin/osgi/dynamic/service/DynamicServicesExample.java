package dev.a7exschin.osgi.dynamic.service;

import org.osgi.annotation.versioning.ConsumerType;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import dev.a7exschin.osgi.dynamic.service.api.DynamicService;

//@formatter:off
@Component(
		service = DynamicServicesExample.class,
		property = {
				"osgi.command.scope=zDSE",
				"osgi.command.function=startService",
				"osgi.command.function=startConsumer",
		})
//@formatter:on
@ConsumerType
public class DynamicServicesExample {

	DynamicService service;

	@Activate
	public void activate(BundleContext context) {
		System.out.println("Activating DynamicServicesExample");
		service = context.getService(context.getServiceReference(DynamicService.class));
	}

	public void startService() {
		System.out.println(service.getDate());
	}
}
