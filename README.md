# Ticket of Eternal Keeping Mod for Minecraft

## Description
This Minecraft mod introduces a new item to the game: the "Ticket of Eternal Keeping". This special item allows players to keep their inventory items upon death. The ticket is consumed in the process, making it a valuable and strategic resource.

## Locations and Probabilities
The "Ticket of Eternal Keeping" can be found in various structures within the game, each with different appearance probabilities:

| Structure       | Appearance Probability |
|-----------------|------------------------|
| Ancient City    | 10%                    |
| Mineshaft       | 3%                     |
| Stronghold      | 5%                     |
| End Cities      | 5%                     |
| Bastions        | 15%                    |
| Villages        | 0.5%                   |
| Ruined Portals  | 0.5%                   |
| Shipwrecks      | 0.5%                   |

## Config
the Toek item is configurable, the file is located in the folder .minecraft/config (or server_folder/config for servers) and is named ToEK.json
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
  "CustomModelDataNumber": 506
}
```
In this file there are some configurable fields, it also accepts '&' for color codes.

`item`: allows you to change the item that the game uses for the ticket; however, doing this removes the textures in the client, it is important to know that the chosen item can still be used for recipes and fulfill its functions, for example, choosing a block and using the right click can cause the object to be lost.

`name`: change the name

`lore`: is the description of the item, it accepts as many lines as you like, just add a new one between the square brackets ('[')

`CustomModeDataNumber`: number used for custom texture pack, with this it is possible to force a texture pack from the server to the players so they can have a desired texture for the item.


## Installation
To install this mod, simply download the `.jar` file and place it in your Minecraft mods folder.
This mod is designed to work on the server side. It provides a convenient solution for server administrators who want to enhance the gameplay experience without requiring players to install additional mods.


In case you want users to have texture you can use the texture pack from the following [release](https://github.com/Gsimken/Ticket-of-Eternal-Keep/releases/tag/V1.1.0) on the server

## Commands
Toek has a command that allows an operator or whoever has the `toek.command.getticket` permission to generate a ticket at will, this ticket can be given to the player who invokes the command using `/getticket` or to another player using `/getticket playerName`.

## Future Work
The development team of the "Ticket of Eternal Keeping" mod is continuously working on improvements and expansions. The roadmap for future updates includes:

-  ~~**Textures for Clients with the Mod Installed**: The team is looking into allowing clients with the mod installed to see a unique texture for the "Ticket of Eternal Keeping".~~
- ~~**Item Configurability**: Plans are in place to make the item used for the "Ticket of Eternal Keeping" configurable, allowing server administrators or players to customize the specific item to be used.~~
- ~~**Customization of Name and Description**: Future versions aim to enable the customization of the item's name and description through configuration files.~~
- **Configurable Crafting**: An option to enable or disable the crafting of the item within the game is planned.
- **Adjustable Locations and Probabilities**: Features that allow the adjustment of locations and probabilities for finding the "Ticket of Eternal Keeping" in various game structures are intended to be implemented.

Contributions are always welcome. If you have ideas or want to contribute to the code, feel free to fork the repository and submit your pull requests.

