package edu.ucne.registrotecnicos.data.repository

import android.util.Log
import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.registrotecnicos.data.local.dao.ArticuloDao
import edu.ucne.registrotecnicos.data.local.entity.ArticuloEntity
import edu.ucne.registrotecnicos.data.remote.RemoteDataSource
import edu.ucne.registrotecnicos.data.remote.Resource
import edu.ucne.registrotecnicos.data.remote.dto.ArticuloDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ArticuloRepositoryTest {
    @Test
    fun `Should add an articulo`() = runTest {
        // Given
        val articulo = ArticuloDto(
            articuloId = 1,
            descripcion = "Articulo 1",
            costo = 10.0,
            ganancia = 20.0,
            precio = 30.0
        )

        val articuloRemoteDataSource = mockk<RemoteDataSource>()
        val articuloDao = mockk<ArticuloDao>()
        val repository = ArticuloRepository(articuloDao, articuloRemoteDataSource)

        coEvery { articuloRemoteDataSource.saveArticulo(any()) } returns articulo

        // When
        repository.save(articulo)

        // Then
        coVerify { articuloRemoteDataSource.saveArticulo(articulo) }
    }

    @Test
    fun `Should return an prioridad`() = runTest {
        //Given
        val articulo = ArticuloDto(
            articuloId = 1,
            descripcion = "Articulo 2",
            costo = 10.0,
            ganancia = 20.0,
            precio = 30.0
        )

        val articuloRemoteDataSource = mockk<RemoteDataSource>()
        val articuloDao = mockk<ArticuloDao>()
        val repository = ArticuloRepository(articuloDao, articuloRemoteDataSource)

        coEvery { articuloRemoteDataSource.getArticuloById(articulo.articuloId) } returns articulo

        //When
        repository.find(articulo.articuloId)

        //Then
        coVerify { articuloRemoteDataSource.getArticuloById(articulo.articuloId) }
    }

    @Test
    fun `Should delete an prioridad`() = runTest {
        //Given
        val articulo = ArticuloDto(
            articuloId = 1,
            descripcion = "Articulo 1",
            costo = 10.0,
            ganancia = 20.0,
            precio = 30.0
        )

        val articuloRemoteDataSource = mockk<RemoteDataSource>()
        val artculoDao = mockk<ArticuloDao>()
        val repository = ArticuloRepository(artculoDao, articuloRemoteDataSource)

        coEvery { articuloRemoteDataSource.deleteArticulo(articulo.articuloId) } returns Response.success(
            Unit
        )

        //When
        repository.delete(articulo.articuloId)

        //Then
        coVerify { articuloRemoteDataSource.deleteArticulo(articulo.articuloId) }
    }
    @Test
    fun `Should update an prioridad`() = runTest {
        //Given
        val articulo = ArticuloDto(
            articuloId = 1,
            descripcion = "Articulo 3",
            costo = 10.0,
            ganancia = 20.0,
            precio = 30.0
        )

        val articuloRemoteDataSource = mockk<RemoteDataSource>()
        val articuloDao = mockk<ArticuloDao>()
        val repository = ArticuloRepository(articuloDao, articuloRemoteDataSource)

        coEvery {
            articuloRemoteDataSource.updateArticulo(
                articulo.articuloId, articulo
            )
        } returns Response.success(articulo)

        //When
        repository.update(articulo.articuloId, articulo)

        //Then
        coVerify { articuloRemoteDataSource.updateArticulo(articulo.articuloId, articulo) }
    }

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
    }
    @Test
    fun `Should return a flow of prioridades`() = runTest {
        // Given
        val articulos = listOf(
            ArticuloEntity(
                1,
                "Articulo 2",
                500.0,
                100.0,
                600.0
            )
        )

        val articuloRemoteDataSource = mockk<RemoteDataSource>()
        val articuloDao = mockk<ArticuloDao>()
        val repository = ArticuloRepository(articuloDao, articuloRemoteDataSource)

        coEvery { articuloDao.getAll() } returns flowOf(articulos)

        //When
        repository.getAllArticulos().test {
            //Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            Truth.assertThat(awaitItem().data).isEqualTo(articulos)

            cancelAndIgnoreRemainingEvents()
        }
    }
}


