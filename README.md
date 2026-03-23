# CubeScale

A lightweight Spigot plugin that lets you change the Nether portal coordinate ratio to whatever you want.

By default, Minecraft uses an 8:1 ratio (1 block in the Nether = 8 blocks in the Overworld). CubeScale lets you set this to any value - 2:1, 1:1, or anything else.

Works with both players and entities (horses, striders, etc.), so mounted travel through portals uses the custom ratio too.

## Requirements

- Spigot or Paper 1.20.4+ (Works on 1.21.111)
- Java 17+

## Installation

Drop `CubeScale.jar` into your server's `plugins` folder and restart.

## Configuration

After the first run, edit `plugins/CubeScale/config.yml`:

```yaml
# Nether portal coordinate ratio
#   8 = vanilla (1 Nether block = 8 Overworld blocks)
#   2 = 1 Nether block = 2 Overworld blocks
#   1 = 1:1 (same coordinates in both dimensions)
ratio: 8
```

Restart the server after changing the ratio.

## Building from Source

```
mvn clean package
```

The compiled jar will be in `target/CubeScale.jar`.
