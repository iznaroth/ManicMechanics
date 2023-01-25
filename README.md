##### Manic Mechanics

(The name is still subject to change.)

Manic Mechanics is a full-scale "tech" mod for minecraft - adding several machinelike blocks which automate tasks and create unique new challenges to pursue. 
It is currently in a tremendously early state more-oriented towards sketching out broader systems. You can expect instability, placeholder assets, inconsistent balance 
and very volatile design - it will change frequently.

##### What's in it?

The roadmap for Manic Mechanics is unnecessarily large and poorly-scoped. For now, I am focusing on what I call Content Phase I, which contains a fairly self-contained
progression arc through the fundamental mechanics of MM. By my estimate, it's around halfway complete - the baseline systems are nearly all there, it's just loads
of boilerplate, debugging and polish. 

Though the progression itself is rudimentary and poorly-telegraphed, the following features are partially-or-mostly implemeted as-is.
- A rudimentary in-game economy allowing the automation of buying and selling items using the Importer and Exporter, and a personal finance system to measure successes.
- Four new core resources generated in-world: Dyspersium, Phosphorite, Thallite and Nitrol. Each has unique properties from positional audio cues to volatile harvesting procedures.
- Special resource entities that spawn in remote locations, called Pinches - which can be harvested to power strange machinery.
- A patchouli-driven tutorial book that ideally guides through resource and machine progression
- Four core production machines: the Infuser, Condenser, Assembler and Etcher - each automatable and carrying unique shortcut recipes for vanilla as well as new processing chains.
- A logistic network system using Tubes for fluid, items, energy and some yet-to-be-implemented materials. Tubes can be stuffed into the same block and seamlessly update their subnetworks,
allowing for more space-efficient automation setup. Tubes are currently a bit buggy due to issues in graph-network traversal - this is not a high priority fix until the remainder of Content Phase I is completed.
- Power generation, storage and transfer.
- A skeletal "suspicion" system that tracks certain actions and can potentially mount an invasion on their player if they commit too many so-called illegal acts.
- Rich Resources, allowing for a difficult but infinite source of special resources for continuous production chains - and four machines designed to harvest these resources.
- Four unusual crystals that spawn in specific areas and can harness unique energy from the presence of in-world events.

...and a lot of under-the-hood BS in preparation for other stuff. This will change faster than I update this list, so don't necessarily expect it to be up-to-date.

##### What's planned?

Content Phase I includes all above features and the following planned features:
- Compleded implemetation of the first wave of Rich Resources (8 of them, pairs assigned to certain biome climates)
- Meaningful customization, throttling and risk-factors for the Harvesting family of machines
- Completing the "Mania System", a mana-equivalent data structure that makes up the baseline for the magic production line.
- Gravity Heart & Gravity Furnace, allowing for collection of mana in crystals by-hand or automatically
- Crystalline Wizards near crystal cluster microbiomes and Node Pointers - the mana transfer network. Stonefinger Batteries as mana storage.
- Morbid Blackstone Enervator, allowing for the 'de-aspecting' of mana to be used elsewhere - the Low-Ritual Conversion Unit for energy or the Manic Manamulcher for Mania stepping
- Crystal production and automation machines: the Raktomic Crystallizer (production), the Chancellor's Holyforge (meltdown sidegrade to GF), and Focii (reaspect in-field))
- Morton's Theoretical Solidifier, the first multiblock (System impl already complete!!), allows the conversion of mana into manafluid for physical machine recipes.
- The Interpreter, a fifth production line machine that can hold NON-FLUID mana.
- Consumer Goods, a laundry-list of useless items and parts that are made from all the prior chains to sell on the market at a gigantic profit.
- World-generated Shrines with cryptic Golden Age Machines that cannot yet be used... (system complete!!)
- Research Synchronizers and Material Decryption Facilities, allowing for the decryption of golden age machinery into usable blueprints by providing Cartridges made from
expensive resources (system speculated, impl partway)
- UMC, second multiblock, takes researched blueprints to allow the crafting of better machines
- 8 new resource bolsterers and prod. machines from research: 
- A special tooltier accessed by researching drops from Luhrnnan enemies, scouting their ruins and recovering their parts - allows the movement of Rich Resources by hand
- A Comms multiblock allowing for employment, contracted sales operations and Permits to access adjacent realities.

That's content phase 1! It really, honestly will take a while. 
