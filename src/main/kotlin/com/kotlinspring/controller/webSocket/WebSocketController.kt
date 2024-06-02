package com.kotlinspring.controller.webSocket

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.RestController

@RestController
class WebSocketController @Autowired constructor(private val template: SimpMessagingTemplate) {

    fun sendRefreshMessage() {
        template.convertAndSend("/topic/refresh", "updateStocks")
    }
}
