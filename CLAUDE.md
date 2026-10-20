# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A minimal Java 17 / Maven sandbox for experimenting with **AgentScope** (Java agent framework, v2.0.4). The sole
application class is `website.longhc.FirstAgent`, a `main()` program that builds an OpenAI-compatible `ReActAgent` and
runs one prompt. Despite `packaging=war`, there is no `src/main/webapp` — the WAR layout under `target/` is leftover
archetype output; this is a console experiment, not a deployed webapp.

## Commands

```bash
mvn compile                                          # compile
mvn test                                             # run all tests (JUnit 4)
mvn test -Dtest=Test#test                            # run a single test
mvn package                                          # build WAR → target/agent-scope-test.war
mvn dependency:build-classpath -Dmdep.outputFile=cp.txt  # emit classpath for running main() outside an IDE
```

`FirstAgent` has a plain `main()` and no exec plugin is configured — run it from IntelliJ IDEA (project has `.idea/`) or
via `java -cp target/classes;<deps> website.longhc.FirstAgent` after building the classpath.

## Architecture

- **Model layer**: `OpenAIChatModel` (from `agentscope-extensions-model-openai`) points at an OpenAI-compatible
  endpoint. API key, base URL, and model name are currently hardcoded in `FirstAgent.main()` — treat them as
  configuration to externalize, not constants.
- **Agent layer**: `ReActAgent` built via its `Builder` (name, system prompt, model, empty `Toolkit`), invoked with
  `agent.call(List<Msg>, RuntimeContext).block()`. `RuntimeContext` carries `userId`/`sessionId`. The agent is closed
  via try-with-resources.
- **Dependency scopes matter**: `agentscope-harness` is `provided` (expected from the runtime/host environment, not
  bundled in the WAR); the OpenAI extension is `compile` (bundled).
- **Logging**: Lombok `@Slf4j` + Logback (`src/main/resources/logback.xml`) → console and rolling file
  `logs/agent-scope-test.log` (daily/50MB rotation, 30-day retention).
- **Tests**: JUnit 4 (not JUnit 5). Existing `Test#test` dumps all system properties — it's a smoke test, not agent
  coverage.

## 注意

1. 这是一个基于Agent Scope Java 构建智能体的项目，用于学习智能体的搭建和开发，扫除知识盲点提升个人技能，所以在实现过程中有描述错误或者不完整的要及时指正。
2. 不要直接实现代码，最好是让我手动实现，这是为了更好的学习和提升。