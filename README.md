VisualMod for Minecraft 1.21.4 (Fabric)

This repository contains a client-side Fabric mod written in Java that implements several visual and utility features for singleplayer / local testing:

- TargetESP: renders a rotating square around nearby players.
- Custom Fog: simple sky/fog overlay effect.
- FreeLook: rotate camera without rotating the player while holding a key.
- AutoSwap: on key press, swaps matching talismans/spheres into a hotbar slot.

Important: This mod is for singleplayer/local use and educational purposes. I will not help bypass server rules or anti-cheat. Use at your own risk on multiplayer servers.

Build: This project includes a Gradle + Fabric Loom scaffold. To build a jar, run the Gradle wrapper or Gradle installed on your machine. If you don't have a wrapper, generate it with `gradle wrapper`.

Example build commands:

```bash
./gradlew build
```

On Windows PowerShell/cmd use `gradlew.bat` instead:

```powershell
gradlew.bat build
```

How to use
1. Place the generated mod jar from `build/libs/` into your `mods` folder for a Fabric 1.21.4 client.
2. In-game, open Controls and assign keys for the mod features (or use defaults):
   - Toggle TargetESP: `O` (default)
   - Toggle Custom Fog: `P` (default)
   - FreeLook (hold): `F` (default)
   - AutoSwap (press): `G` (default)

Notes
- AutoSwap searches the inventory for items whose translation key contains `talisman` or `sphere` and swaps the first found into the selected hotbar slot. It performs client-side inventory modification; server sync behavior depends on server settings.

Next steps
- If you want a full Gradle scaffold (`build.gradle`, `fabric.mod.json` metadata ready-to-build), tell me and I'll add it.