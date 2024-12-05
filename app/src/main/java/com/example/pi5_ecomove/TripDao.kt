package com.example.pi5_ecomove

import androidx.room.*

@Dao
interface TripDao {

    // Inserir uma nova corrida
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrip(trip: TripModel)

    // Atualizar uma corrida existente
    @Update
    fun updateTrip(trip: TripModel)

    // Deletar uma corrida específica
    @Delete
    fun deleteTrip(trip: TripModel)

    // Buscar todas as corridas
    @Query("SELECT * FROM viagens")
    fun getAllTrips(): List<TripModel>

    // Buscar uma corrida pelo ID
    @Query("SELECT * FROM viagens WHERE id = :tripId")
    fun getTripById(tripId: Int): TripModel
}
