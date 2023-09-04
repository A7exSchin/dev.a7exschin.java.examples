package impl;

import java.util.Properties;

import dev.a7exschin.tycho.example.api.SystemProperties;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true)
public class SystemPropertiesImpl implements SystemProperties {

	@Override
	public Properties getSystemProperties() {
		return System.getProperties();
	}

}
