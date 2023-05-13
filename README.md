# Minecraft PvP Plugin

[![Release](https://jitpack.io/v/dev.josantonius/minecraft-whitelist.svg)](https://jitpack.io/#dev.josantonius/minecraft-whitelist)
[![License](https://img.shields.io/github/license/josantonius/minecraft-whitelist)](LICENSE)

A basic whitelist plugin based on player nicknames only.

## [Watch demo on YouTube](https://www.youtube.com/watch?v=LRzij_6m5wE)

## Requirements

- Java 17 or higher.
- Purpur server 1.19.3 or Bukkit/Spigot/Paper server compatible with the Purpur API version used.

## Installation

1. Download the JAR file: [whitelist-1.0.0-purpur-1.19.3.jar](/build/libs/whitelist-1.0.0-purpur-1.19.3.jar).

1. Place the JAR file in the plugins folder of your Minecraft server.

1. Restart the server to load the plugin.

## Building

To build the plugin yourself, follow these steps:

1. Make sure you have `Java 17` or higher and `Gradle` installed on your system.

1. Clone the plugin repository on your local machine:

    ```bash
    git clone https://github.com/josantonius/minecraft-whitelist.git
    ```

1. Navigate to the directory of the cloned repository:

    ```bash
    cd minecraft-whitelist
    ```

1. Use Gradle to compile the plugin:

    ```bash
    gradle build
    ```

## Commands

- `/wl on` - Enable whitelist

- `/wl off` - Disable whitelist

- `/wl add <player>` - Add player to whitelist

- `/wl remove <player>` - Remove player from whitelist

- `/wl info <player>` - Check if player is on the whitelist

- `/wl help` - Show help

- `/wl reload` - Reload the plugin

All commands requires the `wl.admin` permission to be used.

## Configuration

The `plugins/Whitelist/config.yml` file contains specific plugin configurations.

### Enable or disable the whitelist

If enabled, only players on the whitelist will be able to join the server. If disabled,
all players will be able to join the server.

```yaml
enabled: true
```

### Exclude ops from the whitelist

If enabled, ops will be able to join the server even if they are not on the whitelist.
If disabled, ops will not be able to join the server if they are not on the whitelist.

```yaml
excludeOps: true
```

### Kick players who are not on the whitelist

If enabled, players who are not on the whitelist will be kicked from the server when use
the command `/wl on` or `wl del <player>`.

```yaml
notifyOps: true
```

### Notify ops when a player tries to connect to the server and is not on the whitelist

```yaml
notifyOps: true
```

## Messages

The `plugins/Whitelist/messages.yml` file contains all the messages that the plugin uses.
You can change the messages to your liking.

## TODO

- [ ] Add new feature
- [ ] Create tests
- [ ] Improve documentation

## Changelog

Detailed changes for each release are documented in the
[release notes](https://github.com/josantonius/minecraft-whitelist/releases).

## Contribution

Please make sure to read the [Contributing Guide](.github/CONTRIBUTING.md), before making a pull
request, start a discussion or report a issue.

Thanks to all [contributors](https://github.com/josantonius/minecraft-whitelist/graphs/contributors)! :heart:

## Sponsor

If this project helps you to reduce your development time,
[you can sponsor me](https://github.com/josantonius#sponsor) to support my open source work :blush:

## License

This repository is licensed under the [MIT License](LICENSE).

Copyright © 2023-present, [Josantonius](https://github.com/josantonius#contact)
