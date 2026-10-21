package website.longhc;


import cn.hutool.core.util.IdUtil;
import io.agentscope.core.agent.RuntimeContext;
import io.agentscope.core.message.Msg;
import io.agentscope.core.message.UserMessage;
import io.agentscope.extensions.model.openai.OpenAIChatModel;
import io.agentscope.harness.agent.HarnessAgent;

import java.nio.file.Path;

/**
 * @author haicheng.long@gokinsolar.com
 * @Classname SecondAgent
 * @Description
 * @Date 2026/10/20 11:07
 */
public class SecondAgent {
    private static final OpenAIChatModel CHAT_MODEL = OpenAIChatModel.builder()
            .apiKey(System.getenv("OPENAI_API_KEY"))
            .baseUrl("https://token-plan-cn.xiaomimimo.com/v1")
            .modelName("mimo-v2.6-flash")
            .build();
    // 应用启动时配置一次，之后只调用 build()。
    private static final HarnessAgent.Builder AGENT_BUILDER = HarnessAgent.builder()
            .name("note-taker")
            .agentId("note-taker")
            .sysPrompt("你是一个帮助用户做笔记的助手。")
            .model(CHAT_MODEL)
            .workspace(Path.of(".agentscope/workspace"));

    public static void main(String[] args) {
        try (HarnessAgent agent = AGENT_BUILDER.build()) {
            RuntimeContext ctx = RuntimeContext.builder()
                    .userId("alice").sessionId("demo-session").build();
            agent.call(new UserMessage("我叫张三，一个Java后端开发者，要做一个针对多线程编程的培训"), ctx).block();
        }

        // 下一次请求使用新实例；相同会话身份从持久日志恢复上下文。
        try (HarnessAgent agent = AGENT_BUILDER.build()) {
            RuntimeContext ctx = RuntimeContext.builder()
                    .userId("alice").sessionId(IdUtil.fastSimpleUUID()).build();
            Msg reply = agent.call(new UserMessage("我叫什么？今天要干什么？"), ctx).block();
            System.out.println(reply.getTextContent());
        }
    }
}
