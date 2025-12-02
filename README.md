# 🌐 NetFlux

[![Java](https://img.shields.io/badge/java-25-blue)](https://www.java.com/)
[![Build](https://img.shields.io/github/actions/workflow/status/yourusername/netflux/gradle.yml)](https://github.com/yourusername/netflux/actions)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

**NetFlux** is a lightweight networking framework for Minecraft servers.  
It allows all running Paper or Spigot servers to automatically connect to a Velocity proxy.  
Developers can control how players are routed across servers using an easy-to-use API.

---

## ⚡ Features

- **Automatic Server Registration**  
  Paper/Spigot servers connect to Velocity automatically, no manual setup required.

- **Flexible Player Routing**  
  Decide how players connect:
  - By specific server
  - By server type
  - Using custom routing logic (custom `ServerPickerType`)

- **Async-First Design**  
  All network calls are non-blocking and return `CompletableFuture`.

- **Extensible API**  
  Register custom packet listeners, server pickers, and routing strategies.
---
