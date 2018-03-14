/**
 * 
 */
package cn.strong.leke.monitor.core.map;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import cn.strong.leke.common.utils.json.JSON;

/**
 * 内存地图信息存储工厂Bean
 * 
 * @author liulongbiao
 *
 */
public class InMemoryMapRegionStoreFactoryBean implements FactoryBean<InMemoryMapRegionStore>, InitializingBean {
	private static final Logger LOG = LoggerFactory.getLogger(InMemoryMapRegionStoreFactoryBean.class);
	private InMemoryMapRegionStore store;
	private Resource[] files;

	public Resource[] getFiles() {
		return files;
	}

	public void setFiles(Resource[] files) {
		this.files = files;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notEmpty(files, "json files for MapRegionStore is required");
		InMemoryMapRegionStore result = new InMemoryMapRegionStore();
		for (Resource file : files) {
			try (InputStream input = file.getInputStream()) {
				MapCountry country = JSON.parse(input, MapCountry.class);
				result.add(country);
				LOG.info("parsed MapCountry in file {}", file.getFilename());
			}
		}
		this.store = result;
	}

	@Override
	public InMemoryMapRegionStore getObject() throws Exception {
		return store;
	}

	@Override
	public Class<?> getObjectType() {
		return InMemoryMapRegionStore.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
