package com.jz.demo.vertx.core;

import com.jz.demo.vertx.core.AbstractVertxController;
import com.jz.demo.vertx.core.EnableVertxWeb;
import com.jz.demo.vertx.core.VertxController;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.micrometer.MetricsDomain;
import io.vertx.micrometer.backends.BackendRegistries;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * @Auther jd
 */
@Slf4j
@Component
public class VertxWebImportSelector implements ImportSelector, ResourceLoaderAware,
    EnvironmentAware, ApplicationListener<ContextRefreshedEvent> {

  private ResourceLoader resourceLoader;

  private Environment environment;

  private static Set<Class<? extends Verticle>> verticleInstance = new HashSet<>();

  @Override
  public String[] selectImports(AnnotationMetadata importingClassMetadata) {
    ClassPathScanningCandidateComponentProvider scanner = getScanner();
    scanner.setResourceLoader(this.resourceLoader);
    scanner.addIncludeFilter(new AnnotationTypeFilter(VertxController.class));
    Set<String> basePackages = getBasePackages(importingClassMetadata);
    for (String basePackage : basePackages) {
      verticleInstance.addAll(scanner.findCandidateComponents(basePackage)
          .stream().map(beanDefinition ->
              (Class<? extends Verticle>) ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), null)
          ).collect(Collectors.toSet()));
    }
    return new String[0];
  }

  @Override
  public synchronized void onApplicationEvent(ContextRefreshedEvent event) {
    updateVertxMetrics();
    Iterator<Class<? extends Verticle>> iterator = verticleInstance.iterator();
    while (iterator.hasNext()) {
      Class<? extends Verticle> clazz = iterator.next();
      deploymentVerticle(event, clazz);
      iterator.remove();
    }
  }

  private void deploymentVerticle(ContextRefreshedEvent event, Class<? extends Verticle> beanClazz) {
    VertxController vertxController = AnnotatedElementUtils
        .getMergedAnnotation(beanClazz, VertxController.class);
    Environment environment = event.getApplicationContext().getEnvironment();
    Integer instanceNum = Integer
        .valueOf(environment.resolvePlaceholders(vertxController.instanceNum()));
    DeploymentOptions options = new DeploymentOptions()
        .setInstances(instanceNum);
    event.getApplicationContext().getBean(Vertx.class)
        .deployVerticle(() -> event.getApplicationContext().getBean(beanClazz),
        options,
        handler -> {
          if (handler.succeeded()) {
            String description = vertxController.description();
            log.info("{}发布成功. 实例数量：{}", "".equals(description) ? beanClazz.getName() : description, instanceNum);
          } else {
            throw new RuntimeException(handler.cause());
          }
        });
  }

  private void updateVertxMetrics() {
    MeterRegistry meterRegistry = BackendRegistries.getDefaultNow();
    if (meterRegistry == null) {
      return;
    }
    meterRegistry.config()
        .meterFilter(meterFilter(MetricsDomain.HTTP_SERVER));
  }

  private MeterFilter meterFilter(MetricsDomain domain) {
    return new MeterFilter() {
      @Override
      public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {
        if (id.getName().startsWith(domain.getPrefix())
            && Meter.Type.TIMER == id.getType()) {
          return DistributionStatisticConfig.builder()
              .percentiles(0.95, 0.99, 0.995)
              .build()
              .merge(config);
        } else {
          return config;
        }
      }
    };
  }

  private Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
    Map<String, Object> attributes = importingClassMetadata
        .getAnnotationAttributes(EnableVertxWeb.class.getCanonicalName());
    Set<String> basePackages = new HashSet<>();
    for (String pkg : (String[]) attributes.get("value")) {
      if (StringUtils.hasText(pkg)) {
        basePackages.add(pkg);
      }
    }
    for (String pkg : (String[]) attributes.get("basePackages")) {
      if (StringUtils.hasText(pkg)) {
        basePackages.add(pkg);
      }
    }
    for (Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
      basePackages.add(ClassUtils.getPackageName(clazz));
    }
    if (basePackages.isEmpty()) {
      basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
    }
    return basePackages;
  }

  private ClassPathScanningCandidateComponentProvider getScanner() {
    return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
      @Override
      protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        boolean isCandidate = false;
        if (beanDefinition.getMetadata().isIndependent()) {
          isCandidate = AbstractVertxController.class.isAssignableFrom(
              ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), null));
        }
        return isCandidate;
      }
    };
  }

  @Override
  public void setResourceLoader(ResourceLoader resourceLoader) {
    if (this.resourceLoader == null) {
      this.resourceLoader = resourceLoader;
    }
  }

  @Override
  public void setEnvironment(Environment environment) {
    if (this.environment == null) {
      this.environment = environment;
    }
  }

}