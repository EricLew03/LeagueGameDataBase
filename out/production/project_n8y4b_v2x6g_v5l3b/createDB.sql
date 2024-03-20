drop table gameMode cascade constraints;
drop table mapDetermines cascade constraints;
drop table playerStats cascade constraints;
drop table playerEcon cascade constraints;
drop table ownsItem cascade constraints;
drop table turret cascade constraints;
drop table turretStats cascade constraints;
drop table turretDamage cascade constraints;
drop table nexus cascade constraints;
drop table inhibitor cascade constraints;
drop table baronJungleObjective cascade constraints;
drop table dragonJungle cascade constraints;
drop table dragonType cascade constraints;

CREATE TABLE gameMode (
    gamemodeName VARCHAR(20) PRIMARY KEY,
    maxPartySize INTEGER,
    canBan INTEGER
);

CREATE TABLE mapDetermines (
    mapID INT PRIMARY KEY,
    mapName VARCHAR(20),
    numberOfLanes INTEGER,
    gamemodeName VARCHAR(20),
    FOREIGN KEY (gamemodeName) REFERENCES gameMode(gamemodeName) ON DELETE CASCADE
);

CREATE TABLE playerEcon (
    creepScore INTEGER,
    kills INTEGER,
    gold INTEGER,
    playerLevel INTEGER,
    PRIMARY KEY (creepScore, kills)
);

CREATE TABLE playerStats (
    playerID INTEGER PRIMARY KEY,
    champID INTEGER UNIQUE,
    championName VARCHAR(20),
    manaPoints INTEGER,
    healthPoints INTEGER,
    creepScore INTEGER,
    kills INTEGER,
    rank VARCHAR(20),
    mapID INTEGER,
    FOREIGN KEY (mapID) REFERENCES mapDetermines(mapID) ON DELETE CASCADE,
	FOREIGN KEY (creepScore, kills) REFERENCES playerEcon(creepScore, kills) ON DELETE CASCADE
);

CREATE TABLE ownsItem (
    playerID INTEGER,
    itemName VARCHAR(20),
    mr INTEGER,
    ad INTEGER,
    ap INTEGER,
    armor INTEGER,
    cost INTEGER,
    PRIMARY KEY (playerID, itemName),
    FOREIGN KEY (playerID) REFERENCES playerStats(playerID) ON DELETE CASCADE
);

CREATE TABLE turretStats (
    structureLocation VARCHAR(20) PRIMARY KEY,
    healthPoints INTEGER
);

CREATE TABLE turretDamage (
    structureLocation VARCHAR(20) PRIMARY KEY,
    damage INTEGER,
    FOREIGN KEY(structureLocation) REFERENCES turretStats(structureLocation) ON DELETE CASCADE
);

CREATE TABLE turret (
    structureID INTEGER PRIMARY KEY,
    structureLocation VARCHAR(20),
    mapID INTEGER,
    playerID INTEGER,
    FOREIGN KEY (mapID) REFERENCES mapDetermines(mapID) ON DELETE CASCADE,
    FOREIGN KEY (playerID) REFERENCES playerStats(playerID) ON DELETE CASCADE,
	FOREIGN KEY(structureLocation) REFERENCES turretStats(structureLocation) ON DELETE CASCADE
);

CREATE TABLE nexus (
    structureID INTEGER PRIMARY KEY,
    healthPoints INTEGER,
    structureLocation VARCHAR(20),
    vulnerable INTEGER,
    mapID INTEGER,
    playerID INTEGER,
    FOREIGN KEY (mapID) REFERENCES mapDetermines(mapID) ON DELETE CASCADE,
    FOREIGN KEY (playerID) REFERENCES playerStats(playerID) ON DELETE CASCADE
);

CREATE TABLE inhibitor (
    structureID INTEGER PRIMARY KEY,
    healthPoints INTEGER,
    structureLocation VARCHAR(20),
    respawnTime INTEGER,
    mapID INTEGER,
    playerID INTEGER,
    FOREIGN KEY (mapID) REFERENCES mapDetermines(mapID) ON DELETE CASCADE,
    FOREIGN KEY (playerID) REFERENCES playerStats(playerID) ON DELETE CASCADE
);

CREATE TABLE baronJungleObjective (
    jungleObjectiveID INTEGER PRIMARY KEY,
    healthPoints INTEGER,
    effectTime INTEGER, 
    mapID INTEGER,
    FOREIGN KEY (mapID) REFERENCES mapDetermines(mapID) ON DELETE CASCADE
);

CREATE TABLE dragonType (
    dragonType VARCHAR(20) PRIMARY KEY,
    healthPoints INTEGER 
);

CREATE TABLE dragonJungle (
    jungleObjectiveID INTEGER PRIMARY KEY,
    dragonType VARCHAR(20),
    mapID INTEGER,
    FOREIGN KEY (mapID) REFERENCES mapDetermines(mapID) ON DELETE CASCADE,
	FOREIGN KEY (dragonType) REFERENCES dragonType(dragonType) ON DELETE CASCADE
);

INSERT INTO gameMode VALUES ('Ranked Solo/Duo', 2, 1);
INSERT INTO gameMode VALUES ('Normal Draft', 5, 1);
INSERT INTO gameMode VALUES ('ARAM', 5, 0);
INSERT INTO gameMode VALUES ('Blitz', 5, 1);
INSERT INTO gameMode VALUES ('Dominion', 5, 0);

