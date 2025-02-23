package edu.ucne.registrotecnicos.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrotecnicos.data.local.database.AdministracionDb
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule{
    @Provides
    @Singleton
    fun provideTecnicoDb(@ApplicationContext context: Context): AdministracionDb {
        return Room.databaseBuilder(
            context, AdministracionDb::class.java, "Administracion.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTecnicoDao(administracionDb: AdministracionDb) = administracionDb.tecnicoDao()

    @Provides
    fun provideTicketDao(administracionDb: AdministracionDb) = administracionDb.ticketDao()

    @Provides
    fun provideMessageDao(administracionDb: AdministracionDb) = administracionDb.mensajeDao()

    @Provides
    fun provideArticuloDao(administracionDb: AdministracionDb) = administracionDb.articuloDao()
}