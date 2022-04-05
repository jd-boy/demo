package com.jz.demo.guice.core;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jz.demo.guice.ConfigModule;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import java.util.LinkedList;
import java.util.List;
import lombok.SneakyThrows;

/**
 * @Auther jd
 */
public class AutoModuleScanner {

  private static String[] acceptPackages = {"com.jz.demo.guice"};

  private static String[] rejectClasses = {};

  private volatile static Injector injector;

  @SneakyThrows
  public static synchronized void init() {
    if (injector != null) {
      return;
    }
    ClassGraph classGraph = new ClassGraph();
    ScanResult scanResult = classGraph
        .enableClassInfo()
        .acceptPackages(acceptPackages)
        .rejectClasses(rejectClasses)
        .scan();
    List<AbstractModule> modules = new LinkedList<>();
    for (ClassInfo classInfo : scanResult.getSubclasses(AbstractModule.class)) {
      if (classInfo.getName().equals(AutoModuleScanner.class.getName())) {
        continue;
      }
      modules.add(((Class<AbstractModule>) classInfo.loadClass()).getConstructor().newInstance());
    }
    injector = Guice.createInjector(modules.toArray(new AbstractModule[0]));
  }

  public static Injector injector() {
    return injector;
  }

}
