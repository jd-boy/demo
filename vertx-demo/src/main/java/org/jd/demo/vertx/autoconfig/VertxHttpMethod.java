package org.jd.demo.vertx.autoconfig;

import io.vertx.core.http.HttpMethod;

/**
 * @Auther jd
 */
public enum VertxHttpMethod {

  GET(HttpMethod.GET),
  HEAD(HttpMethod.HEAD),
  POST(HttpMethod.POST),
  PUT(HttpMethod.PUT),
  PATCH(HttpMethod.PATCH),
  DELETE(HttpMethod.DELETE),
  OPTIONS(HttpMethod.OPTIONS),
  TRACE(HttpMethod.TRACE);

  private HttpMethod method;

  VertxHttpMethod(HttpMethod method) {
    this.method = method;
  }

  public HttpMethod getMethod() {
    return method;
  }
}
