package dev.a7exschin.osgi.dynamic.service;

import org.osgi.annotation.versioning.ConsumerType;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import dev.a7exschin.osgi.dynamic.service.api.DynamicService;

//@formatter:off
@Component(
		immediate = true,
		service = DynamicServicesExample.class,
		property = {
				"osgi.command.scope=zDSE",
				"osgi.command.function=startService"
		})
//@formatter:on
@ConsumerType
public class DynamicServicesExample {

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	DynamicService service;

	@Activate
	public void activate(BundleContext context) {
		System.out.println("Activating DynamicServicesExample");
	}

	public void startService() {
		BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		service = bundleContext.getService(bundleContext.getServiceReference(DynamicService.class));
		if ( service == null) {
			System.out.println("Service is null!");
		} else {
			System.out.println(service.getDate());
			service = null;
		}
		
	}

}
