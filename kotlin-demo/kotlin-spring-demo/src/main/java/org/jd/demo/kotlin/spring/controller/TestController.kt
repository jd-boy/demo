package org.jd.demo.kotlin.spring.controller

import org.jd.demo.kotlin.spring.dto.Message
import org.springframework.boot.context.properties.bind.Bindable.listOf
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * @Auther jd
 */
@RestController
class TestController {

    @GetMapping("/api/kotlin/test")
    fun test(): Message {
        return Message("122", "jiejijiiji")
    }

    @PostMapping("/api/kotlin/inferenceOnline")
    fun mtlIntf(@RequestBody string: String): String =
        "{\n" +
                "    \"msg\": \"SUCCESS\",\n" +
                "    \"requestId\": \"112233\",\n" +
                "    \"status\": 1,\n" +
                "    \"mtlScore\": {\n" +
                "        \"001\": 0.5384307,\n" +
                "        \"002\": 0.028265351\n" +
                "    },\n" +
                "    \"inCache\": false,\n" +
                "    \"embeddingNum\": 64\n" +
                "}";

}