INSERT INTO mapDetermines VALUES (1, 'Summoner''s Rift', 3, 'Ranked Solo/Duo');
INSERT INTO mapDetermines VALUES (2, 'Howling Abyss', 3, 'Normal Draft');
INSERT INTO mapDetermines VALUES (3, 'Twisted Treeline', 1, 'ARAM');
INSERT INTO mapDetermines VALUES (4, 'Nexus Blitz', 2, 'Blitz');
INSERT INTO mapDetermines VALUES (5, 'The Crystal Scar', 3, 'Dominion');

INSERT INTO playerEcon VALUES (150, 5, 3200, 12);
INSERT INTO playerEcon VALUES (120, 7, 4300, 15);
INSERT INTO playerEcon VALUES (180, 8, 2500, 10);
INSERT INTO playerEcon VALUES (160, 6, 3800, 13);
INSERT INTO playerEcon VALUES (140, 9, 5200, 18);

INSERT INTO playerStats VALUES (1, 123, 'Ahri', 500, 800, 150, 5, 'Gold', 1);
INSERT INTO playerStats VALUES (2, 234, 'Darius', 300, 1000, 120, 7, 'Silver', 1);
INSERT INTO playerStats VALUES (3, 345, 'Ezreal', 600, 750, 180, 8, 'Platinum', 2);
INSERT INTO playerStats VALUES (4, 456, 'Jinx', 400, 900, 160, 6, 'Gold', 2);
INSERT INTO playerStats VALUES (5, 567, 'Yasuo', 200, 850, 140, 9, 'Diamond', 3);

INSERT INTO ownsItem VALUES (1, 'Banshee''s Veil', 60, 0, 0, 45, 2900);
INSERT INTO ownsItem VALUES (2, 'Infinity Edge', 0, 70, 0, 0, 3400);
INSERT INTO ownsItem VALUES (3, 'Rabadon''s Deathcap', 0, 0, 120, 0, 3800);
INSERT INTO ownsItem VALUES (4, 'Sunfire Cape', 0, 0, 0, 60, 2900);
INSERT INTO ownsItem VALUES (5, 'Zhonya''s Hourglass', 45, 0, 75, 0, 2600);

INSERT INTO turretStats VALUES ('Top Lane Outer', 5000);
INSERT INTO turretStats VALUES ('Mid Lane Outer', 4500);
INSERT INTO turretStats VALUES ('Bot Lane Inner', 4000);
INSERT INTO turretStats VALUES ('Base Bot', 4800);
INSERT INTO turretStats VALUES ('Base Nexus Bot', 5200);

INSERT INTO turretDamage VALUES ('Top Lane Outer', 200);
INSERT INTO turretDamage VALUES ('Mid Lane Outer', 180);
INSERT INTO turretDamage VALUES ('Bot Lane Inner', 220);
INSERT INTO turretDamage VALUES ('Base Bot', 240);
INSERT INTO turretDamage VALUES ('Base Nexus Bot', 210);

INSERT INTO turret VALUES (1, 'Top Lane Outer', 1, 1);
INSERT INTO turret VALUES (2, 'Mid Lane Outer', 2, 2);
INSERT INTO turret VALUES (3, 'Bot Lane Inner', 2, 3);
INSERT INTO turret VALUES (4, 'Base Bot', 1, 4);
INSERT INTO turret VALUES (5, 'Base Nexus Bot', 4, 5);

INSERT INTO nexus VALUES (1, 10000, 'Blue Base', 1, 1, 1);
INSERT INTO nexus VALUES (2, 10000, 'Red Base', 1, 1, 4);
INSERT INTO nexus VALUES (3, 10000, 'Blue Base', 1, 2, 2);
INSERT INTO nexus VALUES (4, 10000, 'Red Base', 1, 2, 3);
INSERT INTO nexus VALUES (5, 10000, 'Blue Base', 1, 4, 5);

INSERT INTO inhibitor VALUES (1, 5000, 'Blue Top', 300, 1, 1);
INSERT INTO inhibitor VALUES (2, 5000, 'Blue Mid', 300, 1, 1);
INSERT INTO inhibitor VALUES (3, 5000, 'Blue Bot', 300, 1, 1);
INSERT INTO inhibitor VALUES (4, 5000, 'Red Top', 300, 1, 3);
INSERT INTO inhibitor VALUES (5, 5000, 'Red Mid', 300, 1, 3);
INSERT INTO inhibitor VALUES (6, 5000, 'Red Bot', 300, 1, 3);

INSERT INTO baronJungleObjective VALUES (1, 6400, 180, 1);
INSERT INTO baronJungleObjective VALUES (2, 6400, 180, 1);
INSERT INTO baronJungleObjective VALUES (3, 6400, 180, 1);
INSERT INTO baronJungleObjective VALUES (4, 6400, 180, 1);
INSERT INTO baronJungleObjective VALUES (5, 6400, 180, 1);

INSERT INTO dragonType VALUES ('Infernal', 4090);
INSERT INTO dragonType VALUES ('Ocean', 5730);
INSERT INTO dragonType VALUES ('Mountain', 5730);
INSERT INTO dragonType VALUES ('Cloud', 5730);
INSERT INTO dragonType VALUES ('Elder', 6400);

INSERT INTO dragonJungle VALUES (1, 'Infernal', 1);
INSERT INTO dragonJungle VALUES (2, 'Ocean', 1);
INSERT INTO dragonJungle VALUES (3, 'Mountain', 1);
INSERT INTO dragonJungle VALUES (4, 'Cloud', 1);
INSERT INTO dragonJungle VALUES (5, 'Elder', 1);