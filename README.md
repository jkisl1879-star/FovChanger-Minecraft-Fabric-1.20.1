# Fov Changer 1.0.1

A Fabric mod for Minecraft 1.20.1 that allows dynamic control over the Field of View.

**Author:** jkisl1879-star

## Features
- Adjust FOV in-game using customizable keybinds (default: `+` / `-`).
- Set custom step size (supports decimals).
- Bypass vanilla FOV limits (30-120) via "Unlimited" mode.
- **Separate Viewmodel FOV control (adjust the FOV of your hand/held items independently).**
- Synchronizes with the vanilla FOV settings.
- Auto-saves to `config/fovchanger.json`.
- Supports English and Russian languages.

## Requirements
- Minecraft 1.20.1
- Fabric Loader (0.16.14+)
- Fabric API
- Mod Menu (Optional, for in-game config screen)

## Installation
1. Install Fabric Loader for 1.20.1.
2. Place Fabric API, Mod Menu, and `FovChanger-1.0.1.jar` into your `mods` folder.
3. Launch the game.

## Configuration
You can access the settings via **Mod Menu**. Available options:
- **Increase / Decrease Keybinds:** Change the keys used to adjust FOV.
- **Step Size:** Set how much the FOV changes per key press.
- **Unlimited FOV:** Toggle to bypass the 30-120 limits.
- **Viewmodel FOV:** Set a specific FOV value for your hand and items.
- **Custom VM FOV:** Enable or disable the custom viewmodel FOV override.

## Building
Requires Java 17.
```bash
./gradlew build