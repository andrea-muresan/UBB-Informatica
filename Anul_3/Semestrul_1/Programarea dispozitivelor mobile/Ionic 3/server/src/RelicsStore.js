import { open } from 'sqlite';

import sqlite3 from 'sqlite3';

export class RelicStore {
    constructor({ filename }) {
        this.filename = filename;
    }

    async init() {
        this.db = await open({
            filename: this.filename,
            driver: sqlite3.Database,
        });

        await this.db.run(`
            CREATE TABLE IF NOT EXISTS relics (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                location TEXT,
                dateInStock TEXT,
                isCursed INTEGER,
                price NUMERIC,
                userId TEXT,
                photo TEXT,
                lat REAL,
                lng REAL
            );
        `);
    }

    async find(props) {
        const { userId } = props;
        return this.db.all('SELECT * FROM relics WHERE userId = ?', [userId]);
    }

    async findOne(props) {
        const { id } = parseInt(props.id);
        return this.db.get('SELECT * FROM relics WHERE id = ?', [id]);
    }

    async insert(relic) {
        if (!relic.name || !relic.location || !relic.userId) {
            throw new Error("Title, Author, and UserId are required");
        }

        const { name, location, dateInStock, isCursed, price, userId, photo, lat, lng } = relic;

        await this.db.run(
            'INSERT INTO relics (name, location, dateInStock, isCursed, price, userId, photo, lat, lng) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)',
            [name, location, dateInStock, isCursed, price, userId, photo, lat, lng]
        );

        const { lastID } = await this.db.get('SELECT last_insert_rowid() as lastID');
        relic.id = lastID.toString();
        return relic;
    }

    async update(props, relic) {
        const { name, location, dateInStock, isCursed, price, photo, lat, lng } = relic;
        console.log("^^^^^^^^^^^^^^^^^^^^" + {photo});
        await this.db.run('UPDATE relics SET name = ?, location = ?, dateInStock = ?, isCursed = ?, price = ?, photo = ?, lat = ?, lng = ? WHERE id = ?',
            [name, location, dateInStock, isCursed, price, photo, lat, lng, props.id]);
        return 1;
    }

    async remove(props) {
        const { id } = parseInt(props.id);
        await this.db.run('DELETE FROM relics WHERE id = ?', [id]);
    }
}

const relicStore = new RelicStore({ filename: './db/relics.db' });
await relicStore.init();

export { relicStore };
