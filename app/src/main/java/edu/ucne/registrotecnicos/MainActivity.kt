package edu.ucne.registrotecnicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Upsert
import edu.ucne.registrotecnicos.ui.theme.RegistroTecnicosTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var tecnicoDb: TecnicoDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        tecnicoDb = Room.databaseBuilder(
            applicationContext,
            TecnicoDb::class.java,
            "Tecnico.db"
        ).fallbackToDestructiveMigration()
            .build()

        setContent {
            RegistroTecnicosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerpadding)
                    ) {

                    }
                }
            }
        }
    }


    @Entity(tableName = "Tecnicos")
    data class TecnicoEntity(
        @PrimaryKey
        val tecnicoId: Int? = null,
        val nombres: String = "",
        val sueldo: Double = 0.0
    )

    @Dao
    interface TecnicoDao {
        @Upsert()
        suspend fun save(tecnico: TecnicoEntity)

        @Query(
            """
            SELECT * FROM Tecnicos
            WHERE tecnicoId=:id
            LIMIT 1
        """
        )
        suspend fun find(id: Int): TecnicoEntity?

        @Delete
        suspend fun delete(tecnico: TecnicoEntity)

        @Query("SELECT * FROM Tecnicos")
        fun getAll(): Flow<List<TecnicoEntity>>
    }

    @Database(
        entities = [
            TecnicoEntity::class
        ],
        version = 4,
        exportSchema = false
    )

    abstract class TecnicoDb : RoomDatabase() {
        abstract fun tecnicoDao() : TecnicoDao
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun Mostrar(){
        RegistroTecnicosTheme {
            /*val tecnicoList = listOf(
                TecnicoEntity(1, "DjMarte", "5000"),
                TecnicoEntity(2, "Jorge", "2500")
            )
            TecnicoListScreen(tecnicoList)

             */
        }
    }
}


