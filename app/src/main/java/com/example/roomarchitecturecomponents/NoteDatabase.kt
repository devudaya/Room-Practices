package com.example.roomarchitecturecomponents

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomarchitecturecomponents.daos.NoteDao
import com.example.roomarchitecturecomponents.entities.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getDao(): NoteDao

    companion object {

        private var dataBaseInstance: NoteDatabase? = null

        // get the instance of Note Database
        fun getInstance(context: Context, scope: CoroutineScope): NoteDatabase {

            return dataBaseInstance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "note_database"
                )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this codelab.
                        .fallbackToDestructiveMigration()
                        .addCallback(NoteDbCallBack(scope))
                        .build()
                dataBaseInstance = instance
                // return instance
                instance

            }
        }

        private class NoteDbCallBack(var scope: CoroutineScope) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                dataBaseInstance?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.getDao())
                    }
                }
            }
        }

        fun populateDatabase(note: NoteDao) {

            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            note.deleteAllNotes()
            note.insertNote(Note("Title 1", "DES1", 1))
            note.insertNote(Note("Title 2", "DES2", 2))
            note.insertNote(Note("Title 3", "DES3", 3))

        }
    }
}