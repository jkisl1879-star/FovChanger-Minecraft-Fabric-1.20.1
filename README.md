# Fov Changer 1.0.0

A Fabric mod for Minecraft 1.20.1 that allows dynamic control over the Field of View.

**Author:** jkisl1879-star

## Features
* Adjust FOV in-game using customizable keybinds (default: `+` / `-`).
* Set custom step size (supports decimals).
* Bypass vanilla FOV limits (30-120) via "Unlimited" mode.
* Synchronizes with the vanilla FOV settings.
* Auto-saves to `config/fovchanger.json`.
* Supports English and Russian languages.

## Requirements
* Minecraft 1.20.1
* Fabric Loader (0.16.14+)
* Fabric API
* Mod Menu (Optional, for in-game config screen)

## Installation
1. Install Fabric Loader for 1.20.1.
2. Place Fabric API, Mod Menu, and `FovChanger-1.0.0.jar` into your `mods` folder.
3. Launch the game.

## Building
Requires Java 17.
```bash
./gradlew build