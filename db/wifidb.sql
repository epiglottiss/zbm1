
/* Drop Tables */

DROP TABLE [History];
DROP TABLE [WiFi];




/* Create Tables */

CREATE TABLE [History]
(
	[id] integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	[x_coord] real,
	[y_coord] real,
	[search_date] text
);


CREATE TABLE [WiFi]
(
	[id] text NOT NULL,
	[goo] text,
	[name] text,
	[dorojuso] text,
	[detailjuso] text,
	[floor] text,
	[install_type] text,
	[gigwan] text,
	[service_guboon] text,
	[mang_type] text,
	[install_year] integer,
	[in_out_door] text,
	[wifi_environment] text,
	[y_coord] real,
	[x_coord] real,
	[work_date] text,
	PRIMARY KEY ([id])
);



