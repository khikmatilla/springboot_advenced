package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.entity.QuestionAnswer;
import com.myproject.springboot_advenced.model.ChatMessage;
import com.myproject.springboot_advenced.repository.QuestionAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final QuestionAnswerRepository repository;

    public ChatMessage getAnswer(ChatMessage message) {
        String question = message.getText();
        String answer = repository.findByQuestionIgnoreCase(question)
                .map(QuestionAnswer::getAnswer)
                .orElse("Kechirasiz, bu savolga javob topilmadi.");
        return new ChatMessage("Server", answer);
    }
}
