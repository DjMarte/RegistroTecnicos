package edu.ucne.registrotecnicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registrotecnicos.ui.theme.RegistroTecnicosTheme
import kotlinx.coroutines.flow.Flow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroTecnicosTheme {

            }
        }
    }
}


@Entity(tableName = "Tecnicos")
data class TecnicoEntity(
    @PrimaryKey
    val tecnicoId: Int? = null,
    val nombres: String = "",
    val sueldo: Double
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
    suspend fun Delete(tecnico: TecnicoEntity)

    @Query("SELECT * FROM Tecnicos")
    fun getAll(): Flow<List<TecnicoEntity>>

}