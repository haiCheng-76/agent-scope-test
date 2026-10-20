package website.longhc;


import io.agentscope.core.ReActAgent;
import io.agentscope.core.agent.RuntimeContext;
import io.agentscope.core.message.Msg;
import io.agentscope.core.message.UserMessage;
import io.agentscope.core.tool.Toolkit;
import io.agentscope.extensions.model.openai.OpenAIChatModel;

import java.util.List;

/**
 * @author haicheng.long@gokinsolar.com
 * @Classname FirstAgent
 * @Description
 * @Date 2026/10/20 9:18
 */
public class FirstAgent {

    public static void main(String[] args) {

        OpenAIChatModel chatModel = OpenAIChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .baseUrl("https://token-plan-cn.xiaomimimo.com/v1")
                .modelName("mimo-v2.6-flash")
                .build();

        ReActAgent.Builder agentBuilder = ReActAgent.builder()
                .name("my-agent")
                .sysPrompt("你是一个有帮助的助手。")
                .model(chatModel)
                .toolkit(new Toolkit());

        UserMessage msg = new UserMessage("什么事斐波那契？");
        RuntimeContext ctx = RuntimeContext.builder().userId("alice").sessionId("session-001").build();
        try (ReActAgent agent = agentBuilder.build()) {
            Msg result = agent.call(List.of(msg), ctx).block();
            System.out.println(result.getTextContent());
        }
    }
}