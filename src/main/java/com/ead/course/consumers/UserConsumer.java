package com.ead.course.consumers;

import com.ead.course.dtos.UserEventDTO;
import com.ead.course.enums.ActionType;
import com.ead.course.models.UserModel;
import com.ead.course.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.ExchangeTypes.FANOUT;

@Component
@Log4j2
public class UserConsumer {

    @Autowired
    UserService userService;

//    Exemplo de como configurar um ouvinte de mensagens em uma aplicação Java usando
//    Spring AMQP com anotações, especificamente para ouvir eventos em uma fila RabbitMQ
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.userEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.userEventExchange}", type = FANOUT, ignoreDeclarationExceptions = "true")
    ))
    public void listenUserEvent(@Payload UserEventDTO userEventDTO){
        //converte o DTO para model
        UserModel userModel = userEventDTO.convertToUserModel();

        //Identifica o tipo de ação
        switch (ActionType.valueOf(userEventDTO.getActionType())){
            //salva o usuário na base
            case CREATE:
                userService.save(userModel);
                log.info("[LISTENER] User Created - {}", userModel.getId());
                break;
        }
    }

}