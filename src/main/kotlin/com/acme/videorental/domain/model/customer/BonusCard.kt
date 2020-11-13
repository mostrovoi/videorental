package com.acme.videorental.domain.model.customer

import com.acme.videorental.domain.model.common.FilmTypeEnum

data class BonusCard(val bonusPoints: Int = 0) {

    companion object {

        fun updateBonusPoints(bonusCard: BonusCard, filmType: FilmTypeEnum): BonusCard {
            val bonusPoints = getBonusPointsFromMovieType(filmType)
            val totalPoints = bonusCard.bonusPoints + bonusPoints
            return BonusCard(bonusPoints = totalPoints)
        }

        private fun getBonusPointsFromMovieType(type: FilmTypeEnum): Int {
            return when (type) {
                FilmTypeEnum.NEW -> 2
                FilmTypeEnum.REGULAR, FilmTypeEnum.OLD -> 1
            }
        }
    }

}