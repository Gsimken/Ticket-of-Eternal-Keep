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

In case you want users to have the texture, you can use the texture pack from the following [release](https://github.com/Gsimken/Ticket-of-Eternal-Keep/releases/tag/V1.1.0) on the server.

## Commands
ToEK has a command that allows an operator, or whoever has the `toek.command.give` permission, to generate a ticket at will. This ticket can be given to the player who invokes the command using `/toek give` or to another player using `/toek give playerName`.

For compatibility with existing servers, `/getticket` and `/getticket playerName` are still available as legacy aliases. The old `toek.command.getticket` permission is also accepted.

The config can be reloaded without restarting the client or server using `/toek reload`. This command requires operator level 2 or the `toek.command.reload` permission.

## Future Work
The development team of the "Ticket of Eternal Keeping" mod is continuously working on improvements and expansions. The roadmap for future updates includes:

- ~~**Textures for Clients with the Mod Installed**: The team is looking into allowing clients with the mod installed to see a unique texture for the "Ticket of Eternal Keeping".~~
- ~~**Item Configurability**: Plans are in place to make the item used for the "Ticket of Eternal Keeping" configurable, allowing server administrators or players to customize the specific item to be used.~~
- ~~**Customization of Name and Description**: Future versions aim to enable the customization of the item's name and description through configuration files.~~
- ~~**Adjustable Locations and Probabilities**: Features that allow the adjustment of locations and probabilities for finding the "Ticket of Eternal Keeping" in various game structures are intended to be implemented.~~
- **Configurable Crafting**: An option to enable or disable the crafting of the item within the game is planned.

Contributions are always welcome. If you have ideas or want to contribute to the code, feel free to fork the repository and submit your pull requests.
