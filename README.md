# 🌐 NetFlux

[![Java](https://img.shields.io/badge/java-25-blue)](https://www.java.com/)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

NetFlux is a lightweight networking framework for Minecraft servers that allows Paper/Spigot servers to automatically connect to a Velocity proxy and manage player routing via Redis.

It simplifies server orchestration, allows flexible control over how players connect, and supports seamless server switching.

---

## ⚡ Features

### 🔹 Automatic Server Registration

* Paper/Spigot servers register themselves in Redis and automatically connect to the Velocity proxy.
* NetFlux tracks server and player states in real time.

### 🔹 Flexible Player Routing

* Players can be routed by server type, specific server ID, or using a custom `ServerPicker`.
* Standard implementation: **RoundRobinPicker** — evenly distributes players across servers.
* Developers can override routing logic by creating custom pickers.

### 🔹 Player Switching and Reconnection

* Players can be dynamically moved to other servers based on current load or custom logic.
* Supports automatic reconnection to servers.
---

## 🛠 API

NetFlux provides a simple and extensible API:

### 1. Custom ServerPickers

* Create a class extending `ServerPicker` and implement the method `getServer(UUID, List<LoadedServer>)`.
* Example: distributing players based on least online players, random choice, or your own algorithm.

```java
public class LeastServerPicker extends ServerPicker {

    @Override
    public Optional<LoadedServer> getServer(@NotNull UUID uuid, @NotNull List<LoadedServer> loadedServers) {
        return loadedServers.stream()
                .min(Comparator.comparingInt(LoadedServer::getOnlinePlayers));
    }
}
```

### 2. First Join Configuration

* Set which server a player connects to on their first join:

```java
Netflux.get()
       .getNetfluxServerManager()
       .setFirstJoinPicker("lobby", RoundRobinPicker.class);
```

### 3. Connecting a Player to a Server

* Using a custom ServerPicker
```
UUID playerUUID = player.getUniqueId();
Netflux.get()
       .getPlayerNetworkGateway()
       .connectPlayer(playerUUID, "lobby", RoundRobinPicker.class);
```

* Directly to a specific server
```
UUID playerUUID = player.getUniqueId();
Netflux.get()
       .getPlayerNetworkGateway()
       .connectPlayer(playerUUID, "server_identifier");
```

### 📝 Paper

- **netflux-paper** — main implementation for Paper/Spigot servers.  
- **netflux-paper-api** — API for plugin development. Use `compileOnly` in your project:

```gradle
dependencies {
    compileOnly("com.github.cachewrapper.netflux:paper-api:VERSION")
}
```

### 📝 Velocity

- **netflux-velocity** — main implementation for Velocity proxy.  
- **netflux-velocity-api** — API for plugin development. Use `compileOnly` in your project:

```gradle
dependencies {
    compileOnly("com.github.cachewrapper.netflux:velocity-api:VERSION")
}
```
