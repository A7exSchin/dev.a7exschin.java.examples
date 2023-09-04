package command;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.osgi.annotation.versioning.ConsumerType;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import dev.a7exschin.tycho.example.api.SystemProperties;

//@formatter:off
@Component(
		service = SystemPropertiesCommand.class,
		property = {
				"osgi.command.scope=zSP",
				"osgi.command.function=printProps"
		})
//@formatter:on
@ConsumerType
public class SystemPropertiesCommand {

	@Reference
	SystemProperties systemProeprtiesService;

	public void printProps() {
		Properties systemProps = systemProeprtiesService.getSystemProperties();
		HashMap<String, String> sortedProps = new HashMap<>();
		systemProps.forEach((k, v) -> sortedProps.put(k.toString(), v.toString()));
		
		sortedProps.entrySet()
				.stream()
				.sorted(Map.Entry.<String, String>comparingByKey())
				.forEach(System.out::println);
	}
}
