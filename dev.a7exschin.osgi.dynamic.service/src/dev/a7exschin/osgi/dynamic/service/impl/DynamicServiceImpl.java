package dev.a7exschin.osgi.dynamic.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dev.a7exschin.osgi.dynamic.service.api.DynamicService;
import org.osgi.service.component.annotations.Component;

@Component
public class DynamicServiceImpl implements DynamicService {

	@Override
	public String getDate() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		return dtf.format(now);
	}

}
