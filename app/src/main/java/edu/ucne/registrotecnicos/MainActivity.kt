package edu.ucne.registrotecnicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import edu.ucne.registrotecnicos.data.local.database.AdministracionDb
import edu.ucne.registrotecnicos.data.repository.TecnicoRepository
import edu.ucne.registrotecnicos.data.repository.TicketRepository
import edu.ucne.registrotecnicos.navigation.AdministracionNavHost
import edu.ucne.registrotecnicos.ui.theme.RegistroTecnicosTheme

class MainActivity : ComponentActivity() {
    private lateinit var tecnicoRepository: TecnicoRepository
    private lateinit var ticketRepository: TicketRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val administracionDb = Room.databaseBuilder(
            applicationContext,
            AdministracionDb::class.java,
            "Administracion.db"
        ).fallbackToDestructiveMigration()
            .build()
        tecnicoRepository = TecnicoRepository(administracionDb)
        ticketRepository = TicketRepository(administracionDb)
        setContent {
            RegistroTecnicosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerpadding)
                    ) {
                        AdministracionNavHost(rememberNavController(), tecnicoRepository, ticketRepository)
                    }
                }
            }
        }
    }


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun Mostrar() {
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


