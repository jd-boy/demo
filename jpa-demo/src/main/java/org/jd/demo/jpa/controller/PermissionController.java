package org.jd.demo.jpa.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther jd
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class PermissionController {

  @PostMapping(value = "/api/permissions")
  public Object createPermission() {
    return null;
  }

}
