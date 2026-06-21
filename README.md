# Ticket of Eternal Keeping Mod for Minecraft

## Description
This Minecraft mod introduces a new item to the game: the "Ticket of Eternal Keeping". This special item allows players to keep their inventory items upon death. The ticket is consumed in the process, making it a valuable and strategic resource.

## Locations and Probabilities
The "Ticket of Eternal Keeping" can be found in structures and can also drop from configured mobs. Every probability is configurable via the config file.

| Source          | Default Probability | Configurable |
|-----------------|---------------------|--------------|
| Ancient City    | 10%                 | Yes          |
| Mineshaft       | 3%                  | Yes          |
| Stronghold      | 5%                  | Yes          |
| End Cities      | 5%                  | Yes          |
| Bastions        | 15%                 | Yes          |
| Other Chests    | 0.5%                | Yes          |
| Common Mobs     | 0%                  | Yes          |
| Other Mobs      | 0%                  | Yes          |

**Note:** All probabilities can be customized in the `ToEK.json` config file. See the [Config](#config) section for details.

## Config
The ToEK item is configurable. The file is located in the `.minecraft/config` folder, or `server_folder/config` for servers, and is named `ToEK.json`.

```json
{
  "item": "minecraft:paper",
  "name": "&6Ticket of Eternal Keeping",
  "lore": [
    "&bThis ticket allows whoever carries it",
    "&bin the inventory to keep their items when they die.",
    "",
    "&4&lIt is consumed at death"
  ],
  "CustomModelDataNumber": 506,
  "lootTableProbabilities": {
    "minecraft:chests/ancient_city": 0.1,
    "minecraft:chests/abandoned_mineshaft": 0.03,
    "minecraft:chests/stronghold_library": 0.05,
    "minecraft:chests/stronghold_corridor": 0.05,
    "minecraft:chests/stronghold_crossing": 0.05,
    "minecraft:chests/end_city_treasure": 0.05,
    "minecraft:chests/bastion_bridge": 0.15,
    "minecraft:chests/bastion_hoglin_stable": 0.15,
    "minecraft:chests/bastion_other": 0.15,
    "minecraft:chests/bastion_treasure": 0.15,
    "minecraft:chests/nether_bridge": 0.05
  },
  "mobLootTableProbabilities": {
    "minecraft:entities/zombie": 0.0,
    "minecraft:entities/skeleton": 0.0,
    "minecraft:entities/creeper": 0.0,
    "minecraft:entities/spider": 0.0,
    "minecraft:entities/enderman": 0.0,
    "minecraft:entities/blaze": 0.0,
    "minecraft:entities/wither_skeleton": 0.0
  },
  "genericChestProbability": 0.005,
  "genericMobProbability": 0.0
}
```

### Configurable Fields

**Item Configuration:**
- `item`: Allows you to change the item that the game uses for the ticket. Changing this removes the client texture, and the chosen item can still be used for recipes or normal interactions.
- `name`: Changes the name of the item. Accepts `&` for color codes, such as `&6` for gold or `&b` for aqua.
- `lore`: Changes the item description. Add as many lines as you like inside the list. Also accepts `&` for color codes.
- `CustomModelDataNumber`: Number used for a custom texture pack. This can be used to force a texture pack from the server so players see the desired item texture.

**Loot Table Probabilities:**
- `lootTableProbabilities`: A map that configures the probability of finding the ticket in specific non-mob loot tables. Values range from `0.0` (never) to `1.0` (always/100%).
  - You can add or remove any loot table by its identifier, such as `"minecraft:chests/ancient_city"`.
  - To disable a loot table, set its probability to `0.0` or remove it from the map.
  - The default probabilities are shown in the example above.

- `genericChestProbability`: The probability for the ticket to appear in any chest that does not have a specific entry in `lootTableProbabilities`. Set to `0.005` (0.5%) by default. Set to `0.0` to disable tickets in generic chests.

- `mobLootTableProbabilities`: A map that configures the probability of mobs dropping the ticket. Use entity loot table identifiers such as `"minecraft:entities/zombie"`, `"minecraft:entities/enderman"`, or `"minecraft:entities/wither_skeleton"`.
  - Values range from `0.0` (never) to `1.0` (always/100%).
  - To disable a mob, set its probability to `0.0` or remove it from the map.
  - You can add any mob loot table by its identifier.

- `genericMobProbability`: The probability for the ticket to drop from any mob that does not have a specific entry in `mobLootTableProbabilities`. Set to `0.0` by default so generic mob drops are disabled unless you enable them.

## Installation
To install this mod, simply download the `.jar` file and place it in your Minecraft mods folder.
This mod is designed to work on the server side. It provides a convenient solution for server administrators who want to enhance the gameplay experience without requiring players to install additional mods.

### Server-Side Resource Pack

The mod can run server-side only. Players using a vanilla client can join and use the ticket without installing the mod, but they will see the configured base item, usually paper, unless they load the resource pack.

Release builds include a resource pack zip named `Ticket_of_Eternal_Keep_Resource_Pack_<minecraft-version>.zip`. This pack is only needed when running the mod server-side and you want vanilla clients to see the custom ticket texture.

To use it on a server:
- Upload the resource pack zip somewhere clients can download it.
- Set `resource-pack=<direct-download-url>` in `server.properties`.
- Optionally set `require-resource-pack=true` if players must use the texture.
- Keep `CustomModelDataNumber` as `506`, or update the resource pack if you change it.

If players install the mod on their client, the same client assets are already bundled in the mod jar.

## Commands
ToEK commands require operator level 2 by default. Servers with a Fabric-compatible permissions mod, such as LuckPerms with Fabric Permission API support, can grant the permission nodes listed below instead.

| Command | Permission | Description |
|---------|------------|-------------|
| `/toek give` | `ticket-of-eternal-keep:command.give` | Gives one ticket to the player running the command. |
| `/toek give <players>` | `ticket-of-eternal-keep:command.give` | Gives one ticket to one or more players. Supports vanilla selectors such as `@a`, `@p`, and player names. |
| `/getticket` | `ticket-of-eternal-keep:command.give` | Legacy alias for `/toek give`. |
| `/getticket <players>` | `ticket-of-eternal-keep:command.give` | Legacy alias for `/toek give <players>`. |
| `/toek reload` | `ticket-of-eternal-keep:command.reload` | Reloads `ToEK.json` without restarting the client or server. |
| `/toek config name <name>` | `ticket-of-eternal-keep:command.config` | Saves a new ticket display name. Accepts `&` formatting codes. |
| `/toek config lore set <line1\|line2\|...>` | `ticket-of-eternal-keep:command.config` | Replaces the ticket lore. Separate lore lines with `|`. Accepts `&` formatting codes. |
| `/toek config lore add <line>` | `ticket-of-eternal-keep:command.config` | Adds one lore line. Accepts `&` formatting codes. |
| `/toek config lore clear` | `ticket-of-eternal-keep:command.config` | Clears the configured lore. |

Examples:

```mcfunction
/toek config name &6Ticket of Eternal Keeping
/toek config lore set &bKeep your inventory on death|&4&lConsumed when used
/toek reload
```

## Future Work
The development team of the "Ticket of Eternal Keeping" mod is continuously working on improvements and expansions. The roadmap for future updates includes:

- ~~**Textures for Clients with the Mod Installed**: The team is looking into allowing clients with the mod installed to see a unique texture for the "Ticket of Eternal Keeping".~~
- ~~**Item Configurability**: Plans are in place to make the item used for the "Ticket of Eternal Keeping" configurable, allowing server administrators or players to customize the specific item to be used.~~
- ~~**Customization of Name and Description**: Future versions aim to enable the customization of the item's name and description through configuration files.~~
- ~~**Adjustable Locations and Probabilities**: Features that allow the adjustment of locations and probabilities for finding the "Ticket of Eternal Keeping" in various game structures are intended to be implemented.~~
- **Configurable Crafting**: An option to enable or disable the crafting of the item within the game is planned.

Contributions are always welcome. If you have ideas or want to contribute to the code, feel free to fork the repository and submit your pull requests.
