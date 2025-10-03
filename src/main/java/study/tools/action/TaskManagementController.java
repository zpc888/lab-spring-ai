package study.tools.action;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskManagementController {

    private final ChatClient chatClient;
    private final TaskManagementTools taskManagementTools;

    public TaskManagementController(ChatClient.Builder builder, TaskManagementTools taskManagementTools) {
        this.chatClient = builder.build();
        this.taskManagementTools = taskManagementTools;
    }

    @GetMapping("/tasks")
    public Tasks tasks(@RequestParam String message) {
        return chatClient.prompt()
                .tools(taskManagementTools)
                .user(message + ". At the end, return all the tasks.")
                .call()
                .entity(Tasks.class);
    }
}