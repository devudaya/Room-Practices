package com.example.roomarchitecturecomponents

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomarchitecturecomponents.daos.NoteDao
import com.example.roomarchitecturecomponents.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Database(entities = [Note::class], version = 2)
abstract class NoteDatabase : RoomDatabase() {

    companion object {

        private var dataBaseInstance: NoteDatabase? = null

        // get the instance of Note Database
        fun getInstance(context: Context): NoteDatabase {

            dataBaseInstance.let {
                dataBaseInstance = if (it == null)
                    Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "note_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                else dataBaseInstance!!
                return dataBaseInstance!!

            }
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                runBlocking {
                    launch(Dispatchers.IO) {
                        dataBaseInstance!!.getDao().insertNote(
                            Note(
                                "Title 1",
                                "Description 1", 1
                            )
                        )
                        dataBaseInstance!!.getDao().insertNote(
                            Note(
                                "Title 2",
                                "Description 2", 2
                            )
                        )
                        dataBaseInstance!!.getDao().insertNote(
                            Note(
                                "Title 3",
                                "Description 3", 3
                            )
                        )
                    }
                }
            }
        }
    }

    abstract fun getDao(): NoteDao

}