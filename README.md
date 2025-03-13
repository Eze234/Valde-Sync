> [!IMPORTANT]
Recordá configurar el config.json<br>
> [!WARNING]
El error:
```fix
[22:24:01 ERROR]: [net.dv8tion.jda.internal.requests.WebSocketClient] There was an error in the WebSocket connection. Trace: [["gateway-prd-us-east1-c-x3qx",{"micros":68105,"calls":["id_created",{"micros":924,"calls":[]},"session_lookup_time",{"micros":6296,"calls":[]},"session_lookup_finished",{"micros":17,"calls":[]},"discord-sessions-prd-1-80",{"micros":60192,"calls":["start_session",{"micros":56885,"calls":["discord-api-rpc-6bbdc985f5-xctjm",{"micros":37924,"calls":["get_user",{"micros":5379},"get_guilds",{"micros":5766},"send_scheduled_deletion_message",{"micros":8},"guild_join_requests",{"micros":1},"authorized_ip_coro",{"micros":8},"pending_payments",{"micros":1537}]}]},"starting_guild_connect",{"micros":48,"calls":[]},"presence_started",{"micros":476,"calls":[]},"guilds_started",{"micros":147,"calls":[]},"lobbies_started",{"micros":2,"calls":[]},"guilds_connect",{"micros":3,"calls":[]},"presence_connect",{"micros":2581,"calls":[]},"connect_finished",{"micros":2587,"calls":[]},"build_ready",{"micros":44,"calls":[]},"clean_ready",{"micros":1,"calls":[]},"optimize_ready",{"micros":0,"calls":[]},"split_ready",{"micros":1,"calls":[]}]}]}]]
java.lang.IllegalStateException: zip file closed
        at java.base/java.util.zip.ZipFile.ensureOpen(ZipFile.java:846) ~[?:?]
        at java.base/java.util.zip.ZipFile.getEntry(ZipFile.java:338) ~[?:?]
        at java.base/java.util.jar.JarFile.getEntry(JarFile.java:516) ~[?:?]
        at java.base/java.util.jar.JarFile.getJarEntry(JarFile.java:471) ~[?:?]
        at org.bukkit.plugin.java.PluginClassLoader.findClass(PluginClassLoader.java:209) ~[paper-api-1.21.4-R0.1-SNAPSHOT.jar:?]
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:593) ~[?:?]
        at org.bukkit.plugin.java.PluginClassLoader.loadClass0(PluginClassLoader.java:169) ~[paper-api-1.21.4-R0.1-SNAPSHOT.jar:?]
        at org.bukkit.plugin.java.PluginClassLoader.loadClass(PluginClassLoader.java:164) ~[paper-api-1.21.4-R0.1-SNAPSHOT.jar:?]
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:526) ~[?:?]
        at Valde-Sync-1.0.1-all.jar/net.dv8tion.jda.internal.requests.WebSocketClient.onShutdown(WebSocketClient.java:363) ~[Valde-Sync-1.0.1-all.jar:?]
        at Valde-Sync-1.0.1-all.jar/net.dv8tion.jda.internal.requests.WebSocketClient.handleDisconnect(WebSocketClient.java:547) ~[Valde-Sync-1.0.1-all.jar:?]
        at Valde-Sync-1.0.1-all.jar/net.dv8tion.jda.internal.requests.WebSocketClient.onDisconnected(WebSocketClient.java:464) ~[Valde-Sync-1.0.1-all.jar:?]
        at Valde-Sync-1.0.1-all.jar/com.neovisionaries.ws.client.ListenerManager.callOnDisconnected(ListenerManager.java:224) ~[Valde-Sync-1.0.1-all.jar:?]
        at Valde-Sync-1.0.1-all.jar/com.neovisionaries.ws.client.WebSocket.finish(WebSocket.java:3758) ~[Valde-Sync-1.0.1-all.jar:?]
        at Valde-Sync-1.0.1-all.jar/com.neovisionaries.ws.client.WebSocket.onThreadsFinished(WebSocket.java:3724) ~[Valde-Sync-1.0.1-all.jar:?]
        at Valde-Sync-1.0.1-all.jar/com.neovisionaries.ws.client.WebSocket.onReadingThreadFinished(WebSocket.java:3691) ~[Valde-Sync-1.0.1-all.jar:?]
        at Valde-Sync-1.0.1-all.jar/com.neovisionaries.ws.client.ReadingThread.notifyFinished(ReadingThread.java:1138) ~[Valde-Sync-1.0.1-all.jar:?]
        at Valde-Sync-1.0.1-all.jar/com.neovisionaries.ws.client.ReadingThread.runMain(ReadingThread.java:80) ~[Valde-Sync-1.0.1-all.jar:?]
        at Valde-Sync-1.0.1-all.jar/com.neovisionaries.ws.client.WebSocketThread.run(WebSocketThread.java:45) ~[Valde-Sync-1.0.1-all.jar:?]```
Se debe a que el cliente de discord se cierra, es completamente normal!
