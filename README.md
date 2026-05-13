# ResourcePack to Mod (RPtM)

This mod allows you to bundle a resource pack within a mod, making it a "built-in" pack.

## Features
- Registers a resource pack located inside the mod JAR.
- Visible in the in-game resource pack selection screen.
- Does not appear in the external `resourcepacks` folder.
- ~~Compatible with `respackopts` for configuration.~~

## How to use
1. Place your resource pack into `src/main/resources/resourcepacks/`.
2. Build the mod using `./gradlew build`.
3. The resulting JAR in `build/libs/` will contain your resource pack and the loader logic.